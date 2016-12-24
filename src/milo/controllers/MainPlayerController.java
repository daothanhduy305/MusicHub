package milo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.Window;
import milo.controllers.abstractcontrollers.AbstractPlayerUIController;
import milo.controllers.utils.LOG;
import milo.data.AlbumData;
import milo.data.SongData;
import milo.gui.utils.SettingsFactory;
import milo.gui.utils.SizeCalculator;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Class name:  MainPlayerController
 * Description: This is the controllers for the whole Music Hub
 *              Every magic will happen here.
 */

public class MainPlayerController extends AbstractPlayerUIController {
    /**
     * Start of the variable declarations
     * First is the section for @FXML variables, which are corresponded to the UI element with the same name <-> fx:id
     */
    @FXML private SongPlayerBarController songPlayerBarController;
    @FXML private MainViewPanelController mainViewPanelController;
    @FXML private NavigationDrawerController navigationDrawerController;
    @FXML private GridPane mHolder;

    private SizeCalculator sizeCalculator;
    private Window mainWindow;

    private SettingsFactory settingsFactory;
    private SettingsController settingsController;
    private Stage settingsStage;
    private FXMLLoader settingsLoader;
    private Scene settingsScene;

    @Override
    public void buildUI() {
        settingsFactory = new SettingsFactory(this);

        songPlayerBarController.buildUI();
        navigationDrawerController.buildUI();
        mainViewPanelController.buildUI();

        refreshUI();
    }

    @Override
    public void refreshUI() {
        sizeCalculator.calibrate();

        songPlayerBarController.refreshUI();
        mainViewPanelController.refreshUI();
        navigationDrawerController.refreshUI();
    }

    @Override
    public void playSong(SongData songData) {
        if (songData != null && !songData.getTitle().equals(" ")) {
            settingsFactory.setPlayingSong(songData);
            this.currentPlayingSong = songData;
            this.stopPlaying();
            try {
                File songFile = new File(songData.getPath());
                AudioFile audioFile = AudioFileIO.read(songFile);
                isPlaying = true;
                player = new MediaPlayer(new Media(songFile.toURI().toURL().toExternalForm()));
                songPlayerBarController.setupForPlayingMusic(audioFile);
                player.play();
                // TODO: check whether we reach the end of the list? If so, call to stop instead of pause
                player.setOnEndOfMedia(this::playNextSong);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void pausePlaying() {
        if (player != null) {
            isPlaying = false;
            songPlayerBarController.stopPlaying(false);
            player.pause();
            songPlayerBarController.getPlayButton().replaceButName("play");
            songPlayerBarController.getPlayButton().setOnMouseClicked(event -> resumePlaying());
        }
    }

    @Override
    public void stopPlaying() {
        if (player != null) {
            isPlaying = false;
            player.stop();
            player.dispose();
        }
        songPlayerBarController.stopPlaying(true);
    }

    @Override
    public void resumePlaying() {
        if (player != null) {
            isPlaying = true;
            songPlayerBarController.resumePlaying();
            player.play();
            songPlayerBarController.getPlayButton().replaceButName("pause");
            songPlayerBarController.getPlayButton().setOnMouseClicked(event -> pausePlaying());
        }
    }

    @Override
    public void playNextSong() {
        if (currentPlaylist != null && currentPlaylist.size() > 0) {
            previousPlaylist.add(0, currentPlayingSong);
            currentPlayingSong = currentPlaylist.get(0);
            /*if (settingsFactory.getRepeatModeStatus())
                currentPlaylist.add(currentPlaylist.get(0));*/
            currentPlaylist.remove(0);
            mainViewPanelController.selectCurrentSong(currentPlayingSong);
            playSong(currentPlayingSong);
        }
    }

    @Override
    public void playPreviousSong() {
        if (previousPlaylist != null && previousPlaylist.size() > 0) {
            currentPlaylist.add(0, currentPlayingSong);
            currentPlayingSong = previousPlaylist.get(0);
            previousPlaylist.remove(0);
            mainViewPanelController.selectCurrentSong(currentPlayingSong);
            playSong(currentPlayingSong);
        }
    }

    /**
     * Function name:   setScene
     * Usage:   this method would be called to set the main scene for refreshUI() to work properly. This also pass the
     *          sizeCalculator to children controllers.
     *
     * @param scene main scene from Main
     */
    public void setScene(Scene scene) {
        sizeCalculator = new SizeCalculator(scene);

        scene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> refreshUI());

        songPlayerBarController.setSizeCalculator(sizeCalculator);
        mainViewPanelController.setSizeCalculator(sizeCalculator);
        navigationDrawerController.setSizeCalculator(sizeCalculator);
    }

    public MainViewPanelController getMainViewPanelController() {
        return mainViewPanelController;
    }

    /**
     * Function name:   setDB
     * Usage:   this method would be called to set database and call lower-level relevant methods to set data for also
     *          children views.
     * @param songDatas database for songs
     * @param albumDataMap database for albums
     */
    public void setDB(Map<String, SongData> songDatas, Map<String, AlbumData> albumDataMap) {
        mainViewPanelController.setDB(songDatas, albumDataMap);
        refreshUI();
    }

    public void showSettings() {
        if (settingsLoader == null) {
            settingsLoader = new FXMLLoader();
            settingsLoader.setLocation(getClass().getResource("/milo/gui/designs/settings.fxml"));
            try {
                settingsScene = new Scene(settingsLoader.load(), 600.0, 500.0);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
            settingsController = settingsLoader.getController();
            settingsController.setSettingsFactory(settingsFactory);
            settingsController.buildUI();

            settingsStage = new Stage();
            settingsController.setSettingsWindow(settingsStage);
            settingsStage.initOwner(mainWindow);
            settingsStage.setResizable(false);
            settingsStage.setTitle("Music Hub - Settings");
            settingsStage.setScene(settingsScene);

            settingsFactory.setSettingsController(settingsController);

            LOG.w("New settings window started");
        }

        settingsStage.show();
    }

    public void setMainWindow(Window mainWindow) {
        this.mainWindow = mainWindow;
    }

    public SettingsFactory getSettingsFactory() {
        return settingsFactory;
    }
}

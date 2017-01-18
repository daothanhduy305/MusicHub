package milo.gui.controllers;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import milo.data.SongData;
import milo.gui.controllers.abstractcontrollers.AbstractPlayerUIController;
import milo.gui.controllers.utils.LOG;
import milo.gui.custom.AlbumTile;
import milo.gui.utils.SettingsFactory;
import milo.gui.utils.SizeCalculator;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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
    @FXML private StackPane loadingPane, mainViewPanel;
    @FXML private Pane settingsBg;

    private SizeCalculator sizeCalculator;
    private Window mainWindow;
    private Scene mainScene;

    volatile private boolean isLoading = true;
    private boolean isSongLoading = true, isAlbumLoading = true;

    private SettingsFactory settingsFactory;
    private SettingsController settingsController;
    private Stage settingsStage;
    private FXMLLoader settingsLoader;
    private Scene settingsScene;

    private ObservableList<SongData> songDataObservableList;
    private ObservableList<AlbumTile> albumTileObservableList;

    @Override
    public void buildUI() {
        settingsFactory = new SettingsFactory(this);
        startLoading();

        navigationDrawerController.buildUI();
        new Thread(() -> songPlayerBarController.buildUI()).start();
        new Thread(() -> mainViewPanelController.buildUI()).start();

        settingsFactory.loadSettings();
    }

    @Override
    public void postBuildUI() {
        songPlayerBarController.postBuildUI();
        //navigationDrawerController.postBuildUI();
        //mainViewPanelController.postBuildUI();

        mainScene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> refreshUI());
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
            if (settingsFactory.isRepeat())
                currentPlaylist.add(currentPlaylist.get(0));
            currentPlaylist.remove(0);
            mainViewPanelController.selectCurrentSong(currentPlayingSong);
            playSong(currentPlayingSong);
        }
    }

    @Override
    public void playPreviousSong() {
        if (isPlaying && player.getCurrentTime().greaterThanOrEqualTo(new Duration(5000)))
            playSong(currentPlayingSong);
        else
            if (previousPlaylist != null && previousPlaylist.size() > 0) {
                currentPlaylist.add(0, currentPlayingSong);
                currentPlayingSong = previousPlaylist.get(0);
                previousPlaylist.remove(0);
                mainViewPanelController.selectCurrentSong(currentPlayingSong);
                playSong(currentPlayingSong);
            }
    }

    /**
     * Function name:   buildPlaylist
     * Usage:   this method would be called to build new playlist when needed
     */
    public void buildPlaylist() {
        if (viewId != null) {
            mainViewPanelController.buildPlaylist();
        }
    }

    /**
     * Function name:   buildPlaylist
     * Usage:   this method would be called to build playlist when called
     */
    public void buildPlaylist(TableView<SongData> songListTable) {
        int id = songListTable.getItems().indexOf(currentPlayingSong);
        currentPlaylist = new ArrayList<>(1);
        previousPlaylist = new ArrayList<>(1);
        (new Thread(() -> {
            if (!getSettingsFactory().getRepeatStr().equalsIgnoreCase("repeat_one")) {
                for (int i = id + 1; i < songListTable.getItems().size(); i++) {
                    currentPlaylist.add(songListTable.getItems().get(i));
                }
                for (int i = 0; i < id; i++) {
                    currentPlaylist.add(songListTable.getItems().get(i));
                }

                if (getSettingsFactory().isShuffle())
                    Collections.shuffle(currentPlaylist);
            }

            if (settingsFactory.isRepeat())
                currentPlaylist.add(songListTable.getItems().get(id));
        })).start();
    }

    /**
     * Function name:   setScene
     * Usage:   this method would be called to set the main scene for refreshUI() to work properly. This also pass the
     *          sizeCalculator to children controllers.
     *
     * @param scene main scene from Main
     */
    public void setScene(Scene scene) {
        this.mainScene = scene;
        sizeCalculator = new SizeCalculator(scene);

        songPlayerBarController.setSizeCalculator(sizeCalculator);
        mainViewPanelController.setSizeCalculator(sizeCalculator);
        navigationDrawerController.setSizeCalculator(sizeCalculator);
    }

    /**
     * Function name:   setDB
     * Usage:   this method would be called to set database and call lower-level relevant methods to set data for also
     *          children views.
     */
    public void setDB() {
        songDataObservableList = FXCollections.observableArrayList();
        albumTileObservableList = FXCollections.observableArrayList();

        new Thread(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                mainViewPanelController.setDB();
                return null;
            }
        }).start();

        new Thread(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                settingsFactory.getSettingsData().getSongDatas().values().forEach(songData -> mainViewPanelController.addSong(songData));
                setSongLoading(false);
                LOG.w("Finished loading songs");
                LOG.w("Current time is: " + System.currentTimeMillis());
                return null;
            }
        }).start();

        settingsFactory.getSettingsData().getAlbumDataMap().values().forEach(
                albumData -> Platform.runLater(() -> albumTileObservableList.add(new AlbumTile(albumData)))
        );
        setAlbumLoading(false);
        LOG.w("Finished loading albums");
        LOG.w("Current time is: " + System.currentTimeMillis());
    }

    public void showSettingsWindow() {
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
            settingsController.setMainPlayerController(this);
            settingsController.buildUI();

            settingsStage = new Stage();
            settingsStage.initStyle(StageStyle.UNDECORATED);
            settingsController.setSettingsWindow(settingsStage);
            settingsStage.initOwner(mainWindow);
            settingsStage.setResizable(false);
            settingsStage.setTitle("Music Hub - Settings");
            settingsStage.setScene(settingsScene);

            settingsFactory.setSettingsController(settingsController);

            LOG.w("New settings window started");
        }

        settingsStage.show();
        settingsBg.setVisible(true);
    }

    public void hideSettingsWindow() {
        settingsStage.hide();
        settingsBg.setVisible(false);
    }

    private void startLoading() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setLoadingState();
                if (!isLoading) {
                    Platform.runLater(() -> {
                        FadeTransition loadingPaneFadeAnim = new FadeTransition(Duration.millis(500), loadingPane);
                        loadingPaneFadeAnim.setFromValue(1.0);
                        loadingPaneFadeAnim.setToValue(0.0);
                        loadingPaneFadeAnim.setOnFinished(event -> loadingPane.setVisible(false));
                        loadingPaneFadeAnim.play();

                        postBuildUI();
                        refreshUI();

                        //ParallelTransition loadingEndAnims = new ParallelTransition();
                        LOG.w("Finished loading everything");
                        LOG.w("Current time is: " + System.currentTimeMillis());
                        //loadingPane.setVisible(false);

                        /*FadeTransition mainPanelAppearAnim = new FadeTransition(Duration.millis(1000), mainViewPanel);
                        mainPanelAppearAnim.setFromValue(0.5);
                        mainPanelAppearAnim.setToValue(1.0);

                        TranslateTransition mainPanelSlideAnim = new TranslateTransition(Duration.millis(1000), mainViewPanel);
                        *//*mainPanelSlideAnim.setFromX(0.5);
                        mainPanelSlideAnim.setToX(1.0);*//*
                        mainPanelSlideAnim.setFromY(0.5);
                        mainPanelSlideAnim.setToY(1.0);

                        loadingEndAnims.getChildren().addAll(mainPanelAppearAnim, mainPanelSlideAnim);
                        //loadingEndAnims.setOnFinished(event -> loadingPane.setVisible(false));
                        loadingEndAnims.play();*/
                    });
                    break;
                }
            }
        }).start();
    }

    /**
     * Function name:   showAlbumsView
     * Usage:   this method would be called to set the mainViewPanel to display AlbumsView
     *
     * @param albumKey the key for the album in map
     */
    void showAlbum(String albumKey) {
        mainViewPanelController.showAlbum(albumKey);
    }

    public void setMainWindow(Window mainWindow) {
        this.mainWindow = mainWindow;
    }

    public SettingsFactory getSettingsFactory() {
        return settingsFactory;
    }

    public void setLoadingState() {
        isLoading = (isSongLoading | isAlbumLoading);
    }

    public void setAlbumLoading(boolean albumLoading) {
        isAlbumLoading = albumLoading;
    }

    public void setSongLoading(boolean songLoading) {
        isSongLoading = songLoading;
    }

    public ObservableList<SongData> getSongDataObservableList() {
        return songDataObservableList;
    }

    public ObservableList<AlbumTile> getAlbumTileObservableList() {
        return albumTileObservableList;
    }

    public boolean isSongLoading() {
        return isSongLoading;
    }
}

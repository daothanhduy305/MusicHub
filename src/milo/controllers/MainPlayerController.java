package milo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import milo.controllers.abstractcontrollers.AbstractPlayerUIController;
import milo.data.AlbumData;
import milo.data.SongData;
import milo.gui.utils.SettingsFactory;
import milo.gui.utils.SizeCalculator;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import java.io.File;
import java.util.List;
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
    @FXML private SongPlayerController songPlayerController;
    @FXML private MainViewPanelController mainViewPanelController;
    @FXML private NavigationDrawerController navigationDrawerController;
    @FXML private GridPane mHolder;

    private ObservableList<SongData> songDatas;
    private SizeCalculator sizeCalculator;
    private SettingsFactory settingsFactory;

    @Override
    public void buildUI() {
        songPlayerController.buildUI();
        navigationDrawerController.buildUI();
        mainViewPanelController.buildUI();

        settingsFactory = new SettingsFactory(this);
    }

    @Override
    public void refreshUI() {
        sizeCalculator.calibrate();

        mHolder.setPrefHeight(sizeCalculator.getWindowHeight());

        songPlayerController.refreshUI();
        mainViewPanelController.refreshUI();
        navigationDrawerController.refreshUI();
    }

    @Override
    public void playSong(SongData songData) {
        settingsFactory.setPlayingSong(songData);
        this.currentPlayingSong = songData;
        this.stopPlaying();
        try {
            File songFile = new File(songData.getPath());
            AudioFile audioFile = AudioFileIO.read(songFile);
            isPlaying = true;
            player = new MediaPlayer(new Media(songFile.toURI().toURL().toExternalForm()));
            songPlayerController.setupForPlayingMusic(audioFile);
            player.play();
            // TODO: check whether we reach the end of the list? If so, call to stop instead of pause
            player.setOnEndOfMedia(this::playNextSong);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pausePlaying() {
        if (player != null) {
            isPlaying = false;
            songPlayerController.stopPlaying(false);
            player.pause();
            songPlayerController.getPlayButton().replaceButName("play");
            songPlayerController.getPlayButton().setOnMouseClicked(event -> resumePlaying());
        }
    }

    @Override
    public void stopPlaying() {
        if (player != null) {
            isPlaying = false;
            player.stop();
            player.dispose();
        }
        songPlayerController.stopPlaying(true);
    }

    @Override
    public void resumePlaying() {
        if (player != null) {
            isPlaying = true;
            songPlayerController.resumePlaying();
            player.play();
            songPlayerController.getPlayButton().replaceButName("pause");
            songPlayerController.getPlayButton().setOnMouseClicked(event -> pausePlaying());
        }
    }

    @Override
    public void playNextSong() {
        if (currentPlaylist != null && currentPlaylist.size() > 0) {
            previousPlaylist.add(0, currentPlayingSong);
            currentPlayingSong = currentPlaylist.get(0);
            if (settingsFactory.getRepeatModeStatus())
                currentPlaylist.add(currentPlaylist.get(0));
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

        songPlayerController.setSizeCalculator(sizeCalculator);
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
    public void setDB(List<SongData> songDatas, Map<String, AlbumData> albumDataMap) {
        this.songDatas = FXCollections.observableArrayList(songDatas);

        mainViewPanelController.setDB(this.songDatas, albumDataMap);
        refreshUI();
    }
}

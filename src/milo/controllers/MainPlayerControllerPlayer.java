package milo.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import milo.controllers.abstractcontrollers.AbstractPlayerUIController;
import milo.data.SongData;
import milo.gui.utils.SizeCalculator;

import java.util.List;

/**
 * Class name:  MainPlayerControllerPlayer
 * Description: This is the controllers for the whole Music Hub
 *              Every magic will happen here.
 */

public class MainPlayerControllerPlayer extends AbstractPlayerUIController {
    /**
     * Start of the variable declarations
     * First is the section for @FXML variables, which are corresponded to the UI element with the same name <-> fx:id
     */
    @FXML private SongPlayerController songPlayerController;
    @FXML private AllSongsViewController allSongsViewController;
    @FXML private AlbumsViewController albumsViewController;
    @FXML private NavigationDrawerController navigationDrawerController;
    @FXML private StackPane mainPlayerHolder; // Serve the show/hide views method

    private ObservableList<SongData> songDatas; // TODO: make this into settings class instead
    private List<SongData> currentPlaylist, previousList;
    private SongData currentPlayingSong;
    private MediaPlayer mPlayer;
    private volatile boolean isPlaying = false, isShuffle = false, isRepeat = false; // TODO: make this into settings class instead

    /**
     * Function name:   initPlayer
     * Usage:   this function is called to construct the whole Music Hub UI
     * TODO: implement the method
     */
    public void initPlayer() {
        this.buildUI(); // ofc
    }

    @Override
    public void buildUI() {

    }

    @Override
    public void refreshUI() {

    }

    @Override
    public void playSong(SongData songData) {

    }

    @Override
    public void pausePlaying() {

    }

    @Override
    public void resumePlaying() {

    }

    @Override
    public void playNextSong() {

    }

    @Override
    public void playPreviousSong() {

    }

    public void setScene(Scene scene) {
        SizeCalculator sizeCalculator = new SizeCalculator(scene);

        songPlayerController.setSizeCalculator(sizeCalculator);
        allSongsViewController.setSizeCalculator(sizeCalculator);
        albumsViewController.setSizeCalculator(sizeCalculator);
        navigationDrawerController.setSizeCalculator(sizeCalculator);

        albumsViewController.setScene(scene);
    }
}

package milo.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import milo.controllers.abstractcontrollers.AbstractPlayerUIController;
import milo.data.SongData;
import milo.gui.utils.SizeCalculator;

import java.util.List;

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
    @FXML
    private MainViewPanelController mainViewPanelController;
    @FXML
    private NavigationDrawerController navigationDrawerController;
    @FXML
    private GridPane mHolder;

    private ObservableList<SongData> songDatas; // TODO: make this into settings class instead
    private List<SongData> currentPlaylist, previousList;
    private boolean isShuffle = false, isRepeat = false; // TODO: make this into settings class instead
    private SizeCalculator sizeCalculator;

    @Override
    public void buildUI() {
        songPlayerController.buildUI();
        navigationDrawerController.buildUI();
        mainViewPanelController.buildUI();
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

    /**
     * Function name:   setScene
     * Usage:   this method would be called to set the main scene for refreshUI() to work properly. This also pass the
     * sizeCalculator to children controllers.
     *
     * @param scene main scene from Main
     */
    public void setScene(Scene scene) {
        sizeCalculator = new SizeCalculator(scene);

        songPlayerController.setSizeCalculator(sizeCalculator);
        mainViewPanelController.setSizeCalculator(sizeCalculator);
        navigationDrawerController.setSizeCalculator(sizeCalculator);
    }

    public ObservableList<SongData> getSongDatas() {
        return songDatas;
    }
}

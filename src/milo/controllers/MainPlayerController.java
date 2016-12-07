package milo.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
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
    private MainPanelController mainPanelController;

    private ObservableList<SongData> songDatas; // TODO: make this into settings class instead
    private List<SongData> currentPlaylist, previousList;
    private boolean isShuffle = false, isRepeat = false; // TODO: make this into settings class instead

    @Override
    public void buildUI() {
        songPlayerController.buildUI();
    }

    @Override
    public void refreshUI() {
        songPlayerController.refreshUI();
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
        mainPanelController.setSizeCalculator(sizeCalculator);
    }
}

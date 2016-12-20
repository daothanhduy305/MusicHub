package milo.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import milo.controllers.abstractcontrollers.AbstractSubUIController;
import milo.data.AlbumData;
import milo.data.SongData;
import milo.gui.utils.SizeCalculator;

import java.util.Map;

/**
 * Class name:  MainViewPanelController
 * Description: This is the controllers for the main panel, which consists of all the views (all songs view, albums,...
 */

public class MainViewPanelController extends AbstractSubUIController {
    @FXML private AllSongsViewController allSongsViewController;
    @FXML private AlbumsViewController albumsViewController;
    @FXML private StackPane mHolder;
    @FXML private NavigationDrawerController navigationDrawerController;

    @Override
    public void buildUI() {
        allSongsViewController.buildUI();
        albumsViewController.buildUI();

        showAllSongsView();
    }

    @Override
    public void refreshUI() {
        /*mHolder.setPrefWidth(sizeCalculator.getMainViewPanelWidth());
        mHolder.setPrefHeight(sizeCalculator.getWindowHeight());*/

        allSongsViewController.refreshUI();
        albumsViewController.refreshUI();
    }

    @Override
    public void setSizeCalculator(SizeCalculator sizeCalculator) {
        super.setSizeCalculator(sizeCalculator);

        albumsViewController.setSizeCalculator(sizeCalculator);
        allSongsViewController.setSizeCalculator(sizeCalculator);
    }

    /**
     * Function name:   showAllSongsView
     * Usage:   this method would be called to set the mainViewPanel to display AllSongsView
     */
    void showAllSongsView() {
        mHolder.getChildren().get(0).setVisible(true);
        mHolder.getChildren().get(1).setVisible(false);

        navigationDrawerController.enableAllSongsViewButton();
    }

    /**
     * Function name:   showAlbumsView
     * Usage:   this method would be called to set the mainViewPanel to display AlbumsView
     */
    void showAlbumsView() {
        mHolder.getChildren().get(1).setVisible(true);
        mHolder.getChildren().get(0).setVisible(false);

        navigationDrawerController.enableAlbumsViewButton();
        albumsViewController.showAlbumOverview();
    }

    /**
     * Function name:   showAlbumsView
     * Usage:   this method would be called to set the mainViewPanel to display AlbumsView
     */
    void showAlbum(String albumTitle) {
        mHolder.getChildren().get(1).setVisible(true);
        mHolder.getChildren().get(0).setVisible(false);

        navigationDrawerController.enableAlbumsViewButton();
        albumsViewController.showAlbum(albumTitle);
    }

    /**
     * Function name:   selectCurrentSong
     * Usage:   this function would be called to select the current playing song in the table(s)
     */
    void selectCurrentSong(SongData songData) {
        allSongsViewController.getSongListTable().getSelectionModel().select(songData);
        albumsViewController.selectCurrentSong(songData);
    }

    /**
     * Function name:   setDB
     * Usage:   this method would be called to set database
     *
     * @param songDatas database for songs
     * @param albumDataMap database for albums
     */
    void setDB(ObservableList<SongData> songDatas, Map<String, AlbumData> albumDataMap) {
        allSongsViewController.setDB(songDatas);
        albumsViewController.setDB(albumDataMap);
    }

    public void setNavigationDrawerController(NavigationDrawerController navigationDrawerController) {
        this.navigationDrawerController = navigationDrawerController;
    }
}

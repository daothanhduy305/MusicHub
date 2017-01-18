package milo.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import milo.data.SongData;
import milo.gui.controllers.abstractcontrollers.AbstractSubUIController;
import milo.gui.utils.SizeCalculator;

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
        new Thread(() -> allSongsViewController.buildUI()).start();
        new Thread(() -> albumsViewController.buildUI()).start();

        showAllSongsView();
    }

    @Override
    public void refreshUI() {
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
     *
     * @param albumKey the key for the album in map
     */
    void showAlbum(String albumKey) {
        mHolder.getChildren().get(1).setVisible(true);
        mHolder.getChildren().get(0).setVisible(false);

        navigationDrawerController.enableAlbumsViewButton();
        albumsViewController.showAlbum(albumKey);
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
     * Function name:   buildPlaylist
     * Usage:   this method would be called to build new playlist when needed
     */
    public void buildPlaylist() {
        switch (mainPlayerController.getViewId()) {
            case ALL_SONGS:
                allSongsViewController.buildPlaylist();
                break;
            case ALBUMS:
                albumsViewController.buildPlaylist();
                break;
        }
    }

    /**
     * Function name:   setDB
     * Usage:   this method would be called to set database
     */
    void setDB() {
        allSongsViewController.setDB();
        albumsViewController.setDB();
    }

    /**
     * Function name:   addSong
     * Usage:   this method should be call whenever the albumList is updated with operation add
     * @param songData new Album to add in the list
     */
    void addSong(SongData songData) {
        allSongsViewController.addSong(songData);
    }

    public void setNavigationDrawerController(NavigationDrawerController navigationDrawerController) {
        this.navigationDrawerController = navigationDrawerController;
    }

    @Override
    public void setMainPlayerController(MainPlayerController mainPlayerController) {
        super.setMainPlayerController(mainPlayerController);

        allSongsViewController.setMainPlayerController(mainPlayerController);
        albumsViewController.setMainPlayerController(mainPlayerController);
    }
}

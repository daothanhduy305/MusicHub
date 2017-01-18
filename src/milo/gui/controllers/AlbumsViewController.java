package milo.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import milo.data.AlbumData;
import milo.data.SongData;
import milo.gui.controllers.abstractcontrollers.AbstractSubUIController;
import milo.gui.utils.SizeCalculator;

/**
 * Class name:  AlbumsViewController
 * Description: This controller will control the albums views, consists of Overview (All albums) and Specific view
 *              (specific album)
 */

public class AlbumsViewController extends AbstractSubUIController {
    @FXML private AlbumsViewOverviewController albumsViewOverviewController;
    @FXML private AlbumsViewSpecificController albumsViewSpecificController;
    @FXML private StackPane mHolder;

    @Override
    public void buildUI() {
        new Thread(() -> albumsViewOverviewController.buildUI()).start();
        new Thread(() -> albumsViewSpecificController.buildUI()).start();
    }

    @Override
    public void refreshUI() {
        albumsViewOverviewController.refreshUI();
        albumsViewSpecificController.refreshUI();
    }

    /**
     * Function name:   showAlbumOverview
     * Usage:   this function is called to change the AlbumView back to Overview
     */
    void showAlbumOverview() {
        mHolder.getChildren().get(0).setVisible(true);
        mHolder.getChildren().get(1).setVisible(false);
    }

    /**
     * Function name:   showAlbum
     * Usage:   this function is called display the view for a specific album
     * this also calls the lower level relevant method from albumsViewSpecificController
     *
     * @param albumData the data of the album to be displayed
     */
    void showAlbum(AlbumData albumData) {
        mHolder.getChildren().get(1).setVisible(true);
        mHolder.getChildren().get(0).setVisible(false);

        albumsViewSpecificController.showAlbum(albumData);
    }

    /**
     * Function name:   showAlbum
     * Usage:   this function is called display the view for a specific album
     * this also calls the lower level relevant method from albumsViewSpecificController
     *
     * @param albumKey the key for the album in map
     */
    void showAlbum(String albumKey) {
        mHolder.getChildren().get(1).setVisible(true);
        mHolder.getChildren().get(0).setVisible(false);

        albumsViewSpecificController.showAlbum(
                mainPlayerController.getSettingsFactory().getSettingsData().getAlbumDataMap().get(albumKey)
        );
    }

    /**
     * Function name:   buildPlaylist
     * Usage:   this method would be called to build new playlist when needed
     */
    public void buildPlaylist() {
        albumsViewSpecificController.buildPlaylist();
    }

    /**
     * Function name:   setDB
     * Usage:   this method would be called to set database
     */
    void setDB() {
        albumsViewOverviewController.setDB();
    }

    @Override
    public void setMainPlayerController(MainPlayerController mainPlayerController) {
        super.setMainPlayerController(mainPlayerController);

        albumsViewOverviewController.setMainPlayerController(mainPlayerController);
        albumsViewSpecificController.setMainPlayerController(mainPlayerController);
    }

    /**
     * Function name:   selectCurrentSong
     * Usage:   this function would be called to select the current playing song in the table(s)
     */
    void selectCurrentSong(SongData songData) {
        if (albumsViewSpecificController.getSongListTable() != null)
            albumsViewSpecificController.getSongListTable().getSelectionModel().select(songData);
    }

    @Override
    public void setSizeCalculator(SizeCalculator sizeCalculator) {
        super.setSizeCalculator(sizeCalculator);

        albumsViewOverviewController.setSizeCalculator(sizeCalculator);
        albumsViewSpecificController.setSizeCalculator(sizeCalculator);
    }
}

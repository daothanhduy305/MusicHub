package milo.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import milo.controllers.abstractcontrollers.AbstractSubUIController;
import milo.data.AlbumData;

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
        albumsViewOverviewController.buildUI();
        albumsViewSpecificController.buildUI();
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
    public void showAlbumOverview() {
        mHolder.getChildren().get(0).setVisible(true);
        mHolder.getChildren().get(1).setVisible(true);
    }

    /**
     * Function name:   showAlbum
     * Usage:   this function is called display the view for a specific album
     * this also calls the lower level relevant method from albumsViewSpecificController
     *
     * @param albumData the data of the album to be displayed
     */
    public void showAlbum(AlbumData albumData) {
        mHolder.getChildren().get(1).setVisible(true);
        mHolder.getChildren().get(0).setVisible(true);

        albumsViewSpecificController.showAlbum(albumData);
    }
}

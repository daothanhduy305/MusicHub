package milo.controllers;

import javafx.fxml.FXML;
import milo.controllers.abstractcontrollers.AbstractSubUIController;

/**
 * Class name:  AlbumsViewController
 * Description: This controller will control the albums views, consists of Overview (All albums) and Specific view
 *              (specific album)
 */

public class AlbumsViewController extends AbstractSubUIController {
    @FXML
    AlbumsViewOverviewController albumsViewOverviewController;
    @FXML
    AlbumsViewSpecificController albumsViewSpecificController;

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
}

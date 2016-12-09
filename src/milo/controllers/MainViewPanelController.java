package milo.controllers;

import javafx.fxml.FXML;
import milo.controllers.abstractcontrollers.AbstractSubUIController;

/**
 * Class name:  MainViewPanelController
 * Description: This is the controllers for the main panel, which consists of all the views (all songs view, albums,...
 */

public class MainViewPanelController extends AbstractSubUIController {
    @FXML
    AllSongsViewController allSongsViewController;
    @FXML
    AlbumsViewController albumsViewController;

    @Override
    public void buildUI() {
        allSongsViewController.buildUI();
        albumsViewController.buildUI();
    }

    @Override
    public void refreshUI() {
        allSongsViewController.refreshUI();
        albumsViewController.refreshUI();
    }
}

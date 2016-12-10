package milo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import milo.controllers.abstractcontrollers.AbstractSubUIController;
import milo.gui.custom.NavigationViewButton;
import milo.gui.utils.Constants;

/**
 * Class name:  NavigationDrawerController
 * Description: This controller will control the navigation drawer, which consists of buttons for switching views, as
 *              well as playlist(s), settings, and maybe search bar
 */

public class NavigationDrawerController extends AbstractSubUIController {
    @FXML
    private ScrollPane mHolder;
    @FXML
    private VBox buttonBox;

    private NavigationViewButton allSongViewButton;
    private NavigationViewButton albumsViewButton;
    private NavigationViewButton artistsViewButton;

    private MainViewPanelController mainViewPanelController;

    @Override
    public void buildUI() {
        albumsViewButton = new NavigationViewButton("Albums", sizeCalculator);
        allSongViewButton = new NavigationViewButton("Songs", sizeCalculator);
        buttonBox.getChildren().addAll(allSongViewButton, albumsViewButton);
        buttonBox.setSpacing(Constants.getNavigationDrawerPaddingV());

        makeButtonFunctional();
    }

    @Override
    public void refreshUI() {
        mHolder.setPrefWidth(sizeCalculator.getNavigationDrawerWidth());
        mHolder.setPrefHeight(sizeCalculator.getWindowHeight());

        albumsViewButton.setPrefWidth(mHolder.getPrefWidth());
        allSongViewButton.setPrefWidth(mHolder.getPrefWidth());
    }

    /**
     * Function name:   makeButtonFunctional
     * Usage:   this method would be called to set function to the navigation buttons that were manually added
     */
    private void makeButtonFunctional() {
        allSongViewButton.setOnMouseEntered(event -> allSongViewButton.setBackground(Constants.getBgGrayer()));
        allSongViewButton.setOnMouseExited(event -> allSongViewButton.setBackground(null));
        allSongViewButton.setOnMouseClicked(event -> mainViewPanelController.showAllSongsView());

        albumsViewButton.setOnMouseEntered(event -> albumsViewButton.setBackground(Constants.getBgGrayer()));
        albumsViewButton.setOnMouseExited(event -> albumsViewButton.setBackground(null));
        albumsViewButton.setOnMouseClicked(event -> mainViewPanelController.showAlbumsView());
    }

    /**
     * Function name:   enableAllSongsViewButton
     * Usage:   this method would be called to make the allSongsViewButton glow
     */
    public void enableAllSongsViewButton() {
        allSongViewButton.makeActive();
        albumsViewButton.makeInactive();
    }

    /**
     * Function name:   enableAlbumsViewButton
     * Usage:   this method would be called to make the albumsViewButton glow
     */
    public void enableAlbumsViewButton() {
        allSongViewButton.makeInactive();
        albumsViewButton.makeActive();
    }

    public void setMainViewPanelController(MainViewPanelController mainViewPanelController) {
        this.mainViewPanelController = mainViewPanelController;
    }
}

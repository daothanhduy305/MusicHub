package milo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import milo.gui.custom.PathAddButton;
import milo.gui.custom.PathTile;
import milo.gui.utils.SettingsFactory;

import java.io.File;
import java.util.Optional;

/**
 * Class name:  SettingsController
 * Description: This controller controls the Settings window
 */
public class SettingsController {
    @FXML private GridPane pathsGrid;

    private SettingsFactory settingsFactory;
    private Window settingsWindow;
    private PathAddButton pathAddButton;

    private int item = 1;

    public void buildUI() {
        pathAddButton = new PathAddButton();
        pathAddButton.setOnMouseClicked(event -> addPath());
        pathsGrid.add(pathAddButton, 0, 0);
        if (settingsFactory.getSettingsData().getPathList() != null && settingsFactory.getSettingsData().getPathList().size() > 0) {
            for (String path : settingsFactory.getSettingsData().getPathList()) {
                addPathTile(path);
            }
        }
    }

    /**
     * Function name:   addPath
     * Usage:   This method will call a directory chooser to let the user choose the directory that contains music
     */
    private void addPath() {
        DirectoryChooser pathChooser = new DirectoryChooser();
        pathChooser.setTitle("Add path");
        File folder = pathChooser.showDialog(settingsWindow);
        if (folder != null && folder.exists()) {
            if (settingsFactory.addPath/*Successful*/(folder.getPath())) {
                addPathTile(folder.getPath());
            }
        }
    }

    /**
     * Function name:   addPathTile
     * Usage:   This method would add a new PathTile with relevant data into the grid
     * @param path path to be added
     */
    private void addPathTile(String path) {
        PathTile pathTile = new PathTile(path);
        pathTile.setOnMouseClicked(event -> removePathTile(pathTile));
        pathsGrid.add(pathTile, item % 2, item / 2);
        if (item % 2 == 0) {
            RowConstraints newRow = new RowConstraints(80.0);
            newRow.setVgrow(Priority.NEVER);
            pathsGrid.getRowConstraints().add(newRow);
        }
        item++;
    }

    /**
     * Function name:   removePathTile
     * Usage:   This method would remove the chosen pathTile from the grid and its relevant data from the back-end database
     * @param pathTile pathTile to be removed
     */
    private void removePathTile(PathTile pathTile) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        String headerMessage = "Remove this folder?";
        alert.setTitle(headerMessage);
        alert.setHeaderText(headerMessage);
        alert.setContentText("If you remove the \"" + pathTile.getFolderNameStr() + "\" folder from Music, it won't" +
                "appear in Music anymore, but won't be deleted.");
        alert.initOwner(settingsWindow);

        ButtonType buttonTypeConfirm = new ButtonType("Remove Folder", ButtonBar.ButtonData.APPLY);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeConfirm, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeConfirm){
            int pathTileId = pathsGrid.getChildren().indexOf(pathTile);
            pathsGrid.getChildren().remove(pathTileId);
            for (int i = pathTileId; i < pathsGrid.getChildren().size(); i++) {
                GridPane.setColumnIndex(pathsGrid.getChildren().get(i), i % 2);
                GridPane.setRowIndex(pathsGrid.getChildren().get(i), i / 2);
            }
            settingsFactory.removePath(pathTile.getFolderPathStr());
        }
    }

    public void setSettingsFactory(SettingsFactory settingsFactory) {
        this.settingsFactory = settingsFactory;
    }

    public void setSettingsWindow(Window settingsWindow) {
        this.settingsWindow = settingsWindow;
    }
}

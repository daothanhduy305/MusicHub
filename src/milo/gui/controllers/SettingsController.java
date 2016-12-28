package milo.gui.controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class name:  SettingsController
 * Description: This controller controls the Settings window
 */
public class SettingsController {
    @FXML private GridPane pathsGrid;
    @FXML private Button doneButton;

    private SettingsFactory settingsFactory;
    private Window settingsWindow;

    private List<String> addingFolderList, removingFolderList;
    private int item = 1;

    public void buildUI() {
        PathAddButton pathAddButton = new PathAddButton();
        pathAddButton.setOnMouseClicked(event -> addPath());
        pathsGrid.add(pathAddButton, 0, 0);
        if (settingsFactory.getSettingsData().getPathList() != null && settingsFactory.getSettingsData().getPathList().size() > 0) {
            for (String path : settingsFactory.getSettingsData().getPathList()) {
                addPathTile(path);
            }
        }
        doneButton.setOnAction(event -> applySettings());
        addingFolderList = new ArrayList<>(1);
        removingFolderList = new ArrayList<>(1);
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
                addingFolderList.add(folder.getPath());
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
        pathTile.setOnMouseClicked(event -> showRemovePathTileDialog(pathTile));
        pathsGrid.add(pathTile, item % 2, item / 2);
        if (item % 2 == 0) {
            RowConstraints newRow = new RowConstraints(100.0);
            newRow.setVgrow(Priority.NEVER);
            pathsGrid.getRowConstraints().add(newRow);
        }
        item++;
    }

    /**
     * Function name:   showRemovePathTileDialog
     * Usage:   This method would remove the chosen pathTile from the grid and its relevant data from the back-end database
     * @param pathTile pathTile to be removed
     */
    private void showRemovePathTileDialog(PathTile pathTile) {
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
            removePathTile(pathTile, false);
        }
    }

    /**
     * Function name:   removePathTile
     * Usage:   This method would be called when user want to remove a pathTile
     * @param pathTile pathTile to be removed
     */
    private void removePathTile(PathTile pathTile, boolean UIOnly) {
        String path = pathTile.getFolderPathStr();
        if (!UIOnly) {
            if (addingFolderList.indexOf(path) == -1) {
                removingFolderList.add(path);
            } else {
                addingFolderList.remove(path);
            }

            settingsFactory.getSettingsData().getPathList().remove(path);
        }
        int pathTileId = pathsGrid.getChildren().indexOf(pathTile);
        pathsGrid.getChildren().remove(pathTileId);
        for (int i = pathTileId; i < pathsGrid.getChildren().size(); i++) {
            GridPane.setColumnIndex(pathsGrid.getChildren().get(i), i % 2);
            GridPane.setRowIndex(pathsGrid.getChildren().get(i), i / 2);
        }
        item--;
    }

    /**
     * Function name:   removePathTile
     * Usage:   This method would be called when user want to remove a pathTile. Since this is called only when a parent
     *          path is added so, we don't need to update the database here
     * @param path path of the pathTile to be removed
     */
    public void removePathTile(String path) {
        for (Node node : pathsGrid.getChildren()) {
            if (node instanceof PathTile) {
                PathTile pathTile = (PathTile) node;
                if (pathTile.getFolderPathStr().equalsIgnoreCase(path)) {
                    removePathTile(pathTile, true);
                    break;
                }
            }
        }
    }

    private void applySettings() {
        settingsWindow.hide();
        new Thread(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (String path : removingFolderList) {
                    settingsFactory.removePath(path);
                }
                for (String path : addingFolderList) {
                    settingsFactory.createDB(path);
                }
                settingsFactory.saveSettings();
                removingFolderList.clear();
                addingFolderList.clear();
                settingsFactory.setDB();
                return null;
            }
        }).start();
    }

    public void setSettingsFactory(SettingsFactory settingsFactory) {
        this.settingsFactory = settingsFactory;
    }

    public void setSettingsWindow(Window settingsWindow) {
        this.settingsWindow = settingsWindow;
    }
}

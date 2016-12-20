package milo.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import milo.gui.custom.PathAddButton;
import milo.gui.custom.PathTile;
import milo.gui.utils.SettingsFactory;

import java.io.File;

/**
 * Created by Ebolo on 18/12/2016.
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
                pathsGrid.add(new PathTile(path), item % 2, item / 2);
                item++;
            }
        }
    }

    private void addPath() {
        DirectoryChooser pathChooser = new DirectoryChooser();
        pathChooser.setTitle("Add path");
        File folder = pathChooser.showDialog(settingsWindow);
        if (folder != null && folder.exists()) {
            if (settingsFactory.addPath/*Successful*/(folder.getPath())) {
                pathsGrid.add(new PathTile(folder.getPath()), item % 2, item / 2);
                if (item % 2 == 0) {
                    RowConstraints newRow = new RowConstraints(80.0);
                    newRow.setVgrow(Priority.NEVER);
                    pathsGrid.getRowConstraints().add(newRow);
                }
                item++;
            }
        }
    }

    public void setSettingsFactory(SettingsFactory settingsFactory) {
        this.settingsFactory = settingsFactory;
    }

    public void setSettingsWindow(Window settingsWindow) {
        this.settingsWindow = settingsWindow;
    }
}

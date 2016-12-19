package milo.gui.custom;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import milo.gui.utils.Constants;

import java.io.IOException;

/**
 * Created by Ebolo on 19/12/2016.
 */
public class PathTile extends VBox {
    @FXML private Label folderName, folderPath;

    public PathTile(String fPath) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/milo/gui/designs/path_tile.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        folderName.setText(fPath);
        folderPath.setText(fPath);

        this.setBackground(Constants.getBgButBlue());
        this.setOnMouseEntered(event -> setBackground(Constants.getBgButGray()));
        this.setOnMouseExited(event -> setBackground(Constants.getBgButBlue()));
    }
}

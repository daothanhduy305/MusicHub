package milo.gui.custom;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import milo.gui.utils.Constants;

import java.io.IOException;

/**
 * Class name:  PathTile
 * Description: This is the class for the tiles contain path info in Settings window
 */

public class PathTile extends VBox {
    @FXML private Label folderName, folderPath;
    private String folderNameStr = "", folderPathStr = "";

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

        folderPathStr = fPath;
        int i = fPath.length() - 1;
        while (i >= 0 && !(fPath.charAt(i) == '/' || fPath.charAt(i) == '\\'))
            folderNameStr += fPath.charAt(i--);
        folderNameStr = new StringBuilder(folderNameStr).reverse().toString();
        folderName.setText(folderNameStr);
        folderPath.setText(folderPathStr);

        this.setBackground(Constants.getBgButBlue());
        this.setOnMouseEntered(event -> setBackground(Constants.getBgButGray()));
        this.setOnMouseExited(event -> setBackground(Constants.getBgButBlue()));
    }

    public String getFolderNameStr() {
        return folderNameStr;
    }

    public String getFolderPathStr() {
        return folderPathStr;
    }
}

package milo.gui.custom;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import milo.gui.utils.Constants;

import java.io.IOException;

/**
 * Class name:  PathTile
 * Description: This is the class for the button of adding new path in Settings window
 */

public class PathAddButton extends StackPane {
    public PathAddButton() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/milo/gui/designs/path_add_button.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.setBackground(Constants.getBgButBlue());
        this.setOnMouseEntered(event -> setBackground(Constants.getBgButGray()));
        this.setOnMouseExited(event -> setBackground(Constants.getBgButBlue()));
    }
}

package milo.gui.custom;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import milo.gui.utils.Constants;

/**
 * Created by Ebolo on 19/12/2016.
 */
public class PathTile extends Pane {
    private VBox infoBox;
    private Label folderName, folderPath;

    public PathTile(String fPath) {
        folderPath = new Label(fPath);
        folderName = new Label(fPath);
        infoBox = new VBox(folderName, folderPath);
        this.getChildren().add(infoBox);

        this.setBackground(Constants.getBgButBlue());
        this.setOnMouseEntered(event -> setBackground(Constants.getBgButGray()));
        this.setOnMouseExited(event -> setBackground(Constants.getBgButBlue()));
    }
}

package milo.gui.custom;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import milo.gui.utils.Constants;

/**
 * Created by Ebolo on 19/12/2016.
 */
public class PathAddButton extends Pane {
    private final Label symbol = new Label("+");

    public PathAddButton() {
        this.getChildren().add(symbol);

        this.setBackground(Constants.getBgButBlue());
        this.setOnMouseEntered(event -> setBackground(Constants.getBgButGray()));
        this.setOnMouseExited(event -> setBackground(Constants.getBgButBlue()));
    }
}

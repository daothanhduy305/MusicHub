package milo.gui.custom;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import milo.gui.utils.Constants;
import milo.gui.utils.SizeCalculator;

import java.io.IOException;

/**
 * Class name:  NavigationViewButton
 * Description: This is the class for the custom buttons, which will be used as navigation buttons to change views
 */

public class NavigationViewButton extends HBox {
    private final String iconExtension = Constants.getImageExtension();
    @FXML
    private ImageView buttonIcon;
    @FXML
    private Label buttonTitleLabel;
    @FXML
    private Pane status;
    private String iconPath;
    private String buttonName;
    private SizeCalculator sizeCalculator;

    public NavigationViewButton(String buttonTitle, SizeCalculator sizeCalculator) {
        this.sizeCalculator = sizeCalculator;

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/milo/gui/designs/navigation_view_button.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        iconPath = Constants.getButtonsPrePath() + buttonTitle + iconExtension;
        buttonName = buttonTitle;
        this.buildUI();
    }

    public void buildUI() {
        this.setPrefHeight(sizeCalculator.getNavigationDrawerButtonHeight());
        this.setPrefWidth(sizeCalculator.getNavigationDrawerWidth());
        this.setSpacing(sizeCalculator.getNavigationDrawerPaddingH());

        buttonIcon.setImage(new Image(
                iconPath,
                sizeCalculator.getNavigationDrawerButtonHeight() / 2.5,
                sizeCalculator.getNavigationDrawerButtonHeight() / 2.5,
                true, true, false
        ));
        buttonIcon.setCache(true);

        status.setPrefHeight(sizeCalculator.getNavigationDrawerButtonHeight());
        status.setPrefWidth(3.0);

        buttonTitleLabel.setText(buttonName);
    }

    public void makeActive() {
        status.setBackground(new Background(new BackgroundFill(
                Color.DODGERBLUE, CornerRadii.EMPTY, Insets.EMPTY
        )));
        buttonTitleLabel.setTextFill(Color.DODGERBLUE);
    }

    public void makeInactive() {
        status.setBackground(null);
        buttonTitleLabel.setTextFill(Color.BLACK);
    }
}

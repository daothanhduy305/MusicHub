package milo.gui.custom;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import milo.gui.utils.Constants;

/**
 * Class name:  ActionButton
 * Description: This is the class for the custom buttons, which will be used as media buttons
 */

public class ActionButton extends ImageView {
    private final String prePath = Constants.getButtonsPrePath();
    private final String imageExtension = Constants.getImageExtension();
    private final ActionButton thisButton;
    private String mName;

    public ActionButton(String buttonName) {
        this.thisButton = this;
        this.mName = buttonName;
        this.makeFunctional();
        this.setSmooth(true);
        this.setPreserveRatio(true);
        this.setFitWidth(Constants.getPlayerBarAlbumArtSize() * 0.6);
        this.setFitHeight(Constants.getPlayerBarAlbumArtSize() * 0.6);
    }

    /**
     * Function name:   makeFunctional
     * Usage:   this function is called to add listeners to the button.
     */
    private void makeFunctional() {
        this.setButImg("");
        this.setOnMouseEntered(event -> thisButton.setButImg("_hover"));
        this.setOnMouseExited(event -> thisButton.setButImg(""));
        this.setOnMousePressed(event -> thisButton.setButImg("_pressed"));
        this.setOnMouseReleased(event -> {
            if (thisButton.isHover())
                thisButton.setButImg("_hover");
            else
                this.setButImg("");
        });
    }

    /**
     * Function name:   setButImg
     * Usage:   this function is called to change the button image dynamically
     */
    private void setButImg(String optionalOpt) {
        this.setImage(new Image(prePath + mName + optionalOpt + imageExtension));
    }

    /**
     * Function name:   replaceButName
     * Usage:   this function is called to change the button's name and update the button to correspond to the new one
     */
    public void replaceButName(String newName) {
        this.mName = newName;
        this.makeFunctional();
    }
}

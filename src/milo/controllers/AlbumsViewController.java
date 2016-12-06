package milo.controllers;

import javafx.scene.Scene;
import milo.controllers.abstractcontrollers.AbstractSubUIController;

/**
 * Class name:  AlbumsViewController
 * Description: This controller will control the albums views, consists of Overview (All albums) and Specific view
 *              (specific album)
 */

public class AlbumsViewController extends AbstractSubUIController {
    private Scene mScene;

    @Override
    public void buildUI() {

    }

    @Override
    public void refreshUI() {

    }

    public void setScene(Scene scene) {
        this.mScene = scene;
    }
}

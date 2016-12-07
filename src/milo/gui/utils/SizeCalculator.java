package milo.gui.utils;

import javafx.scene.Scene;

/**
 * Class name:  SizeCalculator
 * Description: This class will be called on resizing to tuned the necessary sizes to adapt to the scene's sizes
 *              dynamically
 * TODO: Some of these (or their names) will need to be tweaked later
 */

public class SizeCalculator {
    private Scene theScene;

    public SizeCalculator(Scene scene) {
        this.theScene = scene;
        this.calibrate();
    }

    /**
     * Function name:   calibrate
     * Usage:   this function is called on size update
     * TODO: implement the method
     */
    public void calibrate() {

    }
}

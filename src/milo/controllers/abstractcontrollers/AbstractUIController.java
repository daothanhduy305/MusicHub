package milo.controllers.abstractcontrollers;

import milo.gui.utils.SizeCalculator;

/**
 * Class name:  AbstractUIController
 * Description: This is an abstract class serves as a template for a modal controller
 *              A controller would be associated with a fxml template file.
 */

public abstract class AbstractUIController {
    protected SizeCalculator sizeCalculator;

    /**
     * Function name:   buildUI
     * Usage:   to construct the GUI at the init stage, each controller will have to cconstruct its own fxml here
     */
    public abstract void buildUI();

    /**
     * Function name:   refreshUI
     * Usage:   this method would be call on resizing
     */
    public abstract void refreshUI();

    public void setSizeCalculator(SizeCalculator sizeCalculator) {
        this.sizeCalculator = sizeCalculator;
    }
}

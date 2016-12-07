package milo.controllers.abstractcontrollers;

import milo.controllers.MainPlayerController;

/**
 * Class name:  AbstractSubUIController
 * Description: This is an abstract class serves as a template for a sub modal controller which is basically the same
 *              as a modal controller but would need to access the main player methods.
 */

public abstract class AbstractSubUIController extends AbstractUIController {
    protected MainPlayerController mainPlayerController;

    public void setMainPlayerController(MainPlayerController mainPlayerController) {
        this.mainPlayerController = mainPlayerController;
    }
}

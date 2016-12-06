package milo.controllers.abstractcontrollers;

import milo.controllers.MainPlayerControllerPlayer;

/**
 * Class name:  AbstractSubUIController
 * Description: This is an abstract class serves as a template for a sub modal controller which is basically the same
 *              as a modal controller but would need to access the main player methods.
 */

public abstract class AbstractSubUIController extends AbstractUIController {
    protected MainPlayerControllerPlayer mainPlayerController;

    public void setMainPlayerController(MainPlayerControllerPlayer mainPlayerController) {
        this.mainPlayerController = mainPlayerController;
    }
}

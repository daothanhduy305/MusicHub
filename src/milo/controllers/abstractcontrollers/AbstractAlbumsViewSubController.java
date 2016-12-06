package milo.controllers.abstractcontrollers;

import milo.controllers.AlbumsViewController;

/**
 * Class name:  AbstractAlbumsViewSubController
 * Description: This is an abstract class serves as a template for a sub albums view modal controller
 *              which is basically the same as a modal controller but would need to access the albums view methods.
 */

public abstract class AbstractAlbumsViewSubController extends AbstractUIController {
    protected AlbumsViewController albumsViewController;

    public void setAlbumsViewController(AlbumsViewController albumsViewController) {
        this.albumsViewController = albumsViewController;
    }
}

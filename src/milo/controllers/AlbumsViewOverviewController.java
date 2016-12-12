package milo.controllers;

import milo.controllers.abstractcontrollers.AbstractAlbumsViewSubController;
import milo.data.AlbumData;
import milo.gui.custom.AlbumTileCell;

import java.util.List;

/**
 * Class name:  AlbumsViewOverviewController
 * Description: This controller will control the albums overviews, where all albums are shown as tiles
 */

public class AlbumsViewOverviewController extends AbstractAlbumsViewSubController {
    private List<AlbumTileCell> monitoringCells;

    @Override
    public void buildUI() {

    }

    @Override
    public void refreshUI() {

    }

    public void showAlbum(AlbumData albumData) {
        albumsViewController.showAlbum(albumData);
    }

    public List<AlbumTileCell> getMonitoringCells() {
        return monitoringCells;
    }
}

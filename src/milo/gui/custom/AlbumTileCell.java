package milo.gui.custom;

import milo.gui.controllers.AlbumsViewOverviewController;
import milo.gui.utils.Constants;
import org.controlsfx.control.GridCell;

/**
 * Class name:  AlbumTileCell
 * Description: This class is the holder for AlbumTile, as a cell in the albums grid cell
 */

public class AlbumTileCell extends GridCell<AlbumTile> {
    private AlbumsViewOverviewController albumsViewOverviewController;

    public AlbumTileCell(AlbumsViewOverviewController controller) {
        this.albumsViewOverviewController = controller;
    }

    @Override
    protected void updateItem(AlbumTile item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setGraphic(item);
            if (item.isClicked())
                setBackground(Constants.getBgGray());
            this.setOnMouseEntered(event -> setBackground(Constants.getBgGray()));

            this.setOnMouseExited(event -> {
                if (!item.isClicked())
                    setBackground(Constants.getBgWhite());
            });

            this.setOnMouseClicked(event -> {
                for (AlbumTileCell albumTileCell : albumsViewOverviewController.getMonitoringCells()) {
                    albumTileCell.setBackground(Constants.getBgWhite());
                    albumTileCell.getItem().setClicked(false);
                }
                item.setClicked(true);
                this.setBackground(Constants.getBgGray());
                if (event.getClickCount() == 2) {
                    albumsViewOverviewController.showAlbum(item.getAlbumData());}
            });
        }
    }
}

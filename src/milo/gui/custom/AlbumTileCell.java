package milo.gui.custom;

import milo.data.AlbumData;
import milo.gui.controllers.AlbumsViewOverviewController;
import milo.gui.utils.Constants;
import org.controlsfx.control.GridCell;

/**
 * Class name:  AlbumTileCell
 * Description: This class is the holder for AlbumTile, as a cell in the albums grid cell
 */

public class AlbumTileCell extends GridCell<AlbumData> {
    private AlbumsViewOverviewController albumsViewOverviewController;
    private AlbumTile albumTile;

    public AlbumTileCell(AlbumsViewOverviewController controller) {
        this.albumsViewOverviewController = controller;
    }

    @Override
    protected void updateItem(AlbumData item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            albumTile = albumsViewOverviewController.getAlbumTileMap().get(item.getAlbumTitle() + item.getAlbumArtist());
            setGraphic(albumTile);
            if (albumTile.isClicked())
                setBackground(Constants.getBgGray());
            this.setOnMouseEntered(event -> setBackground(Constants.getBgGray()));

            this.setOnMouseExited(event -> {
                if (!albumTile.isClicked())
                    setBackground(Constants.getBgWhite());
            });

            this.setOnMouseClicked(event -> {
                for (AlbumTileCell albumTileCell : albumsViewOverviewController.getMonitoringCells()) {
                    albumTileCell.setBackground(Constants.getBgWhite());
                    ((AlbumTile)albumTileCell.getGraphic()).setClicked(false);
                }
                albumTile.setClicked(true);
                this.setBackground(Constants.getBgGray());
                if (event.getClickCount() == 2) {
                    albumsViewOverviewController.showAlbum(albumTile.getAlbumData());}
            });
        }
    }
}

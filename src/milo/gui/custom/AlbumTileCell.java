package milo.gui.custom;

import javafx.application.Platform;
import milo.gui.controllers.AlbumsViewOverviewController;
import milo.gui.utils.Constants;
import org.controlsfx.control.GridCell;

/**
 * Class name:  AlbumTileCell
 * Description: This class is the holder for AlbumTile, as a cell in the albums grid cell
 */

public class AlbumTileCell extends GridCell<AlbumTile> {
    private AlbumsViewOverviewController albumsViewOverviewController;
    private boolean isClicked = false;


    public AlbumTileCell(AlbumsViewOverviewController controller) {
        this.albumsViewOverviewController = controller;
    }

    @Override
    protected void updateItem(AlbumTile item, boolean empty) {
        //super.updateItem(item, empty);
        if (empty || item == null) {
            if (!Platform.isFxApplicationThread()) {
                setText(null);
                setGraphic(null);
            } else
                Platform.runLater(() -> {
                    setText(null);
                    setGraphic(null);
                });
        } else {
            if (!Platform.isFxApplicationThread())
                setGraphic(item);
            else
                Platform.runLater(() -> setGraphic(item));
            this.setOnMouseEntered(event -> setBackground(Constants.getBgGrayer()));

            this.setOnMouseExited(event -> {
                if (!isClicked)
                    setBackground(Constants.getBgWhite());
            });

            this.setOnMouseClicked(event -> {
                for (AlbumTileCell albumTileCell : albumsViewOverviewController.getMonitoringCells()) {
                    albumTileCell.setBackground(Constants.getBgWhite());
                    albumTileCell.setClicked(false);
                }
                setClicked(true);
                this.setBackground(Constants.getBgGrayer());
                if (event.getClickCount() == 2) {
                    albumsViewOverviewController.showAlbum(item.getAlbumData());
                }
            });
        }
    }

    private void setClicked(boolean clicked) {
        isClicked = clicked;
    }
}

package milo.gui.controllers;

import javafx.application.Platform;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import milo.data.AlbumData;
import milo.data.utils.AlbumTileComparator;
import milo.gui.controllers.abstractcontrollers.AbstractAlbumsViewSubController;
import milo.gui.custom.AlbumTile;
import milo.gui.custom.AlbumTileCell;
import milo.gui.utils.Constants;
import org.controlsfx.control.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Class name:  AlbumsViewOverviewController
 * Description: This controller will control the albums overviews, where all albums are shown as tiles
 */

public class AlbumsViewOverviewController extends AbstractAlbumsViewSubController {
    @FXML private VBox mHolder;
    @FXML private GridView<AlbumTile> albumsListView;

    //private ObservableList<AlbumTile> albumTiles;
    private List<AlbumTileCell> monitoringCells;

    @Override
    public void buildUI() {
        // TODO save album data into file instead of createDB()
        monitoringCells = new ArrayList<>(20);

        albumsListView.setCellWidth(Constants.getAlbumOverviewAlbumArtSize() + 12 );
        albumsListView.setCellHeight(Constants.getAlbumOverviewAlbumArtSize() * 1.7);

        Insets oldPaddingVal = albumsListView.getPadding();
        albumsListView.setPadding(new Insets(oldPaddingVal.getTop(), oldPaddingVal.getRight(),
                oldPaddingVal.getBottom(), albumsListView.getHorizontalCellSpacing() * 3.0));
    }

    @Override
    public void refreshUI() {
        albumsListView.setMinWidth(sizeCalculator.getMainViewPanelWidth());
        albumsListView.setMaxWidth(sizeCalculator.getMainViewPanelWidth());
    }

    public void showAlbum(AlbumData albumData) {
        albumsViewController.showAlbum(albumData);
    }

    public void setDB() {
        albumsListView.setCellFactory(gridView -> {
            AlbumTileCell albumTileCell = new AlbumTileCell(this);
            monitoringCells.add(albumTileCell);
            return albumTileCell;
        });
        Platform.runLater(() -> albumsListView.setItems(
                new SortedList<>(mainPlayerController.getAlbumTileObservableList(), new AlbumTileComparator()))
        );
    }

    public List<AlbumTileCell> getMonitoringCells() {
        return monitoringCells;
    }
}

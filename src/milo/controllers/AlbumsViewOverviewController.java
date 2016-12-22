package milo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import milo.controllers.abstractcontrollers.AbstractAlbumsViewSubController;
import milo.data.AlbumData;
import milo.gui.custom.AlbumTile;
import milo.gui.custom.AlbumTileCell;
import milo.gui.utils.Constants;
import org.controlsfx.control.GridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class name:  AlbumsViewOverviewController
 * Description: This controller will control the albums overviews, where all albums are shown as tiles
 */

public class AlbumsViewOverviewController extends AbstractAlbumsViewSubController {
    @FXML private VBox mHolder;
    @FXML private GridView<AlbumTile> albumsListView;

    private ObservableList<AlbumTile> albumTiles;
    private List<AlbumTileCell> monitoringCells;

    @Override
    public void buildUI() {
        // TODO save album data into file instead of createDB()
        monitoringCells = new ArrayList<>(20);

        albumsListView.setCellWidth(Constants.getAlbumOverviewAlbumArtSize() + 12 );
        albumsListView.setCellHeight(Constants.getAlbumOverviewAlbumArtSize() * 1.7);

        albumsListView.setCellFactory(gridView -> {
            AlbumTileCell albumTileCell = new AlbumTileCell(this);
            monitoringCells.add(albumTileCell);
            return albumTileCell;
        });

        Insets oldPaddingVal = albumsListView.getPadding();
        albumsListView.setPadding(new Insets(oldPaddingVal.getTop(), oldPaddingVal.getRight(),
                oldPaddingVal.getBottom(), albumsListView.getHorizontalCellSpacing() * 3.0));

        albumTiles = FXCollections.observableArrayList();
        albumsListView.setItems(albumTiles);

    }

    @Override
    public void refreshUI() {
        albumsListView.setMinWidth(sizeCalculator.getMainViewPanelWidth());
        albumsListView.setMaxWidth(sizeCalculator.getMainViewPanelWidth());
    }

    public void showAlbum(AlbumData albumData) {
        albumsViewController.showAlbum(albumData);
    }

    public void setDB(Map<String, AlbumData> albumDataMap) {
        albumTiles.clear();
        albumDataMap.values().forEach(albumData -> albumTiles.add(new AlbumTile(albumData)));
        FXCollections.sort(albumTiles);
    }

    public List<AlbumTileCell> getMonitoringCells() {
        return monitoringCells;
    }
}

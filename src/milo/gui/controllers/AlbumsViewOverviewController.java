package milo.gui.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import milo.data.AlbumData;
import milo.data.utils.AlbumDataComparator;
import milo.gui.controllers.abstractcontrollers.AbstractAlbumsViewSubController;
import milo.gui.custom.AlbumTile;
import milo.gui.custom.AlbumTileCell;
import milo.gui.utils.Constants;
import org.controlsfx.control.GridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class name:  AlbumsViewOverviewController
 * Description: This controller will control the albums overviews, where all albums are shown as tiles
 */

public class AlbumsViewOverviewController extends AbstractAlbumsViewSubController {
    @FXML private VBox mHolder;
    @FXML private GridView<AlbumData> albumsListView;

    //private ObservableList<AlbumTile> albumTiles;
    private Map<String, AlbumTile> albumTileMap;
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

    public void setDB(Map<String, AlbumData> albumDataMap) {
        ObservableList<AlbumData> albumDatas = FXCollections.observableArrayList(albumDataMap.values());
        albumTileMap = new TreeMap<>();
        albumDatas.forEach(albumData -> albumTileMap.put(albumData.getAlbumTitle() + albumData.getAlbumArtist()
                , new AlbumTile(albumData)));
        albumsListView.setCellFactory(gridView -> {
            AlbumTileCell albumTileCell = new AlbumTileCell(AlbumsViewOverviewController.this);
            monitoringCells.add(albumTileCell);
            return albumTileCell;
        });
        Platform.runLater(() -> albumsListView.setItems(new SortedList<>(albumDatas, new AlbumDataComparator())));
    }

    public List<AlbumTileCell> getMonitoringCells() {
        return monitoringCells;
    }

    public Map<String, AlbumTile> getAlbumTileMap() {
        return albumTileMap;
    }
}

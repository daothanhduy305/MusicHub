package milo.controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import milo.controllers.abstractcontrollers.AbstractAlbumsViewSubController;
import milo.data.AlbumData;
import milo.data.SongData;
import milo.gui.utils.Constants;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Class name:  AlbumsViewSpecificController
 * Description: This controller will control the albums specific view, where a specific album is shown along with its
 *              info, song list
 */

public class AlbumsViewSpecificController extends AbstractAlbumsViewSubController {
    @FXML private TableView<SongData> songListTable;
    @FXML private TableColumn<SongData, String> songListTableTitle, songListTableArtist, songListTableGenre, songListTableYear, songListTableAlbum, songListTableLength;
    @FXML private VBox mainPanel, labelsBox;
    @FXML private HBox albumInfoBox;
    @FXML private ImageView albumArtBig;
    @FXML private Label albumTitleLabel, albumArtistLabel, albumOtherInfoLabel;

    private AlbumData albumData;
    private ObservableList<SongData> albumSongsList;
    private SortedList<SongData> songDataSortedList;

    private boolean isCreated = false;

    @Override
    public void buildUI() {
        albumSongsList = FXCollections.observableArrayList();
        songDataSortedList = new SortedList<SongData>(albumSongsList);
        songListTable.getStylesheets().clear();
        songListTable.getStylesheets().add(Constants.getCssMainFilePath());

        songListTableTitle.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        songListTableArtist.setCellValueFactory(cellData -> cellData.getValue().artistProperty());
        songListTableAlbum.setCellValueFactory(cellData -> cellData.getValue().albumTitleProperty());
        songListTableYear.setCellValueFactory(cellData -> cellData.getValue().yearProperty());
        songListTableGenre.setCellValueFactory(cellData -> cellData.getValue().gerneProperty());
        songListTableLength.setCellValueFactory(cellData -> cellData.getValue().lengthStrProperty());
        songListTable.getSortOrder().setAll(songListTableTitle);
        songListTableLength.setStyle("-fx-alignment: CENTER-RIGHT;");
        songListTable.setItems(songDataSortedList);
        songDataSortedList.comparatorProperty().bind(songListTable.comparatorProperty());
        songListTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                mainPlayerController.playSong(songListTable.getSelectionModel().getSelectedItem());
                buildNowPlaylistLinear(songListTable.getSelectionModel().getSelectedIndex());
            }
        });
    }

    @Override
    public void refreshUI() {
        songListTable.setPrefWidth(sizeCalculator.getSongTableWidth());

        songListTableTitle.setMaxWidth(sizeCalculator.getBigColumnWidth());
        songListTableTitle.setMinWidth(sizeCalculator.getBigColumnWidth());
        songListTableArtist.setMaxWidth(sizeCalculator.getBigColumnWidth());
        songListTableArtist.setMinWidth(sizeCalculator.getBigColumnWidth());
        songListTableAlbum.setMaxWidth(sizeCalculator.getBigColumnWidth());
        songListTableAlbum.setMinWidth(sizeCalculator.getBigColumnWidth());
        songListTableYear.setMaxWidth(sizeCalculator.getSmallColumnWidth());
        songListTableYear.setMinWidth(sizeCalculator.getSmallColumnWidth());
        songListTableGenre.setMaxWidth(sizeCalculator.getSmallColumnWidth());
        songListTableGenre.setMinWidth(sizeCalculator.getSmallColumnWidth());
        songListTableLength.setMaxWidth(sizeCalculator.getSmallColumnWidth());
        songListTableLength.setMinWidth(sizeCalculator.getSmallColumnWidth());

        // TODO choose between the scene's height and the calculation beneath
        songListTable.setFixedCellSize(48.0);
        songListTable.prefHeightProperty().bind(Bindings.size(songListTable.getItems()).multiply(songListTable.getFixedCellSize()).add(2.0));
    }

    /**
     * Function name:   showAlbum
     * Usage:   this function is called display the view for a specific album
     *
     * @param albumData the data of the album to be displayed
     */
    public void showAlbum(AlbumData albumData) {
        this.albumData = albumData;
        albumSongsList.clear();
        albumSongsList.addAll(albumData.getSongList());

        if (mainPlayerController.isPlaying())
            songListTable.getSelectionModel().select(mainPlayerController.getCurrentPlayingSong());

        buildInfoUI();
    }

    /**
     * Function name:   songListTableRefresh
     * Usage:   This is the very ugly workaround to force the tableview to refresh when the data got updated but the UI
     *          view still be stuck with old data
     */
    private void songListTableRefresh() {
        songListTable.getColumns().get(0).setVisible(false);
        songListTable.getColumns().get(0).setVisible(true);
    }

    /**
     * Function name:   buildInfoUI
     * Usage:   This method would be call to set the correct album data for current album
     */
    private void buildInfoUI() {
        if (!isCreated) {
            albumInfoBox.setPrefHeight(Constants.getAlbumPrivateInfoBoxHeight());
            albumInfoBox.setPadding(new Insets(Constants.getAlbumPrivateInfoBoxPadding()));

            albumArtBig.setFitHeight(Constants.getAlbumArtPrivateSize());
            albumArtBig.setFitWidth(Constants.getAlbumArtPrivateSize());

            labelsBox.setPadding(new Insets(0, 0, 0, Constants.getAlbumPrivateInfoBoxPadding()));
            albumTitleLabel.setPrefWidth(Constants.getAlbumPrivateLabelBoxw());
            albumArtistLabel.setPrefWidth(Constants.getAlbumPrivateLabelBoxw());

            albumArtBig.setOnMouseClicked(event -> albumsViewController.showAlbumOverview());
        }

        albumArtBig.setImage(new Image(
                new ByteArrayInputStream(albumData.getAlbumArtByte()),
                Constants.getAlbumArtPrivateSize() * 1.5,
                Constants.getAlbumArtPrivateSize() * 1.5,
                true, true
        ));
        albumTitleLabel.setText(albumData.getAlbumTitle());
        albumArtistLabel.setText(albumData.getAlbumAuthor());

        songListTableRefresh();
        refreshUI();
    }

    /**
     * Function name:   buildCurrentPlaylistLinear
     * Usage:   this method would be called to build linear playlist (without shuffle)
     *
     * @param id the id number of the starting song (in the table view)
     * TODO: have a look at repeat mode
     */
    private void buildNowPlaylistLinear(int id) {
        mainPlayerController.setCurrentPlaylist(new ArrayList<>(20));
        mainPlayerController.setPreviousPlaylist(new ArrayList<>(20));
        (new Thread(() -> {
            for (int i = id + 1; i < albumData.getSongList().size(); i++) {
                mainPlayerController.getCurrentPlaylist().add(songListTable.getItems().get(i));
            }
            for (int i = 0; i <= id; i++) {
                mainPlayerController.getCurrentPlaylist().add(songListTable.getItems().get(i));
            }
        })).start();
    }

    public TableView<SongData> getSongListTable() {
        return songListTable;
    }
}

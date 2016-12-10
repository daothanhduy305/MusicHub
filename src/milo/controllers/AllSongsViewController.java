package milo.controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import milo.controllers.abstractcontrollers.AbstractSubUIController;
import milo.data.SongData;
import milo.gui.utils.Constants;

import java.util.ArrayList;

/**
 * Class name:  AllSongsViewController
 * Description: This controller will control the all songs view, which will show the whole list of every songs that
 *              are available within the defined path(s)
 */

public class AllSongsViewController extends AbstractSubUIController {
    @FXML private TableView<SongData> songListTable;
    @FXML private TableColumn<SongData, String> songListTableTitle, songListTableArtist, songListTableLength;
    @FXML private VBox mHolder;

    private SortedList<SongData> songDataSortedList;
    private boolean isDBSet = false;

    @Override
    public void buildUI() {
        songListTable.getStylesheets().clear();
        songListTable.getStylesheets().add(Constants.getCssMainFilePath());

        songListTableTitle.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        songListTableArtist.setCellValueFactory(cellData -> cellData.getValue().artistProperty());
        songListTableLength.setCellValueFactory(cellData -> cellData.getValue().lengthStrProperty());

        songListTable.setFixedCellSize(48.0);
    }

    @Override
    public void refreshUI() {
        mHolder.setPrefWidth(sizeCalculator.getMainViewPanelWidth());
        mHolder.setPrefHeight(sizeCalculator.getWindowHeight());

        songListTable.setPrefWidth(sizeCalculator.getSongTableWidth());
        songListTable.prefHeightProperty().bind(
                Bindings.size(songListTable.getItems()).multiply(songListTable.getFixedCellSize()).add(2.0)
        );

        songListTableTitle.setPrefWidth(sizeCalculator.getBigColumnWidth());
        songListTableTitle.setMaxWidth(sizeCalculator.getBigColumnWidth());
        songListTableArtist.setPrefWidth(sizeCalculator.getBigColumnWidth());
        songListTableArtist.setMaxWidth(sizeCalculator.getBigColumnWidth());
        songListTableLength.setPrefWidth(sizeCalculator.getSmallColumnWidth());
        songListTableLength.setMaxWidth(sizeCalculator.getSmallColumnWidth());
    }

    /**
     * Function name:   setDB
     * Usage:   this function is called to set the song list. This can be considered as the result action for the method
     * of creating database from Settings later. Moreover, this should be done only on init.
     */
    public void setDB() {
        if (!isDBSet) {
            songDataSortedList = new SortedList<>(mainPlayerController.getSongDatas());
            songListTable.setItems(songDataSortedList);
            songDataSortedList.comparatorProperty().bind(songListTable.comparatorProperty());
            songListTable.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    mainPlayerController.playSong(songListTable.getSelectionModel().getSelectedItem());
                    buildCurrentPlaylistLinear(songListTable.getSelectionModel().getSelectedIndex());
                }
            });
        }

        isDBSet = true;
        //noinspection unchecked
        songListTable.getSortOrder().setAll(songListTableTitle);
        songListTableRefresh();
    }

    /**
     * Function name:   setDB
     * Usage:   This is the very ugly workaround to force the tableview to refresh when the data got updated but the UI
     * view still be stuck with old data
     */
    private void songListTableRefresh() {
        songListTable.getColumns().get(0).setVisible(false);
        songListTable.getColumns().get(0).setVisible(true);
    }

    /**
     * Function name:   buildCurrentPlaylistLinear
     * Usage:   this method would be called to build linear playlist (without shuffle)
     *
     * @param id the id number of the starting song (in the table view)
     *           TODO: have a look at repeat mode
     */
    void buildCurrentPlaylistLinear(int id) {
        mainPlayerController.setCurrentPlaylist(new ArrayList<>(100));
        mainPlayerController.setPreviousPlaylist(new ArrayList<>(100));
        (new Thread(() -> {
            for (int i = id + 1; i < mainPlayerController.getSongDatas().size(); i++) {
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

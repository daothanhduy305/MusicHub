package milo.controllers;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
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
    @FXML private TableColumn<SongData, String> songListTableTitle, songListTableArtist, songListTableLength,
            songListTableAlbum, songListTableYear, songListTableGenre;
    @FXML private VBox mHolder;

    private SortedList<SongData> songDataSortedList;
    private boolean isDBSet = false, hasDummy = false;
    private ObservableList<SongData> songDatas;

    @Override
    public void buildUI() {
        songListTable.getStylesheets().clear();
        songListTable.getStylesheets().add(Constants.getCssMainFilePath());
        songListTable.setVisible(false);

        songListTableTitle.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        songListTableArtist.setCellValueFactory(cellData -> cellData.getValue().artistProperty());
        songListTableAlbum.setCellValueFactory(cellData -> cellData.getValue().albumTitleProperty());
        songListTableYear.setCellValueFactory(cellData -> cellData.getValue().yearProperty());
        songListTableGenre.setCellValueFactory(cellData -> cellData.getValue().gerneProperty());

        songListTableLength.setCellValueFactory(cellData -> cellData.getValue().lengthStrProperty());
        songListTableLength.setStyle("-fx-alignment: CENTER-RIGHT;");

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
        songListTableAlbum.setPrefWidth(sizeCalculator.getBigColumnWidth());
        songListTableAlbum.setMaxWidth(sizeCalculator.getBigColumnWidth());
        songListTableYear.setPrefWidth(sizeCalculator.getSmallColumnWidth());
        songListTableYear.setMaxWidth(sizeCalculator.getSmallColumnWidth());
        songListTableGenre.setPrefWidth(sizeCalculator.getSmallColumnWidth());
        songListTableGenre.setMaxWidth(sizeCalculator.getSmallColumnWidth());
        songListTableLength.setPrefWidth(sizeCalculator.getSmallColumnWidth());
        songListTableLength.setMaxWidth(sizeCalculator.getSmallColumnWidth());
    }

    /**
     * Function name:   setDB
     * Usage:   this function is called to set the song list. This can be considered as the result action for the method
     *          of creating database from Settings later. Moreover, this should be done only on init and partly done on
     *          another thread.
     */
    void setDB(ObservableList<SongData> songDatas) {
        if (!isDBSet) {
            this.songDatas = songDatas;
            // TODO: if we want to watch the paths later, then we have to eliminate these dummy on saving
            // TODO: furthermore, be aware that the scrollbar also need to have padding
            songDataSortedList = new SortedList<>(songDatas, (o1, o2) -> {
                if (o1.getTitle().equals(" ")) {
                    return Integer.MAX_VALUE;
                }
                else if (o2.getTitle().equals(" ")) {
                    return Integer.MIN_VALUE;
                }
                else {
                    if (o1.getTitle().compareToIgnoreCase(o2.getTitle()) != 0)
                        return (o1.getTitle().compareToIgnoreCase(o2.getTitle()));
                    else
                        return (o1.getArtist().compareToIgnoreCase(o2.getArtist()));
                }
            });

            songListTable.setItems(songDataSortedList);

            songListTableTitle.setCellFactory(new Callback<TableColumn<SongData, String>, TableCell<SongData, String>>() {
                @Override
                public TableCell<SongData, String> call(TableColumn<SongData, String> param) {
                    return new TableCell<SongData, String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            setText(item);
                            setStyle("");
                            this.setOnMouseClicked(event -> {
                                if (event.getClickCount() == 2) {
                                    mainPlayerController.playSong(songListTable.getSelectionModel().getSelectedItem());
                                    AllSongsViewController.this.buildCurrentPlaylistLinear(
                                            songListTable.getSelectionModel().getSelectedIndex()
                                    );
                                }
                            });
                        }
                    };
                }
            });

            songListTableAlbum.setCellFactory(new Callback<TableColumn<SongData, String>, TableCell<SongData, String>>() {
                @Override
                public TableCell<SongData, String> call(TableColumn<SongData, String> param) {
                    return new TableCell<SongData, String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            setText(item);
                            setStyle("");
                            this.setOnMouseClicked(event -> mainPlayerController.getMainViewPanelController().showAlbum(item));
                        }
                    };
                }
            });
            isDBSet = true;
            Platform.runLater(() -> {
                songListTable.setVisible(true);
                songListTableRefresh();
            });
        } else {
            this.songDatas.addAll(songDatas);
        }
        if (songDatas.size() * songListTable.getFixedCellSize()
                > sizeCalculator.getWindowHeight() - sizeCalculator.getPlayerBarHeight()
                && !hasDummy) {
            songDatas.addAll(SongData.getDummySongData(
                    (int) Math.ceil(sizeCalculator.getPlayerBarHeight() / songListTable.getFixedCellSize())
            ));
            hasDummy = true;
        } else {
            // How to remove dummies?
        }
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
     * Function name:   buildCurrentPlaylistLinear
     * Usage:   this method would be called to build linear playlist (without shuffle)
     *
     * @param id the id number of the starting song (in the table view)
     * TODO: have a look at repeat mode
     */
    private void buildCurrentPlaylistLinear(int id) {
        mainPlayerController.setCurrentPlaylist(new ArrayList<>(100));
        mainPlayerController.setPreviousPlaylist(new ArrayList<>(100));
        (new Thread(() -> {
            for (int i = id + 1; i < songDatas.size(); i++) {
                mainPlayerController.getCurrentPlaylist().add(songListTable.getItems().get(i));
            }
            for (int i = 0; i <= id; i++) {
                mainPlayerController.getCurrentPlaylist().add(songListTable.getItems().get(i));
            }
        })).start();
    }

    TableView<SongData> getSongListTable() {
        return songListTable;
    }
}

package milo.gui.controllers;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import milo.data.SongData;
import milo.data.utils.SongDataComparator;
import milo.gui.controllers.abstractcontrollers.AbstractSubUIController;
import milo.gui.utils.Constants;
import milo.gui.utils.Constants.VIEWS_ID;

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

    private boolean isDBSet = false, hasDummy = false, onInit = true;

    @Override
    public void buildUI() {
        songListTable.getStylesheets().clear();
        songListTable.getStylesheets().add(Constants.getCssMainFilePath());

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

        if (onInit) {
            if (Platform.isFxApplicationThread())
                songListTableRefresh();
            else
                Platform.runLater(this::songListTableRefresh);
            onInit = false;
        }
    }

    /**
     * Function name:   setDB
     * Usage:   this function is called to set the song list. This can be considered as the result action for the method
     *          of creating database from Settings later. Moreover, this should be done only on init and partly done on
     *          another thread.
     */
    void setDB() {
        // TODO: if we want to watch the paths later, then we have to eliminate these dummy on saving
        // TODO: furthermore, be aware that the scrollbar also need to have padding

        songListTable.setItems(new SortedList<>(mainPlayerController.getSongDataObservableList(), new SongDataComparator()));

        songListTable.prefHeightProperty().bind(
                Bindings.size(songListTable.getItems()).multiply(songListTable.getFixedCellSize()).add(2.0)
        );

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
                                mainPlayerController.setViewId(VIEWS_ID.ALL_SONGS);
                                buildPlaylist();
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
                        this.setOnMouseClicked(event -> {
                            SongData currentSongData = (SongData) this.getTableRow().getItem();
                            mainPlayerController.showAlbum(currentSongData.getAlbumTitle() + currentSongData.getAlbumArtist());
                        });
                    }
                };
            }
        });
    }

    /**
     * Function name:   addSong
     * Usage:   this method should be call whenever the albumList is updated with operation add
     * @param songData new Album to add in the list
     */
    void addSong(SongData songData) {
        mainPlayerController.getSongDataObservableList().add(songData);
        if (!hasDummy) {
            if (mainPlayerController.getSongDataObservableList().size() * songListTable.getFixedCellSize()
                    > sizeCalculator.getWindowHeight() - sizeCalculator.getPlayerBarHeight()) {
                mainPlayerController.getSongDataObservableList().addAll(SongData.getDummySongData(
                        (int) Math.ceil(sizeCalculator.getPlayerBarHeight() / songListTable.getFixedCellSize())
                ));
                hasDummy = true;
            }
        }

        /*if (mainPlayerController.isSongLoading())
            mainPlayerController.setSongLoading(false);*/
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
     * Function name:   buildPlaylist
     * Usage:   this method would be called to build playlist when called
     */
    public void buildPlaylist() {
        mainPlayerController.buildPlaylist(songListTable);
    }

    TableView<SongData> getSongListTable() {
        return songListTable;
    }
}

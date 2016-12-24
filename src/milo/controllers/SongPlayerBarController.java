package milo.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import milo.controllers.abstractcontrollers.AbstractSubUIController;
import milo.data.SongData;
import milo.gui.custom.ActionButton;
import milo.gui.utils.Constants;
import milo.gui.utils.GUIUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.images.Artwork;

import java.io.ByteArrayInputStream;

/**
 * Class name:  SongPlayerBarController
 * Description: This controller will control the player bar, which will show some info, album art of the currently
 *              playing song, and control buttons.
 */

public class SongPlayerBarController extends AbstractSubUIController {
    @FXML private HBox playerBox, buttonsBox;
    @FXML private VBox songInfoLabelsBox;
    @FXML private Label songTitleLabel, songArtistLabel, lengthCountLabel, lengthTotalLabel;
    @FXML private ImageView albumArtView;
    @FXML private StackPane albumArtHolder, seekBarHolder;
    @FXML private Slider songSeekBar;
    @FXML private ProgressBar songProgressBar;

    private ActionButton playButton;
    private ActionButton nextButton;
    private ActionButton prevButton;
    private ActionButton settingButton;
    private ActionButton repeatButton;
    private ActionButton shuffleButton;

    private StackPane thumb;
    private boolean thumbChanged = false;

    private long timeStart, timeCurrent, timePassed;
    private Thread setAlbumArtThread, timeTrackingThread;

    @Override
    public void buildUI() {
        // TODO: move the background to Constants Class
        playerBox.setBackground(
                new Background(new BackgroundFill(Color.web("#01589d", 0.95), CornerRadii.EMPTY, Insets.EMPTY))
        );
        albumArtHolder.setVisible(false);

        setUpSeekBar();
        setUpTheButtons();
    }

    private void setUpSeekBar() {
        songSeekBar.getStylesheets().clear();
        songSeekBar.getStylesheets().add(Constants.getCssMainFilePath());
        songProgressBar.getStylesheets().clear();
        songProgressBar.getStylesheets().add(Constants.getCssMainFilePath());

        songSeekBar.setMin(0f);
        songSeekBar.valueChangingProperty().addListener((obs, wasChanging, isNowChanging) -> {
            if (mainPlayerController.getPlayer() != null) {
                if (!isNowChanging) {
                    mainPlayerController.getPlayer().seek(Duration.seconds(songSeekBar.getValue()));
                    lengthCountLabel.setText(
                            GUIUtils.lengthToLengthStr((int) Math.ceil(songSeekBar.getValue()), "0:00")
                    );
                }
            }
        });
        songSeekBar.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (mainPlayerController.getPlayer() != null) {
                if (!songSeekBar.isValueChanging()) {
                    double currentTime = mainPlayerController.getPlayer().getCurrentTime().toSeconds();
                    double sliderTime = newValue.doubleValue();
                    if (Math.abs(currentTime - sliderTime) > 1.0 && !thumbChanged) {
                        mainPlayerController.getPlayer().seek(new Duration(newValue.doubleValue() * 1000.0));
                    }
                    lengthCountLabel.setText(
                            GUIUtils.lengthToLengthStr((int) Math.ceil(songSeekBar.getValue()), "0:00")
                    );
                    if (songSeekBar.isPressed() && !thumbChanged) {
                        songSeekBar.getStylesheets().clear();
                        songSeekBar.getStylesheets().add(Constants.getCssFileThumbClickedPath());
                        thumbChanged = true;
                    }
                } else {
                    lengthCountLabel.setText(
                            GUIUtils.lengthToLengthStr((int) Math.ceil(songSeekBar.getValue()), "0:00")
                    );
                }
                songProgressBar.setProgress(newValue.doubleValue() /
                        (double) mainPlayerController.getCurrentPlayingSong().getLength());
            }
        });
        songSeekBar.setOnMousePressed(event -> {
            if (songSeekBar.isValueChanging()) {
                songSeekBar.getStylesheets().clear();
                songSeekBar.getStylesheets().add(Constants.getCssFileThumbClickedPath());
                thumbChanged = true;
            }
        });
        songSeekBar.setOnMouseReleased(event -> {
            songSeekBar.getStylesheets().clear();
            songSeekBar.getStylesheets().add(Constants.getCssMainFilePath());
            mainPlayerController.getPlayer().seek(new Duration(songSeekBar.getValue() * 1000.0));
            thumbChanged = false;
        });
    }

    private void setUpTheButtons() {
        prevButton = new ActionButton("prev");
        prevButton.setOnMouseClicked(event -> mainPlayerController.playPreviousSong());
        buttonsBox.getChildren().add(prevButton);

        playButton = new ActionButton("play");
        buttonsBox.getChildren().add(playButton);

        nextButton = new ActionButton("next");
        nextButton.setOnMouseClicked(event -> mainPlayerController.playNextSong());
        buttonsBox.getChildren().add(nextButton);

        repeatButton = new ActionButton(mainPlayerController.getSettingsFactory().getRepeatStr());
        repeatButton.setOnMouseClicked(event -> {
            mainPlayerController.getSettingsFactory().switchRepeat();
            repeatButton.replaceButName(mainPlayerController.getSettingsFactory().getRepeatStr());
        });
        buttonsBox.getChildren().add(repeatButton);

        shuffleButton = new ActionButton(mainPlayerController.getSettingsFactory().getShuffleStr());
        shuffleButton.setOnMouseClicked(event -> {
            mainPlayerController.getSettingsFactory().switchShuffle();
            shuffleButton.replaceButName(mainPlayerController.getSettingsFactory().getShuffleStr());
        });
        buttonsBox.getChildren().add(shuffleButton);

        settingButton = new ActionButton("play");
        settingButton.setOnMouseClicked(event -> mainPlayerController.showSettings());
        buttonsBox.getChildren().add(settingButton);
    }

    @Override
    public void refreshUI() {
        playerBox.setPrefHeight(sizeCalculator.getPlayerBarHeight());
        playerBox.setMaxHeight(sizeCalculator.getPlayerBarHeight());

        playerBox.setPadding(new Insets(
                sizeCalculator.getPlayerBarPaddingV(),
                sizeCalculator.getPlayerBarPaddingH(),
                sizeCalculator.getPlayerBarPaddingV(),
                sizeCalculator.getPlayerBarPaddingH()
        ));
        albumArtView.setFitHeight(sizeCalculator.getPlayerBarAlbumArtSize());
        albumArtView.setFitWidth(sizeCalculator.getPlayerBarAlbumArtSize());
        final int delta = 2;
        albumArtHolder.setMaxWidth(sizeCalculator.getPlayerBarAlbumArtSize() + delta);
        albumArtHolder.setPrefWidth(sizeCalculator.getPlayerBarAlbumArtSize() + delta);
        albumArtHolder.setMaxHeight(sizeCalculator.getPlayerBarAlbumArtSize() + delta);
        albumArtHolder.setPrefHeight(sizeCalculator.getPlayerBarAlbumArtSize() + delta);
        songInfoLabelsBox.setPadding(new Insets(
                sizeCalculator.getPlayerBarPaddingV(),
                sizeCalculator.getPlayerBarPaddingH(),
                sizeCalculator.getPlayerBarPaddingV(),
                2 * sizeCalculator.getPlayerBarPaddingH()
        ));
        songInfoLabelsBox.setMaxWidth(sizeCalculator.getSongInfoLabelsBoxW());
        songInfoLabelsBox.setMinWidth(sizeCalculator.getSongInfoLabelsBoxW());
        lengthCountLabel.setMaxWidth(sizeCalculator.getLengthLabelWidth());
        lengthCountLabel.setMinWidth(sizeCalculator.getLengthLabelWidth());
        lengthTotalLabel.setMaxWidth(sizeCalculator.getLengthLabelWidth());
        lengthTotalLabel.setMinWidth(sizeCalculator.getLengthLabelWidth());

        songSeekBar.setMaxWidth(sizeCalculator.getSeekBarWidth());
        songSeekBar.setPrefWidth(sizeCalculator.getSeekBarWidth());
        songProgressBar.setMaxWidth(sizeCalculator.getSeekBarWidth());
        songProgressBar.setPrefWidth(sizeCalculator.getSeekBarWidth());
        seekBarHolder.setMaxWidth(sizeCalculator.getSeekBarWidth());
        seekBarHolder.setPrefWidth(sizeCalculator.getSeekBarWidth());
        seekBarHolder.setPadding(new Insets(
                0, 4 * sizeCalculator.getPlayerBarPaddingH(), 0, 0
        ));

        if (thumb == null)
            thumb = (StackPane) songSeekBar.lookup(".thumb");
    }

    /**
     * Function name:   setupForPlayingMusic
     * Usage:   this function is called when a song is played, to set up the corresponding info on the player bar to the
     * playing song.
     *
     * @param audioFile the song file of the playing song (since the song data doesn't contain the audio file).
     */
    public void setupForPlayingMusic(AudioFile audioFile) {
        SongData currentPlayingSong = mainPlayerController.getCurrentPlayingSong();

        songTitleLabel.setText(currentPlayingSong.getTitle().substring(4, currentPlayingSong.getTitle().length()));
        songArtistLabel.setText(currentPlayingSong.getArtist());
        lengthCountLabel.setText("0:00");
        songProgressBar.setProgress(0f);
        songSeekBar.setValue(0f);
        songSeekBar.setMax((double) currentPlayingSong.getLength());
        lengthTotalLabel.setText(currentPlayingSong.getLengthStr());
        playButton.setOnMouseClicked(event -> mainPlayerController.pausePlaying());
        playButton.replaceButName("pause");
        this.setAlbumArtView(audioFile);
        this.timeStart = System.currentTimeMillis();
        this.timeTrack();
    }

    /**
     * Function name:   stopPlaying
     * Usage:   this function would be called to set up things that are necessary for stopping job
     */
    public void stopPlaying(boolean timeReset) {
        if (timeReset)
            this.timeCurrent = 0;
        killTimeThread();
    }

    /**
     * Function name:   resumePlaying
     * Usage:   this function would be called to set up things that are necessary for resuming job
     */
    public void resumePlaying() {
        timeStart = System.currentTimeMillis() - Math.round(mainPlayerController.getPlayer().getCurrentTime().toMillis());
        timeTrack();
    }

    /**
     * Function name:   setAlbumArtView
     * Usage:   this function is called when a song is played, to set up the corresponding album art.
     * The loading job would be done in background instead.
     *
     * @param audioFile the song file of the playing song (since the song data doesn't contain the audio file).
     */
    private void setAlbumArtView(AudioFile audioFile) {
        setAlbumArtThread = new Thread(() -> {
            try {
                Artwork artwork = audioFile.getTag().getFirstArtwork();
                byte[] rawAlbumArt = artwork != null ? artwork.getBinaryData(): Constants.getDefaultArtworkRaw();
                Platform.runLater(() -> {
                    Image albumArtImage = new Image(
                            new ByteArrayInputStream(rawAlbumArt),
                            sizeCalculator.getPlayerBarHeight() * 1.5,
                            sizeCalculator.getPlayerBarHeight() * 1.5,
                            true, true
                    );
                    albumArtHolder.setVisible(true);
                    albumArtView.setImage(albumArtImage);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        setAlbumArtThread.start();
    }

    /**
     * Function name:   timeTrack
     * Usage:   this function is called when a song is played, to track time and control the seek bar, as well as time
     * labels. The tracking job would be monitored on another thread instead.
     */
    private void timeTrack() {
        timeTrackingThread = new Thread(() -> {
            while (mainPlayerController.isPlaying()) {
                timeCurrent = System.currentTimeMillis();
                timePassed = timeCurrent - timeStart;
                if ((timePassed % 1000) == 0) {
                    Platform.runLater(() -> {
                        if (!songSeekBar.isValueChanging() && !songSeekBar.isPressed()) {
                            songSeekBar.setValue(Math.ceil(mainPlayerController.getPlayer().getCurrentTime().toSeconds()));
                        }
                    });
                }
            }
        });
        timeTrackingThread.start();
    }

    /**
     * Function name:   stopPlaying
     * Usage:   this function would be called to kill the timeTrackingThread
     */
    private void killTimeThread() {
        if (timeTrackingThread != null && timeTrackingThread.isAlive()) {
            try {
                timeTrackingThread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    public ActionButton getPlayButton() {
        return playButton;
    }
}

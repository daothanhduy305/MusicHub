package milo.gui.custom;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import milo.data.AlbumData;
import milo.data.SongData;
import milo.gui.utils.Constants;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.images.Artwork;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Class name:  AlbumTile
 * Description: This is the custom album tile, which would be used to shows albums general info in albums overview
 */

public class AlbumTile extends VBox implements Comparable<AlbumTile> {
    @FXML private ImageView albumArtImageView;
    @FXML private Label albumTitleLabel, albumAuthorLabel;
    @FXML private VBox albumInfoBox;

    private boolean isClicked = false;

    private String albumTitle, albumAuthor;
    private AlbumData albumData;
    private Thread setAlbumArtThread;

    public AlbumTile(AlbumData albumData) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/milo/gui/designs/album_tile.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.setAlbumData(albumData);
        this.setAlbumTitle(albumData.getAlbumTitle());
        this.setAlbumAuthor(albumData.getAlbumAuthor());
        this.buildUI();
    }

    private void buildUI() {
        albumInfoBox.setPrefWidth(Constants.getAlbumOverviewAlbumArtSize());
        albumInfoBox.setPrefWidth(Constants.getAlbumOverviewAlbumArtSize());
        albumTitleLabel.setPrefWidth(Constants.getAlbumOverviewAlbumArtSize());
        albumTitleLabel.setWrapText(true);
        this.setPadding(new Insets(6, 0, 0, 6));
        albumTitleLabel.setText(albumTitle);
        albumAuthorLabel.setText(albumAuthor);
        albumAuthorLabel.setPrefWidth(Constants.getAlbumOverviewAlbumArtSize());
        albumAuthorLabel.setWrapText(true);

        setAlbumArt();
    }

    public void setAlbumArt() {
        albumArtImageView.setFitHeight(Constants.getAlbumOverviewAlbumArtSize());
        albumArtImageView.setFitWidth(Constants.getAlbumOverviewAlbumArtSize());
        if (albumData.getAlbumArtByte() == null) {
            Runnable setAlbumArtTask = () -> {
                try {
                    Artwork artwork = null;
                    if (albumData.getSongList() != null && albumData.getSongList().size() > 0) {
                        for (Map.Entry<String, SongData> songDataEntry : albumData.getSongList().entrySet()) {
                            File song = new File(songDataEntry.getValue().getPath());
                            AudioFile songFile = AudioFileIO.read(song);
                            artwork = songFile.getTag().getFirstArtwork();
                            if (artwork != null)
                                break;
                        }
                    }
                    byte[] rawAlbumArt = artwork != null ? artwork.getBinaryData(): Constants.getDefaultArtworkRaw();
                    Platform.runLater(() -> {
                        Image albumArtImage = new Image(
                                new ByteArrayInputStream(rawAlbumArt),
                                Constants.getAlbumOverviewAlbumArtSize() * 1.5,
                                Constants.getAlbumOverviewAlbumArtSize() * 1.5,
                                true, true
                        );
                        albumArtImageView.setImage(albumArtImage);
                        albumData.setAlbumArtByte(rawAlbumArt);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            setAlbumArtThread = new Thread(setAlbumArtTask);
            setAlbumArtThread.start();
        } else {
            albumArtImageView.setImage(new Image(
                    new ByteArrayInputStream(albumData.getAlbumArtByte()),
                    Constants.getAlbumOverviewAlbumArtSize() * 1.5,
                    Constants.getAlbumOverviewAlbumArtSize() * 1.5,
                    true, true
            ));
        }
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public void setAlbumAuthor(String albumAuthor) {
        this.albumAuthor = albumAuthor;
    }

    public void setAlbumData(AlbumData albumData) {
        this.albumData = albumData;
    }

    public AlbumData getAlbumData() {
        return albumData;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public boolean isClicked() {
        return isClicked;
    }

    @Override
    public int compareTo(AlbumTile o) {
        return (this.albumData.compareTo(o.albumData));
    }
}

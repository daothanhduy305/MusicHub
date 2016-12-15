package milo.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import milo.gui.utils.GUIUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.FieldKey;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Class name:  AlbumData
 * Description: This class is the data-type that holds all info about a specific song, but not the audio file
 */

public class SongData implements Serializable {
    private StringProperty title, artist, path, lengthStr, albumTitle, albumAuthor, gerne, year; // TODO: Add in album support
    private IntegerProperty length;

    public SongData(AudioFile audioFile) throws Exception {
        String songTitle = audioFile.getTag().getFirst(FieldKey.TITLE);

        // In case that the title is empty, we will use the file name as the replacement
        if (songTitle == null || songTitle.isEmpty()) {
            songTitle = audioFile.getFile().getName();
            songTitle = songTitle.substring(0, songTitle.length() - 4);
        }

        this.title = new SimpleStringProperty("    " + songTitle);
        this.artist = new SimpleStringProperty(audioFile.getTag().getFirst(FieldKey.ARTIST));
        this.path = new SimpleStringProperty(audioFile.getFile().getPath());
        this.length = new SimpleIntegerProperty(audioFile.getAudioHeader().getTrackLength());
        this.lengthStr = new SimpleStringProperty(GUIUtils.lengthToLengthStr(this.getLength(), " "));
        this.albumTitle = new SimpleStringProperty(audioFile.getTag().getFirst(FieldKey.ALBUM));
        this.albumAuthor = new SimpleStringProperty(audioFile.getTag().getFirst(FieldKey.ALBUM_ARTIST));
        this.gerne = new SimpleStringProperty(audioFile.getTag().getFirst(FieldKey.GENRE));
        this.year = new SimpleStringProperty(audioFile.getTag().getFirst(FieldKey.YEAR));
    }

    public SongData() {
        this.title = new SimpleStringProperty(" ");
        this.artist = new SimpleStringProperty(" ");
        this.path = new SimpleStringProperty(" ");
        this.length = new SimpleIntegerProperty(0);
        this.lengthStr = new SimpleStringProperty(" ");
        this.albumTitle = new SimpleStringProperty(" ");
        this.albumAuthor = new SimpleStringProperty(" ");
        this.gerne = new SimpleStringProperty("");
        this.year = new SimpleStringProperty(" ");
    }

    public static List<SongData> getDummySongData(int number) {
        return Collections.nCopies(number, new SongData());
    }

    public int getLength() {
        return this.length.get();
    }

    public void setLength(int length) {
        this.length.set(length);
    }

    public IntegerProperty lengthProperty() {
        return length;
    }

    public String getArtist() {
        return this.artist.get();
    }

    public void setArtist(String artist) {
        this.artist.set(artist);
    }

    public StringProperty artistProperty() {
        return artist;
    }

    public String getLengthStr() {
        return this.lengthStr.get();
    }

    public void setLengthStr(String lengthStr) {
        this.lengthStr.set(lengthStr);
    }

    public StringProperty lengthStrProperty() {
        return lengthStr;
    }

    public String getPath() {
        return this.path.get();
    }

    public StringProperty pathProperty() {
        return path;
    }

    public String getTitle() {
        return this.title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getAlbumTitle() {
        return albumTitle.get();
    }

    public StringProperty albumTitleProperty() {
        return albumTitle;
    }

    public String getAlbumAuthor() {
        return albumAuthor.get();
    }

    public StringProperty albumAuthorProperty() {
        return albumAuthor;
    }

    public String getGerne() {
        return gerne.get();
    }

    public StringProperty gerneProperty() {
        return gerne;
    }

    public String getYear() {
        return year.get();
    }

    public StringProperty yearProperty() {
        return year;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeUTF(title.get());
        s.writeUTF(artist.get());
        s.writeUTF(path.get());
        s.writeInt(length.get());
        s.writeUTF(albumTitle.get());
        s.writeUTF(albumAuthor.get());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        title = new SimpleStringProperty(s.readUTF());
        artist = new SimpleStringProperty(s.readUTF());
        path = new SimpleStringProperty(s.readUTF());
        length = new SimpleIntegerProperty(s.readInt());
        this.lengthStr = new SimpleStringProperty(GUIUtils.lengthToLengthStr(this.getLength(), " "));
        albumTitle = new SimpleStringProperty(s.readUTF());
        albumAuthor = new SimpleStringProperty(s.readUTF());
    }
}

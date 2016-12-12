package milo.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class name:  AlbumData
 * Description: This class is the data-type that holds all info about a specific album
 */

public class AlbumData implements Serializable, Comparable<AlbumData> {
    private String mTitle, mAuthor;
    private List<SongData> mSongList;
    private byte[] albumArtByte;

    public AlbumData(String albumTitle, String albumAuthor) {
        this.mAuthor = albumAuthor;
        this.mTitle = albumTitle;
        mSongList = new ArrayList<>(20);
    }

    public String getAlbumAuthor() {
        return mAuthor;
    }

    public void setAlbumAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getAlbumTitle() {
        return mTitle;
    }

    public void setAlbumTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public List<SongData> getSongList() {
        return mSongList;
    }

    public byte[] getAlbumArtByte() {
        return albumArtByte;
    }

    public void setAlbumArtByte(byte[] albumArtByte) {
        this.albumArtByte = albumArtByte;
    }

    @Override
    public int compareTo(AlbumData o) {
        return (this.mTitle.compareToIgnoreCase(o.mTitle));
    }
}

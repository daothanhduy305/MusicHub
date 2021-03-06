package milo.data;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class name:  AlbumData
 * Description: This class is the data-type that holds all info about a specific album
 */

public class AlbumData implements Serializable, Comparable<AlbumData> {
    private String mTitle, mArtist;
    private Map<String, SongData> mSongList;
    private byte[] albumArtByte;

    public AlbumData(String albumTitle, String albumAuthor) {
        this.mArtist = albumAuthor;
        this.mTitle = albumTitle;
        mSongList = new TreeMap<>();
    }

    public String getAlbumArtist() {
        return mArtist;
    }

    public void setAlbumAuthor(String mAuthor) {
        this.mArtist = mAuthor;
    }

    public String getAlbumTitle() {
        return mTitle;
    }

    public void setAlbumTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Map<String, SongData> getSongList() {
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

package milo.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class name:  SettingsData
 * Description: This is the data-type for settings
 */

public class SettingsData implements Serializable{
    private List<SongData> songDatas;
    private List<String> pathList;
    private Map<String, AlbumData> albumDataMap;
    private SongData lastPlayedSong;
    private boolean isShuffle = false, isRepeat = false;

    public void initData() {
        songDatas = new ArrayList<>(100);
        albumDataMap = new TreeMap<>();
    }

    public List<SongData> getSongDatas() {
        return songDatas;
    }

    public Map<String, AlbumData> getAlbumDataMap() {
        return albumDataMap;
    }

    public List<String> getPathList() {
        return pathList;
    }

    public void setPathList(List<String> pathList) {
        this.pathList = pathList;
    }

    public void setLastPlayedSong(SongData lastPlayedSong) {
        this.lastPlayedSong = lastPlayedSong;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public void setRepeat(boolean repeat) {
        isRepeat = repeat;
    }

    public boolean isShuffle() {
        return isShuffle;
    }

    public void setShuffle(boolean shuffle) {
        isShuffle = shuffle;
    }
}

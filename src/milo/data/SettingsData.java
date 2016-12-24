package milo.data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class name:  SettingsData
 * Description: This is the data-type for settings
 */

public class SettingsData implements Serializable{
    private Map<String, SongData> songDatas;
    private List<String> pathList;
    private Map<String, AlbumData> albumDataMap;
    private SongData lastPlayedSong;
    private boolean isShuffle = false, isRepeat = false;
    private String shuffleStr = "shuffle_dis", repeatStr = "repeat_dis";

    public void initData() {
        songDatas = new TreeMap<>();
        albumDataMap = new TreeMap<>();
    }

    public Map<String, SongData> getSongDatas() {
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

    public void switchRepeat() {
        if (isRepeat && repeatStr.equalsIgnoreCase("repeat_all"))
            repeatStr = "repeat_one";
        else {
            isRepeat = !isRepeat;
            repeatStr = isRepeat? "repeat_all" : "repeat_dis";
        }
    }

    public boolean isShuffle() {
        return isShuffle;
    }

    public void switchShuffle() {
        isShuffle = !isShuffle;
        shuffleStr = isShuffle? "shuffle_ac" : "shuffle_dis";
    }

    public String getShuffleStr() {
        return shuffleStr;
    }

    public String getRepeatStr() {
        return repeatStr;
    }
}

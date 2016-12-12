package milo.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class name:  SettingsData
 * Description: This is the data-type for settings
 */

public class SettingsData implements Serializable{
    private List<SongData> songDatas;
    private SongData lastPlayedSong;
    private boolean isShuffle = false, isRepeat = false;

    public List<SongData> getSongDatas() {
        return songDatas;
    }

    public void initData() {
        songDatas = new ArrayList<>(100);
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

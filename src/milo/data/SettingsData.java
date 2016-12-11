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
    private boolean isShuffle = false, isRepeat = false;

    public List<SongData> getSongDatas() {
        return songDatas;
    }

    public void initData() {
        songDatas = new ArrayList<>(100);
    }
}

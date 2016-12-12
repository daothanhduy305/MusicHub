package milo.gui.utils;

import javafx.application.Platform;
import milo.controllers.MainPlayerController;
import milo.controllers.utils.LOG;
import milo.data.AlbumData;
import milo.data.SettingsData;
import milo.data.SongData;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static milo.gui.utils.Utils.getSongsFromDir;

/**
 * Class name:  SettingsFactory
 * Description: This class serves as a manager modal for settings
 */

public class SettingsFactory {
    private SettingsData settingsData;
    private MainPlayerController mainPlayerController;

    public SettingsFactory(MainPlayerController mainPlayerController) {
        this.mainPlayerController = mainPlayerController;
        this.loadSettings();
    }

    /**
     * Function name:   loadSettings
     * Usage:   this method would be called to either load former settings or create a new one
     */
    private void loadSettings() {
        try {
            FileInputStream settingsFileIS = new FileInputStream("proprietary/data/settings/milo_set");
            ObjectInputStream settingsDataIS = new ObjectInputStream(settingsFileIS);
            settingsData = (SettingsData) settingsDataIS.readObject();
            mainPlayerController.setDB(settingsData.getSongDatas(), settingsData.getAlbumDataMap());
            LOG.w(getClass() + ": Finished loading settings");
        } catch (Exception e) {
            settingsData = new SettingsData();
            settingsData.initData();
            // TODO: this is just a test path, it is necessary to be removed and replaced with users' path(s)
            this.createDB("TestSongs", settingsData.getSongDatas(), settingsData.getAlbumDataMap());
        }
    }

    /**
     * Function name:   createDB
     * Usage:   this method would be called to create the database for songs for the first time database set
     *
     * @param dirPath directory that contains song files
     * @param songList database
     */
    private void createDB(String dirPath, List<SongData> songList, Map<String, AlbumData> albumDataMap) {
        final File directory = new File(dirPath);
        List<File> filesList = new ArrayList<>(100);
        if (directory.isDirectory()) {
            Thread createDBThread = new Thread(() -> {
                getSongsFromDir(directory, filesList);

                for (File songFile : filesList) {
                    try {
                        AudioFile song = AudioFileIO.read(songFile);
                        SongData songData = new SongData(song);
                        songList.add(songData);
                        if (albumDataMap.get(songData.getAlbumTitle()) == null) {
                            AlbumData albumData = new AlbumData(
                                    songData.getAlbumTitle(),
                                    songData.getAlbumAuthor()
                            );
                            if (albumData.getAlbumTitle() == null || albumData.getAlbumTitle().compareTo("") == 0)
                                albumData.setAlbumTitle("Unknown");
                            if (albumData.getAlbumAuthor() == null || albumData.getAlbumAuthor().compareTo("") == 0) {
                                if (songData.getArtist() == null || songData.getArtist().compareTo("") == 0)
                                    albumData.setAlbumAuthor("Unknown");
                                else
                                    albumData.setAlbumAuthor(songData.getArtist());
                            }
                            albumDataMap.put(songData.getAlbumTitle(), albumData);
                        }
                        albumDataMap.get(songData.getAlbumTitle()).getSongList().add(songData);
                    } catch (Exception error) {
                        error.printStackTrace();
                    }
                }
                Platform.runLater(() -> mainPlayerController.setDB(songList, albumDataMap));
                saveSettings();
            });
            createDBThread.start();
        }
    }

    /**
     * Function name:   saveSettings
     * Usage:   this method would be called to save the current settings
     */
    public void saveSettings() {
        try {
            FileOutputStream settingsFileOS = new FileOutputStream("proprietary/data/settings/milo_set");
            ObjectOutputStream settingsDataOS = new ObjectOutputStream(settingsFileOS);
            settingsDataOS.writeObject(settingsData);
            settingsFileOS.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getRepeatModeStatus() {
        return settingsData.isRepeat();
    }

    public void setRepeatModeStatus(boolean newStatus) {
        settingsData.setRepeat(newStatus);
    }

    public boolean getShuffleModeStatus() {
        return settingsData.isShuffle();
    }

    public void setShuffleModeStatus(boolean newStatus) {
        settingsData.setShuffle(newStatus);
    }

    public void setPlayingSong(SongData playingSong) {
        settingsData.setLastPlayedSong(playingSong);
    }

    public SettingsData getSettingsData() {
        return settingsData;
    }
}

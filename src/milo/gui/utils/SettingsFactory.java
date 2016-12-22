package milo.gui.utils;

import javafx.application.Platform;
import milo.controllers.MainPlayerController;
import milo.controllers.utils.LOG;
import milo.data.AlbumData;
import milo.data.SettingsData;
import milo.data.SongData;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
     * TODO:    take care of loading settings and loading/creating database separately
     */
    private void loadSettings() {
        try {
            FileInputStream settingsFileIS = new FileInputStream("proprietary/data/settings/milo_set");
            ObjectInputStream settingsDataIS = new ObjectInputStream(settingsFileIS);
            settingsData = (SettingsData) settingsDataIS.readObject();
            mainPlayerController.setDB(settingsData.getSongDatas(), settingsData.getAlbumDataMap());
            LOG.w( ": Finished loading settings");
        } catch (Exception e) {
            settingsData = new SettingsData();
            settingsData.initData();
        }
    }

    /**
     * Function name:   createDB
     * Usage:   this method would be called to create the database for songs for the first time database set
     *
     * @param dirPath directory that contains song files
     * @param songList database
     */
    private void createDB(String dirPath, Map<String, SongData> songList, Map<String, AlbumData> albumDataMap) {
        final File directory = new File(dirPath);
        List<File> filesList = new ArrayList<>(100);
        if (directory.isDirectory()) {
            new Thread(() -> {
                getSongsFromDir(directory, filesList);

                for (File songFile : filesList) {
                    try {
                        AudioFile song = AudioFileIO.read(songFile);
                        SongData songData = new SongData(song);
                        songList.put(songData.getPath(), songData);
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
                        albumDataMap.get(songData.getAlbumTitle()).getSongList().put(songData.getPath(), songData);
                    } catch (Exception error) {
                        error.printStackTrace();
                    }
                }
                settingsData.getSongDatas().putAll(songList);
                settingsData.getAlbumDataMap().putAll(albumDataMap);
                Platform.runLater(() -> mainPlayerController.setDB(settingsData.getSongDatas(), settingsData.getAlbumDataMap()));
                saveSettings();
                LOG.w(": Finished creating database");
            }).run();
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

    /**
     * Function name:   addPath
     * Usage:   This method would add the folder into the back-end database
     * @param path the path to be monitored
     */
    public boolean addPath(String path) {
        if (settingsData.getPathList() == null) {
            settingsData.setPathList(new ArrayList<>(5));
        }
        if (settingsData.getPathList().indexOf(path) == -1) {
            settingsData.getPathList().add(path);
            createDB(path, new TreeMap<>(), new TreeMap<>());
            return true;
        }
        return false;
    }

    /**
     * Function name:   removePath
     * Usage:   This method would remove the folder into the back-end database
     * @param path the path to be removed
     */
    public void removePath(String path) {
        settingsData.getPathList().remove(path);
        final File directory = new File(path);
        List<File> filesList = new ArrayList<>(100);
        new Thread(() -> {
            getSongsFromDir(directory, filesList);
            for (File file : filesList) {
                String filePath = file.getPath();
                settingsData.getSongDatas().remove(filePath);
                String albumName = "";
                try {
                    albumName = AudioFileIO.read(file).getTag().getFirst(FieldKey.ALBUM) == null?
                             "Unknown" : AudioFileIO.read(file).getTag().getFirst(FieldKey.ALBUM);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                settingsData.getAlbumDataMap().get(albumName).getSongList().remove(filePath);
            }
            Platform.runLater(() -> mainPlayerController.setDB(settingsData.getSongDatas(), settingsData.getAlbumDataMap()));
            saveSettings();
        }).run();
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

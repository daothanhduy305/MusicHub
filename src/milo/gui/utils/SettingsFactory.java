package milo.gui.utils;

import milo.controllers.MainPlayerController;
import milo.controllers.utils.LOG;
import milo.data.SettingsData;
import milo.data.SongData;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
            mainPlayerController.setDB(settingsData.getSongDatas());
            LOG.w("Finished loading data");
        } catch (Exception e) {
            settingsData = new SettingsData();
            settingsData.initData();
            this.createDB("TestSongs", settingsData.getSongDatas());
        }
    }

    /**
     * Function name:   createDB
     * Usage:   this method would be called to create the database for songs for the first time database set
     *
     * @param dirPath directory that contains song files
     * @param songList database
     */
    private void createDB(String dirPath, List<SongData> songList) {
        final File directory = new File(dirPath);
        List<File> filesList = new ArrayList<>(100);
        if (directory.isDirectory()) {
            Thread createDBThread = new Thread(() -> {
                getSongsFromDir(directory, filesList);

                for (File songFile : filesList) {
                    try {
                        AudioFile song = AudioFileIO.read(songFile);
                        songList.add(new SongData(song));
                    } catch (Exception error) {
                        error.printStackTrace();
                    }
                }
                mainPlayerController.setDB(settingsData.getSongDatas());
                // TODO: call setDB also for albums view when it is implemented
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

    public SettingsData getSettingsData() {
        return settingsData;
    }
}

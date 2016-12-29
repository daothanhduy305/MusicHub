package milo.gui.utils;

import javafx.concurrent.Task;
import milo.data.SettingsData;
import milo.data.SongData;
import milo.gui.controllers.MainPlayerController;
import milo.gui.controllers.SettingsController;
import milo.gui.controllers.utils.LOG;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static milo.gui.utils.Utils.getSongsFromDir;
import static milo.gui.utils.Utils.removeSongsFromDir;

/**
 * Class name:  SettingsFactory
 * Description: This class serves as a manager modal for settings
 */

public class SettingsFactory {
    volatile private SettingsData settingsData;
    private MainPlayerController mainPlayerController;
    private SettingsController settingsController;

    public SettingsFactory(MainPlayerController mainPlayerController) {
        this.mainPlayerController = mainPlayerController;
    }

    /**
     * Function name:   loadSettings
     * Usage:   this method would be called to either load former settings or create a new one
     * TODO:    take care of loading settings and loading/creating database separately
     */
    public void loadSettings() {
        try {
            FileInputStream settingsFileIS = new FileInputStream("proprietary/data/settings/milo_set");
            ObjectInputStream settingsDataIS = new ObjectInputStream(settingsFileIS);
            settingsData = (SettingsData) settingsDataIS.readObject();
            new Thread(new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    setDB();
                    return null;
                }
            }).start();
            LOG.w( "Finished loading settings");
        } catch (Exception e) {
            settingsData = new SettingsData();
            settingsData.initData();
            mainPlayerController.setSongLoading(false);
            mainPlayerController.setAlbumLoading(false);
            mainPlayerController.setLoadingState();
        }
    }

    /**
     * Function name:   createDB
     * Usage:   this method would be called to create the database for songs for the first time database set
     *
     * @param dirPath directory that contains song files
     */
    public void createDB(String dirPath) {
        final File directory = new File(dirPath);
        getSongsFromDir(directory, settingsData.getSongDatas(), settingsData.getAlbumDataMap());
        LOG.w("Finished creating database");
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
            List<String> childrenPath = new ArrayList<>(5);
            for (String pathInList : settingsData.getPathList()) {
                if (pathInList.contains(path)) { // Adding path is the parent of one or many former paths
                    settingsController.removePathTile(pathInList);
                    childrenPath.add(pathInList);
                }
            }
            for (String pathInList : settingsData.getPathList()) {
                if (path.contains(pathInList)) { // Adding path is the child of one of the paths in list
                    return false;
                }
            }
            settingsData.getPathList().removeAll(childrenPath);
            settingsData.getPathList().add(path);
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
        removeSongsFromDir(directory, settingsData);
        //saveSettings();
    }

    public void setPlayingSong(SongData playingSong) {
        settingsData.setLastPlayedSong(playingSong);
    }

    public SettingsData getSettingsData() {
        return settingsData;
    }

    public void setSettingsController(SettingsController settingsController) {
        this.settingsController = settingsController;
    }

    public String getShuffleStr() {
        return settingsData.getShuffleStr();
    }

    public void switchShuffle() {
        settingsData.switchShuffle();
        saveSettings();
    }

    public String getRepeatStr() {
        return settingsData.getRepeatStr();
    }

    public void switchRepeat() {
        settingsData.switchRepeat();
        saveSettings();
    }

    public boolean isShuffle() {
        return settingsData.isShuffle();
    }

    public boolean isRepeat() {
        return settingsData.isRepeat();
    }

    public void setDB() {
        mainPlayerController.setDB();
    }
}

package milo.gui.utils;

import milo.data.AlbumData;
import milo.data.SettingsData;
import milo.data.SongData;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import java.io.File;
import java.util.Map;

/**
 * Class name:  Utils
 * Description: This class contains some utilities for other classes, mainly logical ones
 */

public class Utils {

    /**
     * Function name:   isSong
     * Usage:   this method would be called to check whether a file is a song file or not.
     *
     * @param file file to check
     * TODO: support more formats
     */
    public static boolean isSong(File file) {
        String filePath = file.getAbsolutePath();
        filePath = filePath.toLowerCase();
        return (
                filePath.endsWith(".mp3")
                        || filePath.endsWith(".m4a")
        );
    }

    /**
     * Function name:   getSongsFromDir
     * Usage:   this method would be called to load the song files form a directory into a list.
     *
     * @param directory directory path
     * @param songList song list
     * @param albumDataMap album list
     */
    public static void getSongsFromDir(File directory, Map<String, SongData> songList,
                                       Map<String,AlbumData> albumDataMap) {
        File[] filesInDir = directory.listFiles();
        for (File file : filesInDir) {
            if (!file.isDirectory()) {
                if (isSong(file)) {
                    try {
                        AudioFile song = AudioFileIO.read(file);
                        SongData songData = new SongData(song);
                        songList.put(songData.getPath(), songData);
                        String albumKey = songData.getAlbumTitle() + songData.getAlbumArtist();
                        if (albumDataMap.get(songData.getAlbumTitle()) == null) {
                            AlbumData albumData = new AlbumData(
                                    songData.getAlbumTitle(),
                                    songData.getAlbumArtist()
                            );
                            albumDataMap.put(albumKey, albumData);
                        }
                        albumDataMap.get(albumKey).getSongList().put(songData.getPath(), songData);
                    } catch (Exception error) {
                        error.printStackTrace();
                    }
                }
            } else
                getSongsFromDir(file, songList, albumDataMap);
        }
    }

    /**
     * Function name:   getSongsFromDir
     * Usage:   this method would be called to load the song files form a directory into a list.
     *
     * @param directory directory path
     * @param settingsData setting data
     */
    public static void removeSongsFromDir(File directory, SettingsData settingsData) {
        File[] filesInDir = directory.listFiles();
        assert filesInDir != null;
        for (File file : filesInDir) {
            if (!file.isDirectory()) {
                if (isSong(file)) {
                    settingsData.getSongDatas().remove(file.getPath());
                    try {
                        SongData currentSongInfo = new SongData(AudioFileIO.read(file));
                        String albumKey = currentSongInfo.getAlbumTitle() + currentSongInfo.getAlbumArtist();
                        settingsData.getAlbumDataMap().get(albumKey).getSongList().remove(file.getPath());
                        if (settingsData.getAlbumDataMap().get(albumKey).getSongList().size() == 0)
                            settingsData.getAlbumDataMap().remove(albumKey);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else
                removeSongsFromDir(file, settingsData);
        }
    }
}

package milo.gui.utils;

import java.io.File;
import java.util.List;

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
     * @param fileList list to load into
     */
    public static void getSongsFromDir(File directory, List<File> fileList) {
        if (directory.exists()) {
            if (directory.isFile()) {
                if (isSong(directory))
                    fileList.add(directory);
            } else {
                File[] filesInDir = directory.listFiles();
                for (File file : filesInDir) {
                    if (!file.isDirectory()) {
                        if (isSong(file)) {
                            fileList.add(file);
                        }
                    } else
                        getSongsFromDir(file, fileList);
                }
            }
        }
    }
}

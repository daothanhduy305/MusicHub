package milo.gui.utils;

import java.io.File;

/**
 * Class name:  Constants
 * Description: This class will hold every constants that are used in the code
 *              The constants will be fetched through the getters instead of being directly implemented as public static
 * TODO: Some of these constants will need to be tweaked later, especially ones used for size
 */

public class Constants {
    private final static File CSS_FILE = new File("css/music_hub.css");
    private final static String CSS_PATH = "file:///" + CSS_FILE.getAbsolutePath().replace("\\", "/");
    private final static File CSS_FILE_THUMB_CLICKED = new File("css/thumb_clicked.css");
    private final static String CSS_FILE_THUMB_CLICKED_PATH = "file:///" + CSS_FILE_THUMB_CLICKED.getAbsolutePath().replace("\\", "/");
    private final static File DEFAULT_ARTWORK = new File("proprietary/data/media/aw/milo_dfaw");
    private final static String BUTTONS_PRE_PATH = "file:proprietary/data/media/buts/";
    private final static String IMAGE_EXTENSION = ".png";

    private final static double PLAYER_BAR_PADDING_V = GUIUtils.getScreenHeight() / 125;
    private final static double PLAYER_BAR_PADDING_H = GUIUtils.getScreenWidth() / 350;
    private final static double PLAYER_BAR_HEIGHT = GUIUtils.getScreenHeight() / 10.0;
    private final static double PLAYER_BAR_ALBUM_ART_SIZE = PLAYER_BAR_HEIGHT - 2 * PLAYER_BAR_PADDING_V;

    public static String getCssPath() {
        return CSS_PATH;
    }

    public static String getCssFileThumbClickedPath() {
        return CSS_FILE_THUMB_CLICKED_PATH;
    }

    public static File getDefaultArtwork() {
        return DEFAULT_ARTWORK;
    }

    public static String getImageExtension() {
        return IMAGE_EXTENSION;
    }

    public static String getButtonsPrePath() {
        return BUTTONS_PRE_PATH;
    }

    public static double getPlayerBarHeight() {
        return PLAYER_BAR_HEIGHT;
    }

    public static double getPlayerBarAlbumArtSize() {
        return PLAYER_BAR_ALBUM_ART_SIZE;
    }
}

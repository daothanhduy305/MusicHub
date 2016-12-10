package milo.gui.utils;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.io.File;

/**
 * Class name:  Constants
 * Description: This class will hold every constants that are used in the code
 *              The constants will be fetched through the getters instead of being directly implemented as public static
 * TODO: Some of these constants will need to be tweaked later, especially ones used for size
 */

public class Constants {
    private final static File CSS_MAIN_FILE = new File("css/music_hub.css");
    private final static String CSS_MAIN_FILE_PATH = "file:///" + CSS_MAIN_FILE.getAbsolutePath().replace("\\", "/");
    private final static File CSS_FILE_THUMB_CLICKED = new File("css/thumb_clicked.css");
    private final static String CSS_FILE_THUMB_CLICKED_PATH = "file:///" + CSS_FILE_THUMB_CLICKED.getAbsolutePath().replace("\\", "/");
    private final static File DEFAULT_ARTWORK = new File("proprietary/data/media/aw/milo_dfaw");
    private final static String BUTTONS_PRE_PATH = "file:proprietary/data/media/buts/";
    private final static String IMAGE_EXTENSION = ".png";
    private final static Background BG_GRAYER = new Background(new BackgroundFill(
            Color.web("#dadada"), CornerRadii.EMPTY, Insets.EMPTY
    ));

    private final static double PLAYER_BAR_PADDING_V = GUIUtils.getScreenHeight() / 125;
    private final static double PLAYER_BAR_PADDING_H = GUIUtils.getScreenWidth() / 350;
    private final static double PLAYER_BAR_HEIGHT = GUIUtils.getScreenHeight() / 10.0;
    private final static double PLAYER_BAR_ALBUM_ART_SIZE = PLAYER_BAR_HEIGHT - 2 * PLAYER_BAR_PADDING_V;

    private final static double NAVIGATION_DRAWER_WIDTH = GUIUtils.getScreenWidth() / 4.25;
    private final static double NAVIGATION_DRAWER_BUTTON_HEIGHT = GUIUtils.getScreenHeight() * 0.06;
    private final static double NAVIGATION_DRAWER_PADDING_H = GUIUtils.getScreenWidth() * 0.014;
    private final static double NAVIGATION_DRAWER_PADDING_V = 0;

    public static String getCssMainFilePath() {
        return CSS_MAIN_FILE_PATH;
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

    public static double getPlayerBarPaddingH() {
        return PLAYER_BAR_PADDING_H;
    }

    public static double getPlayerBarPaddingV() {
        return PLAYER_BAR_PADDING_V;
    }

    public static double getNavigationDrawerWidth() {
        return NAVIGATION_DRAWER_WIDTH;
    }

    public static double getNavigationDrawerButtonHeight() {
        return NAVIGATION_DRAWER_BUTTON_HEIGHT;
    }

    public static double getNavigationDrawerPaddingH() {
        return NAVIGATION_DRAWER_PADDING_H;
    }

    public static double getNavigationDrawerPaddingV() {
        return NAVIGATION_DRAWER_PADDING_V;
    }

    public static Background getBgGrayer() {
        return BG_GRAYER;
    }
}

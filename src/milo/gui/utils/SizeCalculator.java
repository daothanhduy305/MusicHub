package milo.gui.utils;

import javafx.scene.Scene;

/**
 * Class name:  SizeCalculator
 * Description: This class will be called on resizing to tuned the necessary sizes to adapt to the scene's sizes
 *              dynamically
 * TODO: Some of these (or their names) will need to be tweaked later
 */

public class SizeCalculator {
    private static double playerBarHeight = Constants.getPlayerBarHeight();
    private Scene theScene;
    private double playerBarPaddingV = Constants.getPlayerBarPaddingV();
    private double playerBarPaddingH = Constants.getPlayerBarPaddingH();
    private double playerBarAlbumArtSize = Constants.getPlayerBarAlbumArtSize();
    private double songInfoLabelsBoxW;
    private double seekBarWidth;
    private double lengthLabelWidth;

    private double navigationDrawerWidth;

    private double mainViewPanelWidth;
    private double mainPanelPaddingWidth;
    private double songTableWidth;
    private double bigColumnWidth;
    private double smallColumnWidth;

    public SizeCalculator(Scene scene) {
        this.theScene = scene;
        this.calibrate();
    }

    public static double getPlayerBarHeight() {
        return playerBarHeight;
    }

    /**
     * Function name:   calibrate
     * Usage:   this function is called on size update
     * TODO: implement the method
     */
    public void calibrate() {
        navigationDrawerWidth = theScene.getWidth() / 4.25;

        mainViewPanelWidth = theScene.getWidth() - navigationDrawerWidth;
        mainPanelPaddingWidth = mainViewPanelWidth / 50;
        songTableWidth = mainViewPanelWidth - 2 * mainPanelPaddingWidth;
        bigColumnWidth = (4.7 * songTableWidth) / 10;
        smallColumnWidth = (songTableWidth - 2 * bigColumnWidth) * 0.9;

        songInfoLabelsBoxW = (theScene.getWidth() - (playerBarAlbumArtSize + 4 + 2 * playerBarPaddingH)) / 5;
        lengthLabelWidth = songInfoLabelsBoxW * 0.15;
        seekBarWidth = songInfoLabelsBoxW * 3 - 2 * lengthLabelWidth - 2 * playerBarPaddingH;
    }

    public double getBigColumnWidth() {
        return bigColumnWidth;
    }

    public double getMainPanelPaddingWidth() {
        return mainPanelPaddingWidth;
    }

    public double getSmallColumnWidth() {
        return smallColumnWidth;
    }

    public double getSongTableWidth() {
        return songTableWidth;
    }

    public double getPlayerBarAlbumArtSize() {
        return playerBarAlbumArtSize;
    }

    public double getLengthLabelWidth() {
        return lengthLabelWidth;
    }

    public double getMainViewPanelWidth() {
        return mainViewPanelWidth;
    }

    public double getNavigationDrawerWidth() {
        return navigationDrawerWidth;
    }

    public double getPlayerBarPaddingH() {
        return playerBarPaddingH;
    }

    public double getPlayerBarPaddingV() {
        return playerBarPaddingV;
    }

    public double getSeekBarWidth() {
        return seekBarWidth;
    }

    public double getSongInfoLabelsBoxW() {
        return songInfoLabelsBoxW;
    }
}

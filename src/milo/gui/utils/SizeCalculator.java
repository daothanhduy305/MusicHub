package milo.gui.utils;

import javafx.scene.Scene;

/**
 * Class name:  SizeCalculator
 * Description: This class will be called on resizing to tuned the necessary sizes to adapt to the mScene's sizes
 *              dynamically
 * TODO: Some of these (or their names) will need to be tweaked later
 */

public class SizeCalculator {
    private Scene mScene;

    private double playerBarHeight = Constants.getPlayerBarHeight();
    private double playerBarPaddingV = Constants.getPlayerBarPaddingV();
    private double playerBarPaddingH = Constants.getPlayerBarPaddingH();
    private double playerBarAlbumArtSize = Constants.getPlayerBarAlbumArtSize();
    private double songInfoLabelsBoxW;
    private double seekBarWidth;
    private double lengthLabelWidth = 40.0;
    private double buttonWidth = Constants.getPlayerBarAlbumArtSize() * 0.6;

    private double navigationDrawerWidth = Constants.getNavigationDrawerWidth();
    private double navigationDrawerButtonHeight = Constants.getNavigationDrawerButtonHeight();
    private double navigationDrawerPaddingH = Constants.getNavigationDrawerPaddingH();
    private double navigationDrawerPaddingV = Constants.getNavigationDrawerPaddingV();

    private double mainViewPanelWidth;
    private double mainViewPanelPaddingWidth;
    private double songTableWidth;
    private double bigColumnWidth;
    private double smallColumnWidth;

    public SizeCalculator(Scene scene) {
        this.mScene = scene;
        this.calibrate();
    }

    /**
     * Function name:   calibrate
     * Usage:   this function is called on size update
     * TODO: implement the method
     */
    public void calibrate() {
        mainViewPanelWidth = mScene.getWidth() - navigationDrawerWidth;
        mainViewPanelPaddingWidth = mainViewPanelWidth / 50;
        songTableWidth = mainViewPanelWidth /*- 2 * mainViewPanelPaddingWidth*/;
        bigColumnWidth = (0.24 * songTableWidth) - 1.5;
        smallColumnWidth = ((songTableWidth - 3.0 * bigColumnWidth) / 3.0) - 1.5;

        songInfoLabelsBoxW = navigationDrawerWidth - playerBarAlbumArtSize;
        seekBarWidth = mScene.getWidth() - songInfoLabelsBoxW - 2 * lengthLabelWidth - 14 * playerBarPaddingH - 6 * buttonWidth;
    }

    public double getWindowWidth() {
        return mScene.getWidth();
    }

    public double getWindowHeight() {
        return mScene.getHeight();
    }

    public double getBigColumnWidth() {
        return bigColumnWidth;
    }

    public double getMainViewPanelPaddingWidth() {
        return mainViewPanelPaddingWidth;
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

    public double getPlayerBarHeight() {
        return playerBarHeight;
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

    public double getNavigationDrawerPaddingV() {
        return navigationDrawerPaddingV;
    }

    public double getNavigationDrawerButtonHeight() {
        return navigationDrawerButtonHeight;
    }

    public double getNavigationDrawerPaddingH() {
        return navigationDrawerPaddingH;
    }
}

package milo.gui.utils;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 * Class name:  GUIUtils
 * Description: This class contains utilities methods support GUI building
 */

public class GUIUtils {

    private static final Rectangle2D monitorShape = Screen.getPrimary().getVisualBounds();

    public static double getScreenHeight() {
        return monitorShape.getHeight();
    }

    public static double getScreenWidth() {
        return monitorShape.getWidth();
    }

    /**
     * Function name:   lengthToLengthStr
     * Usage:   to convert time in integer to string under the format min:sec
     *
     * @param   time    the input for time to be converted
     * @param   defaultString   the default String to be returned when time = 0
     * @return  either a string correspond to the time or the default string when time = 0
     */
    public static String lengthToLengthStr(long time, String defaultString) {
        String lengthInString;
        if (time > 0) {
            lengthInString = "" + (time / 60) + ':'
                    + ((time % 60) < 10 ? '0' : "")
                    + (time % 60);
            return lengthInString;
        }
        return defaultString;
    }
}

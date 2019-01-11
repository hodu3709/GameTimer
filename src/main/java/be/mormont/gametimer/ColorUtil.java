package be.mormont.gametimer;

import javafx.scene.paint.Color;

/**
 * Date: 11-01-19
 * By  : Mormont Romain
 */
public class ColorUtil {

    public static String hexCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    public static Color getTextFill(Color color) {
        // Counting the perceptive luminance - human eye favors green color...
        double luminance = ( 0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue());
        int d = luminance > 0.5 ? 0 : 255; // dark colors - white font
        return Color.rgb(d, d, d);
    }
}

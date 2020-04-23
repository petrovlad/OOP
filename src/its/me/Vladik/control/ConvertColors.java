package its.me.Vladik.control;

import javafx.scene.paint.Color;

public class ConvertColors {
    public static int colorToInt(double red, double green, double blue) {
        int result = 0;
        int r = Math.round((float)red * 255) << 16;
        r &= 0x00FF0000;
        int g = Math.round((float)green * 255) << 8;
        g &= 0x0000FF00;
        int b = Math.round((float)blue * 255);
        b &= 0x000000FF;
        result = 0xFF000000 | r | g | b;
        return result;
    }

    public static int colorToInt(Color color) {
        double red = color.getRed();
        double green = color.getGreen();
        double blue = color.getBlue();

        int result = 0;
        int r = Math.round((float)red * 255) << 16;
        r &= 0x00FF0000;
        int g = Math.round((float)green * 255) << 8;
        g &= 0x0000FF00;
        int b = Math.round((float)blue * 255);
        b &= 0x000000FF;
        result = 0xFF000000 | r | g | b;
        return result;
    }

    public static Color intToColor(int value) {
        int r = (value & 0x00FF0000) >> 16;
        int g = (value & 0x0000FF00) >> 8;
        int b = (value & 0x000000FF);
        float red = (float)r / 255;
        float green = (float)g / 255;
        float blue = (float)b / 255;
        Color result = new Color(red, green, blue, 1.0);

        return result;
    }

}

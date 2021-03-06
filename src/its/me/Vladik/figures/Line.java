package its.me.Vladik.figures;

import its.me.Vladik.control.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.File;

public class Line extends Figure {
    public Point startPoint = new Point();
    public Point endPoint = new Point();

    public Line() {
        startPoint = new Point(0, 0);
        endPoint = new Point(0, 0);
        lineSize = 0;
        lineColor = Color.BLACK;
        lineColorValue = ConvertColors.colorToInt(lineColor);

        fillColor = Color.WHITE;
        fillColorValue = ConvertColors.colorToInt(fillColor);
    }

    public Line(double sx, double sy, double ex, double ey, int linesize, Color lineclr) {
        startPoint.x = sx;
        startPoint.y = sy;
        endPoint.x = ex;
        endPoint.y = ey;
        lineSize = linesize;
        lineColor = lineclr;

        lineColorValue = ConvertColors.colorToInt(lineColor);
    }

    public Line(Map map, MessageList messages) {
        lineSize = 1;
        lineColor = Color.BLACK;
        fillColor = Color.WHITE;

        double x, y;
        boolean p1 = false;
        boolean p2 = false;
        for (Attribute attribute : map.attributes) {
            switch (attribute.name) {
                case ("stroke"):
                    try {
                        lineColor = Color.valueOf(attribute.value);
                        lineColorValue = ConvertColors.colorToInt(lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue());
                    }
                    catch (Exception e) {
                        try {
                            lineColorValue = Integer.parseInt(attribute.value, 16);
                        }
                        catch (Exception e1) {
                            messages.add(new Message(Message.Type.ERROR, 2, attribute.value, map.line));
                        }
                    }
                    break;
                case ("stroke-width"):
                    try {
                        lineSize = Integer.parseInt(attribute.value);
                    }
                    catch (Exception e) {
                        messages.add(new Message(Message.Type.ERROR, 2, attribute.value, map.line));
                    }
                    break;
                case ("point1"):
                    try {
                        p1 = true;
                        x = Double.parseDouble(attribute.value.substring(0, attribute.value.indexOf(' ')));
                        y = Double.parseDouble(attribute.value.substring(attribute.value.lastIndexOf(' ') + 1));
                        startPoint = new Point(x, y);
                    }
                    catch (Exception e) {
                        messages.add(new Message(Message.Type.ERROR, 2, attribute.value, map.line));
                        p1 = false;
                    }
                    break;
                case ("point2"):
                    try {
                        p2 = true;
                        x = Double.parseDouble(attribute.value.substring(0, attribute.value.indexOf(' ')));
                        y = Double.parseDouble(attribute.value.substring(attribute.value.lastIndexOf(' ') + 1));
                        endPoint = new Point(x, y);
                    }
                    catch (Exception e) {
                        messages.add(new Message(Message.Type.ERROR, 2, attribute.value, map.line));
                        p2 = false;
                    }
                    break;
                default:
                    // unknown field
                    messages.add(new Message(Message.Type.WARNING, 1, attribute.name, map.line));
                    break;
            }

        }
        if (!(p1 & p2)) {
            // points have not been edited
            startPoint = new Point(1, 1);
            endPoint = new Point(1, 1);
            messages.add(new Message(Message.Type.ERROR, 3, map));
        }
    }


    @Override
    public void Draw(GraphicsContext gc) {
        gc.setStroke(ConvertColors.intToColor(lineColorValue));
        gc.setLineWidth(lineSize);

        gc.strokeLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
    }

    public static String getUsage() {
        return "<line stroke=\"COLOR\" stroke-width=\"N\" point1=\"X Y\" point2=\"X Y\">";
    }

    @Override
    public String toString() {
        return "<line stroke=\"" + ConvertColors.intToColor(lineColorValue).toString() +
                "\" stroke-width=\"" + lineSize +
                "\" point1=\"" + startPoint.x + " " + startPoint.y +
                "\" point2=\"" + endPoint.x + " " + endPoint.y + "\">";
    }

    public String Serialize() {
        return this.toString();
    }

    public void Deserialize(String script) {
        script = script.toLowerCase();

        int index;
        String buf;

        lineSize = 1;
        lineColor = Color.BLACK;

        index = script.indexOf("stroke");
        if (index != -1) {
            index = script.indexOf('"', script.indexOf("stroke"));
            buf = script.substring(index + 1, script.indexOf('"', index + 1));
            lineColor = Color.valueOf(buf);
            lineColorValue = ConvertColors.colorToInt(lineColor);
        }

        index = script.indexOf("stroke-width");
        if (index != -1) {
            index = script.indexOf('"', script.indexOf("stroke-width"));
            buf = script.substring(index + 1, script.indexOf('"', index + 1));
            lineSize = Integer.parseInt(buf);
        }

        double x, y;
        index = script.indexOf('"', script.indexOf("point1"));
        buf = script.substring(index + 1, script.indexOf('"', index + 1));
        x = Double.parseDouble(buf.substring(0, buf.indexOf(' ')));
        y = Double.parseDouble(buf.substring(buf.lastIndexOf(' ') + 1));
        startPoint.x = x;
        startPoint.y = y;

        index = script.indexOf('"', script.indexOf("point2"));
        buf = script.substring(index + 1, script.indexOf('"', index + 1));
        x = Double.parseDouble(buf.substring(0, buf.indexOf(' ')));
        y = Double.parseDouble(buf.substring(buf.lastIndexOf(' ') + 1));
        endPoint.x = x;
        endPoint.y = y;
    }
}

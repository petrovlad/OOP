package its.me.Vladik.figures;

import its.me.Vladik.control.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Triangle extends Figure {
    public Point point1 = new Point();
    public Point point2 = new Point();
    public Point point3 = new Point();

    public double height;

    public Triangle() {
        point1 = new Point(0, 0);
        point2 = new Point(0, 0);
        point3 = new Point(0, 0);
        lineSize = 0;
        lineColor = Color.BLACK;
        lineColorValue = ConvertColors.colorToInt(lineColor);

        fillColor = Color.WHITE;
        fillColorValue = ConvertColors.colorToInt(fillColor);
    }

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3, int linesize, Color fillclr, Color lineclr) {
        point1.x = x1;
        point1.y = y1;
        point2.x = x2;
        point2.y = y2;
        point3.x = x3;
        point3.y = y3;
        lineSize = linesize;
        fillColor = fillclr;
        lineColor = lineclr;

        fillColorValue = ConvertColors.colorToInt(fillColor);
        lineColorValue = ConvertColors.colorToInt(lineColor);
    }
    public Triangle(String script) {

    }

    public Triangle(Map map, MessageList messages) {
        lineSize = 1;
        lineColor = Color.BLACK;
        fillColor = Color.WHITE;


        double x, y;
        boolean p1 = false;
        boolean p2 = false;
        boolean p3 = false;
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
                case ("fill"):
                    try {
                        fillColor = Color.valueOf(attribute.value);
                        fillColorValue = ConvertColors.colorToInt(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue());
                    }
                    catch (Exception e) {
                        try {
                            fillColorValue = Integer.parseInt(attribute.value, 16);
                        }
                        catch (Exception e1) {
                            messages.add(new Message(Message.Type.ERROR, 2, attribute.value, map.line));
                        }
                    }
                    break;
                case ("point1"):
                    try {
                        p1 = true;
                        x = Double.parseDouble(attribute.value.substring(0, attribute.value.indexOf(' ')));
                        y = Double.parseDouble(attribute.value.substring(attribute.value.lastIndexOf(' ') + 1));
                        point1 = new Point(x, y);
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
                        point2 = new Point(x, y);
                    }
                    catch (Exception e) {
                        messages.add(new Message(Message.Type.ERROR, 2, attribute.value, map.line));
                        p2 = false;
                    }
                    break;
                case ("point3"):
                    try {
                        p3 = true;
                        x = Double.parseDouble(attribute.value.substring(0, attribute.value.indexOf(' ')));
                        y = Double.parseDouble(attribute.value.substring(attribute.value.lastIndexOf(' ') + 1));
                        point3 = new Point(x, y);
                    }
                    catch (Exception e) {
                        messages.add(new Message(Message.Type.ERROR, 2, attribute.value, map.line));
                        p3 = false;
                    }
                    break;
                default:
                    // unknown field
                    messages.add(new Message(Message.Type.WARNING, 1, attribute.name, map.line));
                    break;
            }

        }
        if (!(p1 & p2 & p3)) {
            // points have not been edited
            point1 = new Point(1, 1);
            point2 = new Point(1, 1);
            point3 = new Point(1, 1);
            messages.add(new Message(Message.Type.ERROR, 3, map));
        }
    }

    @Override
    public void Draw(GraphicsContext gc) {
        gc.setFill(ConvertColors.intToColor(fillColorValue));
        gc.setStroke(ConvertColors.intToColor(lineColorValue));
        gc.setStroke(lineColor);

        double[] y = {point1.y, point2.y, point3.y};
        double[] x = {point1.x, point2.x, point3.x};

        gc.strokePolygon(x, y, 3);
        gc.fillPolygon(x, y, 3);
    }


    public static String getUsage() {
        return "<triangle stroke=\"COLOR\" stroke-width=\"N\" fill=\"COLOR\" point1=\"X Y\" point2=\"X Y\" point3=\"X Y\">";
    }

    @Override
    public String toString() {
        return "<triangle stroke=\"" + ConvertColors.intToColor(lineColorValue).toString() +
                "\" stroke-width=\"" + lineSize +
                "\" fill=\"" + ConvertColors.intToColor(fillColorValue).toString() +
                "\" point1=\"" + point1.x + " " + point1.y +
                "\" point2=\"" + point2.x + " " + point2.y +
                "\" point3=\"" + point3.x + " " + point3.y + "\">";
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
        fillColor = Color.WHITE;

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

        index = script.indexOf("fill");
        if (index != -1) {
            index = script.indexOf('"', script.indexOf("fill"));
            buf = script.substring(index + 1, script.indexOf('"', index + 1));
            fillColor = Color.valueOf(buf);
            fillColorValue = ConvertColors.colorToInt(fillColor);
        }

        double x, y;
        index = script.indexOf('"', script.indexOf("point1"));
        buf = script.substring(index + 1, script.indexOf('"', index + 1));
        x = Double.parseDouble(buf.substring(0, buf.indexOf(' ')));
        y = Double.parseDouble(buf.substring(buf.lastIndexOf(' ') + 1));
        point1.x = x;
        point1.y = y;

        index = script.indexOf('"', script.indexOf("point2"));
        buf = script.substring(index + 1, script.indexOf('"', index + 1));
        x = Double.parseDouble(buf.substring(0, buf.indexOf(' ')));
        y = Double.parseDouble(buf.substring(buf.lastIndexOf(' ') + 1));
        point2.x = x;
        point2.y = y;

        index = script.indexOf('"', script.indexOf("point3"));
        buf = script.substring(index + 1, script.indexOf('"', index + 1));
        x = Double.parseDouble(buf.substring(0, buf.indexOf(' ')));
        y = Double.parseDouble(buf.substring(buf.lastIndexOf(' ') + 1));
        point3.x = x;
        point3.y = y;
    }
}

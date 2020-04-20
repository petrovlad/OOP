package its.me.Vladik.figures;

import its.me.Vladik.control.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Triangle extends Figure {
    public Point point1 = new Point();
    public Point point2 = new Point();
    public Point point3 = new Point();

    public double height;

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
    }
    public Triangle(String script) {
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
        }

        int x, y;
        index = script.indexOf('"', script.indexOf("point1"));
        buf = script.substring(index + 1, script.indexOf('"', index + 1));
        x = Integer.parseInt(buf.substring(0, buf.indexOf(' ')));
        y = Integer.parseInt(buf.substring(buf.lastIndexOf(' ') + 1));
        point1.x = x;
        point1.y = y;

        index = script.indexOf('"', script.indexOf("point2"));
        buf = script.substring(index + 1, script.indexOf('"', index + 1));
        x = Integer.parseInt(buf.substring(0, buf.indexOf(' ')));
        y = Integer.parseInt(buf.substring(buf.lastIndexOf(' ') + 1));
        point2.x = x;
        point2.y = y;

        index = script.indexOf('"', script.indexOf("point3"));
        buf = script.substring(index + 1, script.indexOf('"', index + 1));
        x = Integer.parseInt(buf.substring(0, buf.indexOf(' ')));
        y = Integer.parseInt(buf.substring(buf.lastIndexOf(' ') + 1));
        point3.x = x;
        point3.y = y;

    }

    public Triangle(Map map, MessageList messages) {
        lineSize = 1;
        lineColor = Color.BLACK;
        fillColor = Color.WHITE;


        int x, y;
        boolean p1 = false;
        boolean p2 = false;
        boolean p3 = false;
        for (Attribute attribute : map.attributes) {
            switch (attribute.name) {
                case ("stroke"):
                    try {
                        lineColor = Color.valueOf(attribute.value);
                    }
                    catch (Exception e) {
                        messages.add(new Message(Message.Type.ERROR, 2, attribute.value, map.line));
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
                    }
                    catch (Exception e) {
                        messages.add(new Message(Message.Type.ERROR, 2, attribute.value, map.line));
                    }
                    break;
                case ("point1"):
                    try {
                        p1 = true;
                        x = Integer.parseInt(attribute.value.substring(0, attribute.value.indexOf(' ')));
                        y = Integer.parseInt(attribute.value.substring(attribute.value.lastIndexOf(' ') + 1));
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
                        x = Integer.parseInt(attribute.value.substring(0, attribute.value.indexOf(' ')));
                        y = Integer.parseInt(attribute.value.substring(attribute.value.lastIndexOf(' ') + 1));
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
                        x = Integer.parseInt(attribute.value.substring(0, attribute.value.indexOf(' ')));
                        y = Integer.parseInt(attribute.value.substring(attribute.value.lastIndexOf(' ') + 1));
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
        gc.setLineWidth(lineSize);
        gc.setFill(fillColor);
        gc.setStroke(lineColor);

        double[] y = {point1.y, point2.y, point3.y};
        double[] x = {point1.x, point2.x, point3.x};

        gc.strokePolygon(x, y, 3);
        gc.fillPolygon(x, y, 3);
    }


    public static String getUsage() {
        return "<triangle stroke=\"COLOR\" stroke-width=\"N\" fill=\"COLOR\" point1=\"X Y\" point2=\"X Y\" point3=\"X Y\">";
    }

}

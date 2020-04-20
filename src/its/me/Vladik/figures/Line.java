package its.me.Vladik.figures;

import its.me.Vladik.control.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line extends Figure {
    public Point startPoint = new Point();
    public Point endPoint = new Point();

    public Line(double sx, double sy, double ex, double ey, int linesize, Color lineclr) {
        startPoint.x = sx;
        startPoint.y = sy;
        endPoint.x = ex;
        endPoint.y = ey;
        lineSize = linesize;
        lineColor = lineclr;
    }

    public Line(String script) {
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
        }

        index = script.indexOf("stroke-width");
        if (index != -1) {
            index = script.indexOf('"', script.indexOf("stroke-width"));
            buf = script.substring(index + 1, script.indexOf('"', index + 1));
            lineSize = Integer.parseInt(buf);
        }

        int x, y;
        index = script.indexOf('"', script.indexOf("point1"));
        buf = script.substring(index + 1, script.indexOf('"', index + 1));
        x = Integer.parseInt(buf.substring(0, buf.indexOf(' ')));
        y = Integer.parseInt(buf.substring(buf.lastIndexOf(' ') + 1));
        startPoint.x = x;
        startPoint.y = y;

        index = script.indexOf('"', script.indexOf("point2"));
        buf = script.substring(index + 1, script.indexOf('"', index + 1));
        x = Integer.parseInt(buf.substring(0, buf.indexOf(' ')));
        y = Integer.parseInt(buf.substring(buf.lastIndexOf(' ') + 1));
        endPoint.x = x;
        endPoint.y = y;
    }

    public Line(Map map, MessageList messages) {
        lineSize = 1;
        lineColor = Color.BLACK;
        fillColor = Color.WHITE;

        int x, y;
        boolean p1 = false;
        boolean p2 = false;
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
                case ("point1"):
                    try {
                        p1 = true;
                        x = Integer.parseInt(attribute.value.substring(0, attribute.value.indexOf(' ')));
                        y = Integer.parseInt(attribute.value.substring(attribute.value.lastIndexOf(' ') + 1));
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
                        x = Integer.parseInt(attribute.value.substring(0, attribute.value.indexOf(' ')));
                        y = Integer.parseInt(attribute.value.substring(attribute.value.lastIndexOf(' ') + 1));
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
        gc.setStroke(lineColor);
        gc.setLineWidth(lineSize);

        gc.strokeLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
    }

    public static String getUsage() {
        return "<line stroke=\"COLOR\" stroke-width=\"N\" point1=\"X Y\" point2=\"X Y\">";
    }
}

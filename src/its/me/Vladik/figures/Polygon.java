package its.me.Vladik.figures;

import its.me.Vladik.control.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Polygon extends Figure {
    public Point[] points;
    public int numOfPoints;

    public Polygon(String script) {
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

        index = script.indexOf('"', script.indexOf("num-of-points"));
        buf = script.substring(index + 1, script.indexOf('"', index + 1));
        numOfPoints = Integer.parseInt(buf);
        // numofPoints should be >=3
        int x, y;
        points = new Point[numOfPoints];
        for (int i = 0; i < numOfPoints; i++) {
            index = script.indexOf('"', script.indexOf("point" + i + 1));
            buf = script.substring(index + 1, script.indexOf('"', index + 1));
            x = Integer.parseInt(buf.substring(0, buf.indexOf(' ')));
            y = Integer.parseInt(buf.substring(buf.lastIndexOf(' ') + 1));
            points[i] = new Point(x, y);
        }
    }

    public Polygon(Map map, MessageList messages) {
        lineSize = 1;
        lineColor = Color.BLACK;
        fillColor = Color.WHITE;
        numOfPoints = 0;

        double x, y;
        int i;
        // if numOfPoints have not been initialized, num = false
        boolean num = false;
        // if p[0]&p[1]&...&p[numOfPoints] == false then not all points have been initialized
        boolean[] p = new boolean[0];

        // find field num-of-points; if it isn't edited - exit
        for (Attribute attribute : map.attributes) {
            if (attribute.name.equals("num-of-points")) {
                num = true;
                try {
                    numOfPoints = Integer.parseInt(attribute.value);
                    p = new boolean[numOfPoints];
                    points = new Point[numOfPoints];
                } catch (Exception e) {
                    messages.add(new Message(Message.Type.ERROR, 2, attribute.value, map.line));
                }
                break;
            }
        }

        if (num) {
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
                    case ("num-of-points"):
                        break;
                    default:
                        if ((attribute.name.startsWith("point")) && ((i = Integer.parseInt(attribute.name.substring(5))) != -1) && (i <= numOfPoints)) {
                            try {
                                p[i - 1] = true;
                                x = Double.parseDouble(attribute.value.substring(0, attribute.value.indexOf(' ')));
                                y = Double.parseDouble(attribute.value.substring(attribute.value.lastIndexOf(' ') + 1));
                                points[i - 1] = new Point(x, y);
                            } catch (Exception e) {
                                messages.add(new Message(Message.Type.ERROR, 2, attribute.value, map.line));
                                p[i] = false;
                            }
                            break;
                        } else
                            // unknown attribute
                            messages.add(new Message(Message.Type.WARNING, 1, attribute.name, map.line));
                        break;
                }

            }
            boolean buf = true;
            for (i = 0; i < numOfPoints; i++)
                buf &= p[i];
            if (!buf) {
                for (i = 0; i < numOfPoints; i++) {
                    points[i] = new Point(1, 1);
                }
                messages.add(new Message(Message.Type.ERROR, 3, map));
            }
        }
    }

    @Override
    public void Draw(GraphicsContext gc) {
        gc.setFill(ConvertColors.intToColor(fillColorValue));
        gc.setStroke(ConvertColors.intToColor(lineColorValue));
        gc.setLineWidth(lineSize);

        double[] x = new double[numOfPoints];
        double[] y = new double[numOfPoints];
        for (int i = 0; i < numOfPoints; i++) {
            x[i] = points[i].x;
            y[i] = points[i].y;
        }

        gc.strokePolygon(x, y, numOfPoints);
        gc.fillPolygon(x, y, numOfPoints);
    }

    public static String getUsage() {
        return "<polygon stroke=\"COLOR\" stroke-width=\"N\" fill=\"COLOR\" num-of-points=\"N\" point1=\"X Y\" ... point_i=\"X Y\">";
    }

    @Override
    public String toString() {
        String buf = "<polygon stroke=\"" + ConvertColors.intToColor(lineColorValue).toString() +
                    "\" stroke-width=\"" + lineSize +
                    "\" fill=\"" + ConvertColors.intToColor(fillColorValue).toString() +
                    "\" num-of-points=\"" + numOfPoints + "\"";
        for (int i = 0; i < numOfPoints; i++) {
            buf += " point" + Integer.toString(i + 1) + "=\"" + points[i].x + " " + points[i].y + "\"";
        }
        return buf  + ">";
    }

}

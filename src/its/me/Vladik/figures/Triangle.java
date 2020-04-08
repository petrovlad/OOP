package its.me.Vladik.figures;

import its.me.Vladik.control.Figure;
import its.me.Vladik.control.Point;
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
        script.toLowerCase();

        int index;
        String buf;

        lineSize = 1;
        lineColor = Color.WHITE;

        index = script.indexOf('"', script.indexOf("stroke"));
        buf = script.substring(index + 1, script.indexOf('"', index + 1));
        lineColor = Color.valueOf(buf);

        index = script.indexOf('"', script.indexOf("fill"));
        buf = script.substring(index + 1, script.indexOf('"', index + 1));
        fillColor = Color.valueOf(buf);

        index = script.indexOf('"', script.indexOf("stroke-width"));
        buf = script.substring(index + 1, script.indexOf('"', index + 1));
        lineSize = Integer.parseInt(buf);

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

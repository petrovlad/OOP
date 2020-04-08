package its.me.Vladik.figures;

import its.me.Vladik.control.Figure;
import its.me.Vladik.control.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Polygon extends Figure {
    public Point[] points;
    public int numOfPoints;

    public Polygon(String script) {
        script.toLowerCase();

        int index;
        String buf;

        lineSize = 1;
        lineColor = Color.WHITE;

        index = script.indexOf('"', script.indexOf("stroke"));
        buf = script.substring(index + 1, script.indexOf('"', index + 1));
        lineColor = Color.valueOf(buf);

        index = script.indexOf('"', script.indexOf("stroke-width"));
        buf = script.substring(index + 1, script.indexOf('"', index + 1));
        lineSize = Integer.parseInt(buf);

        index = script.indexOf('"', script.indexOf("fill"));
        buf = script.substring(index + 1, script.indexOf('"', index + 1));
        fillColor = Color.valueOf(buf);

        index = script.indexOf('"', script.indexOf("num-of-points"));
        buf = script.substring(index + 1, script.indexOf('"', index + 1));
        numOfPoints = Integer.parseInt(buf);

        int x, y;
        points = new Point[numOfPoints];
        for (int i = 0; i < numOfPoints; i++) {
            index = script.indexOf('"', script.indexOf("point" + Integer.toString(i + 1)));
            buf = script.substring(index + 1, script.indexOf('"', index + 1));
            x = Integer.parseInt(buf.substring(0, buf.indexOf(' ')));
            y = Integer.parseInt(buf.substring(buf.lastIndexOf(' ') + 1));
            points[i] = new Point(x, y);
        }
    }

    @Override
    public void Draw(GraphicsContext gc) {
        gc.setFill(fillColor);
        gc.setStroke(lineColor);
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

}

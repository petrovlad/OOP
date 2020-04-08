package its.me.Vladik.figures;

import its.me.Vladik.control.Figure;
import its.me.Vladik.control.Point;
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

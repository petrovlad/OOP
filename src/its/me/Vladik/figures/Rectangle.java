package its.me.Vladik.figures;

import its.me.Vladik.Figure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends Figure {
    public Point startPoint = new Point();
    public Point endPoint = new Point();

    public Rectangle(double sx, double sy, double ex, double ey, int linesize, Color fillclr, Color lineclr) {
        startPoint.x = sx;
        startPoint.y = sy;
        endPoint.x = ex;
        endPoint.y = ey;
        lineSize = linesize;
        fillColor = fillclr;
        lineColor = lineclr;
    }

    public Rectangle(String script) {
        script.toLowerCase();

        int index;
        String buf;

        lineSize = 1;
        lineColor = Color.WHITE;
        fillColor = Color.BLACK;

        index = script.indexOf('"', script.indexOf("stroke"));
        buf = script.substring(index + 1, script.indexOf('"', index + 1));
        lineColor = Color.valueOf(buf);

        index = script.indexOf('"', script.indexOf("stroke-width"));
        buf = script.substring(index + 1, script.indexOf('"', index + 1));
        lineSize = Integer.parseInt(buf);

        index = script.indexOf('"', script.indexOf("fill"));
        buf = script.substring(index + 1, script.indexOf('"', index + 1));
        fillColor = Color.valueOf(buf);

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
        gc.setFill(fillColor);
        gc.setStroke(lineColor);
        gc.setLineWidth(lineSize);

        gc.strokeRect(startPoint.x, startPoint.y, Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.x));
        gc.fillRect(startPoint.x, startPoint.y, Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.x));

    }
}

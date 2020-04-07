package Figures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Triangle extends Figure {
    public Point point1, point2, point3;
    public Color fillColor;

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3, int linesize, Color fillclr, Color lineclr) {
        point1.x = x1;
        point2.x = x2;
        point3.x = x3;
        point1.y = y1;
        point2.y = y2;
        point3.y = y3;
        lineSize = linesize;
        fillColor = fillclr;
        lineColor = lineclr;
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
}

package Figures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Triangle extends Figure {
    public double height;

    public Triangle(double sx, double sy, double ex, double ey, int linesize, Color fillclr, Color lineclr) {
        startPoint.x = sx;
        startPoint.y = sy;
        endPoint.x = ex;
        endPoint.y = ey;
        lineSize = linesize;
        fillColor = fillclr;
        lineColor = lineclr;
    }

    @Override
    public void Draw(GraphicsContext gc) {
        gc.setLineWidth(lineSize);
        gc.setFill(fillColor);
        gc.setStroke(lineColor);

        double[] y = {endPoint.y, endPoint.y, startPoint.y};
        double[] x = {startPoint.x, endPoint.x, startPoint.x + Math.abs((startPoint.x - endPoint.x) / 2)};

        gc.strokePolygon(x, y, 3);
        gc.fillPolygon(x, y, 3);


    }
}

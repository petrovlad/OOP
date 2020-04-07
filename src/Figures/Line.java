package Figures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line extends Figure {

    public Point startPoint, endPoint;
    public Color fillColor;

    public Line(double sx, double sy, double ex, double ey, int linesize, Color lineclr) {
        startPoint.x = sx;
        startPoint.y = sy;
        endPoint.x = ex;
        endPoint.y = ey;
        lineSize = linesize;
        lineColor = lineclr;
    }

    @Override
    public void Draw(GraphicsContext gc) {
        gc.setStroke(lineColor);
        gc.setLineWidth(lineSize);


        gc.strokeLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
    }
}

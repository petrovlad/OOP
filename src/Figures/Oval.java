package Figures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Oval extends Figure {

    public Oval(double sx, double sy, double ex, double ey, int linesize, Color fillclr, Color lineclr) {
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
        gc.setFill(fillColor);
        gc.setStroke(lineColor);
        gc.setLineWidth(lineSize);

        gc.strokeOval(startPoint.x, startPoint.y, Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.x));
        gc.fillOval(startPoint.x, startPoint.y, Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.x));
    }
}

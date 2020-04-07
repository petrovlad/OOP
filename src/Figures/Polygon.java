package Figures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Polygon extends Figure {

    public int nPoints;
    public Point[] points;
    public Color FillColor;

    public Polygon(double[] x, double[] y, int nPoints) {
        points = new Point[nPoints];
        for (int i = 0; i < nPoints; i++) {
            points[i].x = x[i];
            points[i].y = y[i];
        }
    }

    public void Draw(GraphicsContext gc) {
        gc.setLineWidth(lineSize);
        gc.setStroke(lineColor);

        double[] x = new double[nPoints];
        double[] y = new double[nPoints];
        for (int i = 0; i < nPoints; i++) {
            x[i] = points[i].x;
            y[i] = points[i].y;
        }

        gc.strokePolygon(x, y, nPoints);
        gc.fillPolygon(x, y, nPoints);
    }

}

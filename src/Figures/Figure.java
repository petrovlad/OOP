package Figures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public abstract class Figure {
    public Point startPoint = new Point();
    public Point endPoint = new Point();

    public Color fillColor;
    public int lineSize;
    public Color lineColor;

    public void Draw(GraphicsContext gc) { };
}



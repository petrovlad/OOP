package its.me.Vladik.control;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;


public abstract class Figure implements Serializable {
    private static final long serialVersionUID = 1L;

    public transient Color fillColor;
    public int lineSize;
    public transient Color lineColor;

    protected int fillColorValue;
    protected int lineColorValue;

    // конструкторы в абстрактном классе родителе?
    // большинство определений в скрипте одинаковы для остальных
    // определить общие и в каждом конструкторе дописать уникальные?

    public void Draw(GraphicsContext gc) { }

    @Override
    public String toString() { return "";}
}



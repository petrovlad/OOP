package its.me.Vladik.control;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public abstract class Figure {

    public Color fillColor;
    public int lineSize;
    public Color lineColor;

    // конструкторы в абстрактном классе родителе?
    // большинство определений в скрипте одинаковы для остальных
    // определить общие и в каждом конструкторе дописать уникальные?

    public void Draw(GraphicsContext gc) { }
}



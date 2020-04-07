package its.me.Vladik;

import its.me.Vladik.Figure;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class FigureList {
    private ArrayList<Figure> Figures;

    public FigureList() {
        Figures = new ArrayList<Figure>();
    }
    public void Add(Figure figure) {
        Figures.add(figure);
    }

    public void Draw(GraphicsContext gc) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        for (Figure fig: Figures) {
            fig.Draw(gc);
        }
    }
    public void DeleteAll() {
        Figures.clear();
    }

}

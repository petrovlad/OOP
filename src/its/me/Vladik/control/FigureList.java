package its.me.Vladik.control;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class FigureList {
    private ArrayList<Figure> figures;

    public FigureList() {
        figures = new ArrayList<>();
    }
    public void add(Figure figure) {
        figures.add(figure);
    }

    public void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        for (Figure fig: figures) {
            fig.Draw(gc);
        }
    }
    public void deleteAll() {
        figures.clear();
    }

}

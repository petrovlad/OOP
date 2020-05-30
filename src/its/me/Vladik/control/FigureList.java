package its.me.Vladik.control;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class FigureList {
    private static final long serialVersionUID = 1L;

    public ArrayList<Figure> figures;

    public FigureList() {
        figures = new ArrayList<>();
    }
    public void add(Figure figure) {
        figures.add(figure);
    }

    public void draw(GraphicsContext gc) {

        for (Figure fig: figures) {
            fig.Draw(gc);
        }
    }
    public void deleteAll() {
        figures.clear();
    }

    @Override
    public String toString() {
        String buf = new String();
        for (Figure fig : figures) {
            buf += fig.toString() + "\n";
        }
        return buf;
    }
}

package Figures;

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
        for (Figure fig: Figures) {
            fig.Draw(gc);
        }
    }

}

package its.me.Vladik.control;

import javafx.scene.canvas.GraphicsContext;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
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

    public void Serialize(FileOutputStream fileOutputStream) {
        for (Figure fig : figures) {
            try {
                byte[] buf = fig.Serialize().concat("\n").getBytes();
                fileOutputStream.write(buf);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // why should i put all these try catch im so tired please java-god TELL ME WHYYYYYYYYYYYYYYYYYYYYYYYYY
        try {
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Deserialize(FileInputStream fileInputStream, ArrayList<Class> classes, MessageList messages) {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, 256);
        int i;

        try {
            byte[] buf = bufferedInputStream.readAllBytes();
            String str1 = new String(buf);
            while ((i = str1.indexOf('<')) != -1) {
                str1 = str1.substring(str1.indexOf('<'));
                boolean isKnown = false;

                String str2 = str1.substring(0, str1.indexOf('>') + 1);
                String figName = str2.substring(1, str2.indexOf(' ', 2)).trim();
                for (Class clss : classes) {
                    if (clss.getName().toLowerCase().endsWith(figName.toLowerCase())) {
                        Constructor constructor = clss.getConstructor(null);
                        Object fig = constructor.newInstance(null);
                        Method method = clss.getMethod("Deserialize", String.class);
                        method.invoke(fig, str2);
                        figures.add((Figure)fig);

                        isKnown = true;
                        break;
                    }
                }
                if (!isKnown)
                    messages.add(new Message(Message.Type.WARNING, 1, figName, 0));

                str1 = str1.substring(str1.indexOf('>'));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

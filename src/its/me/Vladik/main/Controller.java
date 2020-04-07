package its.me.Vladik.main;

import its.me.Vladik.Figure;
import its.me.Vladik.FigureList;
import its.me.Vladik.figures.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;

public class Controller {

    @FXML
    private Canvas canvas;

    @FXML
    private TextArea txtUsage;

    @FXML
    private TextArea txtInput;

    @FXML
    private Button btnRedraw;

    private FigureList figureList = new FigureList();

    private ArrayList<Class> classes = new ArrayList<Class>();
    private ArrayList<String> names = new ArrayList<String>();
    private GraphicsContext gc;
    @FXML
    void initialize() {
        // initialize form
        txtUsage.setEditable(false);
        gc = canvas.getGraphicsContext2D();


        // get classes from package "figures"
        try {
            classes = getClassesFromPackage("its/me/Vladik/figures");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Loading classes failed");
            alert.setContentText("Error occured while load classes from file!");
        }
        // get names of classes
        for (Class clss : classes) {
            String buf = clss.getName();
            names.add(buf.substring(buf.lastIndexOf('.') + 1));
        }
        // now we have 2 lists: 'classes'(include all possible figures) and 'names'(include names of all possible figures)

    }

    @FXML
    // clear current figureList, get new figureList from the input field and redraw
    void drawObjs(ActionEvent event) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        figureList.DeleteAll();

        String input = txtInput.getText();
        Boolean end = false;
        while (!input.isEmpty()) {
            // get name from script
            String figureName = input.substring(input.indexOf('<') + 1, input.indexOf(' '));
            figureName.trim();

            int i = 0;
            for (String name : names) {
                if (name.equalsIgnoreCase(figureName)) {
                    try {
                        Class figure = classes.get(i);
                        Constructor constructor = figure.getConstructor(String.class);
                        Object fig = constructor.newInstance(input.substring(input.indexOf('<'), input.indexOf('>')));
                        figureList.Add((Figure) fig);
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Loading classes failed");
                        alert.setContentText("Error occured while load classes from file!");
                    }
                    break;
                }
                i++;
            }
            input = input.substring(input.indexOf('>') + 1);
        }

        figureList.Draw(gc);

    }

    private static ArrayList<Class> getClassesFromPackage(String packageName) throws IOException, ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ArrayList<Class> classes = new ArrayList<Class>();

        packageName = packageName.replace(".", "/");
        URL packageURL = classLoader.getResource(packageName);

        InputStream inputStream = (InputStream) packageURL.getContent();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String newLine = bufferedReader.readLine();
        while (newLine != null) {
            if (newLine.endsWith(".class")) {
                String buf = packageName.replace("/", ".") + "." + newLine.substring(0, newLine.lastIndexOf('.'));
                classes.add(Class.forName(buf));
            }
            newLine = bufferedReader.readLine();
        }
        return classes;
    }
}
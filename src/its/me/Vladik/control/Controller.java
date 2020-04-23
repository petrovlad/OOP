package its.me.Vladik.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller {

    @FXML
    private Canvas canvas;

    @FXML
    private TextArea txtUsage;

    @FXML
    private TextArea txtInput;

    @FXML
    private Button btnRedraw;

    @FXML
    private MenuBar menuBar;

    private FigureList figureList = new FigureList();

    private ArrayList<Class> classes = new ArrayList<Class>();
    private ArrayList<String> names = new ArrayList<String>();
    private GraphicsContext gc;
    @FXML
    void initialize() {
        // initialize form
        txtUsage.setEditable(false);
        gc = canvas.getGraphicsContext2D();


        // this string contains all usages
        String usage = "Usage:\n";
        // get classes from package "figures"
        try {
            classes = getClassesFromPackage("its/me/Vladik/figures");
            // get names of classes
            for (Class clss : classes) {
                String buf = clss.getName();
                names.add(buf.substring(buf.lastIndexOf('.') + 1));

                // add usage
                Method method = clss.getMethod("getUsage", null);
                usage = usage + ((String)method.invoke(null, null) + "\n");

            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Loading classes failed");
            alert.setContentText("Error occured while load classes from file!");
        }

        // now we have 2 lists: 'classes'(include all possible figures) and 'names'(include names of all possible figures)

        txtUsage.setText(usage);
    }

    @FXML
    // clear current figureList, get new figureList from the input field and redraw
    void drawObjs(ActionEvent event) {
        figureList.deleteAll();
        // warnings || errors
        MessageList messages = new MessageList();

        String input = txtInput.getText();

        boolean isExist;
        int line = 1;
        while (!input.isEmpty()) {
            // get name from script
            Map map = Parse(input.substring(input.indexOf('<'), input.indexOf('>')), messages, line);
            int i = 0;
            isExist = false;
            for (String name : names) {
                // ignore case cause names contains class names, which begins with high case
                if (name.equalsIgnoreCase(map.figureName)) {
                    try {
                        Class figure = classes.get(i);
                        Constructor constructor = figure.getConstructor(Map.class, MessageList.class);
                        Object fig = constructor.newInstance(map, messages);
                        figureList.add((Figure) fig);
                        isExist = true;
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Creating object failed");
                        alert.setContentText("Oops, you've found bag. Please, report me about this. Thank you :)");
                        alert.show();
                    }
                    break;
                }
                i++;
            }
            if (!isExist)
                messages.add(new Message(Message.Type.ERROR, 1, map));

            if (input.indexOf('<', input.indexOf('<') + 1) != -1)
                input = input.substring(input.indexOf('<', input.indexOf('<') + 1));
            else
                input = "";
            line++;
        }
        figureList.draw(gc);
        String report = messages.formReport();
        if (!report.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Why do you enter incorrect input?");
            alert.setHeaderText("Drawing failed");
            alert.setContentText(report);
            alert.show();
        }

    }

    @FXML
    void saveObjsToFile(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(menuBar.getScene().getWindow());

        if (file != null) {
            try {
//            FileWriter fileWriter = new FileWriter(file);
//            fileWriter.write(txtInput.getText());
//            fileWriter.close();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(figureList.figures);
                objectOutputStream.close();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Saving failed");
                alert.setContentText("Error occurred while save figures to file!");
                alert.show();
            }
        }
    }

    @FXML
    void openObjsFromFile(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(menuBar.getScene().getWindow());

        if (file != null) {
            try {
//            FileReader fileReader = new FileReader(file);
//            char[] buf = new char[256];
//            int c;
//            while ((c = fileReader.read(buf)) > -1) {
//                if (c < 256)
//                    buf = Arrays.copyOf(buf, c);
//
//                txtInput.appendText(String.valueOf(buf));
//            }

                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                figureList.deleteAll();
                figureList.figures = (ArrayList<Figure>) objectInputStream.readObject();
                objectInputStream.close();
                txtInput.setText(figureList.toString());
                figureList.draw(gc);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Opening failed");
                alert.setContentText("Error occurred while open figures from file!");
                alert.show();
            }
        }
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

    Map Parse(String script, MessageList messages, int line) {
        // name="value"
        Map map = new Map();
        map.line = line;

        script = script.toLowerCase().trim();
        char[] buf = script.toCharArray();
        int index1 = script.indexOf('<') + 1;
        int index2;
        // < fig_name attr1 = "sdf" attrNonExist = "123a" >
        // select name
        while ((index1 < script.length()) && (buf[index1] == ' '))
            index1++;
        index2 = index1;
        while ((index2 < script.length()) && (buf[index2] != ' '))
            index2++;
        map.figureName = script.substring(index1, index2);

        // select attributes
        while (index2 < script.length()) {
            while (buf[index2] == ' ')
                index2++;
            index1 = index2;

            // select attr name
            String attrName = new String();
            String attrValue = new String();
            try {
                while ((index2 < script.length()) && (buf[index2] != '=') && (buf[index2] != ' '))
                    index2++;
                attrName = script.substring(index1, index2).trim();
                // select attr value
                while ((index2 < script.length()) && (buf[index2] != '"'))
                    index2++;
                index1 = ++index2;
                while ((index2 < script.length()) && (buf[index2] != '"'))
                    index2++;
                attrValue = script.substring(index1, index2).trim();
                map.attributes.add(new Attribute(attrName, attrValue));
            }
            catch (Exception e) {
                messages.add(new Message(Message.Type.WARNING, 4, attrName, line));
                index2 = index1;
            }

            index2 += 2;
        }

        int numOfQuotes = 0;
        for (int i = 0; i < script.length(); i++) {
            if (buf[i] == '"')
                numOfQuotes++;
        }
        if (!(numOfQuotes % 2 == 0)) {
            messages.add(new Message(Message.Type.WARNING, 2, map));
        }

        return map;
    }
}
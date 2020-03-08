package Main;

import Figures.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class Controller {

    @FXML
    private ComboBox<String> cbxChooseObj;

    @FXML
    private TextField txtX;

    @FXML
    private TextField txtY;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDraw;

    @FXML
    private TextField txtProperty;

    @FXML
    private Label lblProperty;

    @FXML
    private Canvas canvas;

    @FXML
    private ColorPicker pkrFillColor;


    @FXML
    private ColorPicker pkrLineColor;

    @FXML
    private Spinner<Integer> spnnrSize;

    private String[] objNameList = {"Line", "Oval", "Rectangle", "Triangle"};

    private FigureList figureList = new FigureList();

    @FXML
    void initialize() {
        cbxChooseObj.setItems(FXCollections.observableArrayList(objNameList));

        pkrFillColor.setValue(Color.BLACK);
        pkrLineColor.setValue(Color.BLACK);

        final int initValue = 1;
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 500, initValue);

        spnnrSize.setValueFactory(valueFactory);


        figureList.Add(new Line(10.0, 10.0, 30.0, 50.0, 5, Color.BLUE));
        figureList.Add(new Rectangle(50.0, 50.0, 80.0, 90.0, 5, Color.DARKBLUE, Color.BLACK));
        figureList.Add(new Oval(100.0, 100.0, 140.0, 130.0, 10, Color.RED, Color.VIOLET));
        figureList.Add(new Triangle(170.0, 170.0, 200.0, 250.0, 10, Color.BLUEVIOLET, Color.BLACK));



    }

    @FXML
    void change(ActionEvent event) {

    }

    @FXML
    void addObj(ActionEvent event) {


    }
    @FXML
    void drawObjs(ActionEvent event) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        figureList.Draw(gc);
    }
}
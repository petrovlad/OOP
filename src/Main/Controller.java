package Main;

import Figures.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.Color;

import java.lang.reflect.*;

public class Controller {

    @FXML
    private ComboBox<String> cbxChooseObj;

    @FXML
    private Canvas canvas;

    @FXML
    private ColorPicker pkrFillColor;

    @FXML
    private ColorPicker pkrLineColor;

    @FXML
    private Spinner<Integer> spnnrSize;

    private String[] objNameList = {"Line", "Oval", "Rectangle", "Triangle", "Polygon"};


    private FigureList figureList = new FigureList();

    @FXML
    void initialize() {
        cbxChooseObj.setItems(FXCollections.observableArrayList(objNameList));

        pkrFillColor.setValue(Color.BLACK);
        pkrLineColor.setValue(Color.BLACK);

        final int initValue = 1;
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 500, initValue);

        spnnrSize.setValueFactory(valueFactory);

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
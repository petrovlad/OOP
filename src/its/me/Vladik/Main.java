package its.me.Vladik;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader();
        String fxmlPath = "src/its/me/Vladik/control/sample1.fxml";
        FileInputStream fxmlStream = new FileInputStream(fxmlPath);

        Parent root = loader.load(fxmlStream);

        primaryStage.setTitle("GraphEdit");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}

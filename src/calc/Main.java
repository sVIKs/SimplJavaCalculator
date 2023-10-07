package calc;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application {

    @FXML
    public Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("calc.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 470, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Calculator");
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

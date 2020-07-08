package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.getIcons().add(new Image("media/icon128128.png"));
        Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
        primaryStage.setTitle("ZenTracker");
        primaryStage.setScene(new Scene(root, 990, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

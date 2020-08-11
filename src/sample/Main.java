package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    File f = new File("monthlyConfig.txt");
    boolean setupDone = f.isFile();

    @Override
    public void start(Stage primaryStage) throws Exception {


        if (setupDone) {
            changeToTrackerScreen c = new changeToTrackerScreen(primaryStage);
            c.change();

        } else {
            primaryStage.getIcons().add(new Image("media/icon128128.png"));
            Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
            primaryStage.setTitle("JTracker");
            primaryStage.setScene(new Scene(root, 990, 400));
            primaryStage.centerOnScreen();
            primaryStage.show();

        }

    }


    public static void main(String[] args) {
        launch(args);
    }


    ;
}


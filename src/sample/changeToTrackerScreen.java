package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class changeToTrackerScreen {
    Stage primaryStage;

    public changeToTrackerScreen(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    int width;
    int height;

    public int getCount() {
        trackerController tc = new trackerController();

        File monthlyFile = new File("monthlyConfig.txt");
        File weeklyFile = new File("weeklyConfig.txt");
        File dailyFile = new File("dailyConfig.txt");
        int count = 0;

        count += tc.getBarCount(monthlyFile);
        count += tc.getBarCount(weeklyFile);
        count += tc.getBarCount(dailyFile);
        return count;

    }

    public void change() throws IOException {
        primaryStage.getIcons().add(new Image("media/icon128128.png"));
        Parent root = FXMLLoader.load(getClass().getResource("trackerPage.fxml"));
        if (getCount()>=4) {
            width = 1280;
            height = 780;
        }
        else{
            width = 1280;
            height = 600;

        }
        primaryStage.setTitle("JTracker");
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    ;
}

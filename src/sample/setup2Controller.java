package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class setup2Controller {

    public ToggleButton bigChin;
    public ToggleButton guthixCache;
    public ToggleButton sinkhole;
    public ToggleButton reaper;
    public ToggleButton shootingStar;
    public ToggleButton travMerch;
    public ToggleButton nemiForest;
    public ToggleButton bork;
    public ToggleButton bookOfChar;
    public ToggleButton visWax;
    public ToggleButton jot;
    public ToggleButton scarabs;
    public ToggleButton feathers;
    public ToggleButton potatoCactus;

    private ToggleButton[] dailyToggleButtons;

    public void initialize() {
        dailyToggleButtons = new ToggleButton[]{bigChin, guthixCache, sinkhole, reaper, shootingStar, travMerch, nemiForest,
                bork, bookOfChar, visWax, jot, scarabs, feathers, potatoCactus};
    }

    public void makeConfig(ActionEvent actionEvent) throws IOException {
        FileWriter dailyFile = null;
        try {
            dailyFile = new FileWriter("dailyConfig.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bw = new BufferedWriter(dailyFile);
        try {
            for (ToggleButton tb : dailyToggleButtons) {
                if (tb.isSelected()) {
                    bw.write(tb.getText());
                    bw.newLine();
                }
            }
            bw.flush();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent setup = FXMLLoader.load(getClass().getResource("trackerPage.fxml"));
        Scene setupScene = new Scene(setup);
        setupScene.getStylesheets().add("sample/stylesheet.css");
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(setupScene);
    }
}

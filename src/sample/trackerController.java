package sample;

import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class trackerController {
    public HBox monthlyBar;
    public VBox vbox;

    private File monthlyFile;
    private File weeklyFile;
    private File dailyFile;

    public void initialize(){


        monthlyFile = new File("monthlyConfig.txt");
        weeklyFile = new File("weeklyConfig.txt");
        dailyFile =  new File("dailyConfig.txt");
        try {
            Scanner monthlySc = new Scanner(monthlyFile);
            Scanner weeklySc = new Scanner(weeklyFile);
            Scanner dailySc = new Scanner(dailyFile);

            //Reading monthly info
            addText("Monthly");
            HBox monthlyBar = addHbar();
            vbox.getChildren().add(monthlyBar);
            while (monthlySc.hasNext()){
                String name = monthlySc.nextLine();
                addButton(name, monthlyBar);
            }

            //Reading weekly info
            addText("Weekly");
            HBox weeklyBar = addHbar();
            vbox.getChildren().add(weeklyBar);




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void addButton(String buttonName, HBox hbox){
        ToggleButton temp = new ToggleButton();
        temp.setText(buttonName);
        temp.setPrefSize(255,62);
        hbox.getChildren().add(temp);
    }

    public HBox addHbar(){
        HBox temp = new HBox();
        temp.getStyleClass().add("tracker-hbox");
        vbox.getChildren().add(temp);
        return temp;
    }

    public void addText(String name){
        Text temp = new Text(name);
        temp.getStyleClass().add("tracker-text");
        vbox.getChildren().add(temp);
    }
}

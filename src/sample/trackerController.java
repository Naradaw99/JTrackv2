package sample;

import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class trackerController {
    public HBox monthlyBar;
    public VBox vbox;
    public AnchorPane anchorPane;

    private File monthlyFile;
    private File weeklyFile;
    private File dailyFile;
    private File saveFile;
    boolean completedDnd = false;

    public void initialize() {


        monthlyFile = new File("monthlyConfig.txt");
        weeklyFile = new File("weeklyConfig.txt");
        dailyFile = new File("dailyConfig.txt");

        try {
            Scanner monthlySc = new Scanner(monthlyFile);
            Scanner weeklySc = new Scanner(weeklyFile);
            Scanner dailySc = new Scanner(dailyFile);

            //Reading daily info
            addText("Daily");
            for (int i = 0; i < getBarCount(dailyFile); i++) {
                HBox bar = addHbar();
                vbox.getChildren().add(bar);
                for (int j = 0; j < 4; j++) {
                    try {
                        String buttonName = dailySc.nextLine();
                        addButton(buttonName, bar);
                    } catch (NoSuchElementException e) {
                    }
                }
            }

            //Reading weekly info
            addText("Weekly");
            for (int i = 0; i < getBarCount(weeklyFile); i++) {
                HBox bar = addHbar();
                vbox.getChildren().add(bar);
                for (int j = 0; j < 4; j++) {
                    try {
                        String buttonName = weeklySc.nextLine();
                        addButton(buttonName, bar);
                    } catch (NoSuchElementException e) {
                    }
                }
            }
            //Reading monthly info
            addText("Monthly");
            HBox monthlyBar = addHbar();
            vbox.getChildren().add(monthlyBar);
            while (monthlySc.hasNextLine()) {
                String name = monthlySc.nextLine();
                addButton(name, monthlyBar);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public ArrayList<String> loadSave(){
        saveFile = new File("saveFile.txt");
        ArrayList<String> tempList = new ArrayList<String>();

        try {
            Scanner sc =  new Scanner(saveFile);
            while(sc.hasNextLine()){
                String toadd = sc.nextLine();
                System.out.println(toadd);
                tempList.add(toadd);
            }
            sc.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tempList;
    }

    public void addButton(String buttonName, HBox hbox) {
        ToggleButton temp = new ToggleButton();
        temp.setText(buttonName);
        temp.setOnAction(event -> {
            save(temp);
        });
        temp.setPrefSize(255, 62);
        if (loadSave().contains(buttonName)){
            temp.setSelected(true);
        }

        hbox.getChildren().add(temp);
    }

    public HBox addHbar() {
        HBox temp = new HBox();
        temp.getStyleClass().add("tracker-hbox");
        return temp;
    }

    public void addText(String name) {
        Text temp = new Text(name);
        temp.getStyleClass().add("tracker-text");
        vbox.getChildren().add(temp);
    }

    public int getBarCount(File file) {
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        float count = 0;
        while (sc.hasNext()) {
            sc.nextLine();
            count++;
        }
        sc.close();
        //rounding up
        float tempCount = count / 4;
        return (int) Math.ceil(tempCount);

    }

    public void testState(ToggleButton button){
        System.out.println(button.isSelected());
    }

    public void save(ToggleButton button) {
        try {
            if (saveFile.exists()) {
                FileWriter fw = new FileWriter("saveFile.txt", true);
                if (!button.isSelected()){
                    System.out.println("1");
                    //this filewriter is for writing an empty file
                    ArrayList<String> tempList = loadSave();
                    tempList.remove(button.getText());
                    FileWriter fw1 = new FileWriter("saveFile.txt", false);
                    for (String dnd: tempList){
                        fw1.write(dnd);
                        fw1.write("\n");
                    }
                    fw1.flush();
                }
                else{
                    System.out.println("2");
                    fw.write(button.getText());
                    fw.write("\n");
                    fw.flush();
                }
                fw.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

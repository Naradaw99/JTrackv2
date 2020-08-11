package sample;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import jdk.swing.interop.SwingInterOpUtils;

import java.io.*;
import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.*;

public class trackerController {
    public VBox vbox;
    public AnchorPane anchorPane;
    public Text dailyText = new Text("Daily: ");
    public Text weeklyText = new Text("Weekly: ");
    public Text monthlyText = new Text("Monthly: ");
    public Button restartText;

    @FXML

    private File monthlyFile;
    private File weeklyFile;
    private File dailyFile;
    private File saveFile;
    ArrayList<ToggleButton> dailyList;
    ArrayList<ToggleButton> weeklyList;
    ArrayList<ToggleButton> monthlyList;



    enum Type {
        DAILY, WEEKLY, MONTHLY;
    }

    public void initialize() throws IOException {
        timerWeekly();

        dailyList = new ArrayList<ToggleButton>();
        weeklyList = new ArrayList<ToggleButton>();
        monthlyList = new ArrayList<ToggleButton>();


        monthlyFile = new File("monthlyConfig.txt");
        weeklyFile = new File("weeklyConfig.txt");
        dailyFile = new File("dailyConfig.txt");

        try {
            Scanner monthlySc = new Scanner(monthlyFile);
            Scanner weeklySc = new Scanner(weeklyFile);
            Scanner dailySc = new Scanner(dailyFile);

            //Reading daily info
            addText(dailyText);
            for (int i = 0; i < getBarCount(dailyFile); i++) {
                HBox bar = addHbar();
                vbox.getChildren().add(bar);
                for (int j = 0; j < 4; j++) {
                    try {
                        String buttonName = dailySc.nextLine();
                        addButton(buttonName, bar, Type.DAILY);
                    } catch (NoSuchElementException e) {
                    }
                }
            }

            //Reading weekly in
            addText(weeklyText);
            for (int i = 0; i < getBarCount(weeklyFile); i++) {
                HBox bar = addHbar();
                vbox.getChildren().add(bar);
                for (int j = 0; j < 4; j++) {
                    try {
                        String buttonName = weeklySc.nextLine();
                        addButton(buttonName, bar, Type.WEEKLY);
                    } catch (NoSuchElementException e) {
                    }
                }
            }
            timerWeekly();
            //Reading monthly info
            addText(monthlyText);
            HBox monthlyBar = addHbar();
            vbox.getChildren().add(monthlyBar);
            while (monthlySc.hasNextLine()) {
                String name = monthlySc.nextLine();
                addButton(name, monthlyBar, Type.MONTHLY);
            }

            timerDaily();
            timerWeekly();
            timerMonthly();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public ArrayList<String> loadSave() {
        saveFile = new File("saveFile.txt");
        ArrayList<String> tempList = new ArrayList<String>();

        try {
            Scanner sc = new Scanner(saveFile);
            while (sc.hasNextLine()) {
                String toadd = sc.nextLine();
                tempList.add(toadd);
            }
            sc.close();

        } catch (FileNotFoundException e) {
        }
        return tempList;
    }

    public void addButton(String buttonName, HBox hbox, Type t) {
        ToggleButton temp = new ToggleButton();
        temp.setText(buttonName);
        temp.setOnAction(event -> {
            save(temp);
        });
        temp.setPrefSize(255, 62);
        if (loadSave().contains(buttonName)) {
            temp.setSelected(true);
        }

        hbox.getChildren().add(temp);

        switch (t) {
            case DAILY:
                dailyList.add(temp);
                break;
            case WEEKLY:
                weeklyList.add(temp);
                break;
            case MONTHLY:
                monthlyList.add(temp);
                break;
        }
    }

    public HBox addHbar() {
        HBox temp = new HBox();
        temp.getStyleClass().add("tracker-hbox");
        return temp;
    }

    public void addText(Text text) {
        text.getStyleClass().add("tracker-text");
        vbox.getChildren().add(text);
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


    public void save(ToggleButton button) {
        try {

            FileWriter fw = new FileWriter("saveFile.txt", true);
            if (!button.isSelected()) {
                System.out.println("1");
                ArrayList<String> tempList = loadSave();
                tempList.remove(button.getText());
                FileWriter fw1 = new FileWriter("saveFile.txt", false);
                for (String dnd : tempList) {
                    fw1.write(dnd);
                    fw1.write("\n");
                }
                fw1.flush();
            } else {
                System.out.println("2");
                fw.write(button.getText());
                fw.write("\n");
                fw.flush();
            }
            fw.flush();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public void timerDaily() {
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
            LocalDate tomorrow = now.toLocalDate().plusDays(1);
            ZonedDateTime tomorrowStart = tomorrow.atStartOfDay(ZoneId.of("UTC"));
            java.time.Duration duration = java.time.Duration.between(now, tomorrowStart);
            Long seconds = duration.toSeconds();
            Long hours = seconds / 3600;
            Long minutesLeft = (seconds % 3600) / 60;

            if (seconds == 0) {
                try {
                    reset(dailyList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (hours < 1) {
                dailyText.setText("Daily: " + hours + " hour " + minutesLeft + " minutes");
            } else {
                dailyText.setText("Daily: " + hours + " hours " + minutesLeft + " minutes");
            }
        });

        playAnimation(keyFrame);
    }

    public void timerWeekly() {

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            TimeZone tz = TimeZone.getTimeZone("UTC");
            Calendar weeklyReset = Calendar.getInstance(tz);
            Calendar now = Calendar.getInstance(tz);

            weeklyReset.set(Calendar.SECOND, 0);
            weeklyReset.set(Calendar.MINUTE, 0);
            weeklyReset.set(Calendar.HOUR, 24);
            weeklyReset.set(Calendar.AM_PM, Calendar.AM);
            weeklyReset.set(Calendar.DAY_OF_WEEK, 3);

            Long hoursBetween = ChronoUnit.HOURS.between(now.toInstant(), weeklyReset.toInstant());
            Long days = hoursBetween / 24;
            Long hoursLeft = hoursBetween % 24;

            if (hoursBetween == 0) {
                try {
                    reset(weeklyList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (hoursLeft > 1) {
                weeklyText.setText("Weekly: " + days + " days " + hoursLeft + " hours");
            } else {
                weeklyText.setText("Weekly: " + days + " days " + hoursLeft + " hour");
            }
        });

        playAnimation(keyFrame);
    }

    public void timerMonthly() {

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            TimeZone tz = TimeZone.getTimeZone("UTC");
            Calendar now = Calendar.getInstance(tz);

            Calendar nextMonth = Calendar.getInstance(tz);
            nextMonth.add(Calendar.MONTH, 1);
            nextMonth.set(Calendar.DAY_OF_MONTH, 1);
            nextMonth.set(Calendar.SECOND, 0);
            nextMonth.set(Calendar.MINUTE, 0);
            nextMonth.set(Calendar.HOUR, 24);
            nextMonth.set(Calendar.AM_PM, Calendar.AM);

            Long hoursBetween = ChronoUnit.HOURS.between(now.toInstant(), nextMonth.toInstant());
            Long days = hoursBetween / 24;
            Long hoursLeft = hoursBetween % 24;

            if (hoursBetween == 0) {
                try {
                    reset(monthlyList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (days > 1) {
                monthlyText.setText("Monthly: " + days + " days " + hoursLeft + " hours");
            } else {
                monthlyText.setText("Monthly: " + days + " day " + hoursLeft + " hours");
            }
        });

        playAnimation(keyFrame);
    }

    public void playAnimation(KeyFrame kf) {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(kf);
        timeline.play();


    }

    public void restart(ActionEvent actionEvent) throws IOException {
        Parent setup = FXMLLoader.load(getClass().getResource("setupPage.fxml"));
        Scene setupScene = new Scene(setup);
        setupScene.getStylesheets().add("sample/stylesheet.css");
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(setupScene);
        window.centerOnScreen();
    }


    public void reset(ArrayList<ToggleButton> list) throws IOException {
        for (ToggleButton b : list) {
            b.setSelected(false);
        }
        ArrayList<String> currentSave = loadSave();
        System.out.println(currentSave.size());

        for (ToggleButton b : list) {
            if (currentSave.contains(b.getText())) {
                currentSave.remove(b.getText());
            }
        }
        System.out.println(currentSave.size());
        FileWriter fw1 = new FileWriter("saveFile.txt", false);
        for (String s : currentSave) {
            fw1.write(s);
            fw1.write("\n");
        }
        fw1.flush();
        fw1.close();
    }

}

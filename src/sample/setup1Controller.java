package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class setup1Controller {

    @FXML
    private ToggleButton trollInv;
    @FXML
    private ToggleButton godStat;
    @FXML
    private ToggleButton oyester;
    @FXML
    private ToggleButton premier;
    @FXML
    private ToggleButton herby;
    @FXML
    private ToggleButton pengs;
    @FXML
    private ToggleButton meg;
    @FXML
    private ToggleButton tog;
    @FXML
    private ToggleButton nomad;
    @FXML
    private ToggleButton rushOfBlood;
    @FXML
    private ToggleButton famili;
    @FXML
    private ToggleButton circus;
    @FXML
    private ToggleButton agoroth;
    @FXML
    private ToggleButton wisps;
    @FXML
    private ToggleButton skeletal;
    @FXML
    private ToggleButton invention;

    private ToggleButton[] monthlyToggleButtons;
    private ToggleButton[] weeklyToggleButtons;


    public void initialize(){
        monthlyToggleButtons = new ToggleButton[] {trollInv,godStat,oyester,premier};
        weeklyToggleButtons = new ToggleButton[] {herby,pengs,meg,tog,nomad,rushOfBlood,famili,circus,agoroth,wisps,skeletal,invention};

    }


    public void addMonthly(ActionEvent actionEvent) {
       // System.out.println(trollInv.get);

    }


    public void makeConfig(ActionEvent actionEvent) throws IOException {
        FileWriter monthlyFile = null;
        FileWriter weeklyFile = null;
        try {
            monthlyFile = new FileWriter("monthlyConfig.txt");
            weeklyFile = new FileWriter("weeklyConfig.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bw1 = new BufferedWriter(monthlyFile);
        BufferedWriter bw2 = new BufferedWriter(weeklyFile);
        try {
            for (ToggleButton tb:monthlyToggleButtons){
                if (tb.isSelected()){
                    bw1.write(tb.getText());
                    bw1.newLine();


                }
            }

            for (ToggleButton tb:weeklyToggleButtons){
                if (tb.isSelected()){
                    bw2.write(tb.getText());
                    bw2.newLine();
                }
            }
            bw1.flush();
            bw2.flush();
            bw1.close();
            bw2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent setup = FXMLLoader.load(getClass().getResource("setupPageTwo.fxml"));
        Scene setupScene = new Scene(setup);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(setupScene);
    }

}

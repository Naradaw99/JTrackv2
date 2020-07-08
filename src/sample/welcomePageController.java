package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class welcomePageController {


    @FXML
    private MediaView mv;
    private MediaPlayer mp;
    private Media me;

    public void initialize(){
        String path = new File("src/media/videoWallpaper.mp4").getAbsolutePath();
        me = new Media(new File(path).toURI().toString());
        mp = new MediaPlayer(me);
        mv.setMediaPlayer(mp);
        mp.setAutoPlay(true);
        mp.setCycleCount(mp.INDEFINITE);
    }


    public void nextPage(ActionEvent actionEvent) throws IOException, InterruptedException {

        Parent setup = FXMLLoader.load(getClass().getResource("setupPage.fxml"));
        Scene setupScene = new Scene(setup);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(setupScene);



    }
}

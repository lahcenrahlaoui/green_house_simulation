package sample;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {



    public static int circleTime;





    public void onClickAddClient(ActionEvent event) throws InterruptedException {

        Main.tank =4000;
        Main.XX = 4000 ;
        Main.p = Main.XX - 1000 ;
        Main.activetank = true;
        System.out.println(" you added 4000 it and it  IS now  :   " + Main.tank);
    }

    public void onClick5AddClient(ActionEvent event) throws InterruptedException {

        for (int i = 0 ; i<=0 ; i++) {
            new Thread(new Client()).start();
        }
    }
    public void onClickAddClientRandomTime(ActionEvent event) throws InterruptedException {

        Random random = new Random();
        for (int i = 0 ; i<=random.nextInt(10) ; i++) {

            new Thread(new Client()).start();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {


 //       Main.r1.setProgress(Main.XX);




    }


}

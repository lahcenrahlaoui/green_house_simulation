package sample;
import com.jfoenix.controls.JFXProgressBar;
import hhh.FillProgressIndicator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Semaphore;



public class Main extends Application {


    // START DECLARATION
    public static Semaphore semaphoreTank = new Semaphore(1);
    public static Semaphore semaphoreWell = new Semaphore(4);
    public static Semaphore h3 = new Semaphore(1);



    public static int waiting = 0;
    public static int calling = 0;

    public static int numberOfCabines = 8;

    public static int numberOfChairs  = 5 ;

    public static final Point point1  = new Point(1450.0, 350);

    public static final Point point2  = new Point(1200.0, 350);

    public static final ArrayList<Point> cabinPoints = new ArrayList<>(
            Arrays.asList(
                new Point(90.0 , 150.0),
                new Point(90.0 +70, 150.0),
                new Point(280.0, 150.0),
                new Point(280.0+70, 150.0),
                new Point(510.0, 150.0),
                new Point(510.0+70, 150.0),
                new Point(740.0, 150.0),
                new Point(740.0+70, 150.0)

            )
    );

    public static final ArrayList<Point> waitPoints = new ArrayList<>(
            Arrays.asList(
                new Point(100.0 ,448),
                new Point(100.0 ,548),
                new Point(200.0 ,448),
                new Point(200.0 ,548),
                new Point(300.0 ,448)
            )
    );

    public static final ArrayList<Free> freeCabin = new ArrayList<>(
            Arrays.asList(
                    new Free(0,true),
                    new Free(1,true),
                    new Free(2,true),
                    new Free(3,true),
                    new Free(4,true),
                    new Free(5,true),
                    new Free(6,true),
                    new Free(7,true)
            )
    );

    public static final ArrayList<Free> freeWait = new ArrayList<>(
            Arrays.asList(
                    new Free(0,true),
                    new Free(1,true),
                    new Free(2,true),
                    new Free(3,true),
                    new Free(4,true)
            )
    );



    static int tank = 4000  ;
    static  boolean activetank = true ;
    static boolean Well = true  ;



    public static final Semaphore hey = new Semaphore(4);



    public static final Semaphore p1 = new Semaphore(1);
    public static final Semaphore p2 = new Semaphore(1);

    public static final Semaphore waitC = new Semaphore(1);
    public static final Semaphore callC = new Semaphore(1);
    public static final Semaphore checkout = new Semaphore(6);

    public static final Semaphore entree = new Semaphore(1);

    public static final Semaphore gettingWaitingSeat = new Semaphore(numberOfChairs);
    public static final Semaphore getFreeWaitingSeat = new Semaphore(1);

    public static Semaphore gettingCabin = new Semaphore(numberOfCabines);
    public static final Semaphore getCabin = new Semaphore(1);


    public static Double revenueC = Double.valueOf(0);

    public static Label revenueLabel = new Label();

    public static AnchorPane root;


    public static FillProgressIndicator r1 = new FillProgressIndicator();


    public static  int XX = 4000 ;
    public static  int p = XX - 1000  ;


    static double ii = 0;
    static double kk = 1;

    public static ProgressBar tubeWater = new ProgressBar();
    public static ProgressBar tankprog = new ProgressBar();
// FIN DECLARATION

// START METHOD START
    @Override
    public void start(Stage primaryStage) throws Exception{

        root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.setTitle("taxi_phone");


     //   r1.setLayoutX(1100);
     //   r1.setLayoutY(0);


        Platform.runLater(() -> {
      //      Main.root.getChildren().add(Main.r1);
            Main.revenueLabel.setText(String.valueOf(Main.revenueC));
            Main.root.getChildren().add(Main.revenueLabel);
        });



            tankprog.setStyle(" -fx-accent: skyblue; ");
            tankprog.setProgress(1);
            tankprog.setRotate(-90);
            tankprog.setLayoutX(1100);
            tankprog.setLayoutY(0);
            tankprog.setPrefHeight(220);
            tankprog.setPrefWidth(240);


            tubeWater.setStyle(" -fx-accent: skyblue; ");
            tubeWater.setProgress(0);
            tubeWater.setRotate(180);
            tubeWater.setLayoutX(90);
            tubeWater.setLayoutY(210);
            tubeWater.setPrefHeight(20);
            tubeWater.setPrefWidth(1023);


            root.getChildren().add(tankprog);
            root.getChildren().add(tubeWater);




        primaryStage.fullScreenExitHintProperty();

        primaryStage.setScene(new Scene(root, 1400, 700));
        primaryStage.setResizable(true);
        primaryStage.show();


    }


    public static void tubeWaterPlus(ProgressBar p , int j )throws InterruptedException {

        int k = 0 ;
        switch (j){
            case 0 : k = 100; break;
            case 1 : k = 90 ; break;
            case 2 : k = 80 ; break;
            case 3 : k = 70 ; break;
            case 4 : k = 60 ; break;
            case 5 : k = 50 ; break;
            case 6 : k = 40 ; break;
            case 7 : k = 30 ; break;
        }

            for(int i = 0 ; i <=k ; i++) {
                Thread.sleep(5);
                ii += 0.01;
                Platform.runLater(()->  p.setProgress(ii) );
            }
        }


    public static void tubeWaterMinus(ProgressBar p, int j ) throws InterruptedException {

            int k = 0 ;
            switch (j){
                case 0 : k = 100; break ;
                case 1 : k = 90 ; break ;
                case 2 : k = 80 ; break ;
                case 3 : k = 70 ; break ;
                case 4 : k = 60 ; break ;
                case 5 : k = 50 ; break ;
                case 6 : k = 40 ; break ;
                case 7 : k = 30 ; break ;
            }
            for(int i = k ; i >= 0 ; i--) {
                Thread.sleep(5);
                ii -= 0.01;
                Platform.runLater(() -> p.setProgress(ii));
             }
    }
    public static void tankWaterMinus(ProgressBar p , int j ) throws InterruptedException {
     /*   int k = 0 ;
        switch (j){
            case 4000 : k = 4000; break;
            case 3000 : k = 3000 ; break;
            case 2000 : k = 2000 ; break;
            case 1000 : k = 1000 ; break;

        }*/

     int k = j ;
         for(int i = k ; i > k-1000 ; i--) {
             Thread.sleep(1);


        }
    }







/*
    public static void tankWaterPlus(ProgressBar p , int j ) throws InterruptedException {

        for(int i = 0 ; i <=j ; i++) {
            Thread.sleep(5);
            ii += 0.01;
            Platform.runLater(()->  p.setProgress(ii) );
        }
    }

    public static void tankWaterMinus(ProgressBar p , int j ) throws InterruptedException {

        for(int i = j ; i >=100 ; i--) {
            Thread.sleep(5);
            ii -= 0.01;
            Platform.runLater(()->  p.setProgress(ii) );

        }
    }

*/



// FIN METHOD START

// START MAIN
    public static void main(String[] args) {

        launch(args);

    }


// FIN MAIN
}


// START POINT
class Point{

    private double X, Y;

    public Point(double x, double y) {
        X = x;
        Y = y;

    }

    public Point() {
        X = 0.0;
        Y = 0.0;
    }

    public double getX() {

        return X;
    }

    public Point setX(double x) {

        X = x;
        return this;
    }

    public double getY() {

        return Y;
    }

    public Point setY(double y) {

        Y = y;
        return this;
    }

}
// FIN POINT

// START FREE
class Free{

    private int index;

    private boolean free;

    public Free(int i, boolean f){

        index = i;
        free = f;
    }


    public Free setIndex(int index) {

        this.index = index;
        return this;
    }

    public boolean isFree() {

        return free;
    }

        public Free setFree(boolean free) {

        this.free = free;
        return this;
    }
}
// FIN FREE



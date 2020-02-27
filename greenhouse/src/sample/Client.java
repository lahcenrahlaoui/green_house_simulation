package sample;

import hhh.FillProgressIndicator;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.*;
import javafx.util.Duration;
import hhh.*;

 import java.util.concurrent.Semaphore;

import static sample.Main.r1;

public class Client implements Runnable {
    static int watering = 10000 ;
    static int k = 0;
    double vitess = 1.5;
    public Client( ) { }


    @Override
    public void run() {

        try{




            Semaphore s = new Semaphore(0);

            Main.entree.acquire();
            Main.p1.acquire();

            ImageView plant = imgplant(0);
            plant.setLayoutX(Main.point1.getX());
            plant.setLayoutY(Main.point1.getY());
            ImageView firstPlant = plant;
            Platform.runLater(() -> {     Main.root.getChildren().add(firstPlant);  });

            Main.p2.acquire();


            PathTransition pathTransition = new PathTransition();
            pathTransition.setDuration(Duration.seconds(vitess));
            pathTransition.setNode(firstPlant);
            Path path = new Path();

            path.getElements().add(new MoveTo(0, 0 ));
            path.getElements().add(new QuadCurveTo(
                    Main.point2.getX() - Main.point1.getX(),
                    Main.point1.getY() - Main.point1.getY(),
                    Main.point2.getX() - Main.point1.getX(),
                    Main.point2.getY() - Main.point1.getY()
            ));


            pathTransition.setPath(path);
            PathTransition finalPathTransition = pathTransition;
            finalPathTransition.setOnFinished(event -> s.release());
            Platform.runLater(() -> { finalPathTransition.play();  });
            s.acquire();





            Main.p1.release();

            ImageView image = new ImageView("sample/phone.gif");
            image.setFitWidth(30);
            image.setFitHeight(30);


            Point waitPoint = null;
            int waitPlace = -1;

            Point cabinPoint = null;
            int freeCabinIndex = -1;


            Main.waitC.acquire();

            if (Main.waiting >= Main.numberOfChairs)
            {

                Main.waitC.release();

                Main.gettingWaitingSeat.acquire();

                Main.waitC.acquire();
                Main.waiting++;
                Main.waitC.release();

                Main.getFreeWaitingSeat.acquire();


                for (int i = 0; i < Main.freeWait.size(); i++) {
                    if ( Main.freeWait.get(i).isFree() ) {
                        waitPlace = i;
                        waitPoint = Main.waitPoints.get(i);
                        Main.freeWait.get(i).setFree(false);
                        break;
                    }
                }
                Main.getFreeWaitingSeat.release();

                if ( Main.p2 != null && waitPoint != null) {


                    pathTransition = new PathTransition();
                    pathTransition.setDuration(Duration.seconds(vitess));
                    pathTransition.setNode(firstPlant);
                    path = new Path();
                    path.getElements().add(new MoveTo(
                            Main.point2.getX() - Main.point1.getX(),
                            Main.point2.getY() - Main.point1.getY()
                    ));
                    path.getElements().add(new QuadCurveTo(
                            waitPoint.getX() - Main.point1.getX(),
                            Main.point2.getY() - Main.point1.getY(),
                            waitPoint.getX() - Main.point1.getX(),
                            waitPoint.getY() - Main.point1.getY()   ));
                    pathTransition.setPath(path);
                    path.getElements().add(new MoveTo(
                            Main.point2.getX() - Main.point1.getX(),
                            Main.point2.getY() - Main.point1.getY()
                    ));
                    path.getElements().add(new QuadCurveTo(
                            waitPoint.getX() - Main.point1.getX(),
                            Main.point2.getY() - Main.point1.getY(),
                            waitPoint.getX() - Main.point1.getX(),
                            waitPoint.getY() - Main.point1.getY()
                    ));
                    pathTransition.setPath(path);
                }
                PathTransition finalPathTransition1 = pathTransition;
                finalPathTransition1.setOnFinished(event -> s.release());
                Platform.runLater(() -> { finalPathTransition1.play();  });
                s.acquire();


                Main.p2.release();

                Main.entree.release();

                Main.gettingCabin.acquire();

                Main.getCabin.acquire();


                for (int i = 0; i < Main.freeCabin.size(); i++) {
                    if (Main.freeCabin.get(i).isFree()) {
                        cabinPoint = Main.cabinPoints.get(i);
                        Main.freeCabin.get(i).setFree(false);
                        freeCabinIndex = i;
                        break;
                    }
                }

                Main.getCabin.release();

                if (cabinPoint != null && waitPoint != null) {

                    pathTransition = new PathTransition();
                    pathTransition.setDuration(Duration.seconds(vitess));
                    pathTransition.setNode(firstPlant);
                    path = new Path();

                    path.getElements().add(new MoveTo(
                            waitPoint.getX() - Main.point1.getX(),
                            waitPoint.getY() - Main.point1.getY()
                    ));
                    path.getElements().add(new QuadCurveTo(
                            Main.point2.getX() - Main.point1.getX(),
                            Main.point2.getY() - Main.point1.getY(),
                            Main.point2.getX() - Main.point1.getX(),
                            Main.point1.getY() - Main.point1.getY()
                    ));

                    pathTransition.setPath(path);

                }

                PathTransition finalPathTransition6 = pathTransition;
                finalPathTransition6.setOnFinished(event -> {
                    s.release();
                });
                Platform.runLater(() -> {

                    finalPathTransition6.play();
                });
                s.acquire();


                Main.callC.acquire();
                Main.calling++;
                Main.callC.release();

                Main.getFreeWaitingSeat.acquire();
                Main.freeWait.get(waitPlace).setFree(true);
                Main.getFreeWaitingSeat.release();

                Main.waitC.acquire();
                Main.waiting--;
                Main.waitC.release();
                Main.gettingWaitingSeat.release();
            }
            else
            {

                if (Main.waiting == 0)
                {

                    Main.callC.acquire();
                    if (Main.calling >= Main.numberOfCabines)
                    {
                        Main.callC.release();
                        Main.gettingWaitingSeat.acquire();
                        Main.waiting++;
                        Main.waitC.release();

                        Main.getFreeWaitingSeat.acquire();


                        for (int i = 0; i < Main.freeWait.size(); i++) {
                            if ( Main.freeWait.get(i).isFree() ) {
                                waitPlace = i;
                                waitPoint = Main.waitPoints.get(i);
                                Main.freeWait.get(i).setFree(false);
                                break;
                            }
                        }

                        Main.getFreeWaitingSeat.release();

                        if ( Main.p2 != null && waitPoint != null) {


                            pathTransition = new PathTransition();
                            pathTransition.setDuration(Duration.seconds(vitess));
                            pathTransition.setNode(firstPlant);
                            path = new Path();

                            path.getElements().add(new MoveTo(
                                    Main.point2.getX() - Main.point1.getX(),
                                    Main.point2.getY() - Main.point1.getY()
                            ));
                            path.getElements().add(new QuadCurveTo(
                                    waitPoint.getX() - Main.point1.getX(),
                                    Main.point2.getY() - Main.point1.getY(),
                                    waitPoint.getX() - Main.point1.getX(),
                                    waitPoint.getY() - Main.point1.getY()
                            ));

                            pathTransition.setPath(path);

                        }

                        PathTransition finalPathTransition4 = pathTransition;
                        finalPathTransition4.setOnFinished(event -> {
                            s.release();
                        });
                        Platform.runLater(() -> {
                            finalPathTransition4.play();

                        });
                        s.acquire();

                        Main.p2.release();

                        Main.entree.release();

                        Main.gettingCabin.acquire();

                        Main.getCabin.acquire();

                        for (int i = 0; i < Main.freeCabin.size(); i++) {
                            if (Main.freeCabin.get(i).isFree()) {
                                cabinPoint = Main.cabinPoints.get(i);
                                Main.freeCabin.get(i).setFree(false);
                                freeCabinIndex = i;
                                break;
                            }
                        }

                        Main.getCabin.release();

                        if (cabinPoint != null && waitPoint != null) {


                            pathTransition = new PathTransition();
                            pathTransition.setDuration(Duration.seconds(vitess));
                            pathTransition.setNode(firstPlant);
                            path = new Path();

                            path.getElements().add(new MoveTo(
                                    waitPoint.getX() - Main.point1.getX(),
                                    waitPoint.getY() - Main.point1.getY()
                            ));
                            path.getElements().add(new QuadCurveTo(
                                    cabinPoint.getX() - Main.point1.getX(),
                                    waitPoint.getY() - Main.point1.getY(),
                                    cabinPoint.getX() - Main.point1.getX(),
                                    cabinPoint.getY() - Main.point1.getY()
                            ));

                            pathTransition.setPath(path);

                        }

                        PathTransition finalPathTransition5 = pathTransition;
                        finalPathTransition5.setOnFinished(event -> {
                            s.release();
                        });
                        Platform.runLater(() -> {
                            finalPathTransition5.play();
                        });
                        s.acquire();

                        Main.callC.acquire();
                        Main.calling++;
                        Main.callC.release();

                        Main.getFreeWaitingSeat.acquire();
                        Main.freeWait.get(waitPlace).setFree(true);
                        Main.getFreeWaitingSeat.release();

                        Main.waitC.acquire();
                        Main.waiting--;
                        Main.waitC.release();

                        Main.gettingWaitingSeat.release();

                    }

                    else
                    {

                        Main.gettingCabin.acquire();

                        Main.getCabin.acquire();
                        for (int i = 0; i < Main.freeCabin.size(); i++) {
                            if (Main.freeCabin.get(i).isFree()) {
                                cabinPoint = Main.cabinPoints.get(i);
                                Main.freeCabin.get(i).setFree(false);
                                freeCabinIndex = i;
                                break;
                            }
                        }



                        Main.getCabin.release();

                        if (cabinPoint != null && Main.point2 != null) {



                            pathTransition = new PathTransition();
                            pathTransition.setDuration(Duration.seconds(vitess));
                            pathTransition.setNode(firstPlant);
                            path = new Path();

                            path.getElements().add(new MoveTo(
                                    Main.point2.getX() - Main.point1.getX(),
                                    Main.point2.getY() - Main.point1.getY()
                            ));
                            path.getElements().add(new QuadCurveTo(
                                    cabinPoint.getX() - Main.point1.getX(),
                                    Main.point2.getY() - Main.point1.getY(),
                                    cabinPoint.getX() - Main.point1.getX(),
                                    cabinPoint.getY() - Main.point1.getY()
                            ));

                            pathTransition.setPath(path);

                        }

                        PathTransition finalPathTransition6 = pathTransition;
                        finalPathTransition6.setOnFinished(event -> {
                            s.release();
                        });
                        Platform.runLater(() -> {

                            finalPathTransition6.play();
                        });
                        s.acquire();


                        Main.p2.release();

                        Main.entree.release();

                        Main.calling++;

                        Main.callC.release();

                        Main.waitC.release();

                    }
                }
                else
                {


                    Main.gettingWaitingSeat.acquire();

                    Main.waiting++;
                    Main.waitC.release();

                    Main.getFreeWaitingSeat.acquire();
                    for (int i = 0; i < Main.freeWait.size(); i++) {
                        if ( Main.freeWait.get(i).isFree() ) {
                            waitPlace = i;
                            waitPoint = Main.waitPoints.get(i);
                            Main.freeWait.get(i).setFree(false);
                            break;
                        }
                    }
                    Main.getFreeWaitingSeat.release();

                    if ( Main.p2 != null && waitPoint != null ) {


                        pathTransition = new PathTransition();
                        pathTransition.setDuration(Duration.seconds(vitess));
                        pathTransition.setNode(firstPlant);
                        path = new Path();

                        path.getElements().add(new MoveTo(
                                Main.point2.getX() - Main.point1.getX(),
                                Main.point2.getY() - Main.point1.getY()
                        ));
                        path.getElements().add(new QuadCurveTo(
                                waitPoint.getX() - Main.point1.getX(),
                                Main.point2.getY() - Main.point1.getY(),
                                waitPoint.getX() - Main.point1.getX(),
                                waitPoint.getY() - Main.point1.getY()
                        ));

                        pathTransition.setPath(path);

                    }

                    PathTransition finalPathTransition7 = pathTransition;
                    finalPathTransition7.setOnFinished(event -> {
                        s.release();
                    });
                    Platform.runLater(() -> {
                        finalPathTransition7.play();
                    });
                    s.acquire();

                    Main.p2.release();

                    Main.entree.release();

                    Main.gettingCabin.acquire();

                    Main.getCabin.acquire();


                    for (int i = 0; i < Main.freeCabin.size(); i++) {
                        if (Main.freeCabin.get(i).isFree()) {
                            cabinPoint = Main.cabinPoints.get(i);
                            Main.freeCabin.get(i).setFree(false);
                            freeCabinIndex = i;
                            break;
                        }
                    }

                    Main.getCabin.release();

                    if (cabinPoint != null && waitPoint != null) {

                        pathTransition = new PathTransition();
                        pathTransition.setDuration(Duration.seconds(vitess));
                        pathTransition.setNode(firstPlant);
                        path = new Path();

                        path.getElements().add(new MoveTo(
                                waitPoint.getX() - Main.point1.getX(),
                                waitPoint.getY() - Main.point1.getY()
                        ));
                        path.getElements().add(new QuadCurveTo(
                                cabinPoint.getX() - Main.point1.getX(),
                                waitPoint.getY() - Main.point1.getY(),
                                cabinPoint.getX() - Main.point1.getX(),
                                cabinPoint.getY() - Main.point1.getY()
                        ));
                        pathTransition.setPath(path);
                    }

                    PathTransition finalPathTransition8 = pathTransition;
                    finalPathTransition8.setOnFinished(event -> {
                        s.release();
                    });
                    Platform.runLater(() -> {
                        finalPathTransition8.play();
                    });
                    s.acquire();

                    Main.callC.acquire();
                    Main.calling++;
                    Main.callC.release();

                    Main.getFreeWaitingSeat.acquire();
                    Main.freeWait.get(waitPlace).setFree(true);
                    Main.getFreeWaitingSeat.release();

                    Main.waitC.acquire();
                    Main.waiting--;
                    Main.waitC.release();

                    Main.gettingWaitingSeat.release();

                }
            }



 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     //*********************************************************************************************


            ImageView maa1 = new ImageView("sample/images/client5.png");
            maa1.setFitWidth(20);
            maa1.setFitHeight(20);
            maa1.setLayoutX(cabinPoint.getX());
            maa1.setLayoutY(cabinPoint.getY()-80);

            ImageView maa2 = new ImageView("sample/images/cashier.png");
            maa2.setFitWidth(20);
            maa2.setFitHeight(20);
            maa2.setLayoutX(cabinPoint.getX());
            maa2.setLayoutY(cabinPoint.getY()-80);

            ImageView gifPlant = new ImageView("sample/fff.gif");
            gifPlant.setLayoutX(cabinPoint.getX()-40);
            gifPlant.setLayoutY(cabinPoint.getY()-40);
            gifPlant.setFitWidth(80);
            gifPlant.setFitHeight(70);

float ii = 0 ;
                    Main.h3.acquire();

            if ( (Main.semaphoreTank.availablePermits() == 1 ) && (Main.semaphoreWell.availablePermits() > 0 ) ) k = 0 ;

            if ((Main.activetank && (k % 5  == 0)))
                    {
                            k++;


                      //  Main.tankWaterMinus(Main.tankprog ,  Main.tank);
                            Main.tank -= 1000;

                            Main.semaphoreTank.acquire();
                        Main.tubeWaterPlus(Main.tubeWater,Main.tankprog , freeCabinIndex);
                            Main.h3.release();
                                 /*            new water(r1).start();*/
                                             Platform.runLater(()-> {  Main.root.getChildren().remove(firstPlant);   Main.root.getChildren().add(gifPlant);Main.root.getChildren().add(maa2); });

                            Thread.sleep(watering);
                        Main.tubeWaterMinus(Main.tubeWater,Main.tankprog , 0);
                            Main.semaphoreTank.release();
                            if (Main.tank == 0 ){   Main.activetank = false ;  }



                    }
                    else
                    {
                            k++;

                            System.out.println(" else : " + (k-1) );
                            Main.semaphoreWell.acquire();
                            Main.h3.release();
                             Platform.runLater(()-> {
                                 Main.root.getChildren().remove(firstPlant);
                                Main.root.getChildren().add(gifPlant);
                                Main.root.getChildren().add(maa1);
                            });
                            Thread.sleep(watering);

                            Main.semaphoreWell.release();

                    }






                    Main.checkout.acquire();
                    pathTransition = new PathTransition();
                    pathTransition.setDuration(Duration.seconds(vitess));
                    pathTransition.setNode(gifPlant);
                    path = new Path();
                    path.getElements().add(new MoveTo(
                            -40,
                            -40
                    ));
                    path.getElements().add(new QuadCurveTo(
                            Main.point1.getX() - Main.point2.getX(),
                            Main.point2.getY() - Main.point2.getY(),
                            Main.point1.getX() - Main.point2.getX(),
                            Main.point1.getX() - Main.point2.getY()
                    ));
                    pathTransition.setPath(path);

                    PathTransition finalPathTransition9 = pathTransition;
                    finalPathTransition9.setOnFinished(event -> {
                        s.release();
                    });
                    Platform.runLater(()->{   Main.root.getChildren().remove(maa1); Main.root.getChildren().remove(maa2); });
                    Platform.runLater(() -> { finalPathTransition9.play();  });
                    s.acquire();


            Main.getCabin.acquire();
            Main.freeCabin.get(freeCabinIndex).setFree(true);
            Main.getCabin.release();

            Main.callC.acquire();
            Main.calling--;
            Main.callC.release();

            Main.gettingCabin.release();

            Main.checkout.release();


            Platform.runLater(() -> {  Main.root.getChildren().remove(gifPlant); });

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public ImageView imgplant(int p){

        //Image  imageplant = new Image();

        Image  image = null ;
        if (p == 0 ){
            image = new Image("sample/seed.png");
        }
        else{
            image = new Image("sample/gifplant.gif");
        }


        ImageView imageView = new ImageView(image);

        imageView.setPreserveRatio(true);
        imageView.setFitWidth(80);
        imageView.setFitHeight(70);

        return imageView;
    }




/*
    static class water extends Thread{
        FillProgressIndicator r;


        water(FillProgressIndicator r  ){
            this.r = r;

        }
        @Override
        public void run() {

            while(true){

                try {
                    Main.hey.acquire();
                    Thread.sleep(100);
                    Main.hey.release();
                }catch (Exception e){

                }
                Platform.runLater(()->{
                    r.setProgress(Main.XX);
                });


                Main.XX-=3;
                if (Main.XX <= Main.p) {
                    Main.p = Main.XX - 900;
                    break;
                }
            }


        }
    }
*/

}























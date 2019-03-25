package org.lrima.laop.core;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * Launch the LAOP platform with a graphical interface
 *
 * @author Clement Bisaillon
 */
public class LaopGraphical extends Application {
    //TODO: Pouvoir changer entre different stage (settings, simulation et conclusion)

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) {
//        ConfigurationStage configurationStage = new ConfigurationStage();
//        configurationStage.show();

        LAOP laop = new LAOP();
        laop.addAlgorithm("test1212", LaopGraphical.class, new HashMap<>());
        laop.startSimulation(LAOP.SimulationDisplayMode.WITH_INTERFACE);


    }


}

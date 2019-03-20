package org.lrima.laop.core;

import javafx.application.Application;
import javafx.stage.Stage;
import org.lrima.laop.controller.ConfigurationStage;
import org.lrima.laop.controller.SimulationStage;
import org.lrima.laop.simulation.SimulationBuffer;

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
        ConfigurationStage configurationStage = new ConfigurationStage();
        configurationStage.show();

//        SimulationStage simulationStage = new SimulationStage(new SimulationBuffer());
//        simulationStage.show();


    }


}

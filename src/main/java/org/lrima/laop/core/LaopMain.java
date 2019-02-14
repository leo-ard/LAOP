package org.lrima.laop.core;

import javafx.application.Application;

public class LaopMain {

    public static void main(String[] args){
        runGraphical();


//        SimulationManagerModel simulationManagerModel = new SimulationManagerModel(3);
//        SimulationManager simulationManager = new SimulationManager(simulationManagerModel);
//
//        simulationManagerModel.addBatch(new SimulationModel("Algorithm 1"));
//        simulationManagerModel.addBatch(new SimulationModel("Algorithm 2"));
//
//        simulationManager.addSimulationListener(new SimulationListener() {
//            @Override
//            public void simulationFinished() {
//                System.out.println("Simulation finished");
//            }
//
//            @Override
//            public void batchFinished() {
//                System.out.println("Batch finished");
//
//            }
//
//            @Override
//            public void simulationManagerFinished() {
//                System.out.println("SimulationManager finished");
//
//            }
//
//            @Override
//            public void dataReady() {
//                System.out.println("Data ready");
//            }
//        });
//
//        System.out.println("Starting the simulationManager with two algorithms. Update time is fixed at 300 ms.");
//
//        simulationManager.start();
    }

    /**
     * Run the Laop platform with a graphical interface
     *
     * @return true if successful, false otherwise.
     */
    private static boolean runGraphical() {
        //Run the graphical interface
        new Thread(() -> Application.launch(LaopGraphical.class)).start();

        return true;
    }
}

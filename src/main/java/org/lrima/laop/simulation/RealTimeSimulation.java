package org.lrima.laop.simulation;

import org.lrima.laop.network.concreteLearning.DL4JLearning;
import org.lrima.laop.network.concreteNetworks.DL4JNN;
import org.lrima.laop.physic.PhysicEngine;

import java.util.ArrayList;

public class RealTimeSimulation extends Simulation<DL4JLearning> {
    private PhysicEngine physicEngine;

    private ArrayList<DL4JNN> carControllerArrayList;

    /**
     * Creates a simulation
     *
     * @param simulationEngine the simulationEngine to refer to
     */
    public RealTimeSimulation(SimulationEngine simulationEngine) {
        super(simulationEngine);
    }

    @Override
    public void start() {
        this.carControllerArrayList = new ArrayList<>();
        DL4JNN e = (DL4JNN) simulationEngine.generateCurrentNetwork();
//        e.init(this.simulationEngine.getSettings());
        this.carControllerArrayList.add(e);

        this.next(false);
    }

    private void next(boolean tested) {
        //TRAINING
        if(tested){
            carControllerArrayList = learningAlgorithm.learn(carControllerArrayList);
        }

        //SIMULATING
    }

    @Override
    public void pause() {

    }
}

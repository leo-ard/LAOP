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
        this.carControllerArrayList.add(e);

        this.next(false);
    }

    private void next(boolean tested) {
        //TRAINING
        if(tested){
            carControllerArrayList = learningAlgorithm.learn(carControllerArrayList);
        }

        //SIMULATING
        this.simulationEngine.getBuffer().clear();
        this.physicEngine = new PhysicEngine(this.simulationEngine.getBuffer(), this.simulationEngine.getMap());

        this.physicEngine.setWaitDeltaT(true);
        this.physicEngine.setFinishingConditions(PhysicEngine.ALL_CARS_DEAD);
        this.physicEngine.getCars().addAll(this.generateCarObjects(1, (i) -> this.carControllerArrayList.get(i)));

        this.physicEngine.setOnPhysicEngineFinishOnce(engine -> {
            this.next(true);
        });
        this.physicEngine.start();
    }

    @Override
    public void pause() {

    }
}

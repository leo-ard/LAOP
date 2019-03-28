package org.lrima.laop.simulation;

import javafx.stage.Stage;
import org.lrima.laop.core.LAOP;
import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.network.LearningAnotation;
import org.lrima.laop.network.carcontrollers.CarController;
import org.lrima.laop.network.carcontrollers.ManualCarController;
import org.lrima.laop.physic.concreteObjects.Car;
import org.lrima.laop.settings.LockedSetting;
import org.lrima.laop.settings.Settings;
import org.lrima.laop.simulation.buffer.SimulationBuffer;
import org.lrima.laop.simulation.map.AbstractMap;
import org.lrima.laop.simulation.map.MazeMap;
import org.lrima.laop.utils.Actions.Action;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class SimulationEngine {
    private SimulationBuffer simulationBuffer;
    private Settings settings;

    private String currentScope;

    private int batchCount;

    ArrayList<Action<SimulationEngine>> onBatchFinished;
    ArrayList<Action<SimulationEngine>> onEnd;

    private boolean autoRun;
    private Stage mainScene;
    private AbstractMap map;

    private Simulation currentSimulation;

    private LearningAlgorithm<? extends CarController> currentLearningAlgorithm;

    public SimulationEngine(SimulationBuffer simulationBuffer, Settings settings) {
        this.simulationBuffer = simulationBuffer;
        this.settings = settings;

        this.onBatchFinished = new ArrayList<>();
        this.onEnd = new ArrayList<>();

        this.currentScope = this.settings.getLocalScopes().get(0);
        this.autoRun = true;
        
        map = new MazeMap(10);
        map.bakeArea();
    }

    public void start(){
        nextBatch();
    }

    private void nextBatch() {
        currentSimulation = generateSimulation();
        currentSimulation.start();
        currentSimulation.setEnd((simulation) -> nextBatch());
        batchCount++;

    }

    private Simulation generateSimulation() {
        Class<? extends LearningAlgorithm> learningAlgo = (Class<? extends LearningAlgorithm>) settings.get(currentScope, LAOP.KEY_LEARNING_CLASS);

        Class<? extends Simulation> simulationClass = learningAlgo.getDeclaredAnnotation(LearningAnotation.class).simulation();
        try {
            Constructor<? extends Simulation> simulationConstructor = simulationClass.getConstructor(SimulationEngine.class);
            return simulationConstructor.newInstance(this);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return new GenerationBasedSimulation(this);
    }


    public SimulationBuffer getBuffer() {
        return simulationBuffer;
    }



    CarController generateCurrentNetwork() {
        Class<? extends CarController> carClass = (Class<? extends CarController>) settings.get(currentScope, LAOP.KEY_NETWORK_CLASS);

        return new ManualCarController(mainScene);
//        try {
//            return carClass.newInstance();
//        } catch (InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        return null;
    }

    LearningAlgorithm<? extends CarController> generateLearningAlgorithm() {
        Class<? extends LearningAlgorithm> learningClass = (Class<? extends LearningAlgorithm>) settings.get(currentScope, LAOP.KEY_LEARNING_CLASS);

        try {
            return learningClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ArrayList<Car> alterCars() {
        return new ArrayList<>();
    }

    public void setOnBatchFinished(Action<SimulationEngine> onBatchFinished) {
        this.onBatchFinished.add(onBatchFinished);
    }

    public void setOnEnd(Action<SimulationEngine> onEnd) {
        this.onEnd.add(onEnd);
    }

    public void setAutoRun(boolean autoRun) {
        this.autoRun = autoRun;
    }

    public int getBatchCount() {
        return batchCount;
    }

    /**
     * ONLY USED TO CONTROL THE CAR WITH THE KEYBORD (need key listeners)
     * @param mainScene the main JAVAFX scene
     */
    public void setMainScene(Stage mainScene) {
        this.mainScene = mainScene;
    }

    public AbstractMap getMap() {
        return map;
    }

    public LockedSetting getSettings() {
        return this.settings.lock(currentScope);
    }
}
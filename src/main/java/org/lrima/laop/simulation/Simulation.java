package org.lrima.laop.simulation;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.stage.Stage;
import org.lrima.laop.core.LAOP;
import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.network.carcontrollers.CarController;
import org.lrima.laop.network.carcontrollers.ManualCarController;
import org.lrima.laop.physic.PhysicEngine;
import org.lrima.laop.physic.concreteObjects.Car;
import org.lrima.laop.physic.concreteObjects.SimpleCar;
import org.lrima.laop.settings.Settings;
import org.lrima.laop.simulation.data.GenerationData;
import org.lrima.laop.simulation.map.AbstractMap;
import org.lrima.laop.simulation.map.MazeMap;
import org.lrima.laop.simulation.sensors.ProximityLineSensor;
import org.lrima.laop.utils.Actions.Action;
import org.lrima.laop.utils.math.Vector2d;

public class Simulation {
    private SimulationBuffer simulationBuffer;
    private Settings settings;

    private String currentScope;
    private int simulationCount;
    private int batchCount;
    private int generationCount;

    ArrayList<Action<Simulation>> onSimulationFinish;
    ArrayList<Action<Simulation>> onGenerationFinish;
    ArrayList<Action<Simulation>> onBatchFinished;
    ArrayList<Action<Simulation>> onEnd;
    
    //temporary
    private final int NUMBER_OF_SENSORS = 5;
    //endtemporary

    private boolean autoRun;
    private Stage mainScene;
    private AbstractMap map;

    private LearningAlgorithm<? extends CarController> currentLearningAlgorithm;
    private PhysicEngine engine;

    public Simulation(SimulationBuffer simulationBuffer, Settings settings) {
        this.simulationBuffer = simulationBuffer;
        this.settings = settings;

        this.onBatchFinished = new ArrayList<>();
        this.onSimulationFinish = new ArrayList<>();
        this.onGenerationFinish = new ArrayList<>();
        this.onEnd = new ArrayList<>();

        this.currentScope = this.settings.getLocalScopes().get(0);
        this.autoRun = true;
        
        map = new MazeMap(10);
        map.bakeArea();

    }

    private ArrayList<SimpleCar> configureCar(){
        //TODO ConfigureCars depending on settings and currentScope

        //Create the cars
        //Temporary
        ArrayList<SimpleCar> cars = new ArrayList<>();
        this.currentLearningAlgorithm = this.generateLearningAlgorithm();

        if(this.simulationBuffer != null) {
	        for(int i = 0 ; i < 1 ; i++) {
	        	Point2D start = map.getStartPoint();
	        	SimpleCar car = new SimpleCar(new Vector2d(start.getX(), start.getY()), generateCurrentNetwork());
	        	
	        	double orientationIncrement = Math.PI / NUMBER_OF_SENSORS;
	        	//Create the sensors and assign them to the car
	        	for(int x = 0 ; x < this.NUMBER_OF_SENSORS ; x++) {
	        		ProximityLineSensor sensor = new ProximityLineSensor(this.map, car, (Math.PI / 2) - (i * orientationIncrement));
	        		car.getSensors().add(sensor);
	        	}

	        	cars.add(car);
	        }
        }

        return cars;
    }


    public SimulationBuffer getBuffer() {
        return simulationBuffer;
    }

    public void nextGen(){
        simulateGeneration();
        incrementGeneration();
    }

    private void incrementGeneration(){
        //FIRE LISTENERS
        this.onGenerationFinish.forEach(simulationAction -> simulationAction.handle(this));
        generationCount ++;

        //CHECK IF GENERATION IS OUT OF BOUNDS
        if(generationCount > (int) settings.get(currentScope, LAOP.KEY_NUMBER_OF_GENERATIONS)){
            //FIRE LISTENERS
            this.onSimulationFinish.forEach(simulationAction -> simulationAction.handle(this));
            generationCount = 0;
            simulationCount++;

            //CHECK IF SIMULATION IS OUT OF BOUND
            if(simulationCount > (int) settings.get(currentScope, LAOP.KEY_NUMBER_OF_SIMULATION)){
                //FIRE LISTENERS
                this.onBatchFinished.forEach(simulationAction -> simulationAction.handle(this));

                simulationCount = 0;
                batchCount++;

                //CHECK IF BATCHES ARE FINISHED
                if(batchCount < settings.getLocalScopes().size()){
                    currentScope = settings.getLocalScopes().get(batchCount);
                    currentLearningAlgorithm = this.generateLearningAlgorithm();

                }
                else{
                    this.onEnd.forEach(simulationAction -> simulationAction.handle(this));
                }
            }
        }
    }

    private CarController generateCurrentNetwork() {
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

    private LearningAlgorithm<? extends CarController> generateLearningAlgorithm() {
        Class<? extends LearningAlgorithm> learningClass = (Class<? extends LearningAlgorithm>) settings.get(currentScope, LAOP.KEY_LEARNING_CLASS);

        try {
            return learningClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void simulateGeneration(){
        simulationBuffer.clear();
        engine = new PhysicEngine(simulationBuffer, map);

        engine.setWaitDeltaT(true);
        engine.getObjects().addAll(configureCar());

        engine.setOnPhysicEngineFinish(engine -> {
            if(this.autoRun)
                this.nextGen();
        });
        engine.start();
    }

    private ArrayList<Car> alterCars() {
        return new ArrayList<>();
    }
    
    /**
     * Get the generation data of the current generation
     * Used to populate the chart panel
     * @author Clement Bisaillon
     * @return the generation data of the current generation
     */
    public GenerationData getGenerationData() {
    	GenerationData data = new GenerationData(this.generationCount);
    	
    	return data;
    }


    public void setOnSimulationFinish(Action<Simulation> onSimulationFinish) {
        this.onSimulationFinish.add(onSimulationFinish);
    }

    public void setOnBatchFinished(Action<Simulation> onBatchFinished) {
        this.onBatchFinished.add(onBatchFinished);
    }

    public void setOnGenerationFinish(Action<Simulation> onGenerationFinish){
        this.onGenerationFinish.add(onGenerationFinish);
    }

    public void setOnEnd(Action<Simulation> onEnd) {
        this.onEnd.add(onEnd);
    }

    public void setAutoRun(boolean autoRun) {
        this.autoRun = autoRun;
    }

    public boolean getAutoRun() {
        return autoRun;
    }

    public void start() {
        this.nextGen();
    }

    public int getGenerationCount() {
        return generationCount;
    }

    public int getSimulationCount() {
        return simulationCount;
    }

    public int getBatchCount() {
        return batchCount;
    }

    public PhysicEngine getEngine() {
        return engine;
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
}

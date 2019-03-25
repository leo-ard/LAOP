package org.lrima.laop.simulation;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.stage.Stage;
import org.lrima.laop.core.LAOP;
import org.lrima.laop.utils.math.Vector2d;
import org.lrima.laop.network.ManualCarContoller;
import org.lrima.laop.physic.PhysicEngine;
import org.lrima.laop.settings.Settings;
import org.lrima.laop.simulation.data.GenerationData;
import org.lrima.laop.physic.concreteObjects.Car;
import org.lrima.laop.physic.concreteObjects.SimpleCar;
import org.lrima.laop.utils.Actions.Action;

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


    private boolean autoRun;
    private Stage mainScene;

    public Simulation(SimulationBuffer simulationBuffer, HashMap<String, Class<?>> algorithms, Settings settings) {
        this.simulationBuffer = simulationBuffer;
        this.settings = settings;

        this.onBatchFinished = new ArrayList<>();
        this.onSimulationFinish = new ArrayList<>();
        this.onGenerationFinish = new ArrayList<>();
        this.onEnd = new ArrayList<>();

        this.currentScope = this.settings.getLocalScopes().get(0);
        this.autoRun = true;
    }

    private ArrayList configureCar(){
        //TODO ConfigureCars depending on settings and currentScope

        //Create the cars
        //Temporary
        ArrayList<SimpleCar> cars = new ArrayList<>();

        if(this.simulationBuffer != null) {
	        for(int i = 0 ; i < 1 ; i++) {
	        	SimpleCar car = new SimpleCar(Vector2d.origin, new ManualCarContoller(mainScene));

	        	//car.addThrust(Math.random() * 100000);

	        	//car.setRotation(Math.random());
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
                }
                else{
                    this.onEnd.forEach(simulationAction -> simulationAction.handle(this));
                }
            }
        }
    }

    private void simulateGeneration(){
        simulationBuffer.clear();
        PhysicEngine physicEngine = new PhysicEngine(simulationBuffer);

        physicEngine.getObjects().addAll(configureCar());

        physicEngine.setOnPhysicEngineFinish(engine -> {
            if(this.autoRun)
                this.nextGen();
        });
        physicEngine.start();
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

    /**
     * ONLY USED TO CONTROL THE CAR WITH THE KEYBORD (need key listeners)
     * @param mainScene the main JAVAFX scene
     */
    public void setMainScene(Stage mainScene) {
        this.mainScene = mainScene;
    }
}

package org.lrima.laop.simulation;

import org.lrima.laop.core.LAOP;
import org.lrima.laop.physic.PhysicEngine;
import org.lrima.laop.settings.Settings;
import org.lrima.laop.simulation.objects.Car;
import org.lrima.laop.utils.Action;

import java.util.ArrayList;
import java.util.HashMap;

public class Simulation implements Runnable{
    private SimulationBuffer simulationBuffer;
    private Settings settings;

    private String currentScope;
    private int simulationCount;
    private int batchCount;
    private int generationCount;

    private Thread simulationThread;

    ArrayList<Action<Simulation>> onSimulationFinish;
    ArrayList<Action<Simulation>> onGenerationFinish;
    ArrayList<Action<Simulation>> onBatchFinished;

    private boolean autoRun;

    public Simulation(SimulationBuffer simulationBuffer, HashMap<String, Class<?>> algorithms, Settings settings) {
        this.simulationBuffer = simulationBuffer;
        this.settings = settings;

        this.onBatchFinished = new ArrayList<>();
        this.onSimulationFinish = new ArrayList<>();
        this.onGenerationFinish = new ArrayList<>();

        this.currentScope = this.settings.getLocalScopes().get(0);
        this.autoRun = true;
    }

    private ArrayList configureCar(){
        //TODO ConfigureCars depending on settings and currentScope

        //Create the cars
        //Temporary
        ArrayList<Car> cars = new ArrayList<>();

        if(this.simulationBuffer != null) {
	        for(int i = 0 ; i < 10 ; i++) {
	        	Car car = new Car();

	        	car.addThrust(Math.random() * 100000);

	        	car.setRotation(Math.random());
	        	cars.add(car);
	        }
        }

        return cars;
    }


    public void start() {
        simulationThread = new Thread(this);
        simulationThread.start();
    }

    public SimulationBuffer getBuffer() {
        return simulationBuffer;
    }

    @Override
    public void run() {
        this.step();
    }

    private void step(){
        incrementGeneration();
        simulateGeneration();
    }

    private void incrementGeneration(){
        generationCount ++;
        if(generationCount > (int) settings.get(currentScope, LAOP.KEY_NUMBER_OF_GENERATIONS)){
            generationCount = 0;
            simulationCount++;
            this.onSimulationFinish.forEach(simulationAction -> simulationAction.handle(this));
        }

        if(simulationCount > (int) settings.get(currentScope, LAOP.KEY_NUMBER_OF_SIMULATION)){
            simulationCount = 0;
            batchCount++;
            currentScope = settings.getLocalScopes().get(batchCount);
            this.onBatchFinished.forEach(simulationAction -> simulationAction.handle(this));
        }
    }

    private void simulateGeneration(){
        simulationBuffer.clear();
        PhysicEngine physicEngine = new PhysicEngine(simulationBuffer);

        //First generation
        if(generationCount == 0)
            physicEngine.getObjects().addAll(configureCar());
        else
            physicEngine.getObjects().addAll(configureCar());

        physicEngine.setOnPhysicEngineFinish(engine -> {
            if(this.autoRun)
                this.step();
        });
        physicEngine.start();
    }

    private ArrayList<Car> alterCars() {
        return new ArrayList<>();

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

    public void setAutoRun(boolean autoRun) {
        this.autoRun = autoRun;
    }

    public boolean getAutoRun() {
        return autoRun;
    }
}

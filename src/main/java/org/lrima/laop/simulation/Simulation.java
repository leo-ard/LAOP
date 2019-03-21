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
    private Thread simulationThread;

    ArrayList<Action<Simulation>> onSimulationFinish;
    ArrayList<Action<Simulation>> onGenerationFinish;
    ArrayList<Action<Simulation>> onBatchFinished;

    public Simulation(SimulationBuffer simulationBuffer, HashMap<String, Class<?>> algorithms, Settings settings) {
        this.simulationBuffer = simulationBuffer;
        this.settings = settings;

        this.onBatchFinished = new ArrayList<>();
        this.onSimulationFinish = new ArrayList<>();
        this.onGenerationFinish = new ArrayList<>();

        initialise();
    }

    private void initialise() {


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
        for(String scope : settings.getScopes()){
            currentScope = scope;
            for(int i = 0; i < (Integer) settings.get(scope, LAOP.KEY_NUMBER_OF_SIMULATION); i++){
                System.out.println(i);
                ArrayList<Car> carsArrayList = configureCar();

                simulationBuffer.clear();
                PhysicEngine physicEngine = new PhysicEngine(simulationBuffer);
                physicEngine.getObjects().addAll(carsArrayList);

                //TODO : faire les generations
                physicEngine.run();

                this.onSimulationFinish.forEach(simulationAction -> simulationAction.handle(this));
            }


            this.onBatchFinished.forEach(simulationAction -> simulationAction.handle(this));
        }
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
}

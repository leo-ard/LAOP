package org.lrima.laop.physic;

import java.awt.geom.Area;
import java.util.ArrayList;

import org.lrima.laop.network.carcontrollers.CarController;
import org.lrima.laop.network.genetics.GeneticNeuralNetwork;
import org.lrima.laop.network.nn.NeuralNetwork;
import org.lrima.laop.physic.abstractObjects.AbstractCar;
import org.lrima.laop.physic.concreteObjects.SimpleCar;
import org.lrima.laop.physic.staticobjects.StaticLineObject;
import org.lrima.laop.simulation.buffer.SimulationBuffer;
import org.lrima.laop.simulation.buffer.SimulationSnapshot;
import org.lrima.laop.simulation.data.CarData;
import org.lrima.laop.simulation.map.AbstractMap;
import org.lrima.laop.simulation.map.LineCollidable;
import org.lrima.laop.utils.Actions.Action;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author Clement Bisaillon and Léonard Oest OLeary
 */
public class PhysicEngine extends Thread {
	
    public static Function<ArrayList<AbstractCar>, Boolean> ALL_CARS_DEAD = (list) -> {
        for (AbstractCar abstractCar : list) {
            if(!abstractCar.isDead()){
                return false;
            }
        }
        return true;
    };

    static public final double DELTA_T = 0.05;
    public static final double GRAVITY = 9.8;

    private volatile boolean pause = false;

    private ArrayList<AbstractCar> cars;
    private boolean running = true;
    private double worldWidth;

    private ArrayList<Action<PhysicEngine>> onPhysicEngineFinishOnce;
    private ArrayList<Action<PhysicEngine>> onStep;
    private AbstractMap map;

    //////Temporary
    private final int MAX_ITERATION = 30000;
    private int CURRENT_ITERATION = 0;
    //////Temporary

    private SimulationBuffer simulationBuffer;
    private boolean realTime;
    private Function<ArrayList<AbstractCar>, Boolean> finishingCondition;


    /**
     * Creates a physic engine.
     *
     * @param buffer the buffer to export the results of the simulation to
     * @param map the map that the simulation run (collisions)
     */
    public PhysicEngine(SimulationBuffer buffer, AbstractMap map){
    	this.simulationBuffer = buffer;
        this.cars = new ArrayList<>();
        this.onPhysicEngineFinishOnce = new ArrayList<>();
        this.map = map;
        
        this.realTime = false;
    }


    /**
     * Run the physic engine
     */
    @Override
    public void run() {
        //TODO : Condition : quand les voitures sont encore toutes vivante et qu'il reste du temps
        while(running && (this.CURRENT_ITERATION < this.MAX_ITERATION)){
            if(!this.pause) {
                try {
                    this.nextStep();
                    this.checkCollision();

                    //save the car's state in the buffer
                    this.saveCarsState();

                    if(finishingCondition != null && finishingCondition.apply(this.cars))
                        running = false;

                    this.CURRENT_ITERATION++;
                    if(this.realTime){
                        Thread.sleep((int)(PhysicEngine.DELTA_T*1000.0));
                    }
                    else
                        Thread.sleep(1);
                } catch (InterruptedException e) {
                    System.err.println("Thread Stopped");
                }
            }
        }

        this.onPhysicEngineFinishOnce.forEach(action -> action.handle(this));
        this.onPhysicEngineFinishOnce = new ArrayList<>();
    }
    
    /**
     * Save the state of each cars in a simulation snapshot then
     * adds it to the simulation buffer
     */
    private void saveCarsState() {
    	if(this.simulationBuffer != null) {
	    	SimulationSnapshot snapshot = new SimulationSnapshot();

	    	for(AbstractCar car : this.cars) {
	    		snapshot.addCar(new CarData(car));
	    	}
	    	
	    	this.simulationBuffer.addSnapshot(snapshot);
    	}
    }

    /**
     * Move the concreteObjects in the simulation
     */
    private void nextStep(){
        for (AbstractCar car : cars) {
            car.nextStep();
        }
    }

    /**
     * Check if there are collisions.
     *
     * @author Léonard
     */
    private void checkCollision(){
        for(AbstractCar car : this.cars){
            this.map.collide(car);
        }

    }

    /**
     * Add a car to the java.physic engine
     * @param car the car to add
     */
    public void addCar(SimpleCar car){
        //Add the gravity force to the object
        this.cars.add(car);
    }

    public double getWorldWidth() {
        return worldWidth;
    }

    /**
     * Set the simulation to pause or play
     * @param pause true to set the simulation to pause, false otherwise
     */
    public void setPause(boolean pause){
        this.pause = pause;
    }

    public void togglePause(){
        this.pause = !pause;
    }

    /**
     * Sets an action to do after the end of the simulation. Then this action is destroyed and never used again.
     *
     * @param onPhysicEngineFinish
     */
    public void setOnPhysicEngineFinishOnce(Action<PhysicEngine> onPhysicEngineFinish) {
        this.onPhysicEngineFinishOnce.add(onPhysicEngineFinish);
    }

    public void setFinishingConditions(Function<ArrayList<AbstractCar>, Boolean> finishingCondition){
        this.finishingCondition = finishingCondition;
    }

    public void setOnStep(Action<PhysicEngine> onStep){
        this.onStep.add(onStep);
    }
    
    

    /**
     * @return The list of cars in the physic engine
     */
    public ArrayList<AbstractCar> getCars(){
    	return this.cars;
    }

    public ArrayList extractNetworks() {
        return (ArrayList) this.cars.stream().map(AbstractCar::getController).collect(Collectors.toList());
    }

    public boolean getPause() {
        return pause;
    }
    

    public void setRealTime(boolean value) {
        this.realTime = value;
    }
}

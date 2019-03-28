package org.lrima.laop.physic;

import java.awt.geom.Area;
import java.util.ArrayList;

import org.lrima.laop.physic.concreteObjects.SimpleCar;
import org.lrima.laop.physic.objects.Box;
import org.lrima.laop.physic.staticobjects.StaticObject;
import org.lrima.laop.simulation.buffer.SimulationBuffer;
import org.lrima.laop.simulation.buffer.SimulationSnapshot;
import org.lrima.laop.simulation.data.CarData;
import org.lrima.laop.simulation.map.AbstractMap;
import org.lrima.laop.utils.Actions.Action;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.function.Function;

/**
 *
 * @author Clement Bisaillon
 */
public class PhysicEngine extends Thread {
    static public final double DELTA_T = 0.05;
    static final double GRAVITY = 9.8;

    private volatile boolean pause = false;

    private ArrayList<Physicable> objects;
    private ArrayList<SimpleCar> cars;
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
    private boolean waitDeltaT;
    private Function<ArrayList<Physicable>, Boolean> finishingCondition;

    public PhysicEngine(SimulationBuffer buffer, AbstractMap map){
    	this.simulationBuffer = buffer;
        this.objects = new ArrayList<>();
        this.cars = new ArrayList<>();
        this.onPhysicEngineFinishOnce = new ArrayList<>();
        this.map = map;
        this.waitDeltaT = false;
    }

    public ArrayList<Physicable> getObjects() {
        return objects;
    }

    /**
     * Optionnal
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

                    if(finishingCondition != null && finishingCondition.apply(this.objects))
                        running = false;

                    this.CURRENT_ITERATION++;
                    if(waitDeltaT){
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

	    	for(SimpleCar car : this.cars) {
	    		snapshot.addCar(new CarData(car));
	    	}
	    	
	    	this.simulationBuffer.addSnapshot(snapshot);
    	}
    }

    /**
     * Move the concreteObjects in the simulation
     */
    private void nextStep(){
    	ArrayList<Physicable> allObjects = new ArrayList(this.objects);
    	allObjects.addAll(this.cars);

        for (Physicable object : allObjects) {
            object.nextStep();
        }
    }

    /**
     * Check if there are collisions.
     */
    private void checkCollision(){
        //TODO: Pas la meilleur facon de faire

//        for(SimpleCar physicable :this.cars){
//        	for(StaticObject obstacle : this.map.getObjects()) {
//        		Area intersection = obstacle.getArea();
//        		intersection.intersect(physicable.getArea());
//        		if(!intersection.isEmpty()){
//                    obstacle.collideWith(physicable);
//                    physicable.collideWith(obstacle);
//                }
//        	}
//        }
    }

    /**
     * Add an object to the java.physic engine
     * @param object the object to add
     */
    public void addObject(Physicable object){
        //Add the gravity force to the object
        this.objects.add(object);
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

    public void setFinishingConditions(Function<ArrayList<Physicable>, Boolean> finishingCondition){
        this.finishingCondition = finishingCondition;
    }

    public void setOnStep(Action<PhysicEngine> onStep){
        this.onStep.add(onStep);
    }

    public void setWaitDeltaT(boolean waitDeltaT) {
        this.waitDeltaT = waitDeltaT;
    }

    /**
     * @return The list of cars in the physic engine
     */
    public ArrayList<SimpleCar> getCars(){
    	return this.cars;
    }
}

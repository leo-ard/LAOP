package org.lrima.laop.physic;

import org.lrima.laop.physic.objects.Box;
import org.lrima.laop.physic.staticobjects.StaticObject;
import org.lrima.laop.simulation.SimulationBuffer;
import org.lrima.laop.simulation.SimulationSnapshot;
import org.lrima.laop.simulation.data.CarInfo;
import org.lrima.laop.simulation.map.SimulationMap;
import org.lrima.laop.utils.Actions.Action;

import java.awt.geom.Area;
import java.util.ArrayList;

/**
 *
 * @author Clement Bisaillon
 */
public class PhysicEngine extends Thread {
    static public final double DELTA_T = 0.05;
    static final double GRAVITY = 9.8;

    private volatile boolean pause = false;

    private ArrayList<Physicable> objects;
    private boolean running = true;
    private double worldWidth;

    private ArrayList<Action<PhysicEngine>> onPhysicEngineFinish;
    private ArrayList<Action<PhysicEngine>> onStep;
    private SimulationMap map;

    //////Temporary
    private final int MAX_ITERATION = 30000;
    private int CURRENT_ITERATION = 0;
    //////Temporary

    private SimulationBuffer simulationBuffer;
    private boolean waitDeltaT;

    public PhysicEngine(SimulationBuffer buffer, SimulationMap map){
    	this.simulationBuffer = buffer;
        this.objects = new ArrayList<>();
        this.onPhysicEngineFinish = new ArrayList<>();
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

        this.onPhysicEngineFinish.forEach(action -> action.handle(this));
    }
    
    /**
     * Save the state of each cars in a simulation snapshot then
     * adds it to the simulation buffer
     */
    private void saveCarsState() {
    	if(this.simulationBuffer != null) {
	    	SimulationSnapshot snapshot = new SimulationSnapshot();
	    	
	    	for(Physicable object : this.objects) {
	    		if(object instanceof Box) {
	    			snapshot.addCar(new CarInfo((Box)object));
	    		}
	    	}
	    	
	    	this.simulationBuffer.addSnapshot(snapshot);
    	}
    }

    /**
     * Move the concreteObjects in the simulation
     */
    private void nextStep(){
        for (Physicable object : objects) {
            object.nextStep();
        }
    }

    /**
     * Check if there are collisions.
     */
    private void checkCollision(){
        //TODO: Pas la meilleur facon de faire

        for(Physicable physicable :objects){
        	for(StaticObject obstacle : this.map.getObjects()) {
        		Area intersection = obstacle.getArea();
        		intersection.intersect(physicable.getArea());
        		if(!intersection.isEmpty()){
                    obstacle.collideWith(physicable);
                    physicable.collideWith(obstacle);
                }
        	}
        }
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

    public void setOnPhysicEngineFinish(Action<PhysicEngine> onPhysicEngineFinish) {
        this.onPhysicEngineFinish.add(onPhysicEngineFinish);
    }

    public void setOnStep(Action<PhysicEngine> onStep){
        this.onStep.add(onStep);
    }

    public void setWaitDeltaT(boolean waitDeltaT) {
        this.waitDeltaT = waitDeltaT;
    }
}

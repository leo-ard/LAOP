package org.lrima.laop.physic;

import java.awt.geom.Area;
import java.util.ArrayList;

import org.lrima.laop.math.Vector3d;
import org.lrima.laop.simulation.SimulationBuffer;
import org.lrima.laop.simulation.SimulationSnapshot;
import org.lrima.laop.simulation.listeners.SimulationListener;
import org.lrima.laop.simulation.objects.Car;

/**
 *
 * @author Clement Bisaillon
 */
public class PhysicEngine extends Thread {
    static public final double DELTA_T = 0.1;
    static final double GRAVITY = 9.8;

    private volatile boolean pause = false;

    private ArrayList<Physicable> objects;
    private boolean running = true;
    private double worldWidth;
    
    //////Temporary
    private final int MAX_ITERATION = 20000;
    private int CURRENT_ITERATION = 0;
    //////Temporary
    
    private SimulationBuffer simulationBuffer;
    private ArrayList<SimulationListener> simulationListeners;

    public PhysicEngine(SimulationBuffer buffer){
    	this.simulationBuffer = buffer;
        this.objects = new ArrayList<>();
        this.simulationListeners = new ArrayList<>();
        
        //Create the cars
        //Temporary
        if(this.simulationBuffer != null) {
	        for(int i = 0 ; i < 10 ; i++) {
	        	Car car = new Car();
	        	
	        	car.addThrust(Math.random() * 100000);
	        	
	        	car.setRotation(Math.random());
	        	this.addObject(car);
	        }
        }
    }

    public ArrayList<Physicable> getObjects() {
        return objects;
    }

    @Override
    public void run() {
        super.run();
        while(running && (this.CURRENT_ITERATION < this.MAX_ITERATION)){
            if(!this.pause) {
                try {
                	//Save the state of the cars in the buffer
                	this.saveCarsState();
                	
                    this.nextStep();
                    this.checkCollision();
                    
                    this.CURRENT_ITERATION++;
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    System.err.println("Thread Stopped");
                }
            }
        }
        
        this.fireSimulationEnd();
    }
    
    /**
     * Notify the simulation listeners that the simulation ended
     */
    private void fireSimulationEnd() {
    	for(SimulationListener listener : this.simulationListeners) {
    		listener.generationEnd(null);
    	}
    }
    
    /**
     * Save the state of each cars in a simulation snapshot then
     * adds it to the simulation buffer
     */
    private void saveCarsState() {
    	if(this.simulationBuffer != null) {
	    	SimulationSnapshot snapshot = new SimulationSnapshot();
	    	
	    	for(Physicable object : this.objects) {
	    		if(object instanceof Car) {
	    			Car car = (Car) object;
	    			snapshot.addCar(car.getSnapShotInfo());
	    		}
	    	}
	    	
	    	this.simulationBuffer.addSnapshot(snapshot);
    	}
    }

    /**
     * Move the objects in the simulation
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
        for(Physicable object : objects) {
            for(Physicable object2 : objects){
                //Don't intersect with itself
                if(object != object2){
                    Area area1 = object.getArea();
                    Area area2 = object2.getArea();

                    area1.intersect(area2);
                    if(!area1.isEmpty()){
                        //There has been a collision
                        object.collideWith(object2);
                        object2.collideWith(object);
                    }
                }
            }
        }
    }

    /**
     * Add an object to the java.physic engine
     * @param object the object to add
     */
    void addObject(Physicable object){
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
     * Add a simulation listener
     * @param listener the listener
     */
    public void addSimulationListener(SimulationListener listener) {
    	this.simulationListeners.add(listener);
    }
}

package org.lrima.laop.physic;

import java.awt.geom.Area;
import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;

import org.lrima.laop.math.Vector3d;
import org.lrima.laop.physic.objects.Box;
import org.lrima.laop.simulation.CarInfo;
import org.lrima.laop.simulation.SimulationBuffer;
import org.lrima.laop.simulation.SimulationSnapshot;
import org.lrima.laop.simulation.listeners.SimulationListener;
import org.lrima.laop.simulation.objects.Car;
import org.lrima.laop.simulation.objects.SimpleCar;
import org.lrima.laop.utils.Action;
import org.lrima.laop.utils.BlankAction;
import sun.java2d.pipe.SpanShapeRenderer;

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

    private ArrayList<Action<PhysicEngine>> onPhysicEngineFinish;
    
    //////Temporary
    private final int MAX_ITERATION = 30000;
    private int CURRENT_ITERATION = 0;
    //////Temporary
    
    private SimulationBuffer simulationBuffer;

    public PhysicEngine(SimulationBuffer buffer){
    	this.simulationBuffer = buffer;
        this.objects = new ArrayList<>();
        onPhysicEngineFinish = new ArrayList<>();
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
//                    this.checkCollision();

                    //save the car's state in the buffer
                    this.saveCarsState();
                    
                    this.CURRENT_ITERATION++;
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

    public void setOnPhysicEngineFinish(Action<PhysicEngine> onPhysicEngineFinish) {
        this.onPhysicEngineFinish.add(onPhysicEngineFinish);
    }
}

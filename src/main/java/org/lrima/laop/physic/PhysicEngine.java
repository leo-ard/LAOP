package org.lrima.laop.physic;

import java.awt.geom.Area;
import java.util.ArrayList;

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

    PhysicEngine(double worldWidth){
        this.objects = new ArrayList<>();
        this.worldWidth = worldWidth;
    }

    @Override
    public void run() {
        super.run();
        while(running){
            if(!this.pause) {
                try {
                    this.nextStep();
                    this.checkCollision();

                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    System.err.println("Thread Stopped");
                }
            }
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
}

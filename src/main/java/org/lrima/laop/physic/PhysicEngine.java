package org.lrima.laop.physic;

import org.lrima.laop.math.Vector2d;
import org.lrima.laop.math.Vector3d;

import java.awt.geom.Area;
import java.util.ArrayList;

/**
 *
 * @author Clement Bisaillon
 */
public class PhysicEngine extends Thread {
    static final double deltaT = 0.01;

    private ArrayList<Physicable> objects;
    private Vector3d gravity;
    private boolean running = true;
    private int i = 0;

    PhysicEngine(){
        this.objects = new ArrayList<>();
        this.gravity = new Vector3d(0, 0, -9.8);
    }

    @Override
    public void run() {
        super.run();
        while(running){
            try {
                this.nextStep();
                this.checkCollision();

                Thread.sleep(1);
            }catch (InterruptedException e){
                System.err.println("Thread Stopped");
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

    void addObject(Physicable object){
        this.objects.add(object);
    }
}

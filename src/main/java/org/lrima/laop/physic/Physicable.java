package org.lrima.laop.physic;

import org.lrima.laop.math.Vector2d;
import org.lrima.laop.math.Vector3d;

import java.awt.geom.Area;
import java.util.ArrayList;

/**
 * Class that can be simulated physicaly
 * @author Clement Bisaillon
 */
public abstract class Physicable {
    protected Vector3d position;
    private Vector3d velocity;
    private double mass;
    protected ArrayList<Vector3d> forces;

    //Used to disable collision detection right after the collision happened
    protected long stopCheckingCollisionAt;
    private final int MINIMUM_TIME_BEFORE_COLLISION_AGAIN = 50;

    private Physicable(){
        this.position = Vector3d.origin;
        this.velocity = Vector3d.origin;
        this.mass = 0;
        this.forces = new ArrayList<>();
    }

    public Physicable(Vector3d position, double mass){
        this();
        this.position = position;
        this.mass = mass;
    }

    public Physicable(double mass){
        this(Vector3d.origin, mass);
    }

    /**
     * Add a new force to the object
     * @param force - The force to add
     */
    public void addForce(Vector3d force){
        this.forces.add(force);
    }

    /**
     * Set the position of the physicable object
     * @param position - the new position
     */
    public void setPosition(Vector3d position){
        this.position = position.clone();
    }

    /**
     * Get the actual position of the physicable object
     * @return the position of the object
     */
    public Vector3d getPosition(){
        return this.position;
    }

    /**
     * Get all the forces applied to the object
     * @return the forces applied to the object
     */
    public ArrayList<Vector3d> getForces(){
        return this.forces;
    }

    /**
     * Get the resulting force from all the forces on the object
     * @return the resulting force
     */
    public Vector3d getSumForces(){
        Vector3d sum = Vector3d.origin;

        for(Vector3d force : this.forces){
            sum = sum.add(force);
        }

        return sum;
    }


    /**
     * Calculates and set the position of the object depending on
     * the sum of the forces applied to this object
     */
    void nextStep(){
        Vector3d sumOfForces = getSumForces();

        Vector3d acceleration = sumOfForces.multiply(1.0/this.mass);
        Vector3d newVelocity = this.velocity.add(acceleration.multiply(PhysicEngine.deltaT));

        this.position = this.position.add(newVelocity.multiply(PhysicEngine.deltaT));
    }

    /**
     * Check if the object can collide. It is necessary to stop reacting to collisions for a small
     * amount of time after a collision occurred or else it would collide indefinitely.
     * @return true if the object can react to collisions, false otherwise
     */
    protected boolean canCollide(){
        return (System.currentTimeMillis() - this.stopCheckingCollisionAt > this.MINIMUM_TIME_BEFORE_COLLISION_AGAIN);
    }

    /**
     * Removes all the forces applied to this object
     */
    protected void resetForces(){
        this.forces = new ArrayList<>();
    }

    /**
     * Get the area of the object to check for collisions
     * @return the area of the object
     */
    public abstract Area getArea();

    /**
     * Defines what happens when a collision occurs
     * @param object the object colliding with this object
     */
    public abstract void collideWith(Physicable object);
}

package org.lrima.laop.physic.abstractObjects;

import org.lrima.laop.physic.CarControls;
import org.lrima.laop.simulation.map.AbstractMap;
import org.lrima.laop.simulation.map.LineCollidable;
import org.lrima.laop.simulation.sensors.Sensor;
import org.lrima.laop.utils.math.Vector2d;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * Class that can be simulated physicaly
 * @author Clement Bisaillon
 */
public abstract class AbstractCar implements LineCollidable {
    protected Vector2d position;
    protected Vector2d velocity;
    protected Vector2d acceleration;
    protected double rotation;
    protected double angularVelocity;
    protected double angularAccel;
    protected double mass;
    protected double width;
    protected double height;
    protected ArrayList<Vector2d> forces;
    protected AbstractMap map;

    //Used to disable collision detection right after the collision happened
    protected long stopCheckingCollisionAt;
    private final int MINIMUM_TIME_BEFORE_COLLISION_AGAIN = 100;

    protected boolean dead;
    protected Function<AbstractCar, Double> fitnessFunction;
    protected double fitness;

    /**
     * Create a new java.physic object with the default variables
     */
    private AbstractCar(AbstractMap map){
    	this.map = map;
        this.position = Vector2d.origin;
        this.velocity = Vector2d.origin;
        this.acceleration = Vector2d.origin;
        this.mass = 0;
        this.rotation = 0;
        this.forces = new ArrayList<>();
    }

    /**
     * Create a new java.physic object from a position and a mass
     * @param position the position of the object
     * @param mass the mass of the object
     */
    public AbstractCar(AbstractMap map, Vector2d position, double mass){
        this(map);
        this.position = position;
        this.mass = mass;
    }

    public AbstractCar(AbstractMap map, double mass){
        this(map, Vector2d.origin, mass);
    }

    /**
     * Add a new velocity to the object
     * @param velocity - the velocity to add
     */
    public void addVelocity(Vector2d velocity) {this.velocity = this.velocity.add(velocity);}

    /**
     * Set the position of the physicable object
     * @param position - the new position
     */
    public void setPosition(Vector2d position){
        this.position = position.clone();
    }

    /**
     * Get the actual position of the physicable object
     * @return the position of the object
     */
    public Vector2d getPosition(){
        return this.position;
    }

    /**
     * Get all the forces applied to the object
     * @return the forces applied to the object
     */
    public ArrayList<Vector2d> getForces(){
        return this.forces;
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
     * Resets the velocity of the object
     */
    protected void resetVelocity() {
        this.velocity = Vector2d.origin;
    }

    /**
     * @return The velocity of the object
     */
    public Vector2d getVelocity() {
        return velocity;
    }

    /**
     * Applies a velocity to the object
     * @param velocity the velocity of the object
     */
    public void setVelocity(Vector2d velocity) {
        this.velocity = velocity;
    }

    /**
     * Set the rotation of the object and change the rotation of all the forces
     * @param rotation the rotation to apply on the object
     */
    public void rotate(double rotation) {
        this.rotation += rotation;
    }

    /**
     * @return the rotation of the object
     */
    public double getRotation(){
        return this.rotation;
    }

    /**
     * Get the direction of the physicable object
     * @return the direction
     */
    public Vector2d getDirection(){
        Vector2d v = new Vector2d(0, 1);

        return v.rotate(this.rotation, Vector2d.origin);
    }

    public double getAngularVelocity() {
        return angularVelocity;
    }
    
    /**
     * 
     * @return The mass of the object
     */
    protected double getMass() {
    	return this.mass;
    }
    
    /**
     * Translate the position of the object
     * @param position the translation vector
     */
    protected void addPosition(Vector2d position) {
    	this.position = this.position.add(position);
    }
    
    
    /**
     * @return The acceleration of the car
     */
    public Vector2d getAcceleration() {
    	return this.acceleration;
    }
    
    /**
     * @return The width of the car
     */
    public double getWidth() {
    	return this.width;
    }
    
    /**
     * @return The height of the car
     */
    public double getHeight() {
    	return this.height;
    }
    
    //ABSTRACT METHODS
    
    /**
     * @return the list of sensors attached to this car
     */
    public abstract void addSensor(Sensor sensor);

    
    /**
     * Calculates and set the position of the object and all its children depending on
     * the sum of the forces applied to them
     * @param carControls
     */
    public abstract void nextStep(CarControls carControls);

    /**
     * @return the center of the object
     */
    public abstract Vector2d getCenter();

    public boolean isDead(){
        return dead;
    }

    public void kill(){
        dead = true;
    }

    /**
     * Returns the list of all the sensors that can collide with the map
     *
     * @return arrayList of sensors
     */
    public abstract ArrayList<LineCollidable> getCollidableSensors();

    public double getFitness(){
        return this.fitness;
    }

    public void setFitnessFunction(Function<AbstractCar, Double> function){
        this.fitnessFunction = function;
    }
    
    public AbstractMap getMap() {
    	return this.map;
    }
}

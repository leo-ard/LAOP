package org.lrima.laop.physic.concreteObjects;

import org.lrima.laop.physic.staticobjects.StaticObject;
import org.lrima.laop.physic.staticobjects.StaticObjectType;
import org.lrima.laop.utils.MathUtils;
import org.lrima.laop.utils.math.Vector2d;
import org.lrima.laop.physic.PhysicEngine;
import org.lrima.laop.physic.objects.Box;
import org.lrima.laop.simulation.data.CarInfo;
import org.lrima.laop.simulation.sensors.Sensor;

import java.awt.geom.Area;
import java.util.ArrayList;

/**
 * Physic object representing a car
 * @author Clement Bisaillon
 */
public class Car extends Box {
    private Wheel leftFrontWheel;
    private Wheel rightFrontWheel;
    private Wheel leftBackWheel;
    private Wheel rightBackWheel;
    
    private static double  MAX_VELOCITY = 300;
    private ArrayList<Sensor> sensors;

    /**
     * Create a new car with mass 2000
     * This method creates the four wheels and attach them to the car.
     */
    public Car(){
        super(2000, 20, 40);
        this.angularVelocity = 0;

        this.leftBackWheel = new Wheel(this, Wheel.WheelLocation.BACK_LEFT);
        this.rightFrontWheel = new Wheel(this, Wheel.WheelLocation.FRONT_RIGHT);
        this.leftFrontWheel = new Wheel(this, Wheel.WheelLocation.FRONT_LEFT);
        this.rightBackWheel = new Wheel(this, Wheel.WheelLocation.BACK_RIGHT);

        this.leftBackWheel.setCanRotate(false);
        this.rightBackWheel.setCanRotate(false);
    }
    
    public Vector2d getSumForces() {
    	Vector2d sum = Vector2d.origin;

        for(Vector2d force : this.forces){
            sum = sum.add(force);
        }

        //Get the force of the children
        for(Wheel child : this.getAllWeels()){
            sum = sum.add(child.getSumForces());
        }
        
        //Get the rotation friction force
//        sum = sum.add(this.getAngularResistance());

        return sum;
    }

    private Wheel[] getAllWeels() {
        return new Wheel[]{leftBackWheel, leftFrontWheel, rightBackWheel, rightFrontWheel};
    }

    @Override
    protected void nextStep() {
    	Vector2d sumOfForces = this.getSumForces();

        this.acceleration = sumOfForces.multiply(1.0 / this.getMass());


        //Or else the car never stops
        if(MathUtils.nearZero(getAcceleration().modulus(), 0.000001)){
        	this.acceleration = Vector2d.origin;
            this.resetVelocity();
        }

        this.addVelocity(getAcceleration().multiply(PhysicEngine.DELTA_T));
        
        if(this.getVelocity().modulus() > this.MAX_VELOCITY) {
        	this.setVelocity(this.getVelocity().normalize().multiply(this.MAX_VELOCITY));
        }

  
        this.addPosition(this.getVelocity().multiply(PhysicEngine.DELTA_T));

        double totalRotation = this.getRotation() - this.getFromWheelsRotation();

        totalRotation *= 0.0001 * this.getVelocity().modulus();

        if(!MathUtils.nearZero(totalRotation, 0.000001)) {
            double angularAcceleration = totalRotation;
            this.angularVelocity = this.angularVelocity + angularAcceleration * PhysicEngine.DELTA_T;
            this.rotation += (-this.angularVelocity * PhysicEngine.DELTA_T);
        }
        else{
        	if(this.angularVelocity != 0) {
        		this.velocity = this.getDirection().normalize().multiply(this.getVelocity().modulus());
        	}
            this.angularVelocity = 0;
            
        }
    }
    
    /**
     * @return The angular resistance force
     */
    private Vector2d getAngularResistance() {
    	Vector2d direction = this.getDirection().multiply(-1);
    	Vector2d tangent = new Vector2d(direction.getY(), -direction.getX());
    	Vector2d resistance = tangent.multiply(this.angularVelocity * this.getWeight() * 0.2);
    	
    	return resistance;
    }

    @Override
    public Area getArea() {
        Area carArea = super.getArea();

        carArea.add(leftBackWheel.getArea());
        carArea.add(rightBackWheel.getArea());
        carArea.add(leftFrontWheel.getArea());
        carArea.add(rightFrontWheel.getArea());

        return carArea;
    }

    @Override
    public void collideWith(StaticObject object) {

    }

    /**
     * @return The center position of the car in pixels
     */
    public Vector2d getCenter(){
        return new Vector2d(this.getPosition().getX() + this.getWidth() / 2, this.getPosition().getY() + this.getHeight() / 2);
    }

    /**
     * Rotate the front wheels by a certain angle in radian
     * @param rotation the rotation in radian
     */
    public void addRotationToWheels(double rotation){
        this.leftFrontWheel.rotate(rotation);
        this.rightFrontWheel.rotate(rotation);
    }

    /**
     * Add a thrust to the two back wheels
     * @param thrust the thrust
     */
    public void addThrust(double thrust){
        this.rightBackWheel.addThrust(thrust);
        this.leftBackWheel.addThrust(thrust);
    }


    /**
     * Stop the thrust of the car by setting it to 0 for the two back wheels
     */
    public void stopThrust(){
        this.rightBackWheel.setThrust(0);
        this.leftBackWheel.setThrust(0);
    }

    public double getFromWheelsRotation(){
        return (this.rightFrontWheel.getRotation() + this.leftFrontWheel.getRotation()) / 2;
    }

    @Override
    public Vector2d getDirection(){
        Vector2d v = new Vector2d(0, 1);

        return v.rotate(this.getFromWheelsRotation(), Vector2d.origin);
    }
    
    /**
     * Used to save the state of the car in the simulation buffer
     * @return the state of the state
     */
    public CarInfo getSnapShotInfo() {
    	return new CarInfo(this);
    }
    
    /**
     * Set the rotation of this car
     * @param theta
     */
    public void setRotation(double theta) {
    	this.addRotationToWheels(theta);
    }
    
    /**
     * @return the list of sensors attached to this car
     */
    public ArrayList<Sensor> getSensors(){
    	return this.sensors;
    }
}

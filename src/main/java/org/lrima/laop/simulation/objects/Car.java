package org.lrima.laop.simulation.objects;

import org.lrima.laop.math.MathUtils;
import org.lrima.laop.math.Vector3d;
import org.lrima.laop.physic.PhysicEngine;
import org.lrima.laop.physic.Physicable;
import org.lrima.laop.physic.objects.Bloc;
import org.lrima.laop.simulation.CarInfo;

import java.awt.geom.Area;

/**
 * Physic object representing a car
 * @author Clement Bisaillon
 */
public class Car extends Bloc {
    private Wheel leftFrontWheel;
    private Wheel rightFrontWheel;
    private Wheel leftBackWheel;
    private Wheel rightBackWheel;
    
    private static double  MAX_VELOCITY;

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

        this.addSubObject(leftBackWheel, rightBackWheel, leftFrontWheel, rightFrontWheel);

        this.leftBackWheel.setCanRotate(false);
        this.rightBackWheel.setCanRotate(false);
    }
    
    @Override
    public Vector3d getSumForces() {
    	Vector3d sum = Vector3d.origin;

        for(Vector3d force : this.forces){
            sum = sum.add(force);
        }

        //Get the force of the children
        for(Physicable child : this.getSubObjects()){
            sum = sum.add(child.getSumForces());
        }
        
        //Get the rotation friction force
//        sum = sum.add(this.getAngularResistance());

        return sum;
    }

    @Override
    protected void nextStep() {
    	Vector3d sumOfForces = this.getSumForces();

        this.setAcceleration(sumOfForces.multiply(1.0 / this.getMass()));


        //Or else the car never stops
        if(MathUtils.nearZero(getAcceleration().modulus(), 0.000001)){
        	this.setAcceleration(Vector3d.origin);
            this.resetVelocity();
        }

        this.addVelocity(getAcceleration().multiply(PhysicEngine.DELTA_T));

  
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
        		this.setVelocity(this.getDirection().normalize().multiply(this.getVelocity().modulus()));
        	}
            this.angularVelocity = 0;
            
        }
    }
    
    /**
     * @return The angular resistance force
     */
    private Vector3d getAngularResistance() {
    	Vector3d direction = this.getDirection().multiply(-1);
    	Vector3d tangent = new Vector3d(direction.getY(), -direction.getX(), 0);
    	Vector3d resistance = tangent.multiply(this.angularVelocity * this.getWeight().modulus() * 0.2);
    	
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

    /**
     * @return The center position of the car in pixels
     */
    public Vector3d getCenter(){
        return new Vector3d(this.getPosition().getX() + this.getWidth() / 2, this.getPosition().getY() + this.getHeight() / 2, 0);
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
    public Vector3d getDirection(){
        Vector3d v = new Vector3d(0, 1, 0);

        return v.rotateZAround(this.getFromWheelsRotation(), Vector3d.origin);
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
}

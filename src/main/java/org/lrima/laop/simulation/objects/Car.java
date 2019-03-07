package org.lrima.laop.simulation.objects;

import org.lrima.laop.math.MathUtils;
import org.lrima.laop.math.Vector3d;
import org.lrima.laop.physic.PhysicEngine;
import org.lrima.laop.physic.Physicable;
import org.lrima.laop.physic.objects.Bloc;
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

    private double lastTorque;

    /**
     * Create a new car with mass 2000
     * This method creates the four wheels and attach them to the car.
     */
    public Car(){
        super(2000, 200, 400);
        this.angularSpeed = 0;

        this.leftBackWheel = new Wheel(this, Wheel.WheelLocation.BACK_LEFT);
        this.rightFrontWheel = new Wheel(this, Wheel.WheelLocation.FRONT_RIGHT);
        this.leftFrontWheel = new Wheel(this, Wheel.WheelLocation.FRONT_LEFT);
        this.rightBackWheel = new Wheel(this, Wheel.WheelLocation.BACK_RIGHT);

        this.addSubObject(leftBackWheel, rightBackWheel, leftFrontWheel, rightFrontWheel);

        this.leftBackWheel.setCanRotate(false);
        this.rightBackWheel.setCanRotate(false);
    }

    @Override
    protected void nextStep() {
        super.nextStep();

        double totalRotation = this.getRotation() * 4;
        for(Physicable p : this.getSubObjects()){
            Wheel w = (Wheel) p;
            totalRotation -= w.getRotation();
        }

        totalRotation *= 0.0001;

        if(!MathUtils.nearZero(totalRotation, 0.000001)) {
            double angularAcceleration = totalRotation;
            this.angularSpeed = this.angularSpeed + angularAcceleration * PhysicEngine.DELTA_T;
            this.rotation += (-this.angularSpeed * PhysicEngine.DELTA_T);
        }
        else{
            this.angularSpeed = 0;
        }
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

    public double getFronWheelsRotation(){
        return (this.rightFrontWheel.getRotation() + this.leftFrontWheel.getRotation()) / 2;
    }
}

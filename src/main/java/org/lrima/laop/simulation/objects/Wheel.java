package org.lrima.laop.simulation.objects;

import org.lrima.laop.math.MathUtils;
import org.lrima.laop.math.Vector3d;
import org.lrima.laop.physic.PhysicEngine;
import org.lrima.laop.physic.objects.Bloc;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 * Physic object representing the wheel of a car
 * @author Clement Bisaillon
 */
public class Wheel extends Bloc {

    public enum WheelLocation {
        FRONT_LEFT, FRONT_RIGHT, BACK_LEFT, BACK_RIGHT;
    }

    private final static double WHEEL_WIDTH = 20;
    private final static double WHEEL_HEIGHT = 70;
    private final static double WHEEL_MASS = 200;
    private final double ROLLING_RESISTANCE_COEF = 0.001;
    private final double MAX_ROTATION = Math.PI / 3;

    private double thrust;
    private boolean canRotate;

    private WheelLocation location;
    private Car car;

    /**
     * Create a new wheel and attach it to the car at the specified location
     * @param car the car to attach the wheel to
     * @param location the location of the wheel on the car
     */
    public Wheel(Car car, WheelLocation location){
        super(WHEEL_MASS, WHEEL_WIDTH, WHEEL_HEIGHT);

        this.car = car;
        this.thrust = 0;
        this.location = location;
        this.canRotate = true;

        this.setPosition(this.getPosition());
    }

    @Override
    public Shape getShape() {
        AffineTransform af = new AffineTransform();
        af.rotate(this.getRotation(), this.getCenter().getX(), this.getCenter().getY());
        Shape nonRotatedShape = new Rectangle((int)getPosition().getX(), (int)getPosition().getY(), (int)this.width, (int)this.height);
        return af.createTransformedShape(nonRotatedShape);
    }

    /**
     * Get the thrust force of the wheel
     * @return the thrust force of the wheel
     */
    public Vector3d getThrustForce(){
        double x = -Math.sin(this.getRotation()) * this.thrust;
        double y = Math.cos(this.getRotation()) * this.thrust;

        return new Vector3d(x, y, 0);
    }

    /**
     * Set the thrust force of this wheel
     * @param thrust the force
     */
    public void setThrust(double thrust) {
        this.thrust = thrust;
    }

    /**
     * Get the resistance force of the wheel on the ground
     * @return the resistance force of the wheel
     */
    public Vector3d getVelocityResistance(){
        Vector3d resistance = this.car.getVelocity().multiply(this.getWeight().modulus() * this.ROLLING_RESISTANCE_COEF);

        //Flip the vector
        resistance = new Vector3d(resistance.getX() * -1 , resistance.getY() * -1, 0);

        return resistance;
    }

    public Vector3d getAngularResistance(){
        Vector3d radius = Vector3d.distanceBetween(this.getCenter(), this.car.getCenter());
        Vector3d resistance = new Vector3d(-1 * radius.getX(), radius.getY(), 0);

        resistance = resistance.multiply(this.car.getAngularSpeed());

//        System.out.println(resistance);
        System.out.println(this.car.getAngularSpeed());

        return resistance;
    }

    /**
     * Get the torque forces of this wheel
     * @return The list of torques
     */
    public double getTorque(){
        Vector3d distanceWheelCar = Vector3d.distanceBetween(car.getCenter(), this.getCenter());
        Vector3d force = this.getSumForces();

        double torque = (this.car.getRotation() - this.getRotation());


        return torque;
    }

    @Override
    public Vector3d getSumForces() {
        Vector3d sumOfForces = super.getSumForces();

        //Add the thrust and the resistance to the forces
        sumOfForces = sumOfForces.add(this.getThrustForce());
        sumOfForces = sumOfForces.add(this.getVelocityResistance());
        sumOfForces = sumOfForces.add(this.getAngularResistance());

        return sumOfForces;
    }

    @Override
    public double getRotation() {
        double totalRotation = car.getRotation();

        //If the wheel is free to rotate, its rotation is not based on the car's rotation
        if(this.canRotate){
            totalRotation = super.getRotation();
        }

        return totalRotation;
    }


    @Override
    public Vector3d getPosition() {
        //Place the wheels correctly
        Vector3d position = Vector3d.origin;
        switch(location){
            case FRONT_LEFT:
                position = car.getBottomLeftPosition();
                break;
            case FRONT_RIGHT:
                position = car.getBottomRightPosition();
                break;
            case BACK_LEFT:
                position = car.getTopLeftPosition();
                break;
            case BACK_RIGHT:
                position = car.getTopRightPosition();
                break;
        }

        return position.add(new Vector3d(-WHEEL_WIDTH / 2, -WHEEL_HEIGHT / 2, 0));
    }

    @Override
    public Vector3d getCenter() {
        return new Vector3d(this.getPosition().getX() + this.width / 2, this.getPosition().getY() + this.height / 2, 0);
    }


    public void setCanRotate(boolean canRotate) {
        this.canRotate = canRotate;
    }

    @Override
    public void rotate(double rotation) {
        super.rotate(rotation);

        //Maximum rotation of MAX_ROTATION in both directions
        if(Math.abs(this.car.getRotation() - this.getRotation()) > this.MAX_ROTATION){
            //Cancel the rotation
            this.rotation = this.rotation - rotation;
        }
    }

    /**
     * Add a thrust force to this wheel
     * @param thrust the force
     */
    public void addThrust(double thrust){
        this.thrust += thrust;
    }
}
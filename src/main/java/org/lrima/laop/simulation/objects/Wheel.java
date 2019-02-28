package org.lrima.laop.simulation.objects;

import org.lrima.laop.math.Vector3d;
import org.lrima.laop.physic.PhysicEngine;
import org.lrima.laop.physic.objects.Bloc;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 * Object representing the wheel of a car
 * @author Clement Bisaillon
 */
public class Wheel extends Bloc {

    private final static double WHEEL_WIDTH = 20;
    private final static double WHEEL_HEIGHT = 70;
    private final static double WHEEL_MASS = 200;
    private final double ROLLING_RESISTANCE_COEF = 0.001;
    private final double MAX_ROTATION = Math.PI / 4;

    private double thrust;
    private boolean canRotate;

    public enum WheelLocation {
        FRONT_LEFT, FRONT_RIGHT, BACK_LEFT, BACK_RIGHT;
    }

    private WheelLocation location;

    private Car car;

    /**
     * Create a new wheel and attach it to the car at the specified location
     * @param car the car to attach the wheel to
     * @param location the location of the wheel on the car
     */
    public Wheel(Car car, WheelLocation location){
        super(WHEEL_MASS, WHEEL_WIDTH, WHEEL_HEIGHT);

        Vector3d position = Vector3d.origin;
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

    public void setThrust(double thrust) {
        this.thrust = thrust;
    }

    /**
     * Get the resistance force of the wheel on the ground
     * @return the resistance force of the wheel
     */
    public Vector3d getResistance(){
        double friction = -this.getWeight().modulus() * this.ROLLING_RESISTANCE_COEF;

        double x = Math.sin(this.getRotation()) * friction;
        double y = Math.cos(this.getRotation()) * friction;

        Vector3d resistance = new Vector3d(x, y, 0);

        return resistance;
    }

    /**
     * Get the force moment of this wheel
     * @return the force moment
     */
    public double getTorque(){
        Vector3d distanceWheelCar = Vector3d.distanceBetween(this.getCenter(), car.getCenter());
        Vector3d force = this.getResistance();

        //Used to check if the force is to the right or left of the distance vector
        Vector3d rotatedForce = new Vector3d(force.getY(), force.getX(), 0);

        double tetha = Math.PI - Vector3d.angleBetween(force, distanceWheelCar);

        double sign = distanceWheelCar.dot(rotatedForce) > 0 ? -1 : 1;

        double torque = distanceWheelCar.modulus() * force.modulus() * Math.sin(tetha) * sign;

        return Double.isNaN(torque) ? 0 : torque;
    }

    @Override
    public Vector3d getSumForces() {
        Vector3d sumOfForces = super.getSumForces();

        //Add the thrust and the resistance to the forces
        sumOfForces = sumOfForces.add(this.getThrustForce());
        sumOfForces = sumOfForces.add(this.getResistance());

        return sumOfForces;
    }

    @Override
    public Vector3d getSumAngularForces() {
        Vector3d sumOfAngularForces = super.getSumAngularForces();
        sumOfAngularForces = sumOfAngularForces.add(this.getResistance());

        return sumOfAngularForces;
    }

    @Override
    public double getRotation() {
        double totalRotation = car.getRotation();

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

    public boolean canRotate(){
        return this.canRotate;
    }
}

package org.lrima.laop.simulation.objects;

import org.lrima.laop.math.Vector3d;
import org.lrima.laop.physic.PhysicEngine;
import org.lrima.laop.physic.objects.Bloc;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Object representing the wheel of a car
 * @author Clement Bisaillon
 */
public class Wheel extends Bloc {

    private final static double WHEEL_WIDTH = 20;
    private final static double WHEEL_HEIGHT = 70;
    private final static double WHEEL_MASS = 200;

    public enum WheelLocation {
        FRONT_LEFT, FRONT_RIGHT, BACK_LEFT, BACK_RIGHT;
    }

    private Car car;

    /**
     * Create a new wheel and attach it to the car at the specified location
     * @param car the car to attach the wheel to
     * @param location the location of the wheel on the car
     */
    public Wheel(Car car, WheelLocation location){
        super(WHEEL_MASS, WHEEL_WIDTH, WHEEL_HEIGHT);

        Vector3d position = Vector3d.origin;

        //Place the wheels correctly
        switch(location){
            case FRONT_LEFT:
                position = car.getBottomLeftPosition().add(new Vector3d(WHEEL_WIDTH * 2, 0, 0));
                break;
            case FRONT_RIGHT:
                position = car.getBottomRightPosition().add(new Vector3d(WHEEL_WIDTH * 2, 0, 0));
                break;
            case BACK_LEFT:
                position = car.getTopLeftPosition().add(new Vector3d(WHEEL_WIDTH * 2, WHEEL_HEIGHT / 2, 0));
                break;
            case BACK_RIGHT:
                position = car.getTopRightPosition().add(new Vector3d(WHEEL_WIDTH * 2, WHEEL_HEIGHT / 2, 0));
                break;
        }

        this.setPosition(position);
        this.car = car;
    }

    @Override
    public Shape getShape() {
        AffineTransform af = new AffineTransform();
        af.rotate(this.getRotation(), this.getCenter().getX(), this.getCenter().getY());
        Shape nonRotatedShape = new Rectangle((int)position.getX(), (int)position.getY(), (int)this.width, (int)this.height);
        return af.createTransformedShape(nonRotatedShape);
    }

    /**
     * Set the thrust force to the wheel
     * @param force the force of the thrust
     */
    public void setThrust(double force){
        double x = Math.cos(Math.PI / 2 + this.getRotation()) * force;
        double y = Math.sin(Math.PI / 2 + this.getRotation()) * force;

        this.resetForces();
        this.addForce(new Vector3d(x, y, 0));
    }

    /**
     * Set the friction force to the wheel
     */
    private void setFriction(){
        double friction = -this.getWeight().modulus() * PhysicEngine.GROUND_FRICTION_COEF;

        double x = Math.cos(Math.PI / 2 + this.getRotation()) * friction;
        double y = Math.sin(Math.PI / 2 + this.getRotation()) * friction;

        this.addForce(new Vector3d(x, y , 0));
    }

    @Override
    public double getRotation() {
        return car.getRotation() + super.getRotation();
    }

    @Override
    public Vector3d getCenter() {
        return new Vector3d(this.position.getX() + this.width / 2, this.position.getY() + this.width / 2, 0);
    }
}

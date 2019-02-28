package org.lrima.laop.simulation.objects;

import org.lrima.laop.math.Vector3d;
import org.lrima.laop.physic.PhysicEngine;
import org.lrima.laop.physic.Physicable;
import org.lrima.laop.physic.objects.Bloc;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.util.ArrayList;

/**
 * Object representing a car
 * @author Clement Bisaillon
 */
public class Car extends Bloc {

    private Wheel leftFrontWheel;
    private Wheel rightFrontWheel;
    private Wheel leftBackWheel;
    private Wheel rightBackWheel;

    private double angularSpeed;
    private double torque;

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
        this.leftBackWheel.setThrust(30);
        this.rightBackWheel.setThrust(30);

        this.leftFrontWheel.rotate(-Math.PI/5);
        this.rightFrontWheel.rotate(-Math.PI/5);
    }

    @Override
    protected void nextStep() {
//        this.rotate(0.001);
        super.nextStep();

        //Calculates the total torque applied to the car
        double totalTorque = 0;
        for(Physicable p : this.getSubObjects()){
            Wheel w = (Wheel) p;
            totalTorque += w.getTorque();
        }
        double angularAcceleration = 0.00000001 * totalTorque;
        this.angularSpeed = this.angularSpeed + angularAcceleration * PhysicEngine.DELTA_T;

        this.rotation += (-this.angularSpeed * PhysicEngine.DELTA_T);
    }

    @Override
    public Area getArea() {
        Area carArea = super.getArea();

//        Path2D path = new GeneralPath();
//        path.moveTo(this.getTopLeftPosition().getX(), this.getTopLeftPosition().getY());
//        path.lineTo(this.getTopRightPosition().getX(), this.getTopRightPosition().getY());
//        path.lineTo(this.getBottomRightPosition().getX(), this.getBottomRightPosition().getY());
//        path.lineTo(this.getBottomLeftPosition().getX(), this.getBottomLeftPosition().getY());
//        path.closePath();
//
//        Area carArea = new Area(path);


        carArea.add(leftBackWheel.getArea());
        carArea.add(rightBackWheel.getArea());
        carArea.add(leftFrontWheel.getArea());
        carArea.add(rightFrontWheel.getArea());

        return carArea;
    }

    public Vector3d getCenter(){
        return new Vector3d(this.getPosition().getX() + this.getWidth() / 2, this.getPosition().getY() + this.getHeight() / 2, 0);
    }

    public double getTotalThrust(){
        double thrust = 0;
        for(Physicable w : this.getSubObjects()){
            thrust += ((Wheel) w).getThrustForce().modulus();
        }

//        System.out.println(thrust);

        return thrust;
    }

}

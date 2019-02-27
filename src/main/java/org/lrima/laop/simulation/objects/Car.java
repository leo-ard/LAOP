package org.lrima.laop.simulation.objects;

import org.lrima.laop.math.Vector3d;
import org.lrima.laop.physic.objects.Bloc;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

/**
 * Object representing a car
 * @author Clement Bisaillon
 */
public class Car extends Bloc {

    private Wheel leftFrontWheel;
    private Wheel rightFrontWheel;
    private Wheel leftBackWheel;
    private Wheel rightBackWheel;

    public Car(){
        super(2000, 200, 400);

        this.leftBackWheel = new Wheel(this, Wheel.WheelLocation.BACK_LEFT);
        this.rightFrontWheel = new Wheel(this, Wheel.WheelLocation.FRONT_RIGHT);
        this.leftFrontWheel = new Wheel(this, Wheel.WheelLocation.FRONT_LEFT);
        this.rightBackWheel = new Wheel(this, Wheel.WheelLocation.BACK_RIGHT);

        this.addSubObject(leftBackWheel, rightBackWheel, leftFrontWheel, rightFrontWheel);

        this.leftBackWheel.setThrust(20);
        this.rightBackWheel.setThrust(20);

        this.leftFrontWheel.rotate(Math.PI/5);
        this.rightFrontWheel.rotate(Math.PI/5);
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

    public Vector3d getCenter(){
        return new Vector3d(this.position.getX() + this.getWidth() / 2, this.position.getY() + this.getHeight() / 2, 0);
    }
}

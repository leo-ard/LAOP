package org.lrima.laop.physic.objects;

import org.lrima.laop.graphics.AffineTransformation;
import org.lrima.laop.math.Vector3d;
import org.lrima.laop.physic.PhysicEngine;
import org.lrima.laop.physic.Physicable;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

/**
 * @author Clement Bisaillon
 */
public class Bloc extends Physicable {

    protected double width;
    protected double height;

    public Bloc(Vector3d position, double mass, double width, double height){
        super(position, mass);
        this.width = width;
        this.height = height;
    }

    public Bloc(double mass, double width, double height){
        super(mass);
        this.width = width;
        this.height = height;
    }


    @Override
    public Shape getShape() {
        AffineTransform af = new AffineTransform();
        af.rotate(this.getRotation(), this.getCenter().getX(), this.getCenter().getY());

        Shape nonRotatedShape = new Rectangle((int)position.getX(), (int)position.getY(), (int)this.width, (int)this.height);

        return af.createTransformedShape(nonRotatedShape);
    }

    @Override
    public void collideWith(Physicable object) {
        //FOR THE DEMO
        if(this.canCollide()) {
            this.stopCheckingCollisionAt = System.currentTimeMillis();

            this.resetVelocity();

            for (int i = 0; i < this.forces.size(); i++) {
                this.forces.set(i, this.forces.get(i).multiply(-1));
            }
        }
    }


    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Vector3d getTopLeftPosition(){
        double x = 0;
        double y = 0;
        return new Vector3d(x, y, 0);
    }

    public Vector3d getTopRightPosition(){
        double x = this.width;
        double y = 0;
        return new Vector3d(x, y, 0);
    }

    public Vector3d getBottomLeftPosition(){
        double x = 0;
        double y = this.height;
        return new Vector3d(x, y, 0);
    }

    public Vector3d getBottomRightPosition(){
        double x = this.width;
        double y = this.height;
        return new Vector3d(x, y, 0);
    }
}

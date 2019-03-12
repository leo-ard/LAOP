package org.lrima.laop.physic.objects;

import org.lrima.laop.math.Vector3d;
import org.lrima.laop.physic.Physicable;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * @author Clement Bisaillon
 */
public class Bloc extends Physicable {

    protected double width;
    protected double height;

    /**
     * Creates a bloc java.physic object with a position, a mass and its dimensions
     * @param position the position of the object
     * @param mass the mass of the object
     * @param width the width of the object
     * @param height the height of the object
     */
    public Bloc(Vector3d position, double mass, double width, double height){
        super(position, mass);
        this.width = width;
        this.height = height;
    }

    /**
     * Creates a bloc java.physic object with a mass and a dimension.
     * @param mass the mass of the object
     * @param width the width of the object
     * @param height the height of the object
     */
    public Bloc(double mass, double width, double height){
        super(mass);
        this.width = width;
        this.height = height;
    }


    @Override
    public Shape getShape() {
        AffineTransform af = new AffineTransform();
        af.rotate(this.getRotation(), this.getCenter().getX(), this.getCenter().getY());

        Shape nonRotatedShape = new Rectangle((int)getPosition().getX(), (int)getPosition().getY(), (int)this.width, (int)this.height);

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


    /**
     * @return The width of the object
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return The height of the object
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return top-left corner's position in pixels
     */
    public Vector3d getTopLeftPosition(){
        double x = this.position.getX();
        double y = this.position.getY();
        return new Vector3d(x, y, 0).rotateZAround(this.getRotation(), this.getCenter());
    }

    /**
     * @return top-right corner's position in pixels
     */
    public Vector3d getTopRightPosition(){
        double x = this.position.getX() + this.width;
        double y = this.position.getY();
        return (new Vector3d(x, y, 0)).rotateZAround(this.getRotation(), this.getCenter());
    }

    /**
     * @return bottom-left corner's position in pixels
     */
    public Vector3d getBottomLeftPosition(){
        double x = this.position.getX();
        double y = this.position.getY() + this.height;
        return (new Vector3d(x, y, 0)).rotateZAround(this.getRotation(), this.getCenter());
    }

    /**
     * @return top-right corner's position in pixels
     */
    public Vector3d getBottomRightPosition(){
        double x = this.position.getX() + this.width;
        double y = this.position.getY() + this.height;
        return (new Vector3d(x, y, 0)).rotateZAround(this.getRotation(), this.getCenter());
    }
}

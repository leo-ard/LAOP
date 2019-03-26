package org.lrima.laop.physic.objects;

import org.lrima.laop.utils.math.Vector2d;
import org.lrima.laop.physic.Physicable;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

/**
 * @author Clement Bisaillon
 */
public abstract class Box extends Physicable {
    protected double width;
    protected double height;

    /**
     * Creates a bloc java.physic object with a position, a mass and its dimensions
     * @param position the position of the object
     * @param mass the mass of the object
     * @param width the width of the object
     * @param height the height of the object
     */
    public Box(Vector2d position, double mass, double width, double height){
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
    public Box(double mass, double width, double height){
        super(mass);
        this.width = width;
        this.height = height;
    }

    //TODO
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
    public Vector2d getTopLeftPosition(){
        double x = this.getPosition().getX();
        double y = this.getPosition().getY();
        return new Vector2d(x, y).rotate(this.getRotation(), this.getCenter());
    }

    /**
     * @return top-right corner's position in pixels
     */
    public Vector2d getTopRightPosition(){
        double x = this.getPosition().getX() + this.width;
        double y = this.getPosition().getY();
        return new Vector2d(x, y).rotate(this.getRotation(), this.getCenter());
    }

    /**
     * @return bottom-left corner's position in pixels
     */
    public Vector2d getBottomLeftPosition(){
        double x = this.getPosition().getX();
        double y = this.getPosition().getY() + this.height;
        return (new Vector2d(x, y)).rotate(this.getRotation(), this.getCenter());
    }

    /**
     * @return top-right corner's position in pixels
     */
    public Vector2d getBottomRightPosition(){
        double x = this.getPosition().getX() + this.width;
        double y = this.getPosition().getY() + this.height;
        return new Vector2d(x, y).rotate(this.getRotation(), this.getCenter());
    }

    public Vector2d getCenter(){
        return this.position.add(new Vector2d(this.width/2, this.height/2));
    }

    public Area getArea(){
        Rectangle.Double rect = new Rectangle2D.Double(this.position.getX(), this.position.getY(), this.width, this.height);
        AffineTransform at = new AffineTransform();
        at.rotate(this.rotation, this.position.getX() + width/2, this.position.getY()+height/2);

        return new Area(at.createTransformedShape(rect));
    }
}

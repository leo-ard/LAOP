package org.lrima.laop.physic.abstractObjects;

import org.lrima.laop.physic.staticobjects.StaticLineObject;
import org.lrima.laop.simulation.map.AbstractMap;
import org.lrima.laop.utils.MathUtils;
import org.lrima.laop.utils.math.Vector2d;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

/**
 * @author Clement Bisaillon
 */
public abstract class Box extends AbstractCar {
    protected double width;
    protected double height;

    /**
     * Creates a bloc java.physic object with a position, a mass and its dimensions
     * @param position the position of the object
     * @param mass the mass of the object
     * @param width the width of the object
     * @param height the height of the object
     */
    public Box(AbstractMap map, Vector2d position, double mass, double width, double height){
        super(map, position, mass);
        this.width = Math.min(width, height);
        this.height = Math.max(width, height);
    }

    /**
     * Creates a bloc java.physic object with a mass and a dimension.
     * @param mass the mass of the object
     * @param width the width of the object
     * @param height the height of the object
     */
    public Box(AbstractMap map, double mass, double width, double height){
        super(map, mass);
        this.width = Math.max(width, height);
        this.height = Math.min(width, height);
    }

    public boolean isCollidingWith(StaticLineObject staticLineObject){
        return MathUtils.rectSegmentIntersection(
                staticLineObject.getX1(), staticLineObject.getY1(),
                staticLineObject.getX2(), staticLineObject.getY2(),
                (float) getTopLeftPosition().getX(), (float) getTopLeftPosition().getY(),
                (float) getTopRightPosition().getX(), (float) getTopRightPosition().getY(),
                (float) getBottomRightPosition().getX(), (float) getBottomRightPosition().getY(),
                (float) getBottomLeftPosition().getX(), (float) getBottomLeftPosition().getY());
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

    @Override
    public float getX1() {
        return (float) (this.getCenter().getX() - this.height);
    }

    @Override
    public float getX2() {
        return (float) (this.getCenter().getX() + this.height);
    }

    @Override
    public float getY2() {
        return (float) (this.getCenter().getY() - this.height);
    }

    @Override
    public float getY1() {
        return (float) (this.getCenter().getY() + this.height);
    }

    @Override
    public void collide(StaticLineObject line) {
        if(this.isDead()) return;

        if(MathUtils.rectSegmentIntersection(
                line.getX1(), line.getY1(),
                line.getX2(), line.getY2(),
                (float) getTopLeftPosition().getX(), (float) getTopLeftPosition().getY(),
                (float) getTopRightPosition().getX(), (float) getTopRightPosition().getY(),
                (float) getBottomRightPosition().getX(), (float) getBottomRightPosition().getY(),
                (float) getBottomLeftPosition().getX(), (float) getBottomLeftPosition().getY()))
            this.kill();
    }
}

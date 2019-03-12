package org.lrima.laop.simulation;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Map;

import static java.util.Map.entry;

/**
 *  Class that keeps all the information about one car for the buffer
 * @author Leonard Oest OLeary
 */
public class CarInfo {
    public double x, y, width, height, tilt;

    /**
     * Creates a new car with the specified attributes
     *
     * @param x position x
     * @param y position y
     * @param width
     * @param height
     * @param tilt the rotation
     */
    public CarInfo(double x, double y, double width, double height, double tilt) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.tilt = tilt;
    }

    public Map<String, Object> getInformationHashmap() {
        return Map.ofEntries(
                entry("x", x),
                entry("y", y),
                entry("Longeur", width),
                entry("Largeur", height),
                entry("Angle", tilt));

    }

    /**
     * Draws the car
     *
     * @param gc the graphical context to draw the car with
     */
    public void draw(GraphicsContext gc) {
        Affine affine = new Affine(gc.getTransform());
        affine.appendRotation(tilt, new Point2D(x, y));

        Affine temp = gc.getTransform();
        gc.setTransform(affine);

        gc.fillRect(x, y, width, height);

        gc.setTransform(temp);
    }

    public Shape getArea(){
        Rectangle.Double rectangle2D = new Rectangle.Double(x, y, width, height);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(x, y);
        affineTransform.rotate(Math.toRadians(tilt));
        affineTransform.translate(-x, -y);

        return affineTransform.createTransformedShape(rectangle2D);
    }
}

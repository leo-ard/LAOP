package org.lrima.laop.simulation;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

/**
 *  Class that keeps all the information about one car for the buffer
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

        gc.setFill(Color.RED);

        gc.fillRect(x, y, width, height);

        gc.setTransform(temp);
    }
}

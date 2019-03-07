package org.lrima.laop.graphics;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;
import org.lrima.laop.simulation.CarInfo;
import org.lrima.laop.simulation.SimulationBuffer;

import java.util.ArrayList;

/**
 * Class that draws the simulation into the canvas according to the buffer
 */
public class SimulationDrawer {
    Canvas canvas;
    SimulationBuffer buffer;
    Affine affineTransform;
    double mouseXPressed, mouseYPressed;

    int currentStep;
    double currentZoom = 0;

    /**
     * Draws the simulation into the canvas according to the buffer
     *
     * @param canvas The canvas to draw on
     * @param buffer The buffer to take the information from
     */
    public SimulationDrawer(Canvas canvas, SimulationBuffer buffer) {
        this.canvas = canvas;
        this.buffer = buffer;
        this.affineTransform = new Affine();

        this.canvas.setOnMousePressed(e -> {
            mouseXPressed = (int) e.getX();
            mouseYPressed = (int) e.getY();
        });


        this.canvas.setOnMouseDragged((e) ->{
            double deltaX = (e.getX() - mouseXPressed) * Math.exp(currentZoom);
            double deltaY = (e.getY() - mouseYPressed) * Math.exp(currentZoom);
            mouseYPressed = e.getY();
            mouseXPressed = e.getX();

            this.affineTransform.appendTranslation(deltaX, deltaY);

            repaint();
        });

        this.canvas.setOnScroll(e -> {
            double oldZoom = currentZoom;
            currentZoom -= e.getDeltaY()/1000;

            double zoom = Math.exp(oldZoom - currentZoom);

            Point2D point = new Point2D(0, 0);
            try {
                point = this.affineTransform.inverseTransform(e.getX(), e.getY());
                System.out.println(point);
            } catch (NonInvertibleTransformException e1) {}


            this.affineTransform.appendScale(zoom, zoom, point);

            repaint();
        });
    }

    /**
     * Repaints over the canvas
     */
    private void repaint() {
        drawStep(currentStep);
    }

    /**
     * Draws the state of the cars at the specified time
     *
     * @param time the time to draw the state of the car from
     */
    public void drawStep(int time){
        currentStep = time;

        ArrayList<CarInfo> cars = buffer.getCars(time);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setTransform(this.affineTransform);

        gc.setFill(Color.BLACK);
        for(CarInfo car : cars) {
            car.draw(gc);
        }

        gc.setTransform(new Affine());
    }

}

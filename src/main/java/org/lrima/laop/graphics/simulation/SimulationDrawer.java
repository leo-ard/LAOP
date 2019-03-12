package org.lrima.laop.graphics.simulation;

import com.jfoenix.controls.JFXSlider;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;
import org.lrima.laop.controller.InspectorPane;
import org.lrima.laop.simulation.CarInfo;
import org.lrima.laop.simulation.SimulationBuffer;

import java.util.ArrayList;


/**
 * Class that draws the simulation into the canvas according to the buffer
 */
public class SimulationDrawer implements Runnable{
    private Canvas canvas;
    private SimulationBuffer buffer;
    private InspectorPane inspector;

    private Affine affineTransform;
    private double mouseXPressed, mouseYPressed;

    private Thread autoDrawThread;
    private boolean running;
    private int timeBetweenFrames;

    private int currentStep;
    private double currentZoom = 0;

    private Slider slider;

    /**
     * Draws the simulation into the canvas according to the buffer
     *  @param canvas The canvas to draw on
     * @param buffer The buffer to take the information from
     * @param inspector
     */
    public SimulationDrawer(Canvas canvas, SimulationBuffer buffer, InspectorPane inspector) {
        this.canvas = canvas;
        this.buffer = buffer;
        this.affineTransform = new Affine();
        this.inspector = inspector;

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

            Point2D point = this.inverseTransform(e.getX(), e.getY());

            this.affineTransform.appendScale(zoom, zoom, point);

            repaint();
        });

        this.canvas.setOnMouseClicked(e -> {
            Point2D transformedPoints = null;
            transformedPoints = this.inverseTransform(e.getX(), e.getY());

            ArrayList<CarInfo> snap = this.buffer.getCars(currentStep);

            for(int i = 0; i < snap.size(); i++){
                if(snap.get(i).getArea().contains(transformedPoints.getX(), transformedPoints.getY())){
                    final int currentIndex = i;
                    inspector.setObject(()-> getCurrent().get(currentIndex));
                    repaint();
                }
            }


        });

    }

    private ArrayList<CarInfo> getCurrent() {
        return this.buffer.getCars(currentStep);
    }

    private Point2D inverseTransform(double x, double y) {
        try {
            return this.affineTransform.inverseTransform(x, y);
        } catch (NonInvertibleTransformException e1) {}
        return new Point2D(0, 0);
    }

    private Point2D inverseTransform(Point2D p){
        return this.inverseTransform(p.getX(), p.getY());
    }

    /**
     * Repaints over the canvas
     */
    public void repaint() {
        Platform.runLater(()-> drawStep(currentStep));
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

        gc.setFill(Color.rgb(180,200, 250 ));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setTransform(this.affineTransform);

        //drawGrid(gc);

        for(CarInfo car : cars) {
            if(inspector.getSelectedObject() == car)
                gc.setFill(Color.RED);
            else
                gc.setFill(Color.BLUE);
            car.draw(gc);
        }

        gc.setTransform(new Affine());
    }

    private void drawGrid(GraphicsContext gc) {
        Point2D p1 = new Point2D(0, 0);
        p1 = this.inverseTransform(p1);
        Point2D p2 = new Point2D(canvas.getWidth(), canvas.getHeight());
        p2 = this.inverseTransform(p2);

        gc.setFill(Color.RED);

        int division = 100;

        //System.out.println(p1);
        gc.setLineWidth(1);

        for(int i = (int)(p1.getX()/division); i < p2.getX(); i+=division){
            for(int j = (int)(p1.getY()/division); j < p2.getY(); j+=division){
                gc.strokeLine(i, p1.getY(), i, p2.getY());
                gc.strokeLine(p1.getX(), j, p2.getX(), j);
            }
        }
    }

    public void startAutodraw(int timeBetweenFrames){
        this.autoDrawThread = new Thread(this);
        this.autoDrawThread.start();

        this.running = true;
        this.timeBetweenFrames = timeBetweenFrames;
    }

    public void stopAutoDraw(){
        this.running = false;
    }

    @Override
    public void run() {
        while(running){
            Platform.runLater(()->{
                currentStep++;
                if(currentStep >= buffer.getSize()) currentStep = 0;
                inspector.update();
                if(this.slider != null) this.slider.setValue(currentStep);
                drawStep(currentStep);
            });
            try {
                Thread.sleep(timeBetweenFrames);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSlider(JFXSlider slider){
        this.slider = slider;
    }
}

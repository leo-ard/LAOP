package org.lrima.laop.controller;

import com.jfoenix.controls.JFXSlider;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

import org.lrima.laop.graphics.panels.inspector.InspectorPanel;
import org.lrima.laop.simulation.CarInfo;
import org.lrima.laop.simulation.Simulation;
import org.lrima.laop.simulation.SimulationBuffer;

import java.util.ArrayList;


/**
 * Class that draws the simulation into the canvas according to the buffer
 * @author Léonard
 */
public class SimulationDrawer implements Runnable{
    private Canvas canvas;
    private InspectorPanel inspector;

    private Simulation simulation;

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
    public SimulationDrawer(Canvas canvas, Simulation buffer, InspectorPanel inspector) {
        this.canvas = canvas;
        this.simulation = buffer;
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

            ArrayList<CarInfo> snap = this.simulation.getBuffer().getCars(currentStep);

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
        return this.simulation.getBuffer().getCars(currentStep);
    }

    /**
     * Retourne la transformation inverse du point, ou le point (0, 0) si la transformation inverse ne peut pas être faite
     *
     * @param x la position x
     * @param y la position y
     * @return La transformation inverse du point
     */
    private Point2D inverseTransform(double x, double y) {
        try {
            return this.affineTransform.inverseTransform(x, y);
        } catch (NonInvertibleTransformException e1) {}
        return new Point2D(0, 0);
    }

    /**
     * Retourne la transformation inverse du point, ou le point (0, 0) si la transformation inverse ne peut pas être faite
     *
     * @param p la position
     * @return La transformation inverse du point
     */
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
        if(this.simulation.getBuffer().getSize() > 0) {
	        ArrayList<CarInfo> cars = this.simulation.getBuffer().getCars(time);
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
    }

    /**
     * Commence a faire défiler automatiquement le curseur (lancer l'animation)
     *
     * @param timeBetweenFrames le temps entre le déplacement du curseur
     */
    public void startAutodraw(int timeBetweenFrames){
        this.autoDrawThread = new Thread(this);
        this.autoDrawThread.start();

        this.running = true;
        this.timeBetweenFrames = timeBetweenFrames;
    }

    /**
     * Arrete de faire défiler le curseur automatiquement
     */
    public void stopAutoDraw(){
        this.running = false;
    }

    @Override
    public void run() {
        while(running){
            Platform.runLater(()->{
                currentStep++;
                if(currentStep >= this.simulation.getBuffer().getSize())
                    currentStep = 0;
                if(slider != null)
                    this.slider.setValue(currentStep);
                inspector.update();
                drawStep(currentStep);
            });
            try {
                Thread.sleep(timeBetweenFrames);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Attribut le slider qui doit etre assossier avec ce Drawer.
     *
     * @param slider le slider
     */
    public void setSlider(JFXSlider slider){
        this.slider = slider;
    }
}

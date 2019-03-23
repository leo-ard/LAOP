package org.lrima.laop.controller;

import com.jfoenix.controls.JFXSlider;
import javafx.animation.AnimationTimer;
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
import org.lrima.laop.simulation.objects.Car;

import java.util.ArrayList;
import java.util.List;


/**
 * Class that draws the simulation into the canvas according to the buffer
 * @author Léonard
 */
public class SimulationDrawer{
    private Canvas canvas;
    private InspectorPanel inspector;

    private Simulation simulation;

    private Affine affineTransform;
    private double mouseXPressed, mouseYPressed;

    private int currentStep;
    private double currentZoom = 0;

    private Slider slider;
    private boolean autoDraw;
    private int frameWait;
    private int frameCount;
    private boolean realTime;

    private Point2D clicked;
    private ArrayList<CarInfo> currentCars;


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
        });

        this.canvas.setOnScroll(e -> {
            double oldZoom = currentZoom;
            currentZoom -= e.getDeltaY()/1000;

            double zoom = Math.exp(oldZoom - currentZoom);

            Point2D point = this.inverseTransform(e.getX(), e.getY());

            this.affineTransform.appendScale(zoom, zoom, point);
        });

        this.canvas.setOnMouseClicked(e -> {
            clicked = new Point2D(e.getX(), e.getY());
        });

    }

    /**
     * Start the javafx thread to update the canvas at fixed rate.
     *
     */
    public void start(){
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                SimulationBuffer simulationBuffer = simulation.getBuffer();
                int size = simulationBuffer.getSize();
                slider.setMax(size-1);

                if(realTime){
                    slider.setValue(size-1);
                    currentStep = size-1;
                }else if(autoDraw){
                    if(frameCount >= frameWait){
                        slider.setValue(currentStep);
                        currentStep++;
                        frameCount = 0;
                    }

                    frameCount++;
                }
                else{
                    slider.setValue(currentStep);
                }

                if(currentStep >= size){
                    currentStep = 0;
                }

                if(size > 0)
                    currentCars = simulation.getBuffer().getCars(currentStep);

                //HANDLE CLICK
                if(clicked != null){
                    handleClick(clicked);
                    clicked = null;
                }
                inspector.update();

                drawStep();
            }
        };
        animationTimer.start();
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
     * Draws the state of the cars at the specified time
     **/
    private void drawStep(){
        if(this.simulation.getBuffer().getSize() > 0) {
	        GraphicsContext gc = canvas.getGraphicsContext2D();
	
	        gc.setFill(Color.rgb(180,200, 250 ));
	        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	
	        gc.setTransform(this.affineTransform);
	
	        //drawGrid(gc);
	
	        for(CarInfo car : currentCars) {
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
     * Sets the current step of the buffer to be displayed
     *
     * @param currentStep the step
     */
    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    /**
     * Commence a faire défiler automatiquement le curseur (lancer l'animation)
     *
     * @param frameWait le nombre de frame entre chaque changement de valeur
     */
    public void startAutodraw(int frameWait){
        this.frameWait = frameWait;
        this.autoDraw = true;
    }

    /**
     * Arrete de faire défiler le curseur automatiquement
     */
    public void stopAutoDraw(){
        this.autoDraw = false;
    }

    /**
     * Attribut le slider qui doit etre assossier avec ce Drawer.
     *
     * @param slider le slider
     */
    public void setSlider(JFXSlider slider){
        this.slider = slider;
    }

    public void setRealTime(Boolean newVal) {
        this.realTime = newVal;
    }

    private void handleClick(Point2D e){
        Point2D transformedPoints = this.inverseTransform(e.getX(), e.getY());

        for(int i = 0; i < currentCars.size(); i++){
            if(currentCars.get(i).getArea().contains(transformedPoints.getX(), transformedPoints.getY())){
                final int currentIndex = i;
                inspector.setObject(()-> getCurrent().get(currentIndex));
                return;
            }
        }
        inspector.setObject(null);
    }

    private ArrayList<CarInfo> getCurrent() {
        return currentCars;
    }
}

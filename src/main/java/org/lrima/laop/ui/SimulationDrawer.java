package org.lrima.laop.ui;

import java.util.ArrayList;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import org.lrima.laop.simulation.SimulationEngine;
import org.lrima.laop.simulation.buffer.SimulationBuffer;
import org.lrima.laop.simulation.data.CarData;
import org.lrima.laop.ui.components.inspector.InspectorPanel;

import com.jfoenix.controls.JFXSlider;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;


/**
 * Class that draws the simulationEngine into the canvas according to the buffer
 * @author Léonard
 */
public class SimulationDrawer{
    private Canvas canvas;
    private InspectorPanel inspector;

    private SimulationEngine simulationEngine;

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
    private ArrayList<CarData> currentCars;

    private final Color BACKGROUND_COLOR = new Color(237.0/255.0, 247.0/255.0, 245.0/255.0, 1);


    /**
     * Draws the simulationEngine into the canvas according to the SimulationEngine
     *  @param canvas The canvas to draw on
     * @param simulationEngine The SimulationEngine to take the information from
     * @param inspector
     */
    public SimulationDrawer(Canvas canvas, SimulationEngine simulationEngine, InspectorPanel inspector) {
        this.canvas = canvas;
        this.simulationEngine = simulationEngine;
        this.affineTransform = new Affine();
        this.inspector = inspector;

        this.canvas.setOnMousePressed(this::handleMousePressed);
        this.canvas.setOnMouseDragged(this::handleDrag);
        this.canvas.setOnScroll(this::handleScroll);
        this.canvas.setOnMouseClicked(e -> clicked = new Point2D(e.getX(), e.getY()));
    }

    /**
     * Start the javafx thread to update the canvas at fixed rate.
     *
     */
    public void start(){
        resetView();
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                SimulationBuffer simulationBuffer = simulationEngine.getBuffer();
                int size = simulationBuffer.getSize();
                //update slider
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

                //Makes it loop when it comes to the end
                if(currentStep >= size){
                    currentStep = 0;
                }

                if(size > 0){
                    currentCars = simulationBuffer.getCars(currentStep);
                }

                //HANDLE CLICK
                if(clicked != null){
                    handleClick(clicked);
                    clicked = null;
                }

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
     * Draws the state of the cars at the specified time
     **/
    private void drawStep(){
        if(this.simulationEngine.getBuffer().getSize() > 0) {
            this.inspector.update();
	        GraphicsContext gc = canvas.getGraphicsContext2D();
	
	        gc.setFill(this.BACKGROUND_COLOR);
	        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

	        gc.setTransform(this.affineTransform);

	        //Draw the map
	        simulationEngine.getMap().getObjects().forEach(staticObject -> staticObject.draw(gc));

            for(CarData car : currentCars) {
	            car.draw(gc, inspector.getSelectedObject() == car);
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
     * Commence a faire défsiler automatiquement le curseur (lancer l'animation)
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

    //ALL HANDLERS

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

    private ArrayList<CarData> getCurrent() {
        return currentCars;
    }

    public void resetView(){
        this.affineTransform.setToIdentity();
    }

    private void handleScroll(ScrollEvent e) {
        double oldZoom = currentZoom;
        currentZoom -= e.getDeltaY()/1000;

        double zoom = Math.exp(oldZoom - currentZoom);

        Point2D point = this.inverseTransform(e.getX(), e.getY());

        this.affineTransform.appendScale(zoom, zoom, point);
    }

    private void handleDrag(MouseEvent e) {
        double deltaX = (e.getX() - mouseXPressed) * Math.exp(currentZoom);
        double deltaY = (e.getY() - mouseYPressed) * Math.exp(currentZoom);
        mouseYPressed = e.getY();
        mouseXPressed = e.getX();

        this.affineTransform.appendTranslation(deltaX, deltaY);
    }

    private void handleMousePressed(MouseEvent e) {
        mouseXPressed = (int) e.getX();
        mouseYPressed = (int) e.getY();
    }
}

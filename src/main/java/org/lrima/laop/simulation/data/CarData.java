package org.lrima.laop.simulation.data;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.lrima.laop.physic.abstractObjects.AbstractCar;
import org.lrima.laop.physic.concreteObjects.SimpleCar;
import org.lrima.laop.simulation.sensors.Sensor;
import org.lrima.laop.ui.Drawable;
import org.lrima.laop.ui.panels.inspector.Inspectable;
import org.lrima.laop.ui.panels.inspector.InspectorPanel;
import org.lrima.laop.utils.math.Vector2d;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.transform.Affine;

/**
 *  Class that keeps all the information about one car for the buffer
 * @author Leonard Oest OLeary
 */
public class CarData implements Inspectable, Drawable {
    private double x;
    private double y;
    private double width;
    private double height;
    private double tilt;
    private Vector2d velociy;
    private Vector2d acceleration;
    private AbstractCar car;

    private ArrayList<Vector2d> forces;
    private final Color CAR_COLOR = new Color(32.0/255.0, 78.0/255.0, 95.0/255.0, 1);

    /**
     * Retrieve information from a car
     * @param car the car
     */
    public CarData(AbstractCar car) {
    	this.car = car;
        this.x = car.getPosition().getX();
        this.y = car.getPosition().getY();
        this.width = car.getWidth();
        this.height = car.getHeight();
        this.tilt = Math.toDegrees(car.getRotation());
        this.forces = car.getForces();
        this.velociy = car.getVelocity();
        this.acceleration = car.getAcceleration();
    }

    /**
     * @return Toute l'information qui doit etre affichée dans l'inspecteur sous forme de hashmap
     */
    private Map<String, String> getInformationHashmap() {
        Map<String, String> information = new HashMap<>();

        information.put("x", String.format("%.2f", x));
        information.put("y", String.format("%.2f", y));
        information.put("Longeur", String.format("%.2f", width));
        information.put("Largeur", String.format("%.2f", height));
        information.put("Angle", String.format("%.2f", tilt));
        information.put("Velocity", String.format("%.2f", velociy.modulus()));
        information.put("Acceleration", String.format("%.2f", acceleration.modulus()));

        for(Vector2d force : this.forces){
            information.put(force.getTag(), force.toString());
        }

        return information;

    }

    /**
     * Draws the car
     *
     * @param gc the graphical context to draw the car with
     */
    public void draw(GraphicsContext gc, boolean selected) {
    	Affine temp = gc.getTransform();
    	Paint bakColor = Color.rgb(255, 0, 0);
        Affine affine = new Affine(gc.getTransform());
        affine.appendRotation(tilt,new Point2D(car.getCenter().getX(), car.getCenter().getY()));
        gc.setTransform(affine);

        //Draw the rectangle of the car
        gc.setFill(CAR_COLOR);
        gc.fillRect(x, y, width, height);

        //Draw the sensors
        if(selected) {
	        for(Sensor sensor : this.car.getSensors()) {
	        	sensor.draw(gc);
	        }
        }

        gc.setTransform(temp);
        gc.setFill(bakColor);
    }

    /**
     * @return l'aréa de la voiture
     */
    public Shape getArea(){
        Rectangle.Double rectangle2D = new Rectangle.Double(x, y, width, height);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(x + width/2, y + height/2);
        affineTransform.rotate(Math.toRadians(tilt));
        affineTransform.translate(-x - width/2, -y - height/2);

        return affineTransform.createTransformedShape(rectangle2D);
    }

    @Override
    public void generatePanel(InspectorPanel inspectorPanel) {
        Label titleLabel = new Label("CAR INFORMATION");
        titleLabel.setFont(new Font(18));
        inspectorPanel.add(titleLabel);

        inspectorPanel.setAlignment(Pos.TOP_LEFT);

        for(String key : this.getInformationHashmap().keySet()){
            inspectorPanel.add(new Label(key + " : "+ this.getInformationHashmap().get(key)));
        }
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

	@Override
	public void draw(GraphicsContext gc) {
		this.draw(gc, false);
	}
}

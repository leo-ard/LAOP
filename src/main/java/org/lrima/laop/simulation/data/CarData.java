package org.lrima.laop.simulation.data;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lrima.laop.physic.abstractObjects.AbstractCar;
import org.lrima.laop.simulation.sensors.Sensor;
import org.lrima.laop.simulation.sensors.data.SensorData;
import org.lrima.laop.ui.Drawable;
import org.lrima.laop.ui.components.inspector.Inspectable;
import org.lrima.laop.utils.math.Vector2d;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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

    private ArrayList<Vector2d> forces;
    private final Color CAR_COLOR = new Color(32.0/255.0, 78.0/255.0, 95.0/255.0, 1);
    
    private ArrayList<SensorData> sensors;

    /**
     * Retrieve information from a car
     * @param car the car
     */
    public CarData(AbstractCar car) {
        this.x = car.getPosition().getX();
        this.y = car.getPosition().getY();
        this.width = car.getWidth();
        this.height = car.getHeight();
        this.tilt = Math.toDegrees(car.getRotation());
        this.forces = car.getForces();
        this.velociy = car.getVelocity();
        this.acceleration = car.getAcceleration();
        
        this.sensors = new ArrayList<>();
        this.sensors.addAll(car.getSensorsData());
    }

    /**
     * @return Toute l'information qui doit etre affichée dans l'inspecteur sous forme de hashmap
     */
    @Override public Map<String, String> getInformationHashmap() {
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
    
    @Override public String[] getCategories(){
    	return new String[] {"Car", "Algorithm", "Sensors"};
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
        affine.appendRotation(tilt,new Point2D(x+width/2, y+height/2));
        gc.setTransform(affine);

        //Draw the rectangle of the car
        gc.setFill(CAR_COLOR);
        gc.fillRect(x, y, width, height);

        //Draw the sensors
        if(selected) {
        	this.sensors.forEach((sensor) -> {sensor.draw(gc);});
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

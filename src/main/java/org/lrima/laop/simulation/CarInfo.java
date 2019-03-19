package org.lrima.laop.simulation;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.lrima.laop.graphics.panels.Inspectable;
import org.lrima.laop.graphics.panels.InspectorPanel;
import org.lrima.laop.math.Vector3d;
import org.lrima.laop.simulation.objects.Car;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;

/**
 *  Class that keeps all the information about one car for the buffer
 * @author Leonard Oest OLeary
 */
public class CarInfo implements Inspectable {
    private double x;
    private double y;
    private double width;
    private double height;
    private double tilt;
    private Vector3d velociy;
    private Vector3d acceleration;

    /**
     * Retrieve information from a car
     * @param car the car
     */
    public CarInfo(Car car) {
    	this.x = car.getPosition().getX();
    	this.y = car.getPosition().getY();
    	this.width = car.getWidth();
    	this.height = car.getHeight();
    	this.tilt = Math.toDegrees(car.getRotation());
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
        information.put("Velocity", velociy.toFormatedString());
        information.put("Acceleration", acceleration.toFormatedString());

        return information;

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

    /**
     * @return l'aréa de la voiture
     */
    public Shape getArea(){
        Rectangle.Double rectangle2D = new Rectangle.Double(x, y, width, height);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(x, y);
        affineTransform.rotate(Math.toRadians(tilt));
        affineTransform.translate(-x, -y);

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
}

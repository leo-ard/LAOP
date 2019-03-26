package org.lrima.laop.simulation.data;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import org.lrima.laop.ui.panels.inspector.Inspectable;
import org.lrima.laop.ui.panels.inspector.InspectorPanel;
import org.lrima.laop.utils.math.Vector2d;
import org.lrima.laop.physic.objects.Box;

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
    private Vector2d velociy;
    private Vector2d acceleration;

    /**
     * Retrieve information from a car
     * @param car the car
     */
    public CarInfo(Box car) {
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
        information.put("Velocity", velociy.toString());
        information.put("Acceleration", acceleration.toString());

        return information;

    }

    /**
     * Draws the car
     *
     * @param gc the graphical context to draw the car with
     */
    public void draw(GraphicsContext gc) {
        Affine affine = new Affine(gc.getTransform());
        affine.appendRotation(tilt, new Point2D(x + this.width/2, y+this.height/2));

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
}

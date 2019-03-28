package org.lrima.laop.simulation.sensors;

import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;

import org.lrima.laop.physic.concreteObjects.SimpleCar;
import org.lrima.laop.physic.staticobjects.StaticObject;
import org.lrima.laop.simulation.map.AbstractMap;
import org.lrima.laop.utils.GraphicsUtils;
import org.lrima.laop.utils.math.Vector2d;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;

/**
 * Sensor giving the distance from the car to the wall in a straight line
 * @author Clement Bisaillon
 */
public class ProximityLineSensor implements Sensor {
	//The car that this sensor is attached to
	private SimpleCar car;
	private double orientation;
	private final double SENSOR_LENGHT = 100;
	private AbstractMap map;
	private Point2D start;
	
	public ProximityLineSensor(AbstractMap map, SimpleCar car, double orientation) {
		this.car = car;
		this.map = map;
		this.orientation = orientation;
	}
	
	@Override
	public double getValue() {		
		//Move the sensor at the right position
		
		ArrayList<StaticObject> mapObjects = map.getObjects();
		
		Line2D sensorLine = this.getSensorAsLine();
		Path2D sensorLineWithThickness = GraphicsUtils.addThicknessToLine(sensorLine);
		Point2D sensorStart = sensorLine.getP1();
		ArrayList<Double> intersectionDistances = new ArrayList<>();
		
		//Find the objects colliding this sensor
		for(StaticObject object : this.map.getObjects()) {
			Area intersectionArea = new Area(sensorLineWithThickness);
			intersectionArea.intersect(object.getArea());
			if(!intersectionArea.isEmpty()) {
				//Little approximation here
				Rectangle2D intersectionBounds = intersectionArea.getBounds2D();
				Point2D intersectionPoint = new Point2D.Double(intersectionBounds.getX(), intersectionBounds.getY());
				
				//Calculates the percentage of the intersection bounds on the complete sensor line bounds
				double changeInX = intersectionPoint.getX() - sensorStart.getX();
				double changeInY = intersectionPoint.getY() - sensorStart.getY();
				double intersectionLineLenght = Math.sqrt(Math.pow(changeInX, 2) + Math.pow(changeInY, 2));
				
				intersectionDistances.add(1 - (intersectionLineLenght / SENSOR_LENGHT));
			}
		}
		
		//No intersection return 0
		if(intersectionDistances.size() == 0) 
			return 0.0;
		
		//Find the closest intersection distance and return it
		double closestValue = 0;
		for(Double distance : intersectionDistances) {
			if(distance > closestValue) {
				closestValue = distance;
			}
		}
		
		return closestValue;
	}
	
	/**
	 * Get the current sensor represented as a line
	 * @return the line representing the sensor
	 */
	private Line2D getSensorAsLine() {
		Vector2d center = car.getCenter();
		this.start = new Point2D.Double(center.getX(), center.getY());
		double x1 = this.start.getX();
		double y1 = this.start.getY();
		double x2 = this.start.getX() + Math.cos(this.orientation + this.car.getRotation()) *  SENSOR_LENGHT;
		double y2 = this.start.getY() + Math.sin(this.orientation + this.car.getRotation()) * SENSOR_LENGHT;
		
		Line2D line = new Line2D.Double(x1, y1, x2, y2);
		return line;
	}

	@Override
	public void draw(GraphicsContext gc) {
		double lineWidthBak = gc.getLineWidth();
		Paint colorBak = gc.getStroke();
		
		Line2D line = this.getSensorAsLine();
		
		//Rotate back the line
		double x2 = this.start.getX() + Math.cos(this.orientation) *  SENSOR_LENGHT;
		double y2 = this.start.getY() + Math.sin(this.orientation) * SENSOR_LENGHT;
		line.setLine(line.getP1(), new Point2D.Double(x2, y2));
		
		gc.setLineWidth(1);
		gc.setStroke(Color.RED);
		gc.strokeLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());	

	
		gc.setLineWidth(lineWidthBak);
		gc.setStroke(colorBak);
	}

}

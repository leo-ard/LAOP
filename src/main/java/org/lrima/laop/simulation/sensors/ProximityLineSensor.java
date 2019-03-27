package org.lrima.laop.simulation.sensors;

import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import org.lrima.laop.physic.concreteObjects.SimpleCar;
import org.lrima.laop.physic.staticobjects.StaticObject;
import org.lrima.laop.simulation.map.AbstractMap;
import org.lrima.laop.simulation.map.MazeMap;
import org.lrima.laop.utils.math.Vector2d;

/**
 * Sensor giving the distance from the car to the wall in a straight line
 * @author Clement Bisaillon
 */
public class ProximityLineSensor implements Sensor {
	//The car that this sensor is attached to
	private SimpleCar car;
	private double orientation;
	private final double SENSOR_LENGHT = 200;
	private AbstractMap map;
	
	public ProximityLineSensor(AbstractMap map, SimpleCar car, double orientation) {
		this.car = car;
		this.map = map;
		this.orientation = orientation;
	}
	
	@Override
	public double getValue() {		
		ArrayList<StaticObject> mapObjects = map.getObjects();
		
		Line2D sensorLine = this.getSensorAsLine();
		Rectangle2D sensorBounds = sensorLine.getBounds2D();
		Point2D intersectionPoint;
		HashMap<StaticObject, Area> intersectingObjects = new HashMap<>();
		
		//Find the objects colliding this sensor
		for(StaticObject object : this.map.getObjects()) {
			Area intersectionArea = new Area(sensorLine);
			intersectionArea.intersect(object.getArea());
			if(!intersectionArea.isEmpty()) {
				intersectingObjects.put(object, intersectionArea);
				
				Rectangle2D intersectionBounds = intersectionArea.getBounds2D();
				
				//Calculates the percentage of the intersection bounds on the complete sensor line bounds
				double changeInX = intersectionBounds.getWidth() / sensorBounds.getWidth();
				double changeInY = intersectionBounds.getHeight() / sensorBounds.getHeight();
				double intersectionLineLenght = Math.sqrt(Math.pow(changeInX, 2) + Math.pow(changeInY, 2));
				
				return intersectionLineLenght / SENSOR_LENGHT;
			}
		}
		//todo: Keep only the closest object to the sensor
		
		return 0.0;
	}
	
	/**
	 * Get the current sensor represented as a line
	 * @return the line representing the sensor
	 */
	private Line2D getSensorAsLine() {
		Vector2d startLocation = car.getPosition();
		double x1 = startLocation.getX();
		double y1 = startLocation.getY();
		double x2 = startLocation.getX() + Math.cos(this.orientation) *  SENSOR_LENGHT;
		double y2 = startLocation.getY() + Math.sin(this.orientation) * SENSOR_LENGHT;
		return new Line2D.Double(x1, y1, x2, y2);
	}

}

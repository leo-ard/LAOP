package org.lrima.laop.simulation.sensors;

import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;

import org.lrima.laop.physic.concreteObjects.SimpleCar;
import org.lrima.laop.physic.staticobjects.StaticObject;
import org.lrima.laop.simulation.map.AbstractMap;
import org.lrima.laop.utils.GraphicsUtils;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Sensor giving the distance from the car to the wall in a straight line
 * @author Clement Bisaillon
 */
public class ProximityLineSensor implements Sensor {
	//The car that this sensor is attached to
	private SimpleCar car;
	private double orientation;
	private final double SENSOR_LENGHT = 75;
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
		
		Path2D sensorLine = GraphicsUtils.addThicknessToLine(this.getSensorAsLine());
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
		//todo: Doesnt rotate
		this.start = new Point2D.Double(car.getPosition().getX() + car.getWidth() / 2, car.getPosition().getY() + car.getHeight());
		Point2D startLocation = start;
		double x1 = startLocation.getX();
		double y1 = startLocation.getY();
		double x2 = startLocation.getX() + Math.cos(this.orientation) *  SENSOR_LENGHT;
		double y2 = startLocation.getY() + Math.sin(this.orientation) * SENSOR_LENGHT;
		return new Line2D.Double(x1, y1, x2, y2);
	}

	@Override
	public void draw(GraphicsContext gc) {
		double lineWidthBak = gc.getLineWidth();
		Paint colorBak = gc.getStroke();
		
		Line2D line = this.getSensorAsLine();
		
		gc.setLineWidth(1);
		gc.setStroke(Color.RED);
		gc.strokeLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
	
		gc.setLineWidth(lineWidthBak);
		gc.setStroke(colorBak);
	}

}

package org.lrima.laop.simulation.sensors;

import java.awt.geom.Line2D;
import java.util.ArrayList;

import org.lrima.laop.physic.concreteObjects.SimpleCar;
import org.lrima.laop.physic.staticobjects.StaticObject;
import org.lrima.laop.simulation.map.MazeMap;
import org.lrima.laop.utils.math.Vector2d;

/**
 * Sensor giving the distance from the car to the wall in a straight line
 * @author Clement Bisaillon
 */
public class ProximitySensor implements Sensor {
	//The car that this sensor is attached to
	private SimpleCar car;
	private double orientation;
	private final double SENSOR_LENGHT = 75;
	private MazeMap map;
	
	public ProximitySensor(SimpleCar car) {
		this.car = car;
	}
	
	@Override
	public double getValue() {
		ArrayList<StaticObject> mapObjects = map.getObjects();
		Vector2d startLocation = car.getPosition();
		
		//cos a = adj / hyp
		Line2D sensorLine = new Line2D.Double(startLocation.getX(), startLocation.getY(), startLocation.getX() + Math.cos(this.orientation) *  SENSOR_LENGHT, startLocation.getY() + Math.sin(this.orientation) * SENSOR_LENGHT);
		
		return 0.0;
	}

}

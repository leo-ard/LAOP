package org.lrima.laop.simulation.map;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.lrima.laop.physic.staticobjects.StaticObject;

/**
 * Object representing every kind of maps
 * @author Clement Bisaillon
 */
public abstract class AbstractMap {
	protected ArrayList<StaticObject> objects;
	
	public AbstractMap() {
		this.objects = new ArrayList<>();
	}
	
	/**
	 * Converts the objects of the map to multiple areas containing the same type of objects
	 */
	abstract public void bakeArea();
	
	/**
     * @return the starting point of the cars
     */
	abstract public Point2D getStartPoint();
	
	/**
	 * @return All the objects in the map
	 */
	public ArrayList<StaticObject> getObjects(){
		return this.objects;
	}
}

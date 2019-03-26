package org.lrima.laop.simulation.map;

import org.lrima.laop.physic.staticobjects.StaticLineObject;
import org.lrima.laop.physic.staticobjects.StaticObject;
import org.lrima.laop.utils.math.RandomUtils;

import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

//TODO : implements it
public class SimulationMap {
    ArrayList<StaticObject> objects = new ArrayList<>();
    Area area;
    Rectangle2D bounds;
    
    /**
     * Creates a map with bounds
     * @param bounds the bound of the map
     */
    public SimulationMap(Rectangle2D bounds) {
    	this.bounds = bounds;
    }
    
    /**
     * Randomly create a map
     */
    public void randomize(int numberOfPoints) {
    	Point2D firstPoint = RandomUtils.getPoint((int)bounds.getMinX(), (int)bounds.getMaxX(), (int)bounds.getMinY(), (int)bounds.getMaxY());
    	
    	for(int i = 0 ; i < numberOfPoints ; i++) {
//    		Point2D firstPoint = RandomUtils.getPoint((int)bounds.getMinX(), (int)bounds.getMaxX(), (int)bounds.getMinY(), (int)bounds.getMaxY());
    		Point2D point = RandomUtils.getPoint((int)bounds.getMinX(), (int)bounds.getMaxX(), (int)bounds.getMinY(), (int)bounds.getMaxY());
    		//Add a new line
    		this.objects.add(new StaticLineObject(firstPoint.getX(), firstPoint.getY(), point.getX(), point.getY()));
    		firstPoint = point;
    	}
    }

    public void bakeArea() {
        area = new Area();
        objects.forEach(staticObject1 -> area.add(staticObject1.getArea()));
    }

    public Area getArea() {
        return area;
    }

    public ArrayList<StaticObject> getObjects() {
        return objects;
    }
}

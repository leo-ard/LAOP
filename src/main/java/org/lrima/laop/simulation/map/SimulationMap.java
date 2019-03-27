package org.lrima.laop.simulation.map;

import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.lrima.laop.physic.staticobjects.StaticObject;
import org.lrima.laop.utils.math.RandomUtils;

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
    	int corridorWidth = 100;
    	Point2D[] generalPathPoints = this.getRandomGeneralPathPoints(numberOfPoints);

    	//Copy the general Path and translate it by the corridor width
    	Point2D[] generalPathTranslated = new Point2D[generalPathPoints.length];
    	for(int i = 0 ; i < generalPathTranslated.length ; i++) {
    		Point2D pointOnGeneralPath = generalPathPoints[i];
    		generalPathTranslated[i] = new Point2D.Double(pointOnGeneralPath.getX() + corridorWidth, pointOnGeneralPath.getY() + corridorWidth); 
    	}
    	
//    	Point2D[] allPoints = (Point2D[]) ArrayUtils.addAll(generalPathPoints, generalPathTranslated);
//    	
//    	//Create a map from those points
//    	Path2D mapPath = new Path2D.Double();
//    	Point2D start = allPoints[0];
//    	mapPath.moveTo(start.getX(), start.getY());
//	
//    	//Connect all the other points
//    	for(int i = 1 ; i < allPoints.length ; i++) {
//    		Point2D point = allPoints[i];
//    		mapPath.lineTo(point.getX(), point.getY());
//    	}
    }
    
    /**
     * Generate a random path with lines going in a different direction every steps
     * @param numberOfPoints the number of segments in the path
     * @return an array of points representing the points consisting the line
     */
    private Point2D[] getRandomGeneralPathPoints(int numberOfPoints){
    	Point2D[] generalPathPoints = new Point2D[numberOfPoints];
    	Point2D lastPoint = RandomUtils.getPoint(this.bounds);
        
    	int lastDirection = -1;
		int lastSign = 1;
    	for(int i = 0 ; i < generalPathPoints.length ; i++) {
    		
    		int direction = lastDirection == 0 ? 1 : 0;
    		int directionSign = RandomUtils.getBoolean() ? -1 : 1;;
    		int lineLength = RandomUtils.getInteger(50, 500);
    		
    		
    		//Copy the last point and move it horizontaly of verticaly the amount of lineLength
    		Point2D newPoint = new Point2D.Double(lastPoint.getX(), lastPoint.getY());
    		switch(direction) {
    			//Horizontal
		    	case(1):
		    		newPoint.setLocation(newPoint.getX() + directionSign * lineLength, newPoint.getY());
		    		break;
		    	//Vertical
		    	case(2):
		    		newPoint.setLocation(newPoint.getX(), newPoint.getY() + directionSign * lineLength);
		    		break;
    		}
    		
    		//Add the new point to the general path
    		generalPathPoints[i] = newPoint;
    		
    		//set the last variables
    		lastSign = directionSign;
    		lastDirection = direction;
    		lastPoint = newPoint;
    	}
    	
    	return generalPathPoints;
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

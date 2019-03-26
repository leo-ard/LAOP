package org.lrima.laop.simulation.map;

import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;

import org.lrima.laop.physic.staticobjects.StaticLineObject;
import org.lrima.laop.physic.staticobjects.StaticObject;
import org.lrima.laop.utils.math.RandomUtils;

//TODO : implements it
public class SimulationMap {
    ArrayList<StaticObject> objects = new ArrayList<>();
    Area area;
    Rectangle2D bounds;
    private final int MAP_SQUARE_WIDTH = 100;
    private int[][] mapSquareCodes; 
    
    /**
     * Creates a map with bounds
     * @param bounds the bound of the map
     */
    public SimulationMap(Rectangle2D bounds) {
    	this.bounds = bounds;
    	
    	int numberSquareX = (int)(bounds.getWidth() / MAP_SQUARE_WIDTH);
    	int numberSquareY = (int)(bounds.getHeight() / MAP_SQUARE_WIDTH);
    	this.mapSquareCodes = new int[numberSquareY][numberSquareX];
    	
    	//Initialize the codes of the squares
    	for(int y = 0 ; y < mapSquareCodes.length ; y++) {
    		for(int x = 0 ; x < mapSquareCodes[0].length ; x++) {
    			this.mapSquareCodes[y][x] = y * 10 + x;
    		}
    	}
    }
    
    /**
     * Randomly create a maze representing the map
     */
    public void randomize() {
    	//Start by making maze with all squares closed
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

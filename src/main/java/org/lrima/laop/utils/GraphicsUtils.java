package org.lrima.laop.utils;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.MatrixType;

/**
 * Utils about graphics
 * @author Clement Bisaillon
 */
public class GraphicsUtils {

	/**
	 * Converts a line into a line with a small thickness to use it in collision detection
	 * @param line the line to convert
	 * @return a line with a small thickness
	 */
	public static Path2D addThicknessToLine(Line2D line) {
		//The area of the line must be a line with a small width or else there is no collision
    	Point2D.Double p1 = new Point2D.Double(line.getX1(), line.getY1());
    	Point2D.Double p2 = new Point2D.Double(line.getX2(), line.getY2());
    	Point2D.Double p3 = new Point2D.Double(line.getX1() + 1, line.getY1() + 1);
    	Point2D.Double p4 = new Point2D.Double(line.getX2() + 1, line.getY2() + 1);

    	//Create the area of the line with a width
    	Path2D.Float lineWithWidth = new Path2D.Float();
    	lineWithWidth.moveTo((float)p1.getX(), (float)p1.getY());
    	lineWithWidth.lineTo((float)p2.getX(), (float)p2.getY());
    	lineWithWidth.lineTo((float)p3.getX(), (float)p3.getY());
    	lineWithWidth.closePath();
    	
    	return lineWithWidth;
	}

	/**
	 * Converts an AWT area to points and draw it
	 * @param gc the graphical context
	 * @param area the area to convert
	 */
	public static void drawAWTArea(GraphicsContext gc, Area area) {
		double[] m = gc.getTransform().toArray(MatrixType.MT_2D_2x3);
		AffineTransform transform = new AffineTransform(m[0], m[1], m[2], m[3], m[4], m[5]);
		
		ArrayList<double[]> areaPoints = new ArrayList<>();
		ArrayList<Line2D.Double> areaSegments = new ArrayList<Line2D.Double>();
		float[] coords = new float[6];
		
		for(PathIterator it = area.getPathIterator(null) ; !it.isDone() ; it.next()) {			
			int type= it.currentSegment(coords);
			areaPoints.add(new double[] {type, coords[0], coords[1]});
		}
		
		Point2D start;
		Point2D end;
		
		for(int i = 0 ; i < areaPoints.size() ; i++) {
			double[] currentElement = areaPoints.get(i);
			double[] nextElement = {-1, -1, -1};
		    if (i < areaPoints.size() - 1) {
		        nextElement = areaPoints.get(i + 1);
		    }
		    
		    start = new Point2D.Double(currentElement[1], currentElement[2]);
		    end = new Point2D.Double(nextElement[1], nextElement[2]);
		    
		    if(nextElement[0] == PathIterator.SEG_LINETO) {
		    	gc.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
		    }

		    start = end;
		}
	}
}

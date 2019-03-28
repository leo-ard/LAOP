package org.lrima.laop.utils;

import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

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
}

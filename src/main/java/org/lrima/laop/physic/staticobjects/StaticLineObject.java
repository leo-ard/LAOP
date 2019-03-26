package org.lrima.laop.physic.staticobjects;

import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import org.lrima.laop.physic.Physicable;

import javafx.scene.canvas.GraphicsContext;

/**
 * Static object of a simulation representing a single line
 * @author Clement Bisaillon
 */
public class StaticLineObject implements StaticObject{
    double x1, y1, x2, y2;

    public StaticLineObject(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public boolean collide(Physicable physicable) {
        return false;
    }

    @Override
    public Area getArea() {
    	//The area of the line must be a line with a small width or else there is no collision
    	Point2D.Double p1 = new Point2D.Double(x1, y1);
    	Point2D.Double p2 = new Point2D.Double(x2, y2);
    	Point2D.Double p3 = new Point2D.Double(x1 + 5, y1 + 5);
    	Point2D.Double p4 = new Point2D.Double(x2 + 5, y2 + 5);

    	//Create the area of the line with a width
    	Path2D.Float lineWithWidth = new Path2D.Float();
    	lineWithWidth.moveTo((float)p1.getX(), (float)p1.getY());
    	lineWithWidth.lineTo((float)p2.getX(), (float)p2.getY());
    	lineWithWidth.lineTo((float)p3.getX(), (float)p3.getY());
    	lineWithWidth.closePath();
    	
        return new Area(lineWithWidth);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.strokeLine(x1, y1, x2, y2);
    }


}

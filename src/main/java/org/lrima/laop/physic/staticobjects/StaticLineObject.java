package org.lrima.laop.physic.staticobjects;

import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import org.lrima.laop.physic.abstractObjects.AbstractCar;
import org.lrima.laop.utils.GraphicsUtils;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Static object of a simulation representing a single line
 * @author Clement Bisaillon
 */
public class StaticLineObject implements StaticObject{
    double x1, y1, x2, y2;
    private final Color COLOR = new Color(32.0/255.0, 78.0/255.0, 95.0/255.0, 1);

    public StaticLineObject(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void collideWith(AbstractCar physicable) {
        
    }

    @Override
    public Area getArea() {
    	Path2D lineWithWidth = GraphicsUtils.addThicknessToLine(new Line2D.Double(x1, y1, x2, y2));
    	
        return new Area(lineWithWidth);
    }

    @Override
    public void draw(GraphicsContext gc) {
    	Paint bakColor = gc.getFill();
    	
    	gc.setFill(this.COLOR);
        gc.strokeLine(x1, y1, x2, y2);
        
        gc.setFill(bakColor);
    }
    
    /**
     * @return The type of this object
     */
    public StaticObjectType getType() {
    	return StaticObjectType.STATIC_LINE;
    }


}

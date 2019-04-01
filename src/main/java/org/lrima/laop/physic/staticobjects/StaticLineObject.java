package org.lrima.laop.physic.staticobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.lrima.laop.utils.GraphicsUtils;

import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

/**
 * Static object of a simulation representing a single line
 * @author Clement Bisaillon
 */
public class StaticLineObject implements StaticObject {
    float x1, y1, x2, y2;
    private final Color COLOR = new Color(32.0/255.0, 78.0/255.0, 95.0/255.0, 1);

    public StaticLineObject(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
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

    public float getX1() {
        return x1;
    }

    public float getX2() {
        return x2;
    }

    public float getY1() {
        return y1;
    }

    public float getY2() {
        return y2;
    }
}

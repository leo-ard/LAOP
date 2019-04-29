package org.lrima.laop.physic.staticobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Static object of a simulation representing a single line
 * @author Clement Bisaillon
 */
public class StaticLineObject implements StaticObject {
    private float x1, y1, x2, y2;
    private final Color COLOR = new Color(32.0/255.0, 78.0/255.0, 95.0/255.0, 1);

    /**
     * Creates a static line object with parameters x1, x2, y1, y2
     *
     * @param x1 The x value of the first coordinate
     * @param y1 The y value of the first coordinate
     * @param x2 The x value of the second coordinate
     * @param y2 The y value of the second coordinate
     */
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

    /**
     * @return The x component of the first point on the line
     */
    public float getX1() {
        return x1;
    }

    /**
     * @return The x component of the second point on the line
     */
    public float getX2() {
        return x2;
    }

    /**
     * @return The y component of the first point on the line
     */
    public float getY1() {
        return y1;
    }

    /**
     * @return The y component of the second point on the line
     */
    public float getY2() {
        return y2;
    }
}

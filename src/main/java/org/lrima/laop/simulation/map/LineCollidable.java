package org.lrima.laop.simulation.map;

import org.lrima.laop.physic.staticobjects.StaticLineObject;

/**
 * Interface for all objects that can collide with a line (like a car)
 */
public interface LineCollidable {

    /**
     * Called when there is the possibility that the line is touching the car. Must check if its really touching.
     * If it is, it must react properly
     *
     * @param line the line
     */
    void collide(StaticLineObject line);

    /**
     * Called before each step to pre-calculate the controls of x1, x2, y1, y2 (faster that way).
     *
     */
    void bake();

    /**
     *
     * @return the x coordinate of the first point forming the bound
     */
    float getX1();
    /**
     *
     * @return the y coordinate of the first point forming the bound
     */
    float getY1();

    /**
     *
     * @return the x coordinate of the second point forming the bound
     */
    float getX2();

    /**
     *
     * @return the y coordinate of the second point forming the bound
     */
    float getY2();
}

package org.lrima.laop.physic.staticobjects;

import java.awt.geom.Area;

import org.lrima.laop.physic.abstractObjects.AbstractCar;
import org.lrima.laop.ui.Drawable;

import javafx.scene.canvas.GraphicsContext;


/**
 * An object to make collisions with in the Physic Engine. Do not move during the simulation
 *
 * @author Léonard
 */
public interface StaticObject extends Drawable {
    /**
     * Gets its type
     *
     * @return the tyoe of the object
     */
    StaticObjectType getType();
}

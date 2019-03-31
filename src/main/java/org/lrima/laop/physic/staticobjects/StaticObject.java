package org.lrima.laop.physic.staticobjects;

import java.awt.geom.Area;

import org.lrima.laop.physic.abstractObjects.AbstractCar;
import org.lrima.laop.ui.Drawable;

import javafx.scene.canvas.GraphicsContext;


public interface StaticObject extends Drawable {
    Area getArea();
    StaticObjectType getType();
}

package org.lrima.laop.physic.staticobjects;

import java.awt.geom.Area;

import org.lrima.laop.physic.Physicable;
import org.lrima.laop.ui.Drawable;

import javafx.scene.canvas.GraphicsContext;


public interface StaticObject extends Drawable {
    void collideWith(Physicable physicable);
    Area getArea();
    StaticObjectType getType();
}

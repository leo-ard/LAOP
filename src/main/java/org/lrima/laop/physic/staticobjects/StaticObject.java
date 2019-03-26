package org.lrima.laop.physic.staticobjects;

import java.awt.geom.Area;

import org.lrima.laop.physic.Physicable;

import javafx.scene.canvas.GraphicsContext;


public interface StaticObject {
    boolean collide(Physicable physicable);
    Area getArea();
    void draw(GraphicsContext gc);
}

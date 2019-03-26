package org.lrima.laop.physic.staticobjects;

import javafx.scene.canvas.GraphicsContext;
import org.lrima.laop.physic.Physicable;

import java.awt.geom.Area;

public interface StaticObject {
    boolean collide(Physicable physicable);
    Area getArea();
    void draw(GraphicsContext gc);
}

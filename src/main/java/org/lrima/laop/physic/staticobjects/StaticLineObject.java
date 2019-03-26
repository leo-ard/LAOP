package org.lrima.laop.physic.staticobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Line;
import org.lrima.laop.physic.Physicable;

import java.awt.geom.Area;
import java.awt.geom.Line2D;

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
        return new Area(new Line2D.Double(x1, y1, x2, y2));
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.strokeLine(x1, y1, x2, y2);
    }


}

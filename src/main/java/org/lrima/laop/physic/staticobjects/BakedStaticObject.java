package org.lrima.laop.physic.staticobjects;

import javafx.scene.canvas.GraphicsContext;

import java.awt.geom.Area;

public class BakedStaticObject implements StaticObject {
    public BakedStaticObject(Area area, Object p1) {
    }

    @Override
    public Area getArea() {
        return null;
    }

    @Override
    public StaticObjectType getType() {
        return null;
    }

    @Override
    public void draw(GraphicsContext gc) {

    }
}

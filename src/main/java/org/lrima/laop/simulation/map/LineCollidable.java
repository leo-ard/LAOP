package org.lrima.laop.simulation.map;

import org.lrima.laop.physic.staticobjects.StaticLineObject;

public interface LineCollidable {
    void collide(StaticLineObject line);

    void bake();

    float getX1();
    float getY1();
    float getX2();
    float getY2();
}

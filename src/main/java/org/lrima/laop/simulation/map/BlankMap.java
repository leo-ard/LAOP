package org.lrima.laop.simulation.map;

import java.awt.*;
import java.awt.geom.Point2D;

public class BlankMap extends AbstractMap {
    @Override
    public void bakeArea() {

    }

    @Override
    public Point2D getStartPoint() {
        return new Point2D.Double(0, 0);
    }
}

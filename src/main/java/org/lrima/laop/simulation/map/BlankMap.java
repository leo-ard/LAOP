package org.lrima.laop.simulation.map;

import java.awt.geom.Point2D;

/**
 * A blank map with no obstacles
 *
 */
public class BlankMap extends AbstractMap {

    @Override
    public Point2D getStartPoint() {
        return new Point2D.Double(0, 0);
    }
}

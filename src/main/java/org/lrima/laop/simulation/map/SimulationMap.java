package org.lrima.laop.simulation.map;

import org.lrima.laop.physic.staticobjects.StaticLineObject;
import org.lrima.laop.physic.staticobjects.StaticObject;

import java.awt.geom.Area;
import java.util.ArrayList;

//TODO : implements it
public class SimulationMap {
    ArrayList<StaticObject> objects = new ArrayList<>();
    Area area;

    public void bakeArea() {
        area = new Area();
        objects.forEach(staticObject1 -> area.add(staticObject1.getArea()));
    }

    public Area getArea() {
        return area;
    }

    public ArrayList<StaticObject> getObjects() {
        return objects;
    }
}

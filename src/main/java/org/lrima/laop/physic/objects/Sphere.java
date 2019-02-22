package org.lrima.laop.physic.objects;

import org.lrima.laop.math.Vector3d;
import org.lrima.laop.physic.Physicable;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Sphere extends Physicable {

    private double radius;

    public Sphere(Vector3d position, double mass, double radius){
        super(position, mass);
        this.radius = radius;
    }

    public Sphere(double mass, double radius){
        super(mass);
        this.radius = radius;
    }

    @Override
    public Area getArea() {
        return new Area(new Ellipse2D.Double(this.position.getX(), this.position.getY(), this.radius, this.radius));
    }

    @Override
    public void collideWith(Physicable object) {
        //FOR THE DEMO
        if(this.canCollide()){
            this.stopCheckingCollisionAt = System.currentTimeMillis();
            //Stop moving
            this.resetForces();
        }
    }
}

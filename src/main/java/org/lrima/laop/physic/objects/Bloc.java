package org.lrima.laop.physic.objects;

import org.lrima.laop.math.Vector3d;
import org.lrima.laop.physic.Physicable;

import java.awt.*;
import java.awt.geom.Area;

/**
 * @author Clement Bisaillon
 */
public class Bloc extends Physicable {

    private double width;
    private double height;

    public Bloc(Vector3d position, double mass, double width, double height){
        super(position, mass);
        this.width = width;
        this.height = height;
    }

    public Bloc(double mass, double width, double height){
        super(mass);
        this.width = width;
        this.height = height;
    }


    @Override
    public Area getArea() {
        return new Area(new Rectangle((int)position.getX(), (int)position.getY(), (int)this.width, (int)this.height));
    }

    @Override
    public void collideWith(Physicable object) {
        //FOR THE DEMO
        if(this.canCollide()) {
            this.stopCheckingCollisionAt = System.currentTimeMillis();

            for (int i = 0; i < this.forces.size(); i++) {
                this.forces.set(i, this.forces.get(i).multiply(-1));
            }
        }
    }


    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}

package org.lrima.laop.simulation.objects;

import org.lrima.laop.math.Vector3d;
import org.lrima.laop.physic.objects.Bloc;

public class Car extends Bloc {

    public Car(){
        super(Vector3d.origin, 2000, 2, 4.7);
    }

//    private Car(Vector3d position, double mass, double width, double height) {
//        super(position, mass, width, height);
//    }
}

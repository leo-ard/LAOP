package org.lrima.laop.utils;

import org.lrima.laop.math.Vector2d;

import java.util.ArrayList;

public class PhysicUtils {
    private static final double AIR_DENSITY = 1.225;
    private static final double CAR_AREA = 1.5;
    private static final double TRAINER_COEFICIENT = 0.2;


    public static Vector2d accelFromForces(ArrayList<Vector2d> forces, double mass){
        Vector2d sum = Vector2d.origin;

        for(Vector2d force : forces){
            sum = sum.add(force);
        }

        return sum.multiply(1.0/mass);
    }

    public static Vector2d airResistance(Vector2d velocity) {
        // https://fr.wikipedia.org/wiki/A%C3%A9rodynamique_automobile
        return velocity.power2().multiply(AIR_DENSITY * 0.5 * TRAINER_COEFICIENT * CAR_AREA).multiply(velocity.sign().multiply(-1));
    }

    public static Vector2d breakForce(Vector2d velocity, double carControl) {
        double c = 50;
        return velocity.multiply(carControl * c).multiply(-1);
    }

    public static Vector2d directionResistance(Vector2d direction, Vector2d velocity) {
        double projection = velocity.dot(direction);

        Vector2d resistance = velocity.subtract(direction.multiply(projection));

        System.out.println("projection : " + direction.multiply(projection));
        System.out.println("resis" + resistance);


        return resistance.multiply(-10);
    }
}

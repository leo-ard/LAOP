package org.lrima.laop.utils;

import org.lrima.laop.utils.math.Vector2d;

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
        double c = 5_000;
        return velocity.multiply(carControl * c).multiply(-1);
    }

    public static Vector2d directionResistance(Vector2d direction, Vector2d velocity) {
        double c = 15_000;

        Vector2d resistance = velocity.subtract(direction.project(velocity));
        return resistance.multiply(-c);
    }

    public static Vector2d accelFromBackWeels(double accelAmount, double rotation, double wheelDirection, double range) {
        int initForce = 200_000;
        return new Vector2d(0, initForce * accelAmount).rotate(rotation + wheelDirection*range, Vector2d.origin);
    }

    public static double angularAccel(double wheelDirection, Vector2d velocity) {
        return velocity.modulus() * wheelDirection * 100;
    }
}

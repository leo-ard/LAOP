package org.lrima.laop.utils;

import org.lrima.laop.utils.math.Vector2d;

import java.util.ArrayList;

/**
 * Utility class to calculate physic forces
 *
 * @author Leonard
 */
public class PhysicUtils {
    private static final double AIR_DENSITY = 1.225;
    private static final double CAR_AREA = 1.5;
    private static final double TRAINER_COEFICIENT = 1;


    /**
     * Calculates the acceleration from the forces
     *
     * @param forces the fores
     * @param mass the mass
     * @return the resulting acceleration
     */
    public static Vector2d accelFromForces(ArrayList<Vector2d> forces, double mass){
        Vector2d sum = Vector2d.origin;

        for(Vector2d force : forces){
            sum = sum.add(force);
        }

        return sum.multiply(1.0/mass);
    }

    /**
     * The air resistance force
     *
     * @param velocity the velocity of the object
     * @return the force of the velocity
     */
    public static Vector2d airResistance(Vector2d velocity) {
        // https://fr.wikipedia.org/wiki/A%C3%A9rodynamique_automobile
        return velocity.power2().multiply(AIR_DENSITY * 0.5 * TRAINER_COEFICIENT * CAR_AREA).multiply(velocity.sign().multiply(-1));
    }

    /**
     * The break force
     *
     * @param velocity the velocity of the car
     * @param carControl the car controls
     * @return the force that the breaks are applying
     */
    public static Vector2d breakForce(Vector2d velocity, double carControl) {
        double c = 5_000;
        return velocity.multiply(carControl * c).multiply(-1);
    }

    /**
     * The force due to the car not moving in the same direction then the back wheels
     *
     * @param direction the direction of the car
     * @param velocity the velocity of the car
     * @return the friction dur to the backwheels
     */
    public static Vector2d directionResistance(Vector2d direction, Vector2d velocity) {
        double c = 15_000;

        Vector2d resistance = velocity.subtract(direction.project(velocity));
        return resistance.multiply(-c);
    }

    /**
     * The acceleration due to the backwheel force
     *
     * @param accelAmount the acceleration amount
     * @param rotation the rotation of the car
     * @return the thrust force
     */
    public static Vector2d accelFromBackWeels(double accelAmount, double rotation) {
        int initForce = 200_000;
        return new Vector2d(0, initForce * accelAmount).rotate(rotation, Vector2d.origin);
    }

    public static double angularAccel(double wheelDirection, Vector2d velocity) {
        return velocity.modulus() * wheelDirection * 100;
    }
}

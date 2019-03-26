package org.lrima.laop.physic.concreteObjects;

import org.lrima.laop.physic.staticobjects.StaticObjectType;
import org.lrima.laop.utils.MathUtils;
import org.lrima.laop.utils.math.Vector2d;
import org.lrima.laop.network.carcontrollers.CarController;
import org.lrima.laop.physic.objects.Box;
import org.lrima.laop.utils.PhysicUtils;

import java.util.ArrayList;

/**
 *
 *
 * @author Léonard
 */
public class SimpleCar extends Box {
    private CarController carController;
    double wheelDirection;
    final double range = -Math.PI/4;
    private boolean dead;

    public SimpleCar(Vector2d position, CarController controller) {
        super(position, 2000, 10, 30);

        carController = controller;
        wheelDirection = 0;
        dead = false;
    }

    @Override
    protected void nextStep() {
        if(dead) return;
        this.forces = new ArrayList<>();

        /*
         * 0 -> accel
         * 1 -> break
         * 2 -> rotation
         */

        double[] carControls = carController.control(new double[0]);

        this.forces.add(new Vector2d(0, 2 * carControls[0]).rotate(rotation + wheelDirection*range, Vector2d.origin));
        this.forces.add(PhysicUtils.breakForce(this.velocity, carControls[1]));
        this.forces.add(PhysicUtils.airResistance(this.velocity));
        this.forces.add(PhysicUtils.directionResistance(this.getDirection(), this.velocity));

        this.acceleration = PhysicUtils.accelFromForces(forces, this.mass);

        this.wheelDirection = carControls[2] * range;

        this.angularVelocity = MathUtils.angularVelocity(this.angularVelocity, this.wheelDirection, this.velocity);
        this.angularVelocity += -angularVelocity*0.01;
        this.rotate(angularVelocity);


        this.velocity = this.velocity.add(acceleration);
        this.position = this.position.add(this.velocity);
    }

    @Override
    public void collideWith(StaticObjectType type) {
        if(type == StaticObjectType.STATIC_LINE){
            this.dead = true;
        }

    }

    public CarController getController() {
        return carController;
    }
}

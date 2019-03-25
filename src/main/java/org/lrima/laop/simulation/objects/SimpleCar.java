package org.lrima.laop.simulation.objects;

import org.lrima.laop.math.Vector2d;
import org.lrima.laop.network.CarController;
import org.lrima.laop.physic.objects.Box;
import org.lrima.laop.utils.PhysicUtils;

import java.util.ArrayList;

public class SimpleCar extends Box {
    CarController carController;

    public SimpleCar(Vector2d position, CarController controller) {
        super(position, 2000, 10, 30);

        carController = controller;
    }

    @Override
    protected void nextStep() {
        this.forces = new ArrayList<>();

        /*
         * 0 -> accel
         * 1 -> rotation
         */

        double[] carControls = carController.control(new double[0]);

        this.forces.add(new Vector2d(0, 2 * carControls[0]).rotate(this.rotation, Vector2d.origin));
        this.forces.add(PhysicUtils.breakForce(this.velocity, carControls[1]));
        this.forces.add(PhysicUtils.airResistance(this.velocity));
        this.forces.add(PhysicUtils.directionResistance(this.getDirection(), this.velocity));

        this.acceleration = PhysicUtils.accelFromForces(forces, this.mass);

        this.angularVelocity = carControls[2] * 0.01;
        this.rotate(angularVelocity);


        this.velocity = this.velocity.add(acceleration);
        this.position = this.position.add(this.velocity);
    }
}

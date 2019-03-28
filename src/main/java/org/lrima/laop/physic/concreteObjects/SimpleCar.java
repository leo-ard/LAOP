package org.lrima.laop.physic.concreteObjects;

import org.lrima.laop.physic.PhysicEngine;
import org.lrima.laop.physic.staticobjects.StaticObject;
import org.lrima.laop.simulation.sensors.Sensor;
import org.lrima.laop.ui.Drawable;
import org.lrima.laop.utils.math.Vector2d;

import javafx.scene.canvas.GraphicsContext;

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
    final double RANGE = -Math.PI/4;
    private boolean dead;
    private ArrayList<Sensor> sensors;

    public SimpleCar(Vector2d position, CarController controller) {
        super(position, 2000, 10, 30);

        carController = controller;
        wheelDirection = 0;
        dead = false;
        this.sensors = new ArrayList<>();
    }

    @Override
    protected void nextStep() {
        if(dead) return;
        this.forces = new ArrayList<>();
        

        for(Sensor s : this.sensors) {
        	System.out.print(s.getValue() + " ");
        }
        System.out.println();
        
        /*
         * 0 -> accel
         * 1 -> break
         * 2 -> rotation
         */

        double[] carControls = carController.control(new double[0]);

        this.forces.add(PhysicUtils.accelFromBackWeels(carControls[0], rotation, wheelDirection, RANGE));
        this.forces.get(0).setTag("Accel from back");
        this.forces.add(PhysicUtils.breakForce(this.velocity, carControls[1]));
        this.forces.get(1).setTag("Break force");
        this.forces.add(PhysicUtils.airResistance(this.velocity));
        this.forces.get(2).setTag("Air resistance");
        this.forces.add(PhysicUtils.directionResistance(this.getDirection(), this.velocity));
        this.forces.get(3).setTag("Directionnal resistance");

        this.acceleration = PhysicUtils.accelFromForces(forces, this.mass);

        this.wheelDirection = carControls[2] * RANGE;

        this.angularAccel = PhysicUtils.angularAccel(this.wheelDirection, this.velocity);
        this.angularAccel = Math.min(Math.max(RANGE, angularAccel), -RANGE);
        this.angularVelocity = this.velocity.modulus()*this.wheelDirection*PhysicEngine.DELTA_T * 0.08;
        this.rotation += angularVelocity;


        this.velocity = this.velocity.add(acceleration.multiply(PhysicEngine.DELTA_T));
        this.position = this.position.add(this.velocity.multiply(PhysicEngine.DELTA_T));
    }

    @Override
    public void collideWith(StaticObject object) {
    	switch(object.getType()) {
	    	case STATIC_LINE:
	    		this.dead = true;
	    		break;
    	}

    }

    public CarController getController() {
        return carController;
    }
    
    /**
     * @return the list of sensors attached to this car
     */
    public ArrayList<Sensor> getSensors(){
    	return this.sensors;
    }
}

package org.lrima.laop.physic.concreteObjects;

import org.lrima.laop.network.carcontrollers.CarController;
import org.lrima.laop.network.genetics.GeneticNeuralNetwork;
import org.lrima.laop.physic.CarControls;
import org.lrima.laop.physic.PhysicEngine;
import org.lrima.laop.physic.abstractObjects.Box;
import org.lrima.laop.physic.staticobjects.StaticLineObject;
import org.lrima.laop.simulation.map.AbstractMap;
import org.lrima.laop.simulation.map.LineCollidable;
import org.lrima.laop.simulation.sensors.Sensor;
import org.lrima.laop.simulation.sensors.data.SensorData;
import org.lrima.laop.utils.PhysicUtils;
import org.lrima.laop.utils.math.Vector2d;

import java.util.ArrayList;

/**
 * The car that tries to mimic a real car.
 *
 * @author Léonard
 */
public class SimpleCar extends Box {
    private CarController carController;
    double wheelDirection;
    final double RANGE = -Math.PI/4;
    private ArrayList<Sensor> sensors;

    //OPTIMISATION
    private ArrayList<LineCollidable> collidableSensors;

    /**
     * Creates a new car with position <code>position</code> and controller <code>controller</code>
     *
     * @param position the position of the car
     * @param controller the consoller that the car must use
     */
    public SimpleCar(AbstractMap map, Vector2d position, CarController controller) {
        super(map, position, 2000, 30, 10);

        carController = controller;
        wheelDirection = 0;
        this.sensors = new ArrayList<>();
        this.collidableSensors = new ArrayList<>();
    }

    @Override
    public void nextStep() {
        if(this.dead) return;
        this.forces = new ArrayList<>();

        //convert sensor into sensor controls
        double[] sensorValues = this.sensors.stream().mapToDouble(sensor -> sensor.getValue()).toArray();
        CarControls carControls = carController.control(sensorValues);

        this.forces.add(PhysicUtils.accelFromBackWeels(carControls.getAcceleration(), rotation, wheelDirection, RANGE));
        this.forces.get(0).setTag("Accel from back");
        this.forces.add(PhysicUtils.breakForce(this.velocity, carControls.getBreak()));
        this.forces.get(1).setTag("Break force");
        this.forces.add(PhysicUtils.airResistance(this.velocity));
        this.forces.get(2).setTag("Air resistance");
        this.forces.add(PhysicUtils.directionResistance(this.getDirection(), this.velocity));
        this.forces.get(3).setTag("Directionnal resistance");

        this.acceleration = PhysicUtils.accelFromForces(forces, this.mass);

        this.wheelDirection = (carControls.getRotation()-0.5) * 2 * RANGE;

//        this.angularAccel = PhysicUtils.angularAccel(this.wheelDirection, this.velocity);
//        this.angularAccel = Math.min(Math.max(RANGE, angularAccel), -RANGE);
        this.angularVelocity = this.velocity.modulus()*this.wheelDirection*PhysicEngine.DELTA_T * 0.03;
        this.rotation += angularVelocity;

        this.velocity = this.velocity.add(acceleration.multiply(PhysicEngine.DELTA_T));
        this.position = this.position.add(this.velocity.multiply(PhysicEngine.DELTA_T));

        if(this.carController instanceof GeneticNeuralNetwork){
            this.fitness = this.fitnessFunction.apply(this);
            ((GeneticNeuralNetwork) this.carController).setFitness(this.fitness);
        }
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public ArrayList<LineCollidable> getCollidableSensors() {
        return collidableSensors;
    }

    @Override
    public ArrayList<SensorData> getSensorsData() {
        ArrayList<SensorData> list = new ArrayList();

        for (Sensor sensor : sensors) {
            list.add(sensor.getData());
        }

        return list;
    }

    public CarController getController() {
        return carController;
    }

    /**
     * Adds a sensor to the car. If this sensor is of instance LineCollidable (can collide with the map), puts it also in another list for optimisation purposes.
     * @param sensor the sensor to add
     */
    public void addSensor(Sensor sensor){
        this.sensors.add(sensor);
        if(sensor instanceof LineCollidable){
            this.collidableSensors.add((LineCollidable) sensor);
        }
    }

    // OPTIMISATION
    float x1, x2, y1, y2;

    @Override
    public void collide(StaticLineObject line) {
        super.collide(line);
        getCollidableSensors().forEach(s -> s.collide(line));
    }

    @Override
    public void bake() {
        getCollidableSensors().forEach(LineCollidable::bake);

        x1 = (float) (this.getCenter().getX() - this.height);
        y1 = (float) (this.getCenter().getY() - this.height);
        x2 = (float) (this.getCenter().getX() + this.height);
        y2 = (float) (this.getCenter().getY() + this.height);

        for (LineCollidable collidableSensor : collidableSensors) {
            x1 = Math.max(x1, collidableSensor.getX1());
            x1 = Math.max(x1, collidableSensor.getX2());
            x2 = Math.min(x2, collidableSensor.getX1());
            x2 = Math.min(x2, collidableSensor.getX2());
            y1 = Math.max(y1, collidableSensor.getY1());
            y1 = Math.max(y1, collidableSensor.getY2());
            y2 = Math.min(y2, collidableSensor.getY1());
            y2 = Math.min(y2, collidableSensor.getY2());
        }

    }

    @Override
    public float getX1() {
        return x1;
    }

    @Override
    public float getX2() {
        return x2;
    }

    @Override
    public float getY2() {
        return y2;
    }

    @Override
    public float getY1() {
        return y1;
    }
}

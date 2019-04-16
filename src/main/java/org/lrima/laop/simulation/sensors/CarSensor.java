package org.lrima.laop.simulation.sensors;

import org.lrima.laop.physic.abstractObjects.AbstractCar;
import org.lrima.laop.simulation.sensors.data.SingleValueData;

import java.util.function.Function;

public class CarSensor implements Sensor{
    public static CarSensor VELOCITY_SENSOR(AbstractCar abstractCar){
        return new CarSensor(abstractCar, (car) -> car.getVelocity().modulus()/200.0);
    }


    AbstractCar abstractCar;
    Function<AbstractCar, Double> function;

    public CarSensor(AbstractCar abstractCar, Function<AbstractCar, Double> function) {
        this.abstractCar = abstractCar;
        this.function = function;
    }

    @Override
    public double getValue() {
        return this.function.apply(abstractCar);
    }

    @Override
    public SensorData getData() {
        return new SingleValueData(this.function.apply(abstractCar));
    }
}

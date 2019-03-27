package org.lrima.laop.simulation;

import java.util.ArrayList;

import org.lrima.laop.physic.concreteObjects.SimpleCar;
import org.lrima.laop.simulation.data.CarInfo;

/**
 *  A snapshot of the simulation
 * @author Léonard
 */
public class SimulationSnapshot {
    ArrayList<CarInfo> cars;


    public SimulationSnapshot(){
        cars = new ArrayList<>();
    }

    /**
     * Adds that car to the simulation
     *
     * @param car the car to be added
     */
    public void addCar(SimpleCar car){
        cars.add(new CarInfo(car));
    }

    /**
     * Get the cars in that snapshot
     *
     * @return
     */
    public ArrayList<CarInfo> getCars() {
        return cars;
    }

    public void addCar(CarInfo carInfo) {
        cars.add(carInfo);
    }
}

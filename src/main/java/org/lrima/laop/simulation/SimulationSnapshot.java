package org.lrima.laop.simulation;

import org.lrima.laop.simulation.objects.Car;

import java.util.ArrayList;

/**
 *  A snapshot of the simulation
 *
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
    public void addCar(Car car){
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

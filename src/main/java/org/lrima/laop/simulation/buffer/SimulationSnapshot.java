package org.lrima.laop.simulation.buffer;

import org.lrima.laop.physic.SimpleCar;
import org.lrima.laop.simulation.data.CarData;

import java.util.ArrayList;

/**
 *  A snapshot of the simulation
 * @author Léonard
 */
public class SimulationSnapshot {
    ArrayList<CarData> cars;


    public SimulationSnapshot(){
        cars = new ArrayList<>();
    }

    /**
     * Adds that car to the simulation
     *
     * @param car the car to be added
     */
    public void addCar(SimpleCar car){
        cars.add(new CarData(car));
    }

    /**
     * Get the cars in that snapshot
     *
     * @return
     */
    public ArrayList<CarData> getCars() {
        return cars;
    }

    public void addCar(CarData carData) {
        cars.add(carData);
    }
}

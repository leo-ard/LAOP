package org.lrima.laop.simulation;

import org.lrima.laop.physic.CarControls;
import org.lrima.laop.simulation.sensors.Sensor;

import java.util.ArrayList;

public class Agent {
    ArrayList<Sensor> sensors;
    double reward;

    public Agent(ArrayList<Sensor> sensors, double reward) {
        this.sensors = sensors;
        this.reward = reward;
    }

    public ArrayList<Sensor> getSensors() {
        return sensors;
    }

    public double getReward() {
        return reward;
    }
}

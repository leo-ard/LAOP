package org.lrima.laop.simulation;

import org.lrima.laop.simulation.sensors.Sensor;

import java.util.ArrayList;

/**
 * An agent is an object moving in an environnement
 *
 * @author Leonard
 */
public class Agent {
    ArrayList<Sensor> sensors;
    double reward;

    /**
     * Creates a new agent with sensors <code>sensors</code> and reward <code>reward</code>
     *
     * @param sensors the array of sensors
     * @param reward the reward
     */
    public Agent(ArrayList<Sensor> sensors, double reward) {
        this.sensors = sensors;
        this.reward = reward;
    }

    /**
     * Gets the sensors
     *
     * @return an array of sensor
     */
    public ArrayList<Sensor> getSensors() {
        return sensors;
    }

    /**
     * Gets the reward
     *
     * @return the reward
     */
    public double getReward() {
        return reward;
    }
}

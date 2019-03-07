package org.lrima.laop.simulation;

import java.util.ArrayList;

/**
 * Class that keeps the information necessary to be able to have a timeline
 */
public class SimulationBuffer  {
    /**
     * Arrays of every snapshot at each simulation step
     */
    ArrayList<SimulationSnapshot> snapshots;

    /**
     * Creates a new Buffer
     */
    public SimulationBuffer(){
        snapshots = new ArrayList<>();
    }

    /**
     * Adds this snapshot to the arrays of snapshots
     *
     * @param snapshot
     */
    public void addSnapshot(SimulationSnapshot snapshot) {
        this.snapshots.add(snapshot);
    }

    /**
     * Returns the car at that time step
     *
     * @param time
     * @return the array of cars at that simulation step
     */
    public ArrayList<CarInfo> getCars(int time) {
        return this.snapshots.get(time).getCars();
    }

    /**
     * @return the size of the array
     */
    public double getSize() {
        return this.snapshots.size();
    }
}

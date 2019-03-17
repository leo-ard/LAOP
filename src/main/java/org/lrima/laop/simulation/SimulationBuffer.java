package org.lrima.laop.simulation;

import java.util.ArrayList;

import org.lrima.laop.simulation.listeners.BufferListener;

/**
 * Class that keeps the information necessary to be able to have a timeline
 */
public class SimulationBuffer  {
    /**
     * Arrays of every snapshot at each simulation step
     */
    private ArrayList<SimulationSnapshot> snapshots;
    private ArrayList<BufferListener> bufferListeners;

    /**
     * Creates a new Buffer
     */
    public SimulationBuffer(){
        this.snapshots = new ArrayList<>();
        this.bufferListeners = new ArrayList<>();
    }

    /**
     * Adds this snapshot to the arrays of snapshots
     *
     * @param snapshot
     */
    public void addSnapshot(SimulationSnapshot snapshot) {
        this.snapshots.add(snapshot);
        this.fireNewSnapshot();
    }
    
    /**
     * Notify the listeners that a new snapshot has been added
     * @author Clement Bisaillon
     */
    private void fireNewSnapshot() {
    	for(BufferListener listener : this.bufferListeners) {
    		listener.newSnapshot();
    	}
    }
    
    /**
     * Add a new buffer listener
     * @param listener the listener
     */
    public void addBufferListener(BufferListener listener) {
    	this.bufferListeners.add(listener);
    }

    /**
     * Returns the car at that time step
     *
     * @param time
     * @return the array of cars at that simulation step
     */
    public ArrayList<CarInfo> getCars(int time) {
    	if(this.snapshots.size() > 0) {
    		return this.snapshots.get(time).getCars();
    	}
    	return null;
    }

    /**
     * @return the size of the array
     */
    public double getSize() {
        return this.snapshots.size();
    }
}

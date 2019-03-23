package org.lrima.laop.simulation;

import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.LocatorEx;
import org.lrima.laop.simulation.listeners.BufferListener;
import org.lrima.laop.utils.Action;

/**
 * Class that keeps the information necessary to be able to have a timeline
 * @author Léonard
 */
public class SimulationBuffer  {
    /**
     * Arrays of every snapshot at each simulation step
     */
    private ArrayList<SimulationSnapshot> snapshots;
    private ArrayList<Action<SimulationBuffer>> bufferListeners;

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
        //Notify all listeners
        this.bufferListeners.forEach(snapshotAction -> snapshotAction.handle(this));
    }

    /**
     * Set a new Action when a snapshot is added to the buffer
     */
    public void setOnSnapshotAdded(Action<SimulationBuffer> action){
        bufferListeners.add(action);
    }


    /**
     * Returns the car at that time step
     *
     * @param time
     * @return the array of cars at that simulation step
     */
    public ArrayList<CarInfo> getCars(int time) {
    	if(this.snapshots.size() > 0 && time >= 0) {
    		return this.snapshots.get(time).getCars();
    	}
    	return new ArrayList<>();
    }

    /**
     * @return the size of the array
     */
    public int getSize() {
        return this.snapshots.size();
    }

    public void clear() {
        snapshots = new ArrayList<>();
    }
}

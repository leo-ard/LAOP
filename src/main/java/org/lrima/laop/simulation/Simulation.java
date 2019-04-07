package org.lrima.laop.simulation;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Creates a simulation
 *
 * @author Léonard
 */
public abstract class Simulation {
    protected final SimulationEngine simulationEngine;
    protected ArrayList<Consumer<Simulation>> end;

    /**
     * Creates a simulation
     *
     * @param simulationEngine the simulationEngine to refer to
     */
    public Simulation(SimulationEngine simulationEngine){
        this.simulationEngine = simulationEngine;
        this.end = new ArrayList<>();
    }

    /**
     * Start the simulation
     */
    public abstract void start();

    /**
     * Sets what to do when the simulation is finished. CRUTIAL : used by the SimulationEngine to start the next simulation. If not set properlly, the batch wont continue.
     * @param end the action to do at the end
     */
    public void setEnd(Consumer<Simulation> end) {
        this.end.add(end);
    }

    public abstract void pause();
}

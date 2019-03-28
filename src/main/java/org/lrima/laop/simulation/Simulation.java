package org.lrima.laop.simulation;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Simulation {
    protected final SimulationEngine simulationEngine;
    protected ArrayList<Consumer<Simulation>> end;

    public Simulation(SimulationEngine simulationEngine){
        this.simulationEngine = simulationEngine;
        this.end = new ArrayList<>();
    }

    public abstract void start();

    public void setEnd(Consumer<Simulation> end) {
        this.end.add(end);
    }
}

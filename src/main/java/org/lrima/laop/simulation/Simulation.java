package org.lrima.laop.simulation;

import org.lrima.laop.core.LAOP;
import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.network.carcontrollers.CarController;
import org.lrima.laop.physic.concreteObjects.SimpleCar;
import org.lrima.laop.simulation.sensors.ProximityLineSensor;
import org.lrima.laop.utils.math.Vector2d;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Creates a simulation
 *
 * @author Léonard
 */
public abstract class Simulation <T extends LearningAlgorithm>{
    protected final SimulationEngine simulationEngine;
    protected ArrayList<Consumer<Simulation>> end;
    protected T learningAlgorithm;

    /**
     * Creates a simulation
     *
     * @param simulationEngine the simulationEngine to refer to
     */
    public Simulation(SimulationEngine simulationEngine){
        this.simulationEngine = simulationEngine;
        this.end = new ArrayList<>();
        this.learningAlgorithm = (T) simulationEngine.generateLearningAlgorithm();
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

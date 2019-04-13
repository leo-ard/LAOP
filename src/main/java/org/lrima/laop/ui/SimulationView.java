package org.lrima.laop.ui;

import org.lrima.laop.simulation.Environnement;
import org.lrima.laop.ui.stage.MainSimulationStage;

public abstract class SimulationView<T extends Environnement> {
    T engironnement;

    public SimulationView(T engironnement){
        this.engironnement = engironnement;
    }

    public abstract void setup(MainSimulationStage mainSimulationStage);

    public T getEngironnement() {
        return engironnement;
    }
}

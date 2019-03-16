package org.lrima.laop.simulation.listeners;

public interface BatchListener {
    void simulationFinished();
    void batchFinished();
    void simulationManagerFinished();

    //TODO : implements this one
    void dataReady();
}

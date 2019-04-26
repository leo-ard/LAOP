package org.lrima.laop.network;

import org.lrima.laop.physic.CarControls;
import org.lrima.laop.simulation.Agent;
import org.lrima.laop.simulation.Environnement;



/**
 * Interface to make a learning algorithm
 */
public interface LearningAlgorithm{
    void train(Environnement environnement);
    CarControls test(Agent agent);
}

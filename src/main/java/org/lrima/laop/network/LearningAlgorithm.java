package org.lrima.laop.network;

import org.lrima.laop.physic.CarControls;
import org.lrima.laop.simulation.Agent;
import org.lrima.laop.simulation.Environnement;
import org.lrima.laop.simulation.LearningEngine;


/**
 * Interface to make a learning algorithm
 *
 * @author Leonard
 */
public interface LearningAlgorithm{
    /**
     * Trains the neural network given the environnement and the learning engine
     *
     * @param environnement - the environnement
     * @param learningEngine - the learning engine
     */
    void train(Environnement environnement, LearningEngine learningEngine);

    /**
     * The test method. Used when evaluating the algorithms
     *
     * @param agent the agent to be tested on
     * @return the car controls.
     */
    CarControls test(Agent agent);
}

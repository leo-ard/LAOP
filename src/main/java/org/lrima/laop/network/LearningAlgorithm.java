package org.lrima.laop.network;

import org.lrima.laop.physic.CarControls;
import org.lrima.laop.simulation.Agent;
import org.lrima.laop.simulation.Environnement;



/**
 * Interface to make a learning algorithm
 *
 * @param <T> the type of network accepted by this learning algorithm. See {@link org.lrima.laop.network.concreteLearning.GeneticLearning} for an exemple.
 */
public interface LearningAlgorithm{
    void train(Environnement environnement);
    CarControls test(Agent agent);
}

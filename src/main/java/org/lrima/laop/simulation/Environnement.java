package org.lrima.laop.simulation;

import org.lrima.laop.physic.CarControls;
import org.lrima.laop.ui.Drawable;

/**
 * Creates a new environnement
 *
 * @author Léonard
 */
public interface Environnement extends Drawable {
    /**
     * Returns true if the environnement is finished. False otherwise
     *
     * @return true if the environnement is finished. False otherwise
     */
    boolean isFinished();

    /**
     * Resets the environnement and returns the agents with the values of the sensors
     *
     * @return the agents
     */
    Agent reset();

    /**
     * Does a step in the environnement depending on the car controls
     *
     * @param carControls the car controls
     * @return the agent
     */
    Agent step(CarControls carControls);

    /**
     * Render the environnement
     */
    void render();

    /**
     * Initialise the environnement with the learning engine
     *
     * @param learningEngine the learning engine
     */
    void init(LearningEngine learningEngine);
}

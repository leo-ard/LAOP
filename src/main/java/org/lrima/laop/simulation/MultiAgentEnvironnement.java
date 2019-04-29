package org.lrima.laop.simulation;

import org.lrima.laop.physic.CarControls;

import java.util.ArrayList;

/**
 * Interface used to create new multiagent environnements
 *
 * @author Leonard
 */
public interface MultiAgentEnvironnement extends Environnement  {
    /**
     * Does a step in the environnement, but with multiple agents
     *
     * See {@link Environnement#step(CarControls)}
     *
     * @param agents array of agents
     * @return an array of car controls
     */
    ArrayList<Agent> step(ArrayList<CarControls> agents);

    /**
     * Resets the environnement, but with multiple agents
     *
     * See {@link Environnement#reset()}
     *
     * @param numberOfAgents the number of agents in the simulation
     * @return an array of all the agents
     */
    ArrayList<Agent> reset(int numberOfAgents);

    @Override
    default Agent reset() {
        return reset(1).get(0);
    }

    @Override
    default Agent step(CarControls carControls) {
        ArrayList<CarControls> carControlArray = new ArrayList<>();
        carControlArray.add(carControls);
        return step(carControlArray).get(0);
    }
}

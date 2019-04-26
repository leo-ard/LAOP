package org.lrima.laop.simulation;

import org.lrima.laop.physic.CarControls;

import java.util.ArrayList;

public interface MultiAgentEnvironnement extends Environnement  {
    ArrayList<Agent> step(ArrayList<CarControls> agents);
    ArrayList<Agent> reset(int numberOfCars);

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

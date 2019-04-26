package org.lrima.laop.simulation;

import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.physic.CarControls;
import org.lrima.laop.simulation.data.AlgorithmsData;
import org.lrima.laop.ui.Drawable;

import java.util.ArrayList;

public interface Environnement extends Drawable {
    boolean isFinished();

    Agent reset();
    Agent step(CarControls carControls);

    void render();

    void init(LearningEngine learningEngine);

    AlgorithmsData evaluate(LearningAlgorithm[] trained, int max);
    default void evaluate(LearningAlgorithm learningAlgorithm){
        LearningAlgorithm[] trained = new LearningAlgorithm[]{learningAlgorithm};
        this.evaluate(trained, 1);
    }

    ArrayList<Double> getData();
}

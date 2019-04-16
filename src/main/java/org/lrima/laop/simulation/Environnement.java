package org.lrima.laop.simulation;

import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.physic.CarControls;
import org.lrima.laop.ui.Drawable;

public interface Environnement extends Drawable {
//    <T extends CarController> ArrayList<T> evaluate(ArrayList<T> cars);
//    void parallelEvaluation(Consumer<ArrayList<? extends CarController>> cars);
    boolean isFinished();

    Agent reset();
    Agent step(CarControls carControls);

    void render();

    void init(LearningEngine learningEngine);
    void evaluate(LearningAlgorithm learningAlgorithm);

//    AbstractMap getMap();
//    BiFunction<Environnement, AbstractCar, Double> getFitenessFunction();
//
//    BatchData getBatchData();
//    void setFinished(boolean finished);
}

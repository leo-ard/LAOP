package org.lrima.laop.simulation;

import java.util.function.Consumer;

public enum LearningTypes {
    REAL_TIME((simulation) -> {

    }),
    AT_GEN((simulation) -> {

    });

    private Consumer<Simulation> shouldLearn;
    LearningTypes(Consumer<Simulation> shouldLearn){
        this.shouldLearn = shouldLearn;
    }

    public void setUpListeners(Simulation simulation) {
        shouldLearn.accept(simulation);
    }
}

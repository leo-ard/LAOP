package org.lrima.laop.network;

import org.lrima.laop.simulation.GenerationBasedSimulation;
import org.lrima.laop.simulation.Simulation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LearningAnotation {
    Class<? extends Simulation> simulation() default GenerationBasedSimulation.class;
}

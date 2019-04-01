package org.lrima.laop.network;

import org.lrima.laop.simulation.GenerationBasedSimulation;
import org.lrima.laop.simulation.Simulation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation used to tell the ui witch simulation to use when rendering the cars with that learning algorithm
 *
 * @author Léonard
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface LearningAnotation {
    Class<? extends Simulation> simulation();
}

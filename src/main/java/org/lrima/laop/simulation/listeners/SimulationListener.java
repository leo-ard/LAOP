package org.lrima.laop.simulation.listeners;

/**
 * Used to fire events on simulation changes.
 * For exemple when the a generation ended or when all the generation of a simulation ended.
 * @author Clement Bisaillon
 */
public interface SimulationListener {
	public void allGenerationEnd();
	public void generationEnd();
}

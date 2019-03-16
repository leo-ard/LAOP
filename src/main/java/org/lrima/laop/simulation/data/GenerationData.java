package org.lrima.laop.simulation.data;

/**
 * Used to store the information of a generation such as
 * the average fitness score, the number of car, etc...
 * @author Clement Bisaillon
 */
public class GenerationData {
	
	private int generationNumber;
	
	public GenerationData(int generationNumber) {
		this.generationNumber = generationNumber;
	}
	
	/*
	 * @return the average fitness score of the generation
	 */
	public double getAverageFitness() {
		return 17.3;
	}
}

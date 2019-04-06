package org.lrima.laop.simulation.data;

/**
 * Used to store the information of a generation such as
 * the average fitness score, the number of car, etc...
 * @author Clement Bisaillon and Léonard Oest O'Leary
 */
public class GenerationData {

    private int generationNumber;


	public GenerationData(int generationNumber) {
		this.generationNumber = generationNumber;
	}
	
	/**
	 * @return the average fitness score of the generation
	 */
	public double getAverageFitness() {
		//temporary
		return (Math.random() * 1000) * ((Math.random() > 0.5) ? 1 : -1);
	}
}

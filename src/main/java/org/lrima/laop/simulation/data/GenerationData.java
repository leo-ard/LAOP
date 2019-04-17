package org.lrima.laop.simulation.data;

/**
 * Used to store the information of a generation such as
 * the average fitness score, the number of car, etc...
 * @author Clement Bisaillon
 */
public class GenerationData {

    private int generationNumber;
    private int simulationNumber;
    private double averageFitness;
    private double[] fitnesses;

	public GenerationData(int generationNumber, int simulationNumber, double[] fitnesses) {
		this.generationNumber = generationNumber;
		this.simulationNumber = simulationNumber;
		this.fitnesses = fitnesses;
	}
	
	/**
	 * @return the average fitness score of the generation
	 */
	public double getAverageFitness() {
		return this.averageFitness;
	}
	
	public int getGenerationNumber() {
		return this.generationNumber;
	}

	public void setAverageFitness(double averageFitness) {
		this.averageFitness = averageFitness;
	}
	
	public String getCsvLine() {
		String start = 	this.simulationNumber + "," +
						this.generationNumber + ",";
		
		String fitnessesString = "";
		
		for(int i = 0 ; i < this.fitnesses.length ; i++) {
			fitnessesString += fitnesses[i];
			if(i < this.fitnesses.length - 1) {
				fitnessesString += ",";
			}
		}
		
		return start + fitnessesString;
	}
}

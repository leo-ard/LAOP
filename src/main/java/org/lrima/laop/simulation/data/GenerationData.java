package org.lrima.laop.simulation.data;

import java.util.ArrayList;

import org.lrima.laop.network.genetics.GeneticNeuralNetwork;

/**
 * Used to store the information of a generation such as
 * the average fitness score, the number of car, etc...
 * @author Clement Bisaillon
 */
public class GenerationData {

    private int generationNumber;
    private double averageFitness;

	public GenerationData(int generationNumber) {
		this.generationNumber = generationNumber;
	}
	
	public void setAverageFitness(ArrayList<GeneticNeuralNetwork> cars) {
		double totalFitness = 0;
		for(GeneticNeuralNetwork car : cars) {
			totalFitness += car.getFitness();
		}
		
		this.averageFitness = totalFitness / cars.size();
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
}

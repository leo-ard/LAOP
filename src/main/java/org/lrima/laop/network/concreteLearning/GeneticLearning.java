package org.lrima.laop.network.concreteLearning;

import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.network.genetics.GeneticNeuralNetwork;
import org.lrima.laop.simulation.Environnement;
import org.lrima.laop.utils.math.RandomUtils;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * An implementation of a learning algorithm
 *
 * @author Léonard
 */
public class GeneticLearning implements LearningAlgorithm<GeneticNeuralNetwork> {
    ArrayList<GeneticNeuralNetwork> geneticLearnings;

    @Override
    public ArrayList<GeneticNeuralNetwork> learn(ArrayList<GeneticNeuralNetwork> cars) {
        //sort by best-fitness
        try{
            cars = (ArrayList<GeneticNeuralNetwork>) cars.stream()
                    .sorted((gn1, gn2)-> (int) (gn2.getFitness()-gn1.getFitness()))
                    .collect(Collectors.toList());
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }

        double bestFitness = cars.get(0).getFitness();

        //Keep only 50% best cars
        final int initialNumberOfCar = cars.size();
        ArrayList<GeneticNeuralNetwork> bestPerformingCars = new ArrayList<>();
        for(int i = 0 ; i < cars.size() / 2 ; i++) {
        	bestPerformingCars.add(cars.get(i));
        }
        cars = bestPerformingCars;
        
        //Repopulate
        while(cars.size() < initialNumberOfCar){
            GeneticNeuralNetwork random1 = bestPerformingCars.get(RandomUtils.getInteger(0, bestPerformingCars.size()-1));
            GeneticNeuralNetwork random2 = bestPerformingCars.get(RandomUtils.getInteger(0, bestPerformingCars.size()-1));

            GeneticNeuralNetwork geneticNeuralNetwork = random1.crossOver(random2);

            cars.add(geneticNeuralNetwork);
        }

        return cars;
    }

    @Override
    public void cycle(Environnement environnement) {
        geneticLearnings = environnement.evaluate(geneticLearnings);
        geneticLearnings = learn(geneticLearnings);
    }

}

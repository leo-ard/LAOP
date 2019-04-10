package org.lrima.laop.network.concreteLearning;

import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.network.LearningAnotation;
import org.lrima.laop.network.carcontrollers.CarController;
import org.lrima.laop.network.genetics.GeneticNeuralNetwork;
import org.lrima.laop.physic.abstractObjects.AbstractCar;
import org.lrima.laop.simulation.GenerationBasedSimulation;
import org.lrima.laop.utils.Console;
import org.lrima.laop.utils.math.RandomUtils;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * An implementation of a learning algorithm
 *
 * @author Léonard
 */
@LearningAnotation(simulation = GenerationBasedSimulation.class)
public class GeneticLearning implements LearningAlgorithm<GeneticNeuralNetwork> {

    @Override
    public ArrayList<GeneticNeuralNetwork> learn(ArrayList<GeneticNeuralNetwork> cars) {
    	
        //sort by best-fitness
        cars = (ArrayList<GeneticNeuralNetwork>) cars.stream()
                .sorted((gn1, gn2)-> (int) (gn2.getFitness()-gn1.getFitness()))
                .collect(Collectors.toList());

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
    public void init(ArrayList<AbstractCar> cars) {
        Function<AbstractCar, Double> fitness = ((car) -> {
        	Point2D carPos = new Point2D.Double(car.getPosition().getX(), car.getPosition().getY());
        	return car.getMap().distanceFromStart(carPos);
        });
        cars.forEach(car -> car.setFitnessFunction(fitness));
    }
}

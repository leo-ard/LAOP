package org.lrima.laop.network.FUCONN;

import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.physic.CarControls;
import org.lrima.laop.simulation.Agent;
import org.lrima.laop.simulation.Environnement;
import org.lrima.laop.simulation.LearningEngine;
import org.lrima.laop.simulation.MultiAgentEnvironnement;
import org.lrima.laop.simulation.sensors.Sensor;
import org.lrima.laop.utils.math.RandomUtils;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * An implementation of a learning algorithm
 *
 * @author Léonard
 */
public class GeneticLearning implements LearningAlgorithm{
    ArrayList<FUCONN> geneticNN;
    private int NUMBER_CAR = 100;

    /**
     * Makes the array of cars better by doing the three main phases : evaluation, selection and reproduction
     *
     * @param cars - the array of cars
     * @return a better array of cars
     */
    private ArrayList<FUCONN> learn(ArrayList<FUCONN> cars) {
        //Evaluation : sort by best-fitness
        cars = (ArrayList<FUCONN>) cars.stream()
                .sorted((gn1, gn2)-> {
                    int i = (int)gn2.getFitness()-(int)gn1.getFitness();
                    return i;
                })
                .collect(Collectors.toList());


        //Selection : keep only 50% best cars
        final int initialNumberOfCar = this.NUMBER_CAR;
        ArrayList<FUCONN> bestPerformingCars = new ArrayList<>();
        for(int i = 0 ; i < initialNumberOfCar / 2 ; i++) {
            bestPerformingCars.add(cars.get(i));
        }
        cars = bestPerformingCars;

        //Repopulation : repopulate the set of cars with newly generated ones
        while(cars.size() < initialNumberOfCar){
            FUCONN random1 = bestPerformingCars.get(RandomUtils.getInteger(0, bestPerformingCars.size()-1));
            FUCONN random2 = bestPerformingCars.get(RandomUtils.getInteger(0, bestPerformingCars.size()-1));

            FUCONN geneticNeuralNetwork = random1.crossOver(random2);

            cars.add(geneticNeuralNetwork);
        }

        return cars;
    }

    @Override
    public void train(Environnement env1, LearningEngine learningEngine) {
        MultiAgentEnvironnement env = (MultiAgentEnvironnement) env1;

        geneticNN = new ArrayList<>();

        for (int i = 0; i < this.NUMBER_CAR; i++) {
            FUCONN e = new FUCONN();
            e.init();
            geneticNN.add(e);
        }

        ArrayList<Agent> agents = env.reset(this.NUMBER_CAR);
        while(learningEngine.whileButtonNotPressed()){
            while(!env.isFinished()){
                ArrayList<CarControls> carControls = new ArrayList<>();
                for (int i = 0; i < geneticNN.size(); i++) {
                    geneticNN.get(i).setFitness(agents.get(i).getReward());
                    double[] sensorValues = agents.get(i).getSensors().stream().mapToDouble(Sensor::getValue).toArray();
                    carControls.add(geneticNN.get(i).control(sensorValues));
                }

                agents = env.step(carControls);
                env.render();
            }

            geneticNN = learn(geneticNN);
            learningEngine.evaluate(this);
            agents = env.reset(geneticNN.size());
            env.newMap();
        }
    }

    @Override
    public CarControls test(Agent agent) {
        double[] sensorValues = agent.getSensors().stream().mapToDouble(Sensor::getValue).toArray();
        return geneticNN.get(0).control(sensorValues);
    }
}

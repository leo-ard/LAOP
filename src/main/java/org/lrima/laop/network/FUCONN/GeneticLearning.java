package org.lrima.laop.network.FUCONN;

import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.network.FUCONN.FUCONN;
import org.lrima.laop.physic.CarControls;
import org.lrima.laop.simulation.Agent;
import org.lrima.laop.simulation.Environnement;
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

    public ArrayList<FUCONN> learn(ArrayList<FUCONN> cars) {
        //sort by best-fitness
        cars = (ArrayList<FUCONN>) cars.stream()
                .sorted((gn1, gn2)-> {
                    int i = (int)gn2.getFitness()-(int)gn1.getFitness();
                    return i;
                })
                .collect(Collectors.toList());

        System.out.println(cars.get(0).getFitness());

        //Keep only 50% best cars
        final int initialNumberOfCar = 100;
        ArrayList<FUCONN> bestPerformingCars = new ArrayList<>();
        for(int i = 0 ; i < initialNumberOfCar / 2 ; i++) {
            bestPerformingCars.add(cars.get(i));
        }
        cars = bestPerformingCars;

        //Repopulate
        while(cars.size() < initialNumberOfCar){
            FUCONN random1 = bestPerformingCars.get(RandomUtils.getInteger(0, bestPerformingCars.size()-1));
            FUCONN random2 = bestPerformingCars.get(RandomUtils.getInteger(0, bestPerformingCars.size()-1));

            FUCONN geneticNeuralNetwork = random1.crossOver(random2);

            cars.add(geneticNeuralNetwork);
        }

        return cars;
    }

    @Override
    public void train(Environnement env1) {
        MultiAgentEnvironnement env = (MultiAgentEnvironnement) env1;

        geneticNN = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            FUCONN e = new FUCONN();
            e.init();
            geneticNN.add(e);
        }

        ArrayList<Agent> agents = env.reset(100);
        long time = 0;

        while(true){
            while(!env.isFinished() && time < 2000){
                time++;

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
            time = 0;
            env.evaluate(this);
            agents = env.reset(geneticNN.size());
        }
    }

    @Override
    public CarControls test(Agent agent) {
        double[] sensorValues = agent.getSensors().stream().mapToDouble(Sensor::getValue).toArray();
        return geneticNN.get(0).control(sensorValues);
    }
}

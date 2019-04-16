package org.lrima.laop.network.concreteLearning;

import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.network.concreteNetworks.FUCONN;
import org.lrima.laop.network.genetics.GeneticNeuralNetwork;
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
    ArrayList<GeneticNeuralNetwork> geneticNN;

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
            while(!env.isFinished()){
                time++;

                ArrayList<CarControls> carControls = new ArrayList<>();
                for (int i = 0; i < geneticNN.size(); i++) {
                    geneticNN.get(i).setFitness(agents.get(i).getReward());
                    double[] sensorValues = agents.get(i).getSensors().stream().mapToDouble(Sensor::getValue).toArray();
                    carControls.add(geneticNN.get(i).control(sensorValues));
                }

                agents = env.step(carControls);
                env.render();

                if(time > 300){
                    learn(geneticNN);
                    env.reset(geneticNN.size());
                    time = 0;
                }

                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            time = 0;
            learn(geneticNN);
            env.reset(geneticNN.size());
        }



    }

    @Override
    public CarControls test(Agent agent) {
        return null;
    }
}

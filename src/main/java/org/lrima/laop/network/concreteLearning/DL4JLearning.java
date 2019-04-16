package org.lrima.laop.network.concreteLearning;

import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.network.concreteNetworks.DL4J;
import org.lrima.laop.physic.CarControls;
import org.lrima.laop.physic.abstractObjects.AbstractCar;
import org.lrima.laop.simulation.Agent;
import org.lrima.laop.simulation.Environnement;
import org.lrima.laop.simulation.LearningEngine;
import org.lrima.laop.simulation.sensors.CarSensor;
import org.lrima.laop.simulation.sensors.Sensor;

import java.util.ArrayList;

public class DL4JLearning implements LearningAlgorithm {
    private DL4J dl4J;

    @Override
    public void train(Environnement env) {
        dl4J = new DL4J();
        dl4J.init();
        dl4J.configureListeners(LearningEngine.mainScene);

        Agent agent = env.reset();

        while(true){
            if(env.isFinished())
                env.reset();

            ArrayList<Sensor> sensors = agent.getSensors();
            double[] sensorValues = sensors.stream().mapToDouble(Sensor::getValue).toArray();

            CarControls carControls = dl4J.control(sensorValues);

            agent = env.step(carControls);
            env.render();

            try {
                Thread.sleep((long) (LearningEngine.DELTA_T * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public CarControls test(Agent agent) {
        double[] sensorValues = agent.getSensors().stream().mapToDouble(Sensor::getValue).toArray();
        dl4J.disableHumanControl();
        return dl4J.control(sensorValues);
    }


    public void init(ArrayList<AbstractCar> cars) {
        cars.forEach(car -> car.addSensor(CarSensor.VELOCITY_SENSOR(car)));
    }
}

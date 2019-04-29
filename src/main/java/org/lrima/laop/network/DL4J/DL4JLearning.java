package org.lrima.laop.network.DL4J;

import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.physic.CarControls;
import org.lrima.laop.physic.SimpleCar;
import org.lrima.laop.simulation.Agent;
import org.lrima.laop.simulation.Environnement;
import org.lrima.laop.simulation.LearningEngine;
import org.lrima.laop.simulation.sensors.CarSensor;
import org.lrima.laop.simulation.sensors.Sensor;

import java.util.ArrayList;
public class DL4JLearning implements LearningAlgorithm {
    private DL4J dl4J;

    @Override
    public void train(Environnement env, LearningEngine learningEngine) {
        dl4J = new DL4J();
        dl4J.init();
        dl4J.configureListeners(LearningEngine.mainScene);
        dl4J.setTakeOverMode(DL4J.MODE.DIRECT_INPUT);

        Agent agent = env.reset();
        while(learningEngine.whileButtonNotPressed()){
            if(env.isFinished()){
                learningEngine.evaluate(this);
                dl4J.setTakeOverMode(DL4J.MODE.DIRECT_INPUT);
                dl4J.setAIControl(false);
            }
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
        dl4J.setAIControl(true);
        dl4J.setTakeOverMode(DL4J.MODE.AI_CONTROL);
        return dl4J.control(sensorValues);
    }


    public void init(ArrayList<SimpleCar> cars) {
        cars.forEach(car -> car.addSensor(CarSensor.VELOCITY_SENSOR(car)));
    }
}
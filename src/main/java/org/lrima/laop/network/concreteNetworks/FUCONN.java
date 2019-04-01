package org.lrima.laop.network.concreteNetworks;

import org.lrima.laop.network.genetics.GeneticNeuralNetwork;
import org.lrima.laop.network.nn.NeuralNetwork;
import org.lrima.laop.physic.CarControls;
import org.lrima.laop.settings.LockedSetting;
import org.lrima.laop.utils.MathUtils;
import org.lrima.laop.utils.math.RandomUtils;

/**
 * An implementation of a neural network. This on is of type GeneticNeuralNetwork. It can be trained by a Genetic Learning.
 *
 * @author Léonard
 */
public class FUCONN implements GeneticNeuralNetwork {
    public NeuralNetwork neuralNetwork;

    @Override
    public GeneticNeuralNetwork crossOver(GeneticNeuralNetwork otherGeneticNeuralNetwork) {
        return null;
    }

    @Override
    public void init(LockedSetting settings) {
        neuralNetwork = new NeuralNetwork();
        neuralNetwork.setInputsSize(5);
        neuralNetwork.addDenseLayer(2, MathUtils.LOGISTIC);
        neuralNetwork.addDenseLayer(3, MathUtils.LOGISTIC);
    }

    @Override
    public CarControls control(double... captorValues) {
        double[] data = new double[]{RandomUtils.getDouble(0, 1), 0, RandomUtils.getDouble(-1, 1)};
//        double[] data = neuralNetwork.predict(captorValues);

        return new CarControls(data);
    }
}

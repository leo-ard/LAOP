package org.lrima.laop.network.FUCONN;

import org.lrima.laop.network.nn.NeuralNetwork;
import org.lrima.laop.physic.CarControls;
import org.lrima.laop.utils.MathUtils;
import org.lrima.laop.utils.math.RandomUtils;

/**
 * An implementation of a neural network. This on is of type GeneticNeuralNetwork. It can be trained by a Genetic Learning.
 *
 * @author Léonard
 */
public class FUCONN {
    NeuralNetwork neuralNetwork;
    double fitness;

    public FUCONN crossOver(FUCONN otherGeneticNeuralNetwork) {
        FUCONN other = otherGeneticNeuralNetwork;

        double[] weightOther = other.neuralNetwork.getAllWeights();
        double[] weightThis = this.neuralNetwork.getAllWeights();
        double[] newWeights = new double[weightOther.length];

        for (int i = 0; i < newWeights.length; i++) {
            newWeights[i] = RandomUtils.getBoolean()? weightOther[i] : weightThis[i];

            if(RandomUtils.getDouble(0, 100) > 10){
                newWeights[i] = RandomUtils.getDouble(-5, 5);
            }

        }

        FUCONN newNetwork = new FUCONN();
        newNetwork.neuralNetwork = new NeuralNetwork(this.neuralNetwork.getTopology(), newWeights, new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0});

        return newNetwork;
    }

    public CarControls control(double... captorValues) {
        return new CarControls(neuralNetwork.predict(captorValues));
    }

    public void init() {
        neuralNetwork = new NeuralNetwork(5);
        neuralNetwork.addDenseLayer(2, MathUtils.LOGISTIC);
        neuralNetwork.addDenseLayer(3, MathUtils.LOGISTIC);
    }

    public static void main(String[] args){
        NeuralNetwork neuralNetwork = new NeuralNetwork(5);
        neuralNetwork.addDenseLayer(2, MathUtils.LOGISTIC);
        neuralNetwork.addDenseLayer(3, MathUtils.LOGISTIC);
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return fitness;
    }
}

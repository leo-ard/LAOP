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

    /**
     * Creates a new instance of FUCONN that has similar properties as the this and other.
     *
     * @param otherGeneticNeuralNetwork - the other FUCONN
     * @return a new neural network that has similar proprieties as the two other (this and the one in parameters)
     */
    public FUCONN crossOver(FUCONN otherGeneticNeuralNetwork) {
        FUCONN other = otherGeneticNeuralNetwork;

        //WEIGHTS
        double[] weightOther = other.neuralNetwork.getAllWeights();
        double[] weightThis = this.neuralNetwork.getAllWeights();
        double[] newWeights = new double[weightOther.length];

        for (int i = 0; i < newWeights.length; i++) {
            newWeights[i] = RandomUtils.getBoolean()? weightOther[i] : weightThis[i];

            if(RandomUtils.getDouble(0, 100) > 2){
                newWeights[i] = RandomUtils.getDouble(-5, 5);
            }
        }

        FUCONN newNetwork = new FUCONN();
        newNetwork.neuralNetwork = new NeuralNetwork(this.neuralNetwork.getTopology(), newWeights);

        return newNetwork;
    }

    /**
     * Outputs the value value depending on the captor values
     *
     * @param captorValues - the values of the captors
     * @return the CarControls
     */
    public CarControls control(double... captorValues) {
        return new CarControls(neuralNetwork.predict(captorValues));
    }

    /**
     * Initialize the neural network.
     *
     */
    public void init() {
        neuralNetwork = new NeuralNetwork(5);
        neuralNetwork.addDenseLayer(5, MathUtils.TANH);
        neuralNetwork.addDenseLayer(8, MathUtils.TANH);
        neuralNetwork.addDenseLayer(2, MathUtils.TANH);
    }

    /**
     * Sets the fitness values (value that determines the performance of a car)
     *
     * @param fitness the new value of the fitness
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    /**
     * returns the current fitness values
     *
     * @return the fitness value
     */
    public double getFitness() {
        return fitness;
    }
}

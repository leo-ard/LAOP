package org.lrima.laop.network.nn;

import org.lrima.laop.utils.math.RandomUtils;

import java.util.function.Function;

/**
 * Class that represents a Dense layer of a neural network.
 *
 * @author Léonard
 */
public class DenseLayer implements Layer {
    private double[][] weights;
    private double[] bias;
    private Function<Double, Double> activationFunction;
    int size;


    /**
     * Created a Dense Layer and assign random controls to all its weights and bias.
     *
     * @param size the number of neurons of this layer
     * @param previousLayerSize the previous layer's number of neurons (to adjust the number of weights accordingly)
     * @param activationFunction the activation function of this layer
     */
    public DenseLayer(int size, int previousLayerSize, Function<Double, Double> activationFunction){
        this.size = size;
        this.activationFunction = activationFunction;

        this.weights = new double[this.size][previousLayerSize];
        this.bias = new double[this.size];

        for(int i = 0; i < this.size; i++){
            for(int j = 0; j < previousLayerSize; j++){
                weights[i][j] = getRandomWeight();
            }
            bias[i] = getRandomBias();
        }
    }

    /**
     * Created a Dense layer by attributing its weights and bias
     *
     * @param weights A matrix of weights. Its size becomes the size of this layer. The first column is the current neuron and the rows are all the connections to the neurons.
     * @param bias A vector of all the biases at each neuron
     * @param activationFunction the activation function
     */
    public DenseLayer(double[][] weights, double[] bias, Function<Double, Double> activationFunction){
        this.weights = weights;
        this.bias = bias;
        this.activationFunction = activationFunction;
        this.size = this.weights.length;
    }


    /**
     *
     * @return a random bias
     */
    private double getRandomBias() {
        return RandomUtils.getDouble(-5, 5);
    }

    /**
     *
     * @return a random weight
     */
    private double getRandomWeight() {
        return RandomUtils.getDouble(-5, 5);
    }

    /**
     * Feed foward the data using the weighted sum.
     *
     * @param data the data of the previous layer or the inputs
     * @return the data of all the outputings nodes
     */
    @Override
    public double[] feedFoward(double[] data) {
        double[] output = new double[this.size];

        for(int i = 0; i < this.size; i++){
            output[i] = getWeightedSum(i, data);
        }

        return output;
    }

    /**
     * Calculates the weithed sum of the neuron at index <code>neuron</code> with weights <code> weights</code>
     *
     * @param neuron the index of the current neuron being calculated
     * @param weights the weights of the current neuron
     * @return
     */
    private double getWeightedSum(int neuron, double[] weights) {
        double sum = 0;
        for(int i = 0; i < this.weights[neuron].length; i++){
            sum += this.weights[neuron][i] * weights[i];
        }

        sum += bias[neuron];

        sum = activationFunction.apply(sum);

        return sum;
    }

    @Override
    public int size() {
        return size;
    }

    public double[][] getWeights() {
        return weights;
    }

    public double[][] getWeightsAndBias() {
        double[][] weightsAndBias = new double[this.size][weights[0].length+1];

        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                weightsAndBias[i][j] = this.weights[i][j];
            }
            weightsAndBias[i][this.weights[i].length] = bias[i];
        }

        return weightsAndBias;
    }

    public double[] getBias() {
        return bias;
    }
}

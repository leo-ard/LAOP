package org.lrima.laop.network.nn;

import org.lrima.laop.utils.MathUtils;
import org.lrima.laop.utils.math.RandomUtils;

import java.util.Arrays;
import java.util.function.Function;

/**
 * Class that represents a Dense layer of a neural network.
 *
 * @author Léonard
 */
public class DenseLayer implements Layer {
    private double[][] weights;
    private Function<Double, Double> activationFunction;
    private int size;


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

        this.weights = new double[this.size][previousLayerSize + 1];

        for(int i = 0; i < this.size;i++){
            for(int j = 0; j < previousLayerSize; j++){
                weights[i][j] = getRandomWeight();
            }
        }
    }

    /**
     * Created a Dense layer by attributing its weights and bias
     *
     * @param weights A matrix of weights. Its size becomes the size of this layer. The first column is the current neuron and the rows are all the connections to the neurons.
     * @param activationFunction the activation function
     */
    public DenseLayer(double[][] weights, Function<Double, Double> activationFunction){
        this.weights = weights;
        this.activationFunction = activationFunction;
        this.size = this.weights.length;
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
     * @param neuronValues the weights of the current neuron
     * @return
     */
    private double getWeightedSum(int neuron, double[] neuronValues) {
        double sum = 0;
        for(int i = 0; i < neuronValues.length; i++){
            sum += this.weights[neuron][i] * neuronValues[i];
        }

        //BIAS
        sum += this.weights[neuron][neuronValues.length];

        sum = activationFunction.apply(sum);

        return sum;
    }

    @Override
    public int size() {
        return weights.length;
    }

    public double[][] getWeights() {
        return weights;
    }

    public static void main(String[] args){
        NeuralNetwork neuralNetwork = new NeuralNetwork(5);
        neuralNetwork.addDenseLayer(2, MathUtils.LOGISTIC);
        neuralNetwork.addDenseLayer(3, MathUtils.LOGISTIC);

        System.out.println(Arrays.toString(neuralNetwork.predict(new double[]{0, 1, 2, 3, 4})));
        System.out.println(Arrays.toString(neuralNetwork.getAllWeights()));
        System.out.println(neuralNetwork.getAllWeights().length);

        System.out.println(Arrays.toString(neuralNetwork.getTopology()));
        NeuralNetwork neuralNetwork1 = new NeuralNetwork(neuralNetwork.getTopology(), neuralNetwork.getAllWeights());
        System.out.println(Arrays.toString(neuralNetwork1.getAllWeights()));
    }

}

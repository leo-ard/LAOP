package org.lrima.laop.network.nn;

import org.lrima.laop.utils.MathUtils;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * Creates a neural network containing a certain number of layers.
 */
public class NeuralNetwork {
    ArrayList<Layer> layers;
    int inputSize;

    /**
     * Created the neural network with no layers.
     *
     * @param inputSize the size of the inputs
     */
    public NeuralNetwork(int inputSize){
        this.inputSize = inputSize;
        layers = new ArrayList<>();
    }

    /**
     * Predicts a certain value for that input.
     *
     * @param input the inputs (an array that contains a value for each node input)
     * @return the prediction. The size depends on the size of the last layer.
     */
    public double[] predict(double[] input){
        double[] tempData = input;
        for (Layer layer : layers) {
            tempData = layer.feedFoward(tempData);
        }

        return tempData;
    }

    /**
     *
     * @return all the layers
     */
    public ArrayList<Layer> getLayers() {
        return layers;
    }

    /**
     * Add a Dense layer. Automatically assign it the right previous layer size.
     *
     * @param size the size of the current layer.
     * @param activationFunction the activation function of this layer
     */
    public void addDenseLayer(int size, Function<Double, Double> activationFunction){
        if(inputSize == 0)
            System.err.print("INPUT SIZE NOT SET");
        if(layers.size() == 0){
            layers.add(new DenseLayer(size, inputSize, activationFunction));
        }
        else{
            layers.add(new DenseLayer(size, getLastLayer().size(), activationFunction));
        }
    }

    /**
     * @return the last layer created
     */
    private Layer getLastLayer() {
        return layers.get(layers.size()-1);
    }

    /**
     * Main class for testing purposes.
     *
     * @param args no args
     */
    public static void main(String[] args){
        NeuralNetwork neuralNetwork = new NeuralNetwork(3);

        double[][] weights = new double[][]{
                {2, -1, 3},
                {1, 3, -2},
                {5, 2, -4},
                {3, 2, 1}
        };

        double[] bias = new double[]{
                2, 3, 4, 1
        };

        double[] inputs = new double[]{2, 2, 2};

        DenseLayer layer = new DenseLayer(weights, bias, MathUtils.LOGISTIC);

        neuralNetwork.getLayers().add(layer);

        double[] predictionNN = neuralNetwork.predict(inputs);

        double[] prediction = new double[4];
        for(int i = 0; i < weights.length; i++){
            double sum = 0;
            for(int j = 0; j < weights[i].length; j++){
                sum += weights[i][j] * inputs[j];
            }
            sum += bias[i];

            prediction[i] = MathUtils.LOGISTIC.apply(sum);
        }

        for (double v : prediction) {
            System.out.print(v + "\t");
        }
        System.out.println();
        for (double v : predictionNN) {
            System.out.print(v + "\t");
        }


    }
}

package org.lrima.laop.network.nn;

import org.lrima.laop.utils.MathUtils;
import org.lrima.laop.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.Arrays;
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

    public NeuralNetwork(int[] topology, double[] weights, double[] bias){
        this.inputSize = topology[0];

        this.layers = new ArrayList<>();
        double[][][] allweghts = NetworkUtils.remap(topology, weights);

        for (double[][] allweght : allweghts) {
            this.layers.add(new DenseLayer(allweght, bias, MathUtils.LOGISTIC));
        }

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
     * Returns all the weights of the network
     *
     */
    public double[] getAllWeights(){
        double[][][] weights = new double[this.layers.size()][][];

        for (int i = 0; i < layers.size(); i++) {
            if(! (layers.get(i) instanceof DenseLayer)){
                System.err.println("TO USE THE GETALLWEIGHTS FUNCTION OF THE NEURAL NETWORK, ALL THE LAYERS MUST ME DENSE LAYERS");
                return null;
            }
            DenseLayer denseLayer = (DenseLayer) layers.get(i);

            weights[i] = denseLayer.getWeights();
        }

        return NetworkUtils.flatArray(weights);
    }

    public int[] getTopology() {
        int[] topology = new int[this.layers.size()+1];

        topology[0] = this.inputSize;

        for (int i = 0; i < layers.size(); i++) {
            topology[i+1] = layers.get(i).size();
        }

        return topology;
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

        double[][][] expected = new double[1][][];
        for (int i = 0; i < neuralNetwork.layers.size(); i++) {
            expected[i] = ((DenseLayer)neuralNetwork.getLayers().get(i)).getWeights();
        }

        double[] weights1 = neuralNetwork.getAllWeights();
        int[] topo = neuralNetwork.getTopology();


        System.out.println(Arrays.toString(topo));
        double[][][] test = NetworkUtils.remap(topo, weights1);


        for (double[][] doubles : test) {
            for (double[] aDouble : doubles) {
                System.out.println(Arrays.toString(aDouble));
            }
        }

        for (double[][] doubles : expected) {
            for (double[] aDouble : doubles) {
                System.out.println(Arrays.toString(aDouble));
            }
        }



        /*
        for (double v : prediction) {
            System.out.print(v + "\t");
        }
        System.out.println();
        for (double v : predictionNN) {
            System.out.print(v + "\t");
        }*/


    }
}

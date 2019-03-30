package org.lrima.laop.network.nn;

import org.lrima.laop.utils.math.RandomUtils;

import java.util.function.Function;

public class DenseLayer implements Layer {
    private double[][] weights;
    private double[] bias;
    private Function<Double, Double> activationFunction;
    int size;

    public DenseLayer(int size, Layer previousLayer, Function<Double, Double> activationFunction){
        this(size, previousLayer.size(), activationFunction);

    }

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

    public DenseLayer(double[][] weights, double[] bias, Function<Double, Double> activationFunction){
        this.weights = weights;
        this.bias = bias;
        this.activationFunction = activationFunction;
        this.size = this.weights.length;
    }


    private double getRandomBias() {
        return RandomUtils.getDouble(-5, 5);
    }

    private double getRandomWeight() {
        return RandomUtils.getDouble(-5, 5);
    }

    @Override
    public double[] feedFoward(double[] data) {
        double[] output = new double[this.size];

        for(int i = 0; i < this.size; i++){
            output[i] = getWeightedSum(i, data);
        }

        return output;
    }

    private double getWeightedSum(int layer, double[] data) {
        double sum = 0;
        for(int i = 0; i < weights[layer].length; i++){
            sum += weights[layer][i] * data[i];
        }

        sum += bias[layer];

        sum = activationFunction.apply(sum);

        return sum;
    }

    @Override
    public int size() {
        return size;
    }
}

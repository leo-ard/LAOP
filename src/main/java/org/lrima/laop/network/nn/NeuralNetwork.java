package org.lrima.laop.network.nn;

import org.lrima.laop.network.carcontrollers.CarController;
import org.lrima.laop.physic.CarControls;
import org.lrima.laop.utils.MathUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class NeuralNetwork {
    ArrayList<Layer> layers;
    int inputSize;

    public NeuralNetwork(){
        layers = new ArrayList<>();
    }

    public double[] predict(double[] input){
        double[] tempData = input;
        for (Layer layer : layers) {
            tempData = layer.feedFoward(tempData);
        }

        return tempData;
    }

    public ArrayList<Layer> getLayers() {
        return layers;
    }

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

    private Layer getLastLayer() {
        return layers.get(layers.size()-1);
    }

    public void setInputsSize(int i) {
        inputSize = i;
    }

    //test
    public static void main(String[] args){
        NeuralNetwork neuralNetwork = new NeuralNetwork();
        neuralNetwork.setInputsSize(3);

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

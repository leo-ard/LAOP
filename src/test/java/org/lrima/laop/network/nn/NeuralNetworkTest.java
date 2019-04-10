package org.lrima.laop.network.nn;

import org.junit.jupiter.api.Test;

/**
 * @author Léonard
 */
public class NeuralNetworkTest {
    private double floatError = 0.00001;


    @Test
    public void firstTest(){
        //DO NOT WORK CHECK TEST IN NEURAL NETWORK METHOD
        /*
        NeuralNetwork neuralNetwork = new NeuralNetwork();
        neuralNetwork.setInputsSize(3);

        double[][] weights = new double[][]{
                {2, -1, 3},
                {1, 3, -2},
                {5, 2, -4},
                {0, 3, 2}
        };

        double[] bias = new double[]{
                2, 3, 4, 5
        };

        double[] inputs = new double[]{1, 2, 3};

        DenseLayer layer = new DenseLayer(weights, bias, MathUtils.LOGISTIC);

        neuralNetwork.getLayers().add(layer);

        double[] predictionNN = neuralNetwork.predict(inputs);

        double[] prediction = new double[3];
        for(int i = 0; i < weights.length; i++){
            double sum = 0;
            for(int j = 0; j < weights[i].length; j++){
                sum += weights[i][j] * inputs[i];
            }
            sum += bias[i];

            prediction[i] = sum;
        }

        Assert.assertEquals(prediction, predictionNN);
        */

    }

}
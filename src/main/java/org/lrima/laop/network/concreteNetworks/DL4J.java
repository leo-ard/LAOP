package org.lrima.laop.network.concreteNetworks;

import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.lrima.laop.core.LAOP;
import org.lrima.laop.network.concreteLearning.DL4JLearning;
import org.lrima.laop.physic.CarControls;
import org.lrima.laop.settings.LockedSetting;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Sgd;

import java.util.ArrayList;

public class DL4J implements DL4JNN {
    private MultiLayerNetwork network;

    public CarControls control(double... captorValues) {
//        System.out.println(network(Nd4j.create(captorValues)));


        return new CarControls();
    }


    public void init(LockedSetting settings) {
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .weightInit(WeightInit.XAVIER)
                .activation(Activation.RELU)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(new Sgd(0.05))
                .list()
                .layer(0, new DenseLayer.Builder().nIn(5).nOut(5).build())
                .layer(1, new OutputLayer.Builder().nIn(5).nOut(3).build())
                .backprop(true)
                .build();

        network = new MultiLayerNetwork(conf);
        network.init();



    }
}

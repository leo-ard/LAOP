package org.lrima.laop.network.concreteNetworks;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.lrima.laop.network.carcontrollers.CarController;
import org.lrima.laop.network.carcontrollers.ManualCarController;
import org.lrima.laop.physic.CarControls;
import org.lrima.laop.settings.LockedSetting;
import org.lrima.laop.utils.MathUtils;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;

public class DL4J extends ManualCarController implements DL4JNN {
    private MultiLayerNetwork network;
    private INDArray features;
    private INDArray labels;

    private MODE takeOverMode = MODE.WAIT_FOR_INPUT;
    private MODE oldTakeOverMode = MODE.AI_CONTROL;

    private boolean disableHumanControls;
    private double fitness;


    public CarControls control(double... captorValues) {
        if(disableHumanControls)
            takeOverMode = MODE.AI_CONTROL;
        if(oldTakeOverMode != takeOverMode) {
            System.out.println("SWITCH TO " + takeOverMode);
        }

        if(MathUtils.arrayEquals(captorValues, 0)) {
            return CarControls.IDENTITY;
        }

        INDArray captor = Nd4j.create(captorValues);
        MODE tempMode = takeOverMode;
        CarControls carControls = super.control(captorValues);

        //ALL MODES

        if(takeOverMode == MODE.WAIT_FOR_INPUT) {
            if (controls[0] == 0 && controls[1] == 0 && controls[2] == 0 && controls[3] == 0) {
                tempMode = MODE.AI_CONTROL;
            }else{
                tempMode = MODE.DIRECT_INPUT;
            }
        }

        if(tempMode == MODE.DIRECT_INPUT){
            INDArray expected = Nd4j.create(MathUtils.convertToDoubleArray(this.controls));
            this.addToData(captor, expected);
            network.fit(captor, expected);
        }
        else if(tempMode == MODE.AI_CONTROL){
            double[] output = network.output(captor).toDoubleVector();
            carControls = super.getControls(MathUtils.convertToIntArray(output));
        }
        else if(tempMode == MODE.LEARNING_FROM_DIRECT_INPUT){
            INDArray expected = Nd4j.create(this.controls);
            network.fit(captor, expected);

            double[] output = network.output(captor).toDoubleVector();
            carControls = super.getControls(MathUtils.convertToIntArray(output));
        }
        if(tempMode == MODE.RECORD_DATA){
            INDArray expected = Nd4j.create(MathUtils.convertToDoubleArray(this.controls));
            this.addToData(captor, expected);

        }
        else if(tempMode == MODE.TRAIN_ON_RECORDED_DATA){
            network.fit(features, labels);
            this.takeOverMode = MODE.AI_CONTROL;
        }
        else if(tempMode == MODE.EXPORT_DATA){
            DataSet dataSet = new DataSet(features, labels);
            File f = new File("save");
            int i = 0;
            while(f.exists()){
                f = new File("save" + i);
                i++;
            }

            dataSet.save(f);
            this.takeOverMode = MODE.AI_CONTROL;
        }
        else if(tempMode == MODE.LOAD_DATA){
            DataSet dataSet = new DataSet();

            File f = new File("save");
            int i = 0;
            while(f.exists()){
                System.out.println("loading... " + f.getName());
                dataSet.load(f);
                this.addToData(dataSet.getFeatures(), dataSet.getLabels());

                f = new File("save" + i);
                i++;
            }

            this.takeOverMode = MODE.AI_CONTROL;
        }


        this.oldTakeOverMode = takeOverMode;

//        System.out.println(carControls);

        return carControls;

    }

    private void addToData(INDArray feature, INDArray label){
        if(features.rows() == 0){
            features = feature;
            labels = label;
        }
        else{
            features = Nd4j.vstack(features, feature);
            labels = Nd4j.vstack(labels, label);
        }
    }

    public void init(LockedSetting settings) {
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .weightInit(WeightInit.XAVIER)
                .activation(Activation.RELU)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(new Sgd(0.05))
                .list()
                .layer(0, new DenseLayer.Builder().nIn(5).nOut(3).build())
                .layer(1, new DenseLayer.Builder().nOut(10).build())
                .layer(2, new OutputLayer.Builder().nOut(4).lossFunction(LossFunctions.LossFunction.SQUARED_LOSS).build())
                .backprop(true)
                .build();

        network = new MultiLayerNetwork(conf);
        network.init();

        features = Nd4j.zeros(0, 5);
        labels = Nd4j.zeros(0, 4);
    }

    @Override
    public void configureListeners(Stage mainScene) {
        super.configureListeners(mainScene);

        EventHandler<? super KeyEvent> onKeyPressed = mainScene.getScene().getOnKeyPressed();
        mainScene.getScene().setOnKeyPressed(event->{
            onKeyPressed.handle(event);
            if(event.getCode() == KeyCode.E){
                this.takeOverMode = MODE.EXPORT_DATA;
            }
            else if(event.getCode() == KeyCode.R){
                this.takeOverMode = MODE.RECORD_DATA;
            }
            else if(event.getCode() == KeyCode.L){
                this.takeOverMode = MODE.LOAD_DATA;
            }
            else if(event.getCode() == KeyCode.Q){
                this.takeOverMode = MODE.WAIT_FOR_INPUT;
            }
            else if(event.getCode() == KeyCode.T){
                this.takeOverMode = MODE.TRAIN_ON_RECORDED_DATA;
            }
        });

        /*EventHandler<? super KeyEvent> onKeyReleased = mainScene.getScene().getOnKeyReleased();
        mainScene.getScene().setOnKeyReleased(event->{
            onKeyReleased.handle(event);

        });*/
    }

    @Override
    public void disableHumanControl() {
        disableHumanControls = true;
    }

    @Override
    public <T extends CarController> T copy() {
        DL4J dl4J = new DL4J();

        dl4J.network = this.network.clone();
        return (T) dl4J;
    }

    @Override
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public double getFitness() {
        return fitness;
    }

    private enum MODE {
        WAIT_FOR_INPUT,
        AI_CONTROL,
        DIRECT_INPUT,
        LEARNING_FROM_DIRECT_INPUT,
        RECORD_DATA,
        TRAIN_ON_RECORDED_DATA,
        EXPORT_DATA,
        LOAD_DATA;
    }
}

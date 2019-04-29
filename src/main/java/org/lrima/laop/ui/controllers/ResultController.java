package org.lrima.laop.ui.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.layout.BorderPane;
import org.lrima.laop.simulation.data.AlgorithmData;
import org.lrima.laop.ui.components.ChartPanel;

public class ResultController implements Initializable {

	AlgorithmData learning;
	AlgorithmData training;

	@FXML
	JFXComboBox<String> chartCbBox;
	@FXML
	JFXComboBox<String> dataTypeCbBox;

	@FXML
	BorderPane chartPane;

	HashMap<String, Chart> charts;


	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		getDataBtn.setOnMouseClicked(event -> this.data.toCsv("learning"));
	}
	
	public void initData(AlgorithmData learning, AlgorithmData training) {
		this.learning = learning;
		this.training = training;


		String keyLearning = "learning";
		String keyTraining = "training";

		dataTypeCbBox.getItems().addAll(keyLearning, keyTraining);

		charts = new HashMap<>();

		for (Map.Entry<String, ArrayList<Double>> en : this.learning.getData().entrySet()) {

            NumberAxis xAxis = new NumberAxis();
            NumberAxis yAxis = new NumberAxis();

            xAxis.setLowerBound(1);
            xAxis.setUpperBound(en.getValue().size());
            xAxis.setTickUnit(1);

            double max = 0;
            for (Double val : en.getValue()) {
                max = Math.max(max, val);
            }

            yAxis.setLowerBound(0);
            yAxis.setUpperBound(max);
            yAxis.setTickUnit(max/10);



			Chart currentChart = new LineChart<>(xAxis, yAxis);
		}






	}

}

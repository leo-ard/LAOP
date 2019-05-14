package org.lrima.laop.ui.controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.lrima.laop.simulation.data.AlgorithmData;
import org.lrima.laop.ui.I18n;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * javafx controller for the result page
 *
 * @author Léonard
 */
public class ResultController {

	AlgorithmData learning;
	AlgorithmData training;

	@FXML
	JFXComboBox<String> chartCbBox;
	@FXML
    Label chartLabel;

	@FXML
	BorderPane chartPane;

	HashMap<String, Chart> charts;

    /**
     * Initialize the page with the values algorithmData and trainingData
     *
     * @param learning the learning data
     * @param training the training data
     */
	public void initData(AlgorithmData learning, AlgorithmData training) {
		this.learning = learning;
		this.training = training;

		charts = new HashMap<>();

		add(this.learning, "learning");
		add(this.training, "training");

		chartCbBox.getItems().addAll(charts.keySet());
		chartCbBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
		    chartPane.setCenter(charts.get(newVal));
        });

		chartCbBox.getSelectionModel().select((String) charts.keySet().toArray()[0]);

        I18n.bind(chartLabel);
	}

	private void add(AlgorithmData algorithmData, String name){
        for (Map.Entry<String, ArrayList<Double>> en : algorithmData.getData().entrySet()) {
            NumberAxis xAxis = new NumberAxis();
            NumberAxis yAxis = new NumberAxis();

            xAxis.setLowerBound(1);
            xAxis.setUpperBound(en.getValue().size());
            xAxis.setTickUnit(1);

            double max = 0;
            for (Double val : en.getValue()) {
                max = Math.max(max, val);
            }

            ArrayList<XYChart.Data<Number, Number>> data = new ArrayList<>();
            for (int i = 0; i < en.getValue().size(); i++) {
                data.add(new XYChart.Data<>(i+1, en.getValue().get(i)));
            }

            yAxis.setLowerBound(0);
            yAxis.setUpperBound(max);
            yAxis.setTickUnit(max/10);

            LineChart currentChart = new LineChart<>(xAxis, yAxis);
            currentChart.setTitle(en.getKey());
            XYChart.Series<Number, Number> serie = new XYChart.Series<>();
            serie.getData().addAll(data);
            currentChart.getData().add(serie);

            charts.put(name + " - " + en.getKey(), currentChart);
        }
    }

}

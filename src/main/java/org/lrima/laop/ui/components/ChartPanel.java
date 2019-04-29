package org.lrima.laop.ui.components;

import javafx.application.Platform;

import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.lrima.laop.simulation.data.AlgorithmData;

import java.util.ArrayList;

/**
 * Panel to show to progression of the current simulation.
 * This panel shows a chart displaying information about the average fitness
 * of the cars by the generation number
 * @author Clement Bisaillon
 */
public class ChartPanel extends HBox {
	private NumberAxis xAxis;
	private NumberAxis yAxis;
	private LineChart<Number, Number> chart;
	
	private XYChart.Series<Number, Number> averageFitnessSerie;
	
	public ChartPanel() {
		this.setPadding(new Insets(0));
		this.setPrefHeight(300);
		HBox.setHgrow(this, Priority.ALWAYS);
		
		this.getStyleClass().add("panel");

		this.setupChart();
	}
	
	/**
	 * Creates the axis and configure the chart
	 */
	private void setupChart() {
		this.xAxis = new NumberAxis("Generation", 1, 0, 1);
		this.yAxis = new NumberAxis("Score", 0, 500, 100);
		this.chart = new LineChart<>(xAxis, yAxis);
		
		this.chart.setTitle("Fitness score by generation");
		this.chart.setCreateSymbols(false);
		this.chart.setLegendSide(Side.RIGHT);
		ChartPanel.setHgrow(chart, Priority.ALWAYS);

		//Average fitness serie
		this.averageFitnessSerie = new XYChart.Series<>();
		this.averageFitnessSerie.setName("Average fitness");
		this.chart.getData().add(this.averageFitnessSerie);

		this.getChildren().add(this.chart);
		
	}

	public void link(AlgorithmData algorithmData){
	    algorithmData.setOnAddValue((scope) ->{
	        ArrayList<Double> d = algorithmData.getData().get(scope);
	        updateChartData(d, scope);
        });
    }

	public void updateChartData(ArrayList<Double> values, String name) {
	    this.averageFitnessSerie.getData().clear();

		//Add new data to the series from the past generation
		Platform.runLater(() -> {
		    ArrayList<XYChart.Data<Number, Number>> data = new ArrayList<>();
            for (int i = 0; i < values.size(); i++) {
                data.add(new XYChart.Data<>(i+1, values.get(i)));
            }

			double max = 0;
            for (Double value : values) {
                max = Math.max(max, value);
            }

            this.yAxis.setTickUnit(max/10);

			this.yAxis.setLowerBound(0);
			this.yAxis.setUpperBound(max);
			this.xAxis.setUpperBound(values.size());

			this.chart.setTitle(name);

			this.averageFitnessSerie.getData().addAll(data);
		});
	}
}

package org.lrima.laop.ui.components;

import javafx.application.Platform;
import org.lrima.laop.simulation.SimulationEngine;
import org.lrima.laop.simulation.data.GenerationData;

import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

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
	private double maxY;
	private double minY;
	
	private XYChart.Series<Number, Number> averageFitnessSerie;
	
	public ChartPanel() {
		this.maxY = 0;
		this.minY = 0;
		
		this.setPadding(new Insets(10, 10, 10, 10));
		
		this.setPrefHeight(200);
		HBox.setHgrow(this, Priority.ALWAYS);
		
		this.getStyleClass().add("panel");

//		simulation.setOnGenerationFinish( (sim) -> {
//			this.updateChartData(sim.getGenerationData());
//		});
//		simulation.setOnSimulationFinish((sim) -> {
//			this.resetChart();
//		});

		this.setupChart();
	}
	
	/**
	 * Creates the axis and configure the chart
	 */
	private void setupChart() {
		this.xAxis = new NumberAxis("Generation", 1, 0, 1);
		this.yAxis = new NumberAxis("Score", 0, 500, 100);
		this.chart = new LineChart<>(xAxis, yAxis);
		
//		this.chart.setTitle("Fitness score by generation");
//		this.chart.setCreateSymbols(false);
		this.chart.setLegendSide(Side.RIGHT);
		ChartPanel.setHgrow(chart, Priority.ALWAYS);

		//Average fitness serie
		this.averageFitnessSerie = new XYChart.Series<>();
		this.averageFitnessSerie.setName("Average fitness");
		this.chart.getData().add(this.averageFitnessSerie);
		
		this.getChildren().add(this.chart);
		
	}

	public void resetChart() {
		//Reset the series and the generation count
		this.averageFitnessSerie.getData().clear();
		this.maxY = 0;
		this.minY = 0;
		
		this.yAxis.setLowerBound(this.minY);
		this.yAxis.setUpperBound(this.maxY);
	}

	public void updateChartData(GenerationData pastGeneration) {
		//Add new data to the series from the past generation
		double averageFitnessScore = pastGeneration.getAverageFitness();
		int generationNumber = pastGeneration.getGenerationNumber();
		Platform.runLater(() -> {
			XYChart.Data<Number, Number> data = new XYChart.Data(generationNumber, averageFitnessScore);
			
			//Change the bounds of the chart
			if(averageFitnessScore > this.maxY) {
				this.maxY = averageFitnessScore;
			}
			else if(averageFitnessScore < this.minY) {
				this.minY = averageFitnessScore;
			}
			
			this.yAxis.setLowerBound(this.minY);
			this.yAxis.setUpperBound(this.maxY);
			this.xAxis.setUpperBound(generationNumber);

			this.averageFitnessSerie.getData().add(data);
		});
	}
}

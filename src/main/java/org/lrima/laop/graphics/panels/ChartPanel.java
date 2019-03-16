package org.lrima.laop.graphics.panels;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.lrima.laop.simulation.SimulationModel;
import org.lrima.laop.simulation.listeners.BatchListener;
import org.lrima.laop.simulation.listeners.SimulationListener;

import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Panel to show to progression of the current simulation.
 * This panel shows a chart displaying information about the average fitness
 * of the cars by the generation number
 * @author Clement Bisaillon
 */
public class ChartPanel extends HBox implements SimulationListener {

	private NumberAxis xAxis;
	private NumberAxis yAxis;
	private LineChart<Number, Number> chart;
	private Pane parentPane;
	private SimulationModel simulation;
	
	private XYChart.Series<Number, Number> averageFitnessSerie;
	
	public ChartPanel(Pane parentPane) {
		this.parentPane = parentPane;
		
		this.setPadding(new Insets(10, 10, 10, 10));
		setStyle( "-fx-background-color: rgb(255, 255, 255, 0.5)");
		
		this.setPrefHeight(200);
		
		this.setupChart();
	}
	
	/**
	 * Creates the axis and configure the chart
	 */
	private void setupChart() {
		
		
		this.xAxis = new NumberAxis("Generation", 0, 10, 1);
		this.yAxis = new NumberAxis("Score", 0, 500, 100);
		this.chart = new LineChart<>(xAxis, yAxis);
		
		this.chart.setTitle("Fitness score by generation");
		this.chart.setCreateSymbols(false);
		this.chart.setLegendSide(Side.RIGHT);
		//Chart always take full width
		this.chart.prefWidthProperty().bind(parentPane.widthProperty());
		
		//Average fitness serie
		this.averageFitnessSerie = new XYChart.Series<>();
		this.averageFitnessSerie.setName("Average fitness");
		this.averageFitnessSerie.getData().add(new XYChart.Data<Number, Number>(0, 20));
		this.averageFitnessSerie.getData().add(new XYChart.Data<Number, Number>(1, 50));
		this.averageFitnessSerie.getData().add(new XYChart.Data<Number, Number>(2, 100));
		this.averageFitnessSerie.getData().add(new XYChart.Data<Number, Number>(3, 200));
		this.averageFitnessSerie.getData().add(new XYChart.Data<Number, Number>(4, 240));
		this.averageFitnessSerie.getData().add(new XYChart.Data<Number, Number>(5, 230));
		this.chart.getData().add(this.averageFitnessSerie);
		
		this.getChildren().add(this.chart);
		
	}

	@Override
	public void allGenerationEnd() {
		//1. reset the chart
	}

	@Override
	public void generationEnd() {
		//1. get the data of the past generation
		//2. add a new data to the serie
		//
	}
}

package org.lrima.laop.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.lrima.laop.simulation.data.AlgorithmsData;

public class ResultController implements Initializable {

	@FXML JFXButton getDataBtn;
	AlgorithmsData data;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getDataBtn.setOnMouseClicked(event -> this.data.toCsv());
	}
	
	public void initData(AlgorithmsData data) {
		this.data = data;
	}

}

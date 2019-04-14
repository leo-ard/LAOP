package org.lrima.laop.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.lrima.laop.simulation.data.ResultData;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class ResultController implements Initializable {

	@FXML JFXButton getDataBtn;
	ResultData data;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getDataBtn.setOnMouseClicked(event -> {
			this.data.toCsv();
		});
	}
	
	public void initData(ResultData data) {
		this.data = data;
	}

}

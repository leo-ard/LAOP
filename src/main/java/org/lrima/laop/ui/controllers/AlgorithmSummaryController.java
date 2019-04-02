package org.lrima.laop.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class AlgorithmSummaryController implements Initializable {
	@FXML Label title;
	
	public void initData(String title) {
		this.title.setText(title);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}

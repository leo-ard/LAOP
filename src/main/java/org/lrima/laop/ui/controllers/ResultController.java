package org.lrima.laop.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class ResultController implements Initializable {

	@FXML JFXButton getDataBtn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getDataBtn.setOnMouseClicked(event -> {
			System.out.println("get data");
		});
		
	}

}

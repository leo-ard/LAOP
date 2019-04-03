package org.lrima.laop.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.lrima.laop.utils.lasp.beans.algorithm.AlgorithmBean;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class AlgorithmSummaryController implements Initializable {
	@FXML Label title;
	@FXML Label nbLikes;
	
	public void initData(AlgorithmBean algorithm) {
		this.title.setText(algorithm.getTitle());
		this.nbLikes.setText("" + algorithm.getNb_likes());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}

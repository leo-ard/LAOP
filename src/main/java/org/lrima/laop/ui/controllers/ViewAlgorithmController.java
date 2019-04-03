package org.lrima.laop.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.lrima.laop.utils.lasp.beans.algorithm.AlgorithmBean;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * View a certain algorithm with more detail
 * @author Clement Bisaillon
 */
public class ViewAlgorithmController implements Initializable {
	@FXML Label algorithmName;
	@FXML JFXButton backButton;
	@FXML BorderPane root;
	
	
	public void initData(StackPane parent, AlgorithmBean algorithm) {
		this.algorithmName.setText(algorithm.getTitle());
		this.backButton.setOnMouseClicked((event) -> {
			parent.getChildren().remove(root);
		});
	}



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
}

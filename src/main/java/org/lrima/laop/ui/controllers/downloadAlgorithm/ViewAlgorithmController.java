package org.lrima.laop.ui.controllers.downloadAlgorithm;

import java.net.URL;
import java.util.ResourceBundle;

import org.lrima.laop.ui.stage.DownloadProgressDialog;
import org.lrima.laop.utils.lasp.beans.algorithm.AlgorithmBean;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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
	@FXML JFXTextArea descriptionTextArea;
	@FXML JFXButton downloadBtn;
	
	//Author
	@FXML Label authorName;
	
	AlgorithmBean algorithm;
	
	
	public void initData( StackPane parent, AlgorithmBean algorithm) {
		this.algorithm = algorithm;
		
		this.algorithmName.setText(algorithm.getTitle());
		this.descriptionTextArea.setText(algorithm.getDescription());
		this.authorName.setText(algorithm.getUser().getName());
		
		this.backButton.setOnMouseClicked((event) -> {
			parent.getChildren().remove(root);
		});
		
		this.downloadBtn.setOnMouseClicked((event) -> {
			this.downloadAlgorithm();
		});
	}



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	/**
	 * Called when the download button has been clicked.
	 * This starts the download and shows a dialog with the progress
	 */
	private void downloadAlgorithm() {
		DownloadProgressDialog progressDialog = new DownloadProgressDialog(this.algorithm);
	}
}

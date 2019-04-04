package org.lrima.laop.ui.controllers.downloadAlgorithm;

import java.net.URL;
import java.util.ResourceBundle;

import org.lrima.laop.ui.stage.DownloadProgressDialog;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * Controller for the download progress fxml view
 * @author Clement Bisaillon
 */
public class DownloadProgressController implements Initializable {
	@FXML JFXButton cancelButton;

	private DownloadProgressDialog parent;
	
	public void initData(DownloadProgressDialog parent) {
		this.parent = parent;
		
		this.cancelButton.setOnMouseClicked((value) -> {
			this.parent.close();
		});
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	
}

package org.lrima.laop.ui.controllers.downloadAlgorithm;

import java.net.URL;
import java.util.ResourceBundle;

import org.lrima.laop.ui.stage.DownloadProgressDialog;
import org.lrima.laop.utils.lasp.beans.algorithm.AlgorithmBean;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;

/**
 * Controller for the download progress fxml view
 * @author Clement Bisaillon
 */
public class DownloadProgressController implements Initializable {
	@FXML JFXButton cancelButton;
	@FXML ProgressBar downloadProgressBar;

	private DownloadProgressDialog parent;

	/**
	 * Initialize the controller with the parent dialog
	 * @param parent the parent dialog
	 */
	public void initData(DownloadProgressDialog parent) {
		this.parent = parent;
		
		this.cancelButton.setOnMouseClicked((value) -> this.parent.close());
	}

	/**
	 * Set the progress of the progress bar
	 * @param bytesLeft the number of bytes left to download
	 */
	public void setProgress(int bytesLeft) {
		double progress;

		if(bytesLeft == 0) {
			progress = 1;
		}else {
			progress = 1.0 / bytesLeft;
		}
		
		this.downloadProgressBar.setProgress(progress);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}

}

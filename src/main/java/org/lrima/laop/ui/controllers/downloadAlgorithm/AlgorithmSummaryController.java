package org.lrima.laop.ui.controllers.downloadAlgorithm;

import java.net.URL;
import java.util.ResourceBundle;

import org.lrima.laop.utils.lasp.beans.algorithm.AlgorithmBean;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * The controller of the component that show a summary of the algorithms
 * @author Clement Bisaillon
 */
public class AlgorithmSummaryController implements Initializable {
	@FXML Label title;
	@FXML Label nbLikes;
	@FXML Label authorLbl;

	/**
	 * Initialize the controller with the algorithm to display
	 * @param algorithm the algorithm to display
	 */
	void initData(AlgorithmBean algorithm) {
		this.title.setText(algorithm.getTitle());
		this.nbLikes.setText("" + algorithm.getNb_likes());
		this.authorLbl.setText(algorithm.getUser().getName());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {}
}

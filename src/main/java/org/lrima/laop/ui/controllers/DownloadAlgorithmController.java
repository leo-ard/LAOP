package org.lrima.laop.ui.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import org.lrima.laop.utils.lasp.AlgorithmsApiGateway;
import org.lrima.laop.utils.lasp.beans.algorithms.AlgorithmBean;
import org.lrima.laop.utils.lasp.beans.algorithms.AlgorithmResponseBean;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;

/**
 * Controller for the algorithm downloader stage
 * 
 * @author Clement Bisaillon
 */
public class DownloadAlgorithmController implements Initializable {
	@FXML
	TilePane algorithmTiles;
	@FXML
	JFXButton nextPageBtn;
	@FXML
	JFXButton prevPageBtn;
	@FXML
	Label currentPageLbl;

	private int currentPage = 1;
	private int maxPages = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.nextPageBtn.setOnMouseClicked((event) -> this.nextPageClicked());
		this.prevPageBtn.setOnMouseClicked((event) -> this.prevPageClicked());

		Collection<AlgorithmBean> algorithms = this.getData(this.currentPage);
		this.showAlgorithmList(algorithms);
	}

	private Collection<AlgorithmBean> getData(int page) {
		AlgorithmResponseBean algorithmResponse = AlgorithmsApiGateway.getAllAlgorithms(page);
		
		this.currentPage = algorithmResponse.getCurrent_page();
		this.maxPages = (int) Math.ceil(algorithmResponse.getTotal() / algorithmResponse.getPer_page());
		
		Collection<AlgorithmBean> algorithms = algorithmResponse.getData();

		return algorithms;
	}

	private void showAlgorithmList(Collection<AlgorithmBean> algorithms) {
		this.algorithmTiles.getChildren().clear();
		try {
			for (AlgorithmBean algorithm : algorithms) {
				FXMLLoader loader = new FXMLLoader(
						getClass().getResource("/views/panels/downloadAlgo/algorithmSummary.fxml"));
				Node node = loader.load();

				AlgorithmSummaryController controller = loader.getController();
				controller.initData(algorithm.getTitle());

				this.algorithmTiles.getChildren().add(node);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		this.currentPageLbl.setText("" + this.currentPage);
	}

	private void nextPageClicked() {
		if(currentPage <= maxPages) {
			Collection<AlgorithmBean> algorithms = this.getData(++currentPage);
			this.showAlgorithmList(algorithms);
		}
	}

	private void prevPageClicked() {
		if (currentPage > 1) {
			Collection<AlgorithmBean> algorithms = this.getData(--currentPage);
			this.showAlgorithmList(algorithms);
		}
	}

}

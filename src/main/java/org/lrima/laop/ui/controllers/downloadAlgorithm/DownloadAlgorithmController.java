package org.lrima.laop.ui.controllers.downloadAlgorithm;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import org.lrima.laop.ui.I18n;
import org.lrima.laop.utils.lasp.AlgorithmsApiGateway;
import org.lrima.laop.utils.lasp.beans.algorithm.AlgorithmBean;
import org.lrima.laop.utils.lasp.beans.algorithm.AlgorithmResponseBean;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

/**
 * Controller for the algorithm downloader stage
 * 
 * @author Clement Bisaillon
 */
public class DownloadAlgorithmController implements Initializable {
	@FXML TilePane algorithmTiles;
	@FXML JFXButton nextPageBtn;
	@FXML JFXButton prevPageBtn;
	@FXML Label currentPageLbl;
	@FXML HBox errorBox;
	@FXML HBox bottomBar;
	@FXML Label algorithmLabel;
	
	@FXML StackPane root;

	private int currentPage = 1;
	private int maxPages = 0;
	private AlgorithmsApiGateway algorithmApi;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.algorithmApi = new AlgorithmsApiGateway();
		this.nextPageBtn.setOnMouseClicked((event) -> this.nextPageClicked());
		this.prevPageBtn.setOnMouseClicked((event) -> this.prevPageClicked());

		try {
			Collection<AlgorithmBean> algorithms = this.getData(this.currentPage);
			this.showAlgorithmList(algorithms);
			this.errorBox.setManaged(false);
		}catch(Exception e) {
			//Connection to LASP error
			this.currentPageLbl.setVisible(false);
			this.nextPageBtn.setVisible(false);
			this.prevPageBtn.setVisible(false);
			this.errorBox.setVisible(true);
			this.bottomBar.setVisible(false);
			this.bottomBar.setManaged(false);
		}

		I18n.bind(algorithmLabel, nextPageBtn, prevPageBtn);
	}

	/**
	 * Get the algorithm list from LASP for a certain page
	 * @param page the page to get the algorithm from
	 * @return a collection of the algorithm on the specified page
	 */
	private Collection<AlgorithmBean> getData(int page) {
		AlgorithmResponseBean algorithmResponse = this.algorithmApi.getAllAlgorithms(page);
		
		this.currentPage = algorithmResponse.getCurrent_page();
		this.maxPages = (int) Math.ceil((double)algorithmResponse.getTotal() / algorithmResponse.getPer_page());
		
		return algorithmResponse.getData();
	}

	/**
	 * Resets and display the list of algorithms each in a separate square
	 * @param algorithms the list of algorithms
	 */
	private void showAlgorithmList(Collection<AlgorithmBean> algorithms) {
		this.algorithmTiles.getChildren().clear();
		try {
			for (AlgorithmBean algorithm : algorithms) {
				FXMLLoader loader = new FXMLLoader(
						getClass().getResource("/views/panels/downloadAlgo/algorithmSummary.fxml"));
				Node node = loader.load();
				
				node.setOnMouseClicked((event) -> this.algorithmClicked(algorithm));
				
				AlgorithmSummaryController controller = loader.getController();
				controller.initData(algorithm);

				this.algorithmTiles.getChildren().add(node);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		this.currentPageLbl.setText("" + this.currentPage);
	}

	/**
	 * When the next page button is clicked, it retrieves the data for the next page and 
	 * shows it
	 */
	private void nextPageClicked() {
		if(currentPage <= maxPages) {
			Collection<AlgorithmBean> algorithms = this.getData(++currentPage);
			this.showAlgorithmList(algorithms);
		}
	}

	/**
	 * When the prev page button is clicked, it retrieves the data for the next page and 
	 * shows it
	 */
	private void prevPageClicked() {
		if (currentPage > 1) {
			Collection<AlgorithmBean> algorithms = this.getData(--currentPage);
			this.showAlgorithmList(algorithms);
		}
	}
	
	/**
	 * When a algorithm summary box has been clicked.
	 * Show a more detailed view of the algorithm
	 * @param algorithm the algorithm clicked on
	 */
	private void algorithmClicked(AlgorithmBean algorithm) {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/views/panels/downloadAlgo/viewAlgorithm.fxml"));
			BorderPane node = loader.load();
			
			ViewAlgorithmController controller = loader.getController();
			controller.initData(this.root, algorithm);
			
			this.root.getChildren().add(node);
		}catch(Exception e) {
			System.err.println("Can't view algorithm");
			e.printStackTrace();
		}
	}

}

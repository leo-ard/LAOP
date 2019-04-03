package org.lrima.laop.ui.controllers;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import org.lrima.laop.utils.lasp.AlgorithmsApiGateway;
import org.lrima.laop.utils.lasp.beans.AlgorithmBean;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;

/**
 * Controller for the algorithm downloader stage
 * @author Clement Bisaillon
 */
public class DownloadAlgorithmController implements Initializable {
	@FXML TilePane algorithmTiles;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

		Collection<AlgorithmBean> algorithms = AlgorithmsApiGateway.getAllAlgorithms();
		
		//Test
		try {
			for(AlgorithmBean algorithm : algorithms) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/panels/downloadAlgo/algorithmSummary.fxml"));
				Node node = loader.load();
				
				AlgorithmSummaryController controller = loader.getController();
				controller.initData(algorithm.getTitle());
				
				this.algorithmTiles.getChildren().add(node);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}

package org.lrima.laop.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

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
		//Test
		try {
			for(int i = 0 ; i < 10 ; i++) {
				Node node = FXMLLoader.load(getClass().getResource("/views/panels/downloadAlgo/algorithmSummary.fxml"));
				this.algorithmTiles.getChildren().add(node);
			}
			
		}catch(Exception e) {
			
		}
	}
	
	
}

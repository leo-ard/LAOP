package org.lrima.laop.ui.stage;

import org.lrima.laop.ui.controllers.DownloadAlgorithmController;

import com.google.gson.Gson;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Show a dialog to allow the user to download an algorithm from 
 * the LISP website
 * @author Clement Bisaillon
 */
public class DownloadAlgorithmDialog extends Stage {
	
	Scene scene;
	
	/**
	 * Initiates a DownloadAlgorithmDialog attached to a parent stage
	 * @param parent the parent stage
	 */
	public DownloadAlgorithmDialog(Stage parent) {
		this.setTitle("Algorithm downloader");
		this.initOwner(parent);
		this.initModality(Modality.APPLICATION_MODAL);
		
		this.configureDialogComponents();
		this.sizeToScene();
		this.showAndWait();
		
	}
	
	private void configureDialogComponents() {
		try {
			BorderPane pane = FXMLLoader.load(getClass().getResource("/views/panels/downloadAlgo/downloadAlgorithm.fxml"));
			this.scene = new Scene(pane);
			this.scene.getStylesheets().add(getClass().getResource("/css/downloadAlgorithm.css").toExternalForm());
			this.scene.getStylesheets().add(getClass().getResource("/css/general.css").toExternalForm());
			
			this.setScene(this.scene);
		}catch(Exception e) {
			System.out.println("err");
		}
	}
}

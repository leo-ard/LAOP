package org.lrima.laop.ui.stage;

import org.lrima.laop.core.ConclusionTest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Stage used to show the various results extracted from the simulations
 * @author Clement Bisaillon
 */
public class ResultStage extends Stage {
	
	public static void main(String[] args) {
		new Thread(() ->
        	Application.launch(ConclusionTest.class)
		).start();
	}
	
	
	public ResultStage() {
		this.setTitle("Results extracted from the simulations");
		
		this.loadScene();
	}
	
	/**
	 * Load the scene from the fxml file
	 */
	private void loadScene() {
		try {
			BorderPane root = FXMLLoader.load(getClass().getResource("/views/conclusion/results.fxml"));
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add("/css/conclusion.css");
			
			this.setScene(scene);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

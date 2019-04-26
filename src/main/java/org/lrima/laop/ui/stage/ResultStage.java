package org.lrima.laop.ui.stage;

import org.lrima.laop.ui.controllers.ResultController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Stage used to show the various results extracted from the simulations
 * @author Clement Bisaillon
 */
public class ResultStage extends Stage {
//	ResultData data;
	
//	public ResultStage(ResultData data) {
//		this.data = data;
//		this.setTitle("Results extracted from the simulations");
//
//		this.loadScene();
//	}
	
	/**
	 * Load the scene from the fxml file
	 */
	private void loadScene() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/conclusion/results.fxml"));
			Parent parent = loader.load();
			ResultController controller = loader.<ResultController>getController();
//			controller.initData(this.data);
			
			BorderPane root = loader.getRoot();
			Scene scene = new Scene(root);
			scene.getStylesheets().add("/css/conclusion.css");
			
			this.setScene(scene);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

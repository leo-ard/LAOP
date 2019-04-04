package org.lrima.laop.ui.stage;

import org.lrima.laop.ui.controllers.downloadAlgorithm.DownloadProgressController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DownloadProgressDialog extends Stage {
	
	private Scene scene;
	
	public DownloadProgressDialog() {
		this.setTitle("Download progress");
		this.initModality(Modality.APPLICATION_MODAL);
		
		this.configureDialogComponents();
		this.sizeToScene();
		this.showAndWait();
	}
	
	private void configureDialogComponents() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/panels/downloadAlgo/downloadProgress.fxml"));
			VBox pane = loader.load();
			
			DownloadProgressController controller = loader.getController();
			controller.initData(this);
			
			this.scene = new Scene(pane);
			this.scene.getStylesheets().add(getClass().getResource("/css/downloadAlgorithm.css").toExternalForm());
			this.scene.getStylesheets().add(getClass().getResource("/css/general.css").toExternalForm());
			
			this.setScene(this.scene);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

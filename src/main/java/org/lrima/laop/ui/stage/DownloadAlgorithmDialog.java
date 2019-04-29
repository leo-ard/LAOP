package org.lrima.laop.ui.stage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.lrima.laop.ui.I18n;

/**
 * Show a dialog to allow the user to download an algorithm from 
 * the LISP website
 * @author Clement Bisaillon
 */
public class DownloadAlgorithmDialog extends Stage {
	private Scene scene;
	
	/**
	 * Initiates a DownloadAlgorithmDialog attached to a parent stage
	 * @param parent the parent stage
	 */
	public DownloadAlgorithmDialog(Stage parent) {
		I18n.bind("algorithm-download", this::setTitle);
		this.initOwner(parent);
		this.initModality(Modality.APPLICATION_MODAL);
		
		this.configureDialogComponents();
		this.sizeToScene();
		this.showAndWait();
		
	}

	/**
	 * Configures all the components to show on this dialog
	 */
	private void configureDialogComponents() {
		try {
			StackPane pane = FXMLLoader.load(getClass().getResource("/views/panels/downloadAlgo/downloadAlgorithm.fxml"));
			this.scene = new Scene(pane);
			this.scene.getStylesheets().add(getClass().getResource("/css/downloadAlgorithm.css").toExternalForm());
			this.scene.getStylesheets().add(getClass().getResource("/css/general.css").toExternalForm());
			
			this.setScene(this.scene);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

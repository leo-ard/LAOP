package org.lrima.laop.ui.stage;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.lrima.laop.ui.controllers.downloadAlgorithm.DownloadProgressController;
import org.lrima.laop.utils.lasp.beans.algorithm.AlgorithmBean;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DownloadProgressDialog extends Stage {

	private Scene scene;
	private AlgorithmBean algorithm;
	private DownloadProgressController controller;

	public DownloadProgressDialog(AlgorithmBean algorithm) {
		this.algorithm = algorithm;
		this.setTitle("Download progress");
		this.initModality(Modality.APPLICATION_MODAL);

		this.configureDialogComponents();
		this.sizeToScene();
		this.show();
		this.startDownload();
	}

	private void configureDialogComponents() {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/views/panels/downloadAlgo/downloadProgress.fxml"));
			VBox pane = loader.load();

			this.controller = loader.getController();
			controller.initData(this);

			this.scene = new Scene(pane);
			this.scene.getStylesheets().add(getClass().getResource("/css/downloadAlgorithm.css").toExternalForm());
			this.scene.getStylesheets().add(getClass().getResource("/css/general.css").toExternalForm());

			this.setScene(this.scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void startDownload() {
		try {
			URL url = new URL("https://lasp.lrima.cmaisonneuve.qc.ca/api/posts/" + this.algorithm.getId() + "/download");
			BufferedInputStream in = new BufferedInputStream(url.openStream());
			FileOutputStream fileOutputStream = new FileOutputStream("algos/" + algorithm.getTitle() + algorithm.getId() + ".jar");
			byte dataBuffer[] = new byte[256];
			int bytesRead;
			while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
				fileOutputStream.write(dataBuffer, 0, bytesRead);
				this.controller.setProgress(in.available());
			}
			
			in.close();
			fileOutputStream.close();
			
			//Show an alert dialog telling the user that the file has been saved
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Download successful");
			alert.setContentText("The algorithm was downloaded successfully. Don't forget to rate it !");
			alert.showAndWait();
			this.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

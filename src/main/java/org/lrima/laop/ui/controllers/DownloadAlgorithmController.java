package org.lrima.laop.ui.controllers;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import org.lrima.laop.ui.stage.installAlgorithm.AlgorithmBean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

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
		
		GsonBuilder builder = new GsonBuilder(); 
	    builder.setPrettyPrinting(); 
	      
	    Gson gson = builder.create();
	    Type collectionType = new TypeToken<Collection<AlgorithmBean>>(){}.getType();
	    String json = "[{\"id\":10,\"title\":\"asdfasdfas\",\"description\":\"fdasdfsafdasdfasdfa\",\"user\":{\"id\":1,\"name\":\"Clement Bisaillon\"},\"created_at\":\"2019-04-02T16:24:28.000000Z\"},{\"id\":9,\"title\":\"zdfasdfasdf\",\"description\":\"asdfasdfasdf\",\"user\":{\"id\":1,\"name\":\"Clement Bisaillon\"},\"created_at\":\"2019-04-01T23:21:25.000000Z\"},{\"id\":8,\"title\":\"asdfasdf\",\"description\":\"adfasdfasdf\",\"user\":{\"id\":1,\"name\":\"Clement Bisaillon\"},\"created_at\":\"2019-04-01T23:20:38.000000Z\"},{\"id\":7,\"title\":\"NEW\",\"description\":\"asdfasdfasdfa\",\"user\":{\"id\":1,\"name\":\"Clement Bisaillon\"},\"created_at\":\"2019-04-01T12:13:26.000000Z\"},{\"id\":5,\"title\":\"111\",\"description\":\"asdfasdf\",\"user\":{\"id\":1,\"name\":\"Clement Bisaillon\"},\"created_at\":\"2019-03-31T23:59:37.000000Z\"},{\"id\":4,\"title\":\"111\",\"description\":\"asdfasdf\",\"user\":{\"id\":1,\"name\":\"Clement Bisaillon\"},\"created_at\":\"2019-03-31T23:59:24.000000Z\"},{\"id\":3,\"title\":\"TESTTT\",\"description\":\"asdfasdfasdfasdffff\",\"user\":{\"id\":1,\"name\":\"Clement Bisaillon\"},\"created_at\":\"2019-03-31T23:58:01.000000Z\"},{\"id\":2,\"title\":\"asdfasdf\",\"description\":\"asdfasdfa\",\"user\":{\"id\":1,\"name\":\"Clement Bisaillon\"},\"created_at\":\"2019-03-31T23:56:46.000000Z\"},{\"id\":1,\"title\":\"asdfasdf\",\"description\":\"asdfasdfa\",\"user\":{\"id\":1,\"name\":\"Clement Bisaillon\"},\"created_at\":\"2019-03-31T23:56:14.000000Z\"}]";
	    Collection<AlgorithmBean> algorithms = gson.fromJson(json, collectionType);
	    
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

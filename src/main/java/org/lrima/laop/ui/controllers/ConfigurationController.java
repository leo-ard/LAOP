package org.lrima.laop.ui.controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import org.lrima.laop.core.LAOP;
import org.lrima.laop.network.concreteLearning.GeneticLearning;
import org.lrima.laop.network.concreteNetworks.NEAT;
import org.lrima.laop.settings.Scope;
import org.lrima.laop.ui.stage.DownloadAlgorithmDialog;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Class that controls the configuration panel (configuration.fxml)
 *
 * @author Clement Bisaillon
 */
public class ConfigurationController implements Initializable {
    
    @FXML private JFXListView<String> scopeList;
    @FXML private BorderPane settingsContainer;
    @FXML private JFXButton downloadBtn;
    private LAOP laop;
    private HashMap<String, Node> panels;
    private Stage parent;


    /**
     * When the user clicks the add algorithm button
     */
    @FXML protected void addAlgorithmClicked(ActionEvent event) {
    	//Ask for the name of the scope
    	TextInputDialog scopeNameDialog = new TextInputDialog();
    	scopeNameDialog.setTitle("Scope name");
    	scopeNameDialog.setHeaderText("Choose a name for the new scope");
    	Optional<String> scopeName = scopeNameDialog.showAndWait();
    	
    	scopeName.ifPresent(name -> {
    		this.laop.addAlgorithm(name, NEAT.class, GeneticLearning.class, new HashMap<>());
    		this.reloadScopeTableFromSettings();
    	});
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {    	
    	scopeList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal == null) {
                scopeList.getSelectionModel().select(0);
//                settingsContainer.setCenter(panels.get(GLOBAL_SCOPE));
            }
            else{
            	settingsContainer.setCenter(panels.get(newVal));
            }
        });
    	
    	downloadBtn.setOnMouseClicked((event) -> {
    		//Open the download dialog
    		DownloadAlgorithmDialog dialog = new DownloadAlgorithmDialog(parent);
    	});
    }
    
    public void setLaop(LAOP laop) {
    	this.laop = laop;
    	this.reloadScopeTableFromSettings();
    }
    
    /**
     * Sets the parent of the stage
     * @param parent the parent
     */
    public void setParent(Stage parent) {
    	this.parent = parent;
    }
    
    /**
     * Check the laop and add a table cell for each scopes
     */
    private void reloadScopeTableFromSettings() {
    	ObservableList itemList = FXCollections.observableArrayList();
    	this.panels = new HashMap<>();

		itemList.addAll(this.laop.getSettings().getScopeKeys());
    	
    	scopeList.setItems(itemList);
    	
    	//Get the content node of the scopes
    	this.laop.getSettings().getScopes().keySet().forEach((scopeKey) -> {
    		Scope scope = this.laop.getSettings().getScopes().get(scopeKey);
    		this.panels.put(scopeKey, scope.generatePanel());
    	});
    	
    	scopeList.getSelectionModel().selectLast();
    }



}

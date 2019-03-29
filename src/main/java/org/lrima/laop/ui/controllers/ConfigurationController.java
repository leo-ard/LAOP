package org.lrima.laop.ui.controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import org.lrima.laop.settings.Scope;
import org.lrima.laop.settings.Settings;

import com.jfoenix.controls.JFXListView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;

/**
 * Class that controls the configuration panel (configuration.fxml)
 *
 * @author Clement Bisaillon
 */
public class ConfigurationController implements Initializable {
    
    @FXML private JFXListView<String> scopeList;
    @FXML private BorderPane settingsContainer;
    private Settings settings;
    private HashMap<String, Node> panels;


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
    		this.settings.addScope(name);
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
    }
    
    public void setSettings(Settings settings) {
    	this.settings = settings;
    	this.reloadScopeTableFromSettings();
    }
    
    /**
     * Check the settings and add a table cell for each scopes
     */
    private void reloadScopeTableFromSettings() {
    	ObservableList itemList = FXCollections.observableArrayList();
    	this.panels = new HashMap<>();
    	
    	for(String scopeName : this.settings.getScopeKeys()) {
    		itemList.add(scopeName);
    	}
    	
    	scopeList.setItems(itemList);
    	
    	//Get the content node of the scopes
    	this.settings.getScopes().keySet().forEach((scopeKey) -> {
    		Scope scope = this.settings.getScopes().get(scopeKey);
    		this.panels.put(scopeKey, scope.generatePanel());
    	});
    	
    	scopeList.getSelectionModel().selectLast();
    }



}

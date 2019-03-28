package org.lrima.laop.ui.controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.lrima.laop.settings.Settings;

import com.jfoenix.controls.JFXListView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;

/**
 * Class that controls the configuration panel (configuration.fxml)
 *
 * @author Clement Bisaillon
 */
public class ConfigurationController implements Initializable {
    
    @FXML private JFXListView<String> scopeList;
    @FXML private HBox settingsBox;
    private Settings settings;


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
    }
    
    public void setSettings(Settings settings) {
    	this.settings = settings;
    }
    
    /**
     * Check the settings and add a table cell for each scopes
     */
    private void reloadScopeTableFromSettings() {
    	ObservableList itemList = FXCollections.observableArrayList();
    	
    	for(String scopeName : this.settings.getLocalScopes()) {
    		itemList.add(scopeName);
    	}
    	
    	scopeList.setItems(itemList);
    }



}

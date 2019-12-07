package org.lrima.laop.ui.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.lrima.laop.core.LAOP;
import org.lrima.laop.network.FUCONN.GeneticLearning;
import org.lrima.laop.settings.Scope;
import org.lrima.laop.ui.I18n;
import org.lrima.laop.ui.stage.DownloadAlgorithmDialog;

import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class that controls the configuration panel (configuration.fxml)
 *
 * @author Clement Bisaillon
 */
public class ConfigurationController implements Initializable {
    
    @FXML private ListView<String> scopeList;
    @FXML private BorderPane settingsContainer;
    @FXML private JFXButton downloadBtn;
    @FXML private Label settingLabel;
    @FXML private Label algorithmLabel;
    protected LAOP laop;
    private HashMap<String, Node> panels;
    private Stage parent;


    /**
     * When the user clicks the add algorithm button
     */
    @FXML protected void addAlgorithmClicked(ActionEvent event) {
    	//Ask for the name of the scope
    	TextInputDialog scopeNameDialog = new TextInputDialog();
    	scopeNameDialog.setTitle(I18n.getString("scope-name"));
    	scopeNameDialog.setHeaderText(I18n.getString("scope-name-long"));
    	Optional<String> scopeName = scopeNameDialog.showAndWait();
    	
    	scopeName.ifPresent(name -> {
    		this.laop.addAlgorithm(name, GeneticLearning.class, new HashMap<>());
    		this.reloadScopeTableFromSettings();
    	});
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {    
    	this.panels = new HashMap<>();
    	this.scopeList.setCellFactory(list -> new AlgorithmCell());
    	scopeList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal == null) {
                scopeList.getSelectionModel().select(0);
            }
            else{
            	settingsContainer.setCenter(panels.get(newVal));
            }
        });
    	
    	downloadBtn.setOnMouseClicked((event) -> {
    		//Open the download dialog
            new DownloadAlgorithmDialog(parent);
    	});
		I18n.bind(downloadBtn, settingLabel, algorithmLabel);

    }

	/**
	 * Attach the LAOP instance to this controller
	 * @param laop the LAOP instance
	 */
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

		itemList.addAll(this.laop.getSettings().getScopeKeys());
    	
    	scopeList.setItems(itemList);
    	
    	//Get the content node of the scopes
    	this.laop.getSettings().getScopes().keySet().forEach((scopeKey) -> {
    		Scope scope = this.laop.getSettings().getScopes().get(scopeKey);
    		if(!this.panels.containsKey(scopeKey)) {
    			this.panels.put(scopeKey, scope.generatePanel());
    		}
    	});
    	
    	scopeList.getSelectionModel().selectLast();
    }

	/**
	 * Used to display a button to remove the row next to the label
	 */
	private class AlgorithmCell extends ListCell<String> {
    	HBox cellContent = new HBox();
    	Label name;
    	JFXButton deleteBtn;

    	AlgorithmCell() {
    		super();

    		cellContent.setPadding(new Insets(0, 10, 0, 10));
    		name = new Label("");
    		deleteBtn = new JFXButton("X");
    		deleteBtn.getStyleClass().add("btn-danger");

    		Region space = new Region();
            HBox.setHgrow(space, Priority.ALWAYS);

            deleteBtn.setOnMouseClicked((event) ->{
            	if(!getItem().equals("GLOBAL")) {
            		ConfigurationController.this.laop.removeAlgorithm(this.getItem());
                	getListView().getItems().remove(getItem());
                	ConfigurationController.this.reloadScopeTableFromSettings();
            	}

            });

            this.cellContent.getChildren().addAll(name, space, deleteBtn);
    	}

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            cellContent.setAlignment(Pos.CENTER);
            if (item != null) {
            	//Algorithm name label
            	this.name.setText(item);
            	if(item.equals("GLOBAL")){
            		this.deleteBtn.setVisible(false);
				}
				else{
					this.deleteBtn.setVisible(true);
				}

                setGraphic(this.cellContent);
            }
            else {
            	setGraphic(null);
            }
        }
    }


}

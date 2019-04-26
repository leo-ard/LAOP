package org.lrima.laop.ui.stage;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.lrima.laop.core.LAOP;
import org.lrima.laop.ui.I18n;
import org.lrima.laop.ui.controllers.ConfigurationController;
import org.lrima.laop.utils.FXUtils;

import java.util.ArrayList;

/**
 * Class that controls the transitions between the configuration panels (with next and back button)
 *
 * @author Léonard
 */
public class ConfigurationStage extends Stage {
    /**
     *  Array of all the panels that must be displayed
     */
    private ArrayList<Parent> scenes;

    /**
     *  Indicates which panel is displayed
     */
    private int panelIndex;

    private JFXButton left;
    private JFXButton right;
    private BorderPane root;
    private LAOP laop;

    public ConfigurationStage(){
        this.setTitle("LAOP : configuration");

        laop = new LAOP();

        loadAllScenes();

        //Initialisation of the next and back button
        left = new JFXButton("%next");
        left.getStyleClass().add("btn");

        right = new JFXButton("%back");
        right.getStyleClass().add("btn");

        setButtonLabels(panelIndex);

        //Bottom panel with the next and back buttons
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        HBox bottom = new HBox(left, region, right);
        bottom.setSpacing(30);
        bottom.setPadding(new Insets(10,10,10,10));

        //Border layout to put them together
        root = new BorderPane();
        root.setBottom(bottom);
        panelIndex = -1;
        next();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/css/general.css");

        I18n.bind(left, right);
        this.setScene(scene);
    }

    /**
     * Loads all the scene that must be displayed
     */
    private void loadAllScenes() {
        scenes = new ArrayList<>();
        scenes.add(load("home"));
        scenes.add(load("configuration"));
    }

    /**
     * Loads a panel according to the name. Checks within the files for that panel (extension .fxml) and returns it.
     *
     * @param name The name of the panel to load
     * @return The loaded panel
     */
    private Parent load(String name){
        FXMLLoader loader = FXUtils.load(name);
        
        if(loader.getController() instanceof ConfigurationController){
            ConfigurationController configurationController = loader.getController();
            configurationController.setLaop(laop);
            configurationController.setParent(this);
        }

        return loader.getRoot();
    }

    /**
     *  Checks the panelIndex of the panel and changes the label of the button accordingly
     *
     * @param panelIdex the stage of the panel
     */
    private void setButtonLabels(int panelIdex){
        if(panelIdex == 0){
            left.setText("");
            left.setOnAction((e)-> launchDemo());
            right.setText("%next");
            right.setOnAction((e)-> next());
            right.setButtonType(JFXButton.ButtonType.RAISED);
            left.setVisible(false);

        } else if(panelIdex > 0){
            left.setVisible(true);
            left.setText("%go-back");
            left.setOnAction((e)-> back());
            right.setText("%simulate");
            right.setOnAction((e)-> simulate());
        }

        I18n.remove(left, right);
        I18n.bind(left, right);

    }

    /**
     * Launches the simulation
     */
    private void simulate() {
    	//Check if there are errors before running the simulation
    	String error = laop.canStartSimulations();
    	
    	
    	//Run the simulation if there are no errors
    	if(error.length() == 0) {
    		this.close();
    		laop.startSimulation(LAOP.SimulationDisplayMode.WITH_INTERFACE);
    	}
    	else {
    		//Show an error dialog with the error
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setHeaderText("Error in the configuration");
    		alert.setContentText(error);
    		alert.show();
    	}
    }

    /**
     * Displays the previous panel
     */
    private void back() {
        panelIndex--;
        root.setCenter(scenes.get(panelIndex));
        this.sizeToScene();
        setButtonLabels(panelIndex);
    }

    /**
     * Displays the next panel
     */
    private void next() {
        panelIndex++;
        root.setCenter(scenes.get(panelIndex));
        this.sizeToScene();
        setButtonLabels(panelIndex);

    }

    private void launchDemo() {
        //TODO
    }


}

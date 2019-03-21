package org.lrima.laop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.lrima.laop.core.LAOP;
import org.lrima.laop.simulation.Simulation;
import org.lrima.laop.simulation.SimulationBuffer;
import org.lrima.laop.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that controls the transitions between the configuration panels (with next and back button)
 *
 * @author Léonard
 */
public class ConfigurationStage extends Stage {
    /**
     *  Array of all the panels that must be displayed
     */
    ArrayList<Parent> scenes;

    /**
     *  Indicates which panel is displayed
     */
    int panelIndex;

    JFXButton left;
    JFXButton right;
    BorderPane root;
    LAOP laop;

    public ConfigurationStage(){
        this.setTitle("LAOP : laop");

        laop = new LAOP();

        //TODO : TEMP
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("SETTING 1", "HAHAHA");
        laop.addAlgorithm("test1212", ConfigurationController.class, hashMap);

        loadAllScenes();

        //Initialisation of the next and back button
        left = new JFXButton("next");
        left.getStyleClass().add("btn");

        right = new JFXButton("back");
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
        FXMLLoader loader = Utils.load(name);

        if(loader.getController() instanceof ConfigurationController){
            ConfigurationController configurationController = loader.getController();
            configurationController.setLAOP(laop);
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
            left.setText("demo");
            left.setOnAction((e)-> launchDemo());
            right.setText("next");
            right.setOnAction((e)-> next());
            right.setButtonType(JFXButton.ButtonType.RAISED);

        } else if(panelIdex > 0){
            left.setText("Go Back");
            left.setOnAction((e)-> back());
            right.setText("simulate");
            right.setOnAction((e)-> simulate());
        }

    }

    /**
     * Launches the simulation
     */
    private void simulate() {
        this.close();
        laop.startSimulation(LAOP.SimulationDisplayMode.WITH_INTERFACE);
    }

    /**
     * Displays the previous panel
     */
    private void back() {
        panelIndex--;
        root.setCenter(scenes.get(panelIndex));
        setButtonLabels(panelIndex);
    }

    /**
     * Displays the next panel
     */
    private void next() {
        panelIndex++;
        root.setCenter(scenes.get(panelIndex));
        setButtonLabels(panelIndex);

    }

    private void launchDemo() {
        //TODO
    }


}

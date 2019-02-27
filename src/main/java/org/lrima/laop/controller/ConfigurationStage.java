package org.lrima.laop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.lrima.laop.core.LAOP;
import org.lrima.laop.settings.Settings;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ConfigurationStage extends Stage {
    ArrayList<Parent> scenes;
    int state;

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
        left.getStyleClass().add("high");

        right = new JFXButton("back");
        right.getStyleClass().add("high");

        setButtonAccordingToState(state);

        //Bottom panel with the next and back buttons
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        HBox bottom = new HBox(left, region, right);
        bottom.setSpacing(30);
        bottom.setPadding(new Insets(10,10,10,10));

        //Border layout to put them together
        root = new BorderPane();
        root.setBottom(bottom);
        state = -1;
        next();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/css/general.css");

        this.setScene(scene);
    }

    private void loadAllScenes() {
        scenes = new ArrayList<>();
        scenes.add(load("home"));
        scenes.add(load("configuration"));
    }

    private Parent load(String file) {
        URL url = getClass().getResource("/views/configuration/" + file + ".fxml");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(url);
            loader.load();
            if(loader.getController() instanceof ConfigurationController){
                ConfigurationController configurationController = loader.getController();
                System.out.println("settoping " + url);
                configurationController.setLAOP(laop);
            }

            return loader.getRoot();
        } catch (IOException e) {
            System.err.println("Could not load " + file +".fxml");
            return null;
        }
    }

    private void setButtonAccordingToState(int state){
        if(state == 0){
            left.setText("demo");
            left.setOnAction((e)-> launchDemo());
            right.setText("next");
            right.setOnAction((e)-> next());
            right.setButtonType(JFXButton.ButtonType.RAISED);

        } else if(state > 0){
            left.setText("Go Back");
            left.setOnAction((e)-> back());
            right.setText("next");
            right.setOnAction((e)-> next());
        }

    }

    private void back() {
        state--;
        root.setCenter(scenes.get(state));
        setButtonAccordingToState(state);
    }

    private void next() {
        state++;
        root.setCenter(scenes.get(state));
        setButtonAccordingToState(state);

    }

    private void launchDemo() {
        //TODO
    }


}

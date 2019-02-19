package org.lrima.laop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ConfigurationStage extends Stage {
    ArrayList<Parent> scenes;
    int state;

    JFXButton left;
    JFXButton right;
    BorderPane scene;

    public ConfigurationStage(){
        this.setTitle("LAOP : configuration");

        loadAllScenes();

        //Initialisation of the next and back button
        left = new JFXButton("next");
        right = new JFXButton("back");

        setButtonAccordingToState(state);

        //Bottom panel with the next and back buttons
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        HBox bottom = new HBox(left, region, right);
        bottom.setSpacing(30);
        bottom.setPadding(new Insets(10,10,10,10));

        //Border layout to put them together
        scene = new BorderPane();
        scene.setBottom(bottom);
        state = -1;
        next();


        this.setScene(new Scene(scene));
    }

    private void loadAllScenes() {
        scenes = new ArrayList<>();
        scenes.add(load("home"));
        scenes.add(load("configuration"));
    }

    private Parent load(String file) {
        URL url = getClass().getResource("/views/configuration/" + file + ".fxml");
        try {
            return FXMLLoader.load(url);
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
        } else if(state > 0){
            left.setText("Go Back");
            left.setOnAction((e)-> back());
            right.setText("next");
            right.setOnAction((e)-> next());
        }

    }

    private void back() {
        state--;
        scene.setCenter(scenes.get(state));
        setButtonAccordingToState(state);
    }

    private void next() {
        state++;
        scene.setCenter(scenes.get(state));
        setButtonAccordingToState(state);

    }

    private void launchDemo() {
        //TODO
    }


}

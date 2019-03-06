package org.lrima.laop.controller;

import com.jfoenix.controls.JFXSlider;
import javafx.beans.property.IntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import org.lrima.laop.simulation.SimulationBuffer;
import org.lrima.laop.utils.Utils;

public class SimulationStage extends Stage {
    SimulationBuffer buffer;

    public SimulationStage(SimulationBuffer buffer){
        this.setTitle("LAOP : Simulation");
        this.buffer = buffer;

        this.loadAllScenes();
    }

    private void loadAllScenes() {
        BorderPane rootPane = new BorderPane();
        Canvas canvas = canvas();

        HBox timeLine = timeLine();

        rootPane.setCenter(canvas);
        rootPane.setBottom(timeLine);

        Scene scene = new Scene(rootPane, 600, 500);

        this.setScene(scene);
    }

    private Canvas canvas() {
        return new Canvas();
    }

    private HBox timeLine() {
        Button button = new Button(">");

        JFXSlider slider = new JFXSlider();
        slider.setMaxWidth(Integer.MAX_VALUE);
        HBox.setMargin(slider, new Insets(7,0,7,0));

        Button button1 = new Button("Next gen");

        HBox hBox = new HBox(button, slider, button1);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(5));

        HBox.setHgrow(slider, Priority.ALWAYS);

        return hBox;

    }
}

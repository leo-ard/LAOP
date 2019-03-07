package org.lrima.laop.controller;

import com.jfoenix.controls.JFXSlider;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import org.lrima.laop.graphics.SimulationDrawer;
import org.lrima.laop.simulation.CarInfo;
import org.lrima.laop.simulation.SimulationBuffer;
import org.lrima.laop.simulation.SimulationSnapshot;

/**
 * Class that displays the simulation with the side panels
 *
 * @author Léonard
 */
public class SimulationStage extends Stage {
    SimulationBuffer buffer;
    Canvas canvas;

    SimulationDrawer simulationDrawer;

    public SimulationStage(SimulationBuffer buffer){
        this.setTitle("LAOP : Simulation");
        this.buffer = buffer;
        this.canvas = new Canvas(1280, 720);

        for(int i = 0; i < 100; i++){
            SimulationSnapshot snapshot = new SimulationSnapshot();
            snapshot.addCar(new CarInfo(i*10, i*10, 10, 30, -45));
            snapshot.addCar(new CarInfo(i*11 + 20, i*11, 10, 30, -45));
            snapshot.addCar(new CarInfo(i*20 + 40, i*10, 10, 30, -45));
            snapshot.addCar(new CarInfo(i*15 - 20,  i*10, 10, 30, -45));
            snapshot.addCar(new CarInfo(i*10 - 40, i*10, 10, 30, -45));

            this.buffer.addSnapshot(snapshot);
        }

        this.loadAllScenes();
    }

    /**
     * Adds all the layouts with their components to root Pane
     */
    private void loadAllScenes() {
        BorderPane rootPane = new BorderPane();

        HBox timeLine = timeLine();

        rootPane.setCenter(canvas);
        rootPane.setBottom(timeLine);

        Scene scene = new Scene(rootPane);

        this.setScene(scene);
    }

    /**
     * Creates and returns all the nodes to create a timeline.
     *
     * @return The nodes to create the timeline
     */
    private HBox timeLine() {
        Button button = new Button(">");

        button.setOnAction(e ->{
            if(button.getText().equals(">")){
                this.simulationDrawer.startAutodraw(100);
                button.setText("||");
            }
            else{
                this.simulationDrawer.stopAutoDraw();
                button.setText(">");
            }
        });

        JFXSlider slider = new JFXSlider();
        slider.setMax(buffer.getSize()-1);
        slider.setValue(0);
        slider.setMinorTickCount(1);
        slider.setMaxWidth(Integer.MAX_VALUE);
        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int currentValue = (int)Math.round(newVal.doubleValue());
            int oldValue = (int)Math.round(oldVal.doubleValue());

            System.out.println(currentValue + " " + oldValue);

            if(currentValue != oldValue)
                setTime(currentValue);
        });


        HBox.setMargin(slider, new Insets(7,0,7,0));

        Button button1 = new Button("Next gen");

        HBox hBox = new HBox(button, slider, button1);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(5));

        HBox.setHgrow(slider, Priority.ALWAYS);

        hBox.setStyle("-fx-background-color: blue");
        this.simulationDrawer = new SimulationDrawer(canvas, buffer);
        this.simulationDrawer.drawStep(0);

        this.simulationDrawer.setSlider(slider);

        return hBox;

    }

    /**
     * Sets the time at which we want the simulation to be displayed at
     *
     * @param time The time
     */
    private void setTime(int time) {
        this.simulationDrawer.drawStep(time);
    }
}

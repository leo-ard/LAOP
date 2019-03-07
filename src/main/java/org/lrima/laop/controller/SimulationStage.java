package org.lrima.laop.controller;

import com.jfoenix.controls.JFXSlider;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.lrima.laop.graphics.SimulationDrawer;
import org.lrima.laop.simulation.SimulationBuffer;
import org.lrima.laop.simulation.SimulationSnapshot;
import org.lrima.laop.simulation.objects.Car;

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
        this.canvas = new Canvas(500, 500);

        SimulationSnapshot snapshot = new SimulationSnapshot();
        snapshot.addCar(new Car());
        this.buffer.addSnapshot(snapshot);

        snapshot = new SimulationSnapshot();
        snapshot.addCar(0, 0, 100, 100);
        this.buffer.addSnapshot(snapshot);

        snapshot = new SimulationSnapshot();
        snapshot.addCar(10, 10, 100, 100);
        this.buffer.addSnapshot(snapshot);

        snapshot = new SimulationSnapshot();
        snapshot.addCar(30, 30, 100, 100);
        this.buffer.addSnapshot(snapshot);

        snapshot = new SimulationSnapshot();
        snapshot.addCar(50, 50, 100, 100);
        this.buffer.addSnapshot(snapshot);

        snapshot = new SimulationSnapshot();
        snapshot.addCar(100, 100, 100, 100);
        this.buffer.addSnapshot(snapshot);

        this.loadAllScenes();

        this.simulationDrawer = new SimulationDrawer(canvas, buffer);
        this.simulationDrawer.drawStep(0);


        canvas.getGraphicsContext2D().setFill(Color.BLUE);
        canvas.getGraphicsContext2D().strokeLine(0, 0, 10, 10);
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

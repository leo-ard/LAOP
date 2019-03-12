package org.lrima.laop.graphics.simulation.timeline;

import com.jfoenix.controls.JFXSlider;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.lrima.laop.graphics.simulation.SimulationDrawer;
import org.lrima.laop.simulation.SimulationBuffer;

/**
 * The timeline reusable component
 * @author Clement Bisaillon
 */
public class TimeLine extends HBox {
    private SimulationDrawer simulationDrawer;
    private PlayButton playButton;
    private JFXSlider timeSlider;
    private SimulationBuffer buffer;

    public TimeLine(SimulationDrawer simulationDrawer, SimulationBuffer buffer){
        this.simulationDrawer = simulationDrawer;
        this.buffer = buffer;
        this.playButton = new PlayButton(this.simulationDrawer);
        this.configure();
    }

    private void configure(){
        this.createTimeSlider();
        Button nextGenButton = new Button("Next gen");

        this.getChildren().addAll(playButton, this.timeSlider, nextGenButton);
        this.setSpacing(10);
        this.setPadding(new Insets(5));
        HBox.setHgrow(this.timeSlider, Priority.ALWAYS);
        this.setStyle("-fx-background-color: blue");

        //View the first frame
        this.simulationDrawer.drawStep(0);
    }

    /**
     * Creates the time slider
     * @author Leonard Oest OLeary
     */
    private void createTimeSlider(){
        this.timeSlider = new JFXSlider();
        this.timeSlider.setMax(buffer.getSize()-1);
        this.timeSlider.setValue(0);
        this.timeSlider.setMinorTickCount(1);
        this.timeSlider.setMaxWidth(Integer.MAX_VALUE);
        this.timeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int currentValue = (int)Math.round(newVal.doubleValue());
            int oldValue = (int)Math.round(oldVal.doubleValue());

            if(currentValue != oldValue)
                setTime(currentValue);
        });
        HBox.setMargin(this.timeSlider, new Insets(7,0,7,0));
        this.simulationDrawer.setSlider(this.timeSlider);
    }

    /**
     * Sets the time at which we want the simulation to be displayed at
     *
     * @param time The time
     * @author Leonard Oest OLeary
     */
    private void setTime(int time) {
        this.simulationDrawer.drawStep(time);
    }
}

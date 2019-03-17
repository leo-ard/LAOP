package org.lrima.laop.graphics.panels.simulation.timeline;

import com.jfoenix.controls.JFXSlider;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import org.lrima.laop.controller.SimulationDrawer;
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

            if(currentValue != oldValue)
                setTime(currentValue);
        });


        HBox.setMargin(slider, new Insets(7,0,7,0));

        Button button1 = new Button("Next gen");

        this.getChildren().addAll(button, slider, button1);
        setSpacing(10);
        setPadding(new Insets(5));

        HBox.setHgrow(slider, Priority.ALWAYS);

        setStyle("-fx-background-color: rgb(255, 255, 255, 0.5)");
        this.simulationDrawer.drawStep(0);

        this.simulationDrawer.setSlider(slider);
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

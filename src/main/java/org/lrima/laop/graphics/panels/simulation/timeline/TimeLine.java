package org.lrima.laop.graphics.panels.simulation.timeline;

import com.jfoenix.controls.JFXSlider;
import com.sun.media.jfxmedia.events.BufferProgressEvent;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import org.lrima.laop.controller.SimulationDrawer;
import org.lrima.laop.simulation.SimulationBuffer;
import org.lrima.laop.simulation.listeners.BufferListener;

/**
 * The timeline reusable component
 * @author Léonard
 */
public class TimeLine extends HBox {
    private SimulationDrawer simulationDrawer;
    private PlayButton playButton;
    private SimulationBuffer buffer;
    private JFXSlider slider;
    private CheckBox checkBox;

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

        checkBox = new CheckBox("");
        checkBox.selectedProperty().setValue(false);
        checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> this.slider.setDisable(newVal));
        
        this.slider = new JFXSlider();

        
        slider.setMax(buffer.getSize());
        slider.setValue(0);
        slider.setMinorTickCount(1);
        slider.setMaxWidth(Integer.MAX_VALUE);
        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int currentValue = (int)Math.round(newVal.doubleValue());
            int oldValue = (int)Math.round(oldVal.doubleValue());

            if(currentValue != oldValue)
                this.simulationDrawer.drawStep(currentValue);
        });


        HBox.setMargin(slider, new Insets(7,0,7,0));

        Button button1 = new Button("Next gen");

        this.getChildren().addAll(button, slider, checkBox, button1);
        setSpacing(10);
        setPadding(new Insets(5));

        HBox.setHgrow(slider, Priority.ALWAYS);

        setStyle("-fx-background-color: rgb(255, 255, 255, 0.5)");

        this.simulationDrawer.setSlider(slider);
    }

    public void setSliderMax(int max) {
        this.slider.setMax(max);
    }

    public boolean getRealTime() {
        return checkBox.selectedProperty().get();

    }
}

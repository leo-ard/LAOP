package org.lrima.laop.ui.components;

import org.lrima.laop.ui.SimulationDrawer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSlider;

import javafx.beans.property.BooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Creates a Timeline element (JavaFX)
 *
 * @author Léonard
 */
public class Timeline extends HBox{

    public SimulationDrawer simulationDrawer;
    private JFXCheckBox checkBoxRealTime;
    private JFXSlider sliderTimeLine;
    private JFXButton btnGenFinish;
    private PlayButton playButton;

    public Timeline(SimulationDrawer simulationDrawer){
        this.simulationDrawer = simulationDrawer;
        init();
    }

    public void disable(boolean b){
        this.simulationDrawer.setRealTime(b);
        this.sliderTimeLine.setDisable(b);
        this.playButton.setDisable(b);
    }

    private void init() {
        this.getStyleClass().add("panel");

        playButton = new PlayButton(
                (b)-> {
                    if(b)
                        this.simulationDrawer.startAutodraw(2);
                    else
                        this.simulationDrawer.stopAutoDraw();
                });

        sliderTimeLine = new JFXSlider();

        sliderTimeLine.setValue(0);
        sliderTimeLine.setMinorTickCount(1);
        sliderTimeLine.setMaxWidth(Integer.MAX_VALUE);
        sliderTimeLine.valueProperty().addListener((obs, oldVal, newVal) -> {
            int currentValue = (int)Math.round(newVal.doubleValue());
            int oldValue = (int)Math.round(oldVal.doubleValue());

            if(currentValue != oldValue)
                this.simulationDrawer.setCurrentStep(currentValue);
        });

        sliderTimeLine.setOnMousePressed(e -> this.simulationDrawer.stopAutoDraw());




        HBox.setMargin(sliderTimeLine, new Insets(7,0,7,0));

        this.getChildren().addAll(playButton, sliderTimeLine);
        this.setSpacing(10);
        this.setPadding(new Insets(5));

        HBox.setHgrow(sliderTimeLine, Priority.ALWAYS);
    }

    public void reset(){
        this.getChildren().clear();
        this.getChildren().addAll(playButton, sliderTimeLine, checkBoxRealTime);
    }

    public JFXSlider getSliderTimeLine() {
        return sliderTimeLine;
    }
    
    public BooleanProperty getRealTimeAttribute() {
    	return this.checkBoxRealTime.selectedProperty();
    }
}

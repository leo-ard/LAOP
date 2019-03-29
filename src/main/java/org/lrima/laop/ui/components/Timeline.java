package org.lrima.laop.ui.components;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSlider;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.lrima.laop.ui.SimulationDrawer;

import java.sql.Time;

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

    public Timeline(SimulationDrawer simulationDrawer){
        this.simulationDrawer = simulationDrawer;
        init();
    }

    private void init() {
        this.getStyleClass().add("panel");

        PlayButton playButton = new PlayButton(
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

        checkBoxRealTime = new JFXCheckBox("");
        checkBoxRealTime.selectedProperty().addListener((obs, oldVal, newVal) -> {
            this.sliderTimeLine.setDisable(newVal);
            playButton.setDisable(newVal);
            playButton.setIsPlaying(false);
            simulationDrawer.setRealTime(newVal);
        });
        checkBoxRealTime.selectedProperty().setValue(true);


        HBox.setMargin(sliderTimeLine, new Insets(7,0,7,0));

        btnGenFinish = new JFXButton("Next generation");
        btnGenFinish.getStyleClass().add("btn-light");
        btnGenFinish.setOnAction( e-> {
            btnGenFinish.setDisable(true);
//            this.simulationEngine.nextGen();
        });

        this.getChildren().addAll(playButton, sliderTimeLine, checkBoxRealTime, btnGenFinish);
        this.setSpacing(10);
        this.setPadding(new Insets(5));

        HBox.setHgrow(sliderTimeLine, Priority.ALWAYS);
    }

    public JFXSlider getSliderTimeLine() {
        return sliderTimeLine;
    }
}

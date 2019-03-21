package org.lrima.laop.controller;

import com.jfoenix.controls.JFXSlider;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.lrima.laop.controller.components.PlayButton;
import org.lrima.laop.graphics.panels.ChartPanel;
import org.lrima.laop.graphics.panels.ConsolePanel;
import org.lrima.laop.graphics.panels.InspectorPanel;
import org.lrima.laop.simulation.Simulation;
import org.lrima.laop.simulation.SimulationBuffer;


/**
 * Class that displays the simulation with the side panels
 *
 * @author Léonard
 */
public class SimulationStage extends Stage {
    private final Simulation simulation;
    private Canvas canvas;

    private SimulationDrawer simulationDrawer;
    private InspectorPanel inspector;
    private ConsolePanel consolePanel;
    private ChartPanel chartPanel;

    private final double WINDOW_WIDTH = 1280;
    private final double WINDOW_HEIGHT = 720;
    private CheckBox checkBoxRealTime;
    private JFXSlider sliderTimeLine;

    /**
     * Initialize a new simulation stage with a specific simulation buffer
     * @param simulation the simulation to initialize the simulation stage with
     */
    public SimulationStage(Simulation simulation){
        this.setTitle("LAOP : Simulation");
        this.simulation = simulation;
        this.simulation.setAutoRun(false);

        this.canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.inspector = new InspectorPanel();
        this.consolePanel = new ConsolePanel();
        this.simulationDrawer = new SimulationDrawer(canvas, simulation, inspector);
        this.chartPanel = new ChartPanel();


        this.setOnCloseRequest(e->{
            Platform.exit();
            System.exit(0);
        });
        
        this.loadAllScenes();
    }

    /**
     * Adds all the layouts with their components to root Pane
     */
    private void loadAllScenes() {
        Node timeLine = timeline();

        BorderPane rootPane = new BorderPane();





        Pane blankPane = new Pane();
        blankPane.setVisible(false);

        //rootPane.setCenter(blankPane);
        
        //Add the timeLine and the chart panel to the bottom
        VBox bottomPanelBox = new VBox();
        bottomPanelBox.getChildren().addAll(timeLine, this.chartPanel);
        
        rootPane.setBottom(bottomPanelBox);
        rootPane.setRight(inspector);

        rootPane.setLeft(this.consolePanel);
        canvas.setPickOnBounds(false);
        rootPane.setPickOnBounds(false);

        StackPane rootrootPane = new StackPane(canvas, rootPane);

        //CANVAS
        ChangeListener<Number> updateWidthHeight = (observable, oldValue, newValue) -> {
            canvas.setHeight(rootrootPane.getHeight());
            canvas.setWidth(rootrootPane.getWidth());
            simulationDrawer.repaint();
        };

        rootrootPane.widthProperty().addListener(updateWidthHeight);
        rootrootPane.heightProperty().addListener(updateWidthHeight);


        this.simulationDrawer.setSlider(sliderTimeLine);
        this.simulation.getBuffer().setOnSnapshotAdded(this::handleNewSnapshot);

        Scene scene = new Scene(rootrootPane);
        scene.getStylesheets().add("/css/general.css");

        this.setScene(scene);
    }

    /**
     * Creates all the elements that compose the Timeline
     *
     * @return the elements of the timeline
     */
    private HBox timeline() {
        //TODO : Probablement que c'est bien de l'encapsuler, mais il faut penser à comment le faire si on veut qu'il soit réutilisable
        HBox root = new HBox();

        PlayButton button = new PlayButton(
                (b)-> {
                    if(b)
                        this.simulationDrawer.startAutodraw(100);
                    else
                        this.simulationDrawer.stopAutoDraw();
                });

        checkBoxRealTime = new CheckBox("");
        checkBoxRealTime.selectedProperty().setValue(false);
        checkBoxRealTime.selectedProperty().addListener((obs, oldVal, newVal) -> this.sliderTimeLine.setDisable(newVal));

        sliderTimeLine = new JFXSlider();

        sliderTimeLine.setMax(simulation.getBuffer().getSize());
        sliderTimeLine.setValue(0);
        sliderTimeLine.setMinorTickCount(1);
        sliderTimeLine.setMaxWidth(Integer.MAX_VALUE);
        sliderTimeLine.valueProperty().addListener((obs, oldVal, newVal) -> {
            int currentValue = (int)Math.round(newVal.doubleValue());
            int oldValue = (int)Math.round(oldVal.doubleValue());

            if(currentValue != oldValue){
                this.simulationDrawer.drawStep(currentValue);
            }
        });

        sliderTimeLine.setOnMousePressed(e -> this.simulationDrawer.stopAutoDraw());


        HBox.setMargin(sliderTimeLine, new Insets(7,0,7,0));

        Button button1 = new Button("Next gen");
        button1.setOnAction( e-> {
            this.simulation.run();
        });

        root.getChildren().addAll(button, sliderTimeLine, checkBoxRealTime, button1);
        root.setSpacing(10);
        root.setPadding(new Insets(5));

        HBox.setHgrow(sliderTimeLine, Priority.ALWAYS);

        root.setStyle("-fx-background-color: rgb(255, 255, 255, 0.5)");

        return root;
    }

    private void handleNewSnapshot(SimulationBuffer buffer) {
        sliderTimeLine.setMax(buffer.getSize()-1);
        if(checkBoxRealTime.selectedProperty().get() && buffer.getSize() != 0){
            simulationDrawer.drawStep(buffer.getSize()-1);
        }

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

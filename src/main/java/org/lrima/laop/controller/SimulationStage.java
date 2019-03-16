package org.lrima.laop.controller;

import org.lrima.laop.graphics.panels.ChartPanel;
import org.lrima.laop.graphics.panels.ConsolePanel;
import org.lrima.laop.graphics.panels.InspectorPanel;
import org.lrima.laop.graphics.panels.simulation.timeline.TimeLine;
import org.lrima.laop.simulation.CarInfo;
import org.lrima.laop.simulation.SimulationBuffer;
import org.lrima.laop.simulation.SimulationSnapshot;

import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Class that displays the simulation with the side panels
 *
 * @author Léonard
 */
public class SimulationStage extends Stage {
    private SimulationBuffer buffer;
    private Canvas canvas;
    private TimeLine timeLine;

    private BorderPane rootPane;
    
    private SimulationDrawer simulationDrawer;
    private InspectorPanel inspector;
    private ConsolePanel consolePanel;
    private ChartPanel chartPanel;

    /**
     * Initialize a new simulation stage with a new simulation buffer
     */
    public SimulationStage(){
        this(new SimulationBuffer());
    }

    /**
     * Initialize a new simulation stage with a specific simulation buffer
     * @param buffer the simulation buffer to initialize the simulation stage with
     */
    public SimulationStage(SimulationBuffer buffer){
        this.setTitle("LAOP : Simulation");
        this.buffer = buffer;
        //todo: creer constantes pour width et height
        this.rootPane = new BorderPane();
        
        this.canvas = new Canvas(1280, 720);
        this.inspector = new InspectorPanel();
        this.consolePanel = new ConsolePanel();
        this.chartPanel = new ChartPanel(this.rootPane);

        //TEMPORARY PROVIDER
        for(int i = 0; i < 100; i++){
            SimulationSnapshot snapshot = new SimulationSnapshot();

            for(int j = 0; j < 100; j++){
                snapshot.addCar(new CarInfo(i*10 + j*15, i*10, 10, 30, -45));

            }

            this.buffer.addSnapshot(snapshot);
        }

        this.loadAllScenes();
    }

    /**
     * Adds all the layouts with their components to root Pane
     */
    private void loadAllScenes() {
        

        this.simulationDrawer = new SimulationDrawer(canvas, buffer, inspector);
        this.timeLine = new TimeLine(this.simulationDrawer, this.buffer);

        Pane canvasHolder = new Pane(canvas);

        //CANVAS
        ChangeListener<Number> updateWidthHeight = (observable, oldValue, newValue) -> {
            canvas.setHeight(canvasHolder.getHeight());
            canvas.setWidth(canvasHolder.getWidth());
            simulationDrawer.repaint();
        };
        
        canvasHolder.widthProperty().addListener(updateWidthHeight);
        canvasHolder.heightProperty().addListener(updateWidthHeight);

        Pane clickerPane = new Pane();
        clickerPane.setVisible(false);

        rootPane.setCenter(clickerPane);
        
        //Add the timeLine and the chart panel to the bottom
        VBox bottomPanelBox = new VBox();
        bottomPanelBox.getChildren().addAll(this.timeLine, this.chartPanel);
        
        rootPane.setBottom(bottomPanelBox);
        
        rootPane.setRight(inspector);

        rootPane.setLeft(this.consolePanel);
        canvas.setPickOnBounds(false);
        rootPane.setPickOnBounds(false);

        StackPane rootrootPane = new StackPane(canvas, rootPane);


        Scene scene = new Scene(rootrootPane);
        scene.getStylesheets().add("/css/general.css");

        this.setScene(scene);
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

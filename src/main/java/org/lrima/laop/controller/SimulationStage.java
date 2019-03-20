package org.lrima.laop.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.lrima.laop.graphics.panels.ChartPanel;
import org.lrima.laop.graphics.panels.ConsolePanel;
import org.lrima.laop.graphics.panels.InspectorPanel;
import org.lrima.laop.graphics.panels.simulation.timeline.TimeLine;
import org.lrima.laop.physic.PhysicEngine;
import org.lrima.laop.simulation.SimulationBuffer;
import org.lrima.laop.simulation.SimulationSnapshot;

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
    
    private PhysicEngine physicEngine;
    
    private final double WINDOW_WIDTH = 1280;
    private final double WINDOW_HEIGHT = 720;

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
        this.rootPane = new BorderPane();
        
        this.canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.inspector = new InspectorPanel();
        this.consolePanel = new ConsolePanel();
        this.chartPanel = new ChartPanel(this.rootPane);

        this.setOnCloseRequest(e->{
            Platform.exit();
            System.exit(0);
        });
        
        this.loadAllScenes();
        
        this.runSimulation();
    }
    
    /**
     * Run the physic engine with some cars in it
     * @author Clement Bisaillon
     */
    private void runSimulation() {
    	this.physicEngine = new PhysicEngine(this.buffer);
    	this.physicEngine.start();
    }

    /**
     * Adds all the layouts with their components to root Pane
     */
    private void loadAllScenes() {
        this.simulationDrawer = new SimulationDrawer(canvas, buffer, inspector);
        this.timeLine = new TimeLine(this.simulationDrawer, this.buffer);
        
        this.buffer.setOnSnapshotAdded(this::handleNewSnapshow);

        //CANVAS
        ChangeListener<Number> updateWidthHeight = (observable, oldValue, newValue) -> {
            canvas.setHeight(rootPane.getHeight());
            canvas.setWidth(rootPane.getWidth());
            simulationDrawer.repaint();
        };

        rootPane.widthProperty().addListener(updateWidthHeight);
        rootPane.heightProperty().addListener(updateWidthHeight);

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

    private void handleNewSnapshow(SimulationSnapshot snapshot) {
        timeLine.setSliderMax(buffer.getSize()-1);
        if(timeLine.getRealTime()){
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

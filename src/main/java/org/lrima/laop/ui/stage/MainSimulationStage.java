package org.lrima.laop.ui.stage;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.lrima.laop.simulation.GenerationBasedSimulation;
import org.lrima.laop.simulation.Simulation;
import org.lrima.laop.simulation.SimulationEngine;
import org.lrima.laop.ui.SimulationDrawer;
import org.lrima.laop.ui.SimulationView;
import org.lrima.laop.ui.components.ConsolePanel;
import org.lrima.laop.ui.components.LaopMenuBar;
import org.lrima.laop.ui.components.Timeline;
import org.lrima.laop.ui.components.inspector.InspectorPanel;
import org.lrima.laop.utils.Actions.Procedure;


/**
 * Class that displays the simulationEngine with the side panels
 *
 * @author Léonard
 */
public class MainSimulationStage extends Stage {
    private SimulationEngine simulationEngine;
    private Timeline timeline;
    private Canvas canvas;

    private SimulationDrawer simulationDrawer;
    private InspectorPanel inspector;
    private ConsolePanel consolePanel;

    private final double WINDOW_WIDTH = 1280;
    private final double WINDOW_HEIGHT = 720;

    private LaopMenuBar menuBar;
    private VBox bottomBar;

    /**
     * Initialize a new simulationEngine stage with a specific simulationEngine buffer
     * @param simulationEngine the simulationEngine to initialize the simulationEngine stage with
     */
    public MainSimulationStage(SimulationEngine simulationEngine){
        this.setTitle("LAOP : SimulationEngine");
        this.simulationEngine = simulationEngine;

        this.canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.inspector = new InspectorPanel();
        this.consolePanel = new ConsolePanel();
        this.simulationDrawer = new SimulationDrawer(canvas, simulationEngine, inspector);
        this.timeline = new Timeline(simulationDrawer);
        this.simulationDrawer.setSlider(this.timeline.getSliderTimeLine());

        this.menuBar = new LaopMenuBar();
        this.menuBar.init(consolePanel, inspector, simulationDrawer);

        this.setOnCloseRequest(e->{
            Platform.exit();
            System.exit(0);
        });

        this.loadAllScenes();

        this.simulationEngine.setMainScene(this);
        this.simulationEngine.setOnBatchStarted(this::changeSimulation);

        this.simulationDrawer.start();
    }

    private void changeSimulation(SimulationEngine simulationEngine) {
        Simulation simulation = simulationEngine.getSimulation();
        reset(()->{
            SimulationView simulationView = null;
            if(simulation instanceof GenerationBasedSimulation) simulationView = new GeneticStage((GenerationBasedSimulation) simulation);

            if(simulationView != null)
                simulationView.setup(this);
        });
    }

    private void reset(Procedure runLater) {
        Platform.runLater(() -> {
            this.menuBar.reset();
            this.timeline.reset();

            this.bottomBar.getChildren().clear();
            this.bottomBar.getChildren().add(this.timeline);

            runLater.invoke();
        });
    }

    /**
     * Adds all the layouts with their components to root Pane
     */
    private void loadAllScenes() {
        BorderPane uiElements = new BorderPane();

        Pane blankPane = new Pane();
        blankPane.setVisible(false);

        uiElements.setTop(this.menuBar);

        //Add the timeLine and the chart panel to the bottom
        bottomBar = new VBox();
        bottomBar.getChildren().add(this.timeline);

        uiElements.setBottom(bottomBar);
        uiElements.setRight(inspector);

        uiElements.setLeft(this.consolePanel);
        canvas.setPickOnBounds(false);
        uiElements.setPickOnBounds(false);

        StackPane mainPane = new StackPane(canvas, uiElements);

        //CANVAS
        ChangeListener<Number> updateWidthHeight = (observable, oldValue, newValue) -> {
            canvas.setHeight(mainPane.getHeight());
            canvas.setWidth(mainPane.getWidth());
        };

        mainPane.widthProperty().addListener(updateWidthHeight);
        mainPane.heightProperty().addListener(updateWidthHeight);

        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add("/css/general.css");

        scene.setOnKeyPressed(key->{
            if(key.getCharacter().equals("p")){
                this.simulationEngine.pause();
            }
        });

        this.setScene(scene);
    }

    public Timeline getTimeline() {
        return this.timeline;
    }

    public VBox getBotomBar() {
        return bottomBar;
    }

    public LaopMenuBar getMenuBar() {
        return menuBar;
    }
}

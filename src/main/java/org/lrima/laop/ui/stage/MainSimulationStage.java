package org.lrima.laop.ui.stage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.lrima.laop.simulation.LearningEngine;
import org.lrima.laop.ui.I18n;
import org.lrima.laop.ui.SimulationDrawer;
import org.lrima.laop.ui.components.ChartPanel;
import org.lrima.laop.ui.components.ConsolePanel;
import org.lrima.laop.ui.components.LaopMenuBar;
import org.lrima.laop.ui.components.Timeline;
import org.lrima.laop.ui.components.inspector.InspectorPanel;


/**
 * Class that displays the learningEngine with the side panels
 *
 * @author Léonard
 */
public class MainSimulationStage extends Stage {
    private LearningEngine learningEngine;
    private Timeline timeline;
    private Canvas canvas;

    private SimulationDrawer simulationDrawer;
    private InspectorPanel inspector;
    private ConsolePanel consolePanel;

    private final double WINDOW_WIDTH = 1280;
    private final double WINDOW_HEIGHT = 720;

    private LaopMenuBar menuBar;
    private VBox bottomBar;
    private ChartPanel chartPanel;

    /**
     * Initialize a new learningEngine stage with a specific learningEngine buffer
     * @param learningEngine the learningEngine to initialize the learningEngine stage with
     */
    public MainSimulationStage(LearningEngine learningEngine){
        I18n.bind("laop-title", this::setTitle);
        this.learningEngine = learningEngine;

        this.canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.inspector = new InspectorPanel();
        this.consolePanel = new ConsolePanel();
        this.simulationDrawer = new SimulationDrawer(canvas, learningEngine, inspector);
        this.timeline = new Timeline(simulationDrawer);
        this.simulationDrawer.setSlider(this.timeline.getSliderTimeLine());
        this.chartPanel = new ChartPanel();
        this.chartPanel.link(learningEngine.getLearningData());

        this.menuBar = new LaopMenuBar();
        this.menuBar.init(consolePanel, inspector, simulationDrawer);

        this.setOnCloseRequest(e->{
            Platform.exit();
            System.exit(0);
        });

        this.loadAllScenes();

        this.learningEngine.setMainScene(this);
        this.learningEngine.setOnEnd(this::endSimulationAndShowResults);

        JFXButton nextAlgorithm = new JFXButton("%next-algorithm");
        nextAlgorithm.setOnAction((e)-> {
            learningEngine.nextAlgorithm();
        });

        I18n.bind(nextAlgorithm);
        timeline.getChildren().add(nextAlgorithm);

        this.simulationDrawer.start();
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
        bottomBar.getChildren().add(this.chartPanel);

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


        //CHECKBOX
        JFXCheckBox checkBoxRealTime = new JFXCheckBox("");
        checkBoxRealTime.selectedProperty().addListener((obs, oldVal, newVal) -> {
            this.timeline.disable(newVal);
            simulationDrawer.setRealTime(newVal);
            if(newVal)
                learningEngine.resume();
            else
                learningEngine.pause();
        });
        checkBoxRealTime.selectedProperty().setValue(true);
        this.timeline.getChildren().addAll(checkBoxRealTime);


        this.setScene(scene);
    }
    
    private void endSimulationAndShowResults(LearningEngine engine) {
    	Platform.runLater(() -> {
    		ResultStage resultStage = new ResultStage(engine.getLearningData(), engine.getTrainingData());
        	resultStage.show();
        	this.close();
    	});
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

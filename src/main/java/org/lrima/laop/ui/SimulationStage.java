package org.lrima.laop.ui;

import com.jfoenix.controls.JFXSlider;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.lrima.laop.ui.components.PlayButton;
import org.lrima.laop.ui.panels.ChartPanel;
import org.lrima.laop.ui.panels.ConsolePanel;
import org.lrima.laop.ui.panels.inspector.InspectorPanel;
import org.lrima.laop.simulation.SimulationEngine;


/**
 * Class that displays the simulationEngine with the side panels
 *
 * @author Léonard
 */
public class SimulationStage extends Stage {
    private final SimulationEngine simulationEngine;
    private Canvas canvas;

    private SimulationDrawer simulationDrawer;
    private InspectorPanel inspector;
    private ConsolePanel consolePanel;
    private ChartPanel chartPanel;

    private final double WINDOW_WIDTH = 1280;
    private final double WINDOW_HEIGHT = 720;
    private CheckBox checkBoxRealTime;
    private JFXSlider sliderTimeLine;

    private MenuBar menuBar;
    private Button btnGenFinish;

    /**
     * Initialize a new simulationEngine stage with a specific simulationEngine buffer
     * @param simulationEngine the simulationEngine to initialize the simulationEngine stage with
     */
    public SimulationStage(SimulationEngine simulationEngine){
        this.setTitle("LAOP : SimulationEngine");
        this.simulationEngine = simulationEngine;

        this.canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.inspector = new InspectorPanel();
        this.consolePanel = new ConsolePanel();
        this.chartPanel = new ChartPanel(simulationEngine);
        this.simulationDrawer = new SimulationDrawer(canvas, simulationEngine, inspector);
        this.configureMenu();

        this.setOnCloseRequest(e->{
            Platform.exit();
            System.exit(0);
        });

        this.loadAllScenes();

//        this.simulationEngine.setAutoRun(false);
//        this.simulationEngine.setOnGenerationFinish(this::handleGenerationFinish);
        this.simulationEngine.setMainScene(this);

        this.checkBoxRealTime.selectedProperty().setValue(true);

        this.simulationDrawer.start();
    }

    /**
     * Called when the generations finished
     *
     * @param simulationEngine the simulationEngine
     */
    private void handleGenerationFinish(SimulationEngine simulationEngine) {
        btnGenFinish.setDisable(false);
    }

    /**
     * Adds all the layouts with their components to root Pane
     */
    private void loadAllScenes() {
        Node timeLine = timeline();

        BorderPane rootPane = new BorderPane();

        Pane blankPane = new Pane();
        blankPane.setVisible(false);

        rootPane.setTop(this.menuBar);
        //rootPane.setCenter(blankPane);

        //Add the timeLine and the chart panel to the bottom
        VBox bottomPanelBox = new VBox();
        bottomPanelBox.getChildren().add(timeLine);
        bottomPanelBox.getChildren().add(this.chartPanel);

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
        };

        rootrootPane.widthProperty().addListener(updateWidthHeight);
        rootrootPane.heightProperty().addListener(updateWidthHeight);

        this.simulationDrawer.setSlider(sliderTimeLine);

        Scene scene = new Scene(rootrootPane);
        scene.getStylesheets().add("/css/general.css");

        this.setScene(scene);
    }

    /**
     * Cree le menu au dessus de la fenetre avec des boutons
     * @author Clement Bisaillon
     */
    private void configureMenu() {
    	this.menuBar = new MenuBar();

    	Menu windowMenu = new Menu("Window");
    	CheckMenuItem showConsole = new CheckMenuItem("Console");
    	CheckMenuItem showCharts = new CheckMenuItem("Charts");
    	CheckMenuItem showCarInfo = new CheckMenuItem("Car info");
    	showConsole.setSelected(true);
    	showCharts.setSelected(true);
    	showCarInfo.setSelected(true);

    	//Les actions quand nous cliquons sur les boutons
    	showConsole.selectedProperty().addListener((obs, oldVal, newVal) -> {
    		this.consolePanel.setVisible(newVal);
    		this.consolePanel.setManaged(newVal);
    	});

    	showCharts.selectedProperty().addListener((obs, oldVal, newVal) -> {
    		this.chartPanel.setVisible(newVal);
    		this.chartPanel.setManaged(newVal);
    	});

    	showCarInfo.selectedProperty().addListener((obs, oldVal, newVal) -> {
    		this.inspector.setVisible(newVal);
    		this.inspector.setManaged(newVal);
    	});

    	//Two way bind with the inspector panel (When you click a car)
    	this.inspector.visibleProperty().addListener((obs, oldVal, newVal) -> {
    		showCarInfo.setSelected(newVal);
    	});

    	windowMenu.getItems().addAll(showConsole, showCharts, showCarInfo);

    	Menu view = new Menu("View");

    	MenuItem resetView = new MenuItem("Reset View");
    	resetView.setOnAction(e -> simulationDrawer.resetView());

    	view.getItems().add(resetView);

    	this.menuBar.getMenus().addAll(windowMenu, view);
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
                        this.simulationDrawer.startAutodraw(2);
                    else
                        this.simulationDrawer.stopAutoDraw();
                });

        checkBoxRealTime = new CheckBox("");
        checkBoxRealTime.selectedProperty().setValue(false);
        checkBoxRealTime.selectedProperty().addListener((obs, oldVal, newVal) -> {
            this.sliderTimeLine.setDisable(newVal);
            button.setDisable(newVal);
            button.setIsPlaying(false);
            simulationDrawer.setRealTime(newVal);
        });

        sliderTimeLine = new JFXSlider();

        sliderTimeLine.setMax(simulationEngine.getBuffer().getSize());
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

        btnGenFinish = new Button("Next gen");
        btnGenFinish.setOnAction( e-> {
            btnGenFinish.setDisable(true);
//            this.simulationEngine.nextGen();
        });

        root.getChildren().addAll(button, sliderTimeLine, checkBoxRealTime, btnGenFinish);
        root.setSpacing(10);
        root.setPadding(new Insets(5));

        HBox.setHgrow(sliderTimeLine, Priority.ALWAYS);

        root.setStyle("-fx-background-color: rgb(255, 255, 255, 0.5)");

        return root;
    }
}

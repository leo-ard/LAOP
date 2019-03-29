package org.lrima.laop.ui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.lrima.laop.simulation.SimulationEngine;
import org.lrima.laop.ui.components.Timeline;
import org.lrima.laop.ui.components.ChartPanel;
import org.lrima.laop.ui.components.ConsolePanel;
import org.lrima.laop.ui.components.inspector.InspectorPanel;


/**
 * Class that displays the simulationEngine with the side panels
 *
 * @author Léonard
 */
public class MainSimulationStage extends Stage {
    private final SimulationEngine simulationEngine;
    private Timeline timeline;
    private Canvas canvas;

    private SimulationDrawer simulationDrawer;
    private InspectorPanel inspector;
    private ConsolePanel consolePanel;

    private ChartPanel chartPanel;

    private final double WINDOW_WIDTH = 1280;
    private final double WINDOW_HEIGHT = 720;

    private MenuBar menuBar;

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
        this.chartPanel = new ChartPanel(simulationEngine);
        this.simulationDrawer = new SimulationDrawer(canvas, simulationEngine, inspector);
        this.timeline = new Timeline(simulationDrawer);
        this.simulationDrawer.setSlider(this.timeline.getSliderTimeLine());

        this.configureMenu();

        this.setOnCloseRequest(e->{
            Platform.exit();
            System.exit(0);
        });

        this.loadAllScenes();

        this.simulationEngine.setMainScene(this);

        this.simulationDrawer.start();
    }

    /**
     * Adds all the layouts with their components to root Pane
     */
    private void loadAllScenes() {
        BorderPane rootPane = new BorderPane();

        Pane blankPane = new Pane();
        blankPane.setVisible(false);

        rootPane.setTop(this.menuBar);
        //rootPane.setCenter(blankPane);

        //Add the timeLine and the chart panel to the bottom
        VBox bottomPanelBox = new VBox();
        bottomPanelBox.getChildren().add(this.timeline);
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
    	showCarInfo.setSelected(false);

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
}

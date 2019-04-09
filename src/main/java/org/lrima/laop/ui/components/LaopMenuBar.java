package org.lrima.laop.ui.components;

import java.util.ArrayList;

import org.lrima.laop.simulation.GenerationBasedSimulation;
import org.lrima.laop.ui.SimulationDrawer;
import org.lrima.laop.ui.components.inspector.InspectorPanel;

import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class LaopMenuBar extends MenuBar {
    private ArrayList<MenuItem> menuItems;
    private Menu windowMenu;
    private Menu view;
    private Menu simulationMenu;

    //window
    private CheckMenuItem showConsole;
    private CheckMenuItem showCarInfo;
    
    //Simulation
    private CheckMenuItem realTimeCheck;
    
    private MenuItem resetView;

    public void init(ConsolePanel consolePanel, InspectorPanel inspector, SimulationDrawer simulationDrawer){
        windowMenu = new Menu("Window");
        showConsole = new CheckMenuItem("Console");
        showCarInfo = new CheckMenuItem("Car info");
        
        showConsole.setSelected(true);
        showCarInfo.setSelected(false);

        //Les actions quand nous cliquons sur les boutons
        showConsole.selectedProperty().addListener((obs, oldVal, newVal) -> {
            consolePanel.setVisible(newVal);
            consolePanel.setManaged(newVal);
        });

        showCarInfo.selectedProperty().addListener((obs, oldVal, newVal) -> {
            inspector.setVisible(newVal);
            inspector.setManaged(newVal);
        });

        //Two way bind with the inspector panel (When you click a car)
        inspector.visibleProperty().addListener((obs, oldVal, newVal) -> {
            showCarInfo.setSelected(newVal);
        });


        view = new Menu("View");

        resetView = new MenuItem("Reset View");
        resetView.setOnAction(e -> simulationDrawer.resetView());

        windowMenu.getItems().addAll(showConsole, showCarInfo);
        view.getItems().add(resetView);

        this.getMenus().addAll(windowMenu, view);
    }

    public void reset(){
        this.getMenus().clear();
        windowMenu.getItems().clear();
        view.getItems().clear();

        windowMenu.getItems().addAll(showConsole, showCarInfo);
        view.getItems().add(resetView);

        this.getMenus().addAll(windowMenu, view);
    }

    public void addShowCharts(ChartPanel chartPanel) {
        CheckMenuItem showCharts = new CheckMenuItem("Charts");
        showCharts.setSelected(true);


        showCharts.selectedProperty().addListener((obs, oldVal, newVal) -> {
            chartPanel.setVisible(newVal);
            chartPanel.setManaged(newVal);
        });
        windowMenu.getItems().add(showCharts);
    }
    
    public void addRealTime(GenerationBasedSimulation simulation) {
    	this.realTimeCheck = new CheckMenuItem("Real time");
    	this.realTimeCheck.setSelected(false);
    	simulationMenu = new Menu("Simulation");
    	
    	this.realTimeCheck.selectedProperty().addListener((observer, oldVal, newVal) -> {
    		simulation.setRealTime(newVal);
    	});
    	
    	simulationMenu.getItems().add(this.realTimeCheck);
    	this.getMenus().add(simulationMenu);
    }
}

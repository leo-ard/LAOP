package org.lrima.laop.ui.components;

import javafx.scene.control.*;
import org.lrima.laop.ui.I18n;
import org.lrima.laop.ui.SimulationDrawer;
import org.lrima.laop.ui.components.inspector.InspectorPanel;

import java.util.ArrayList;
import java.util.Locale;

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
        windowMenu = new Menu("%window");
        showConsole = new CheckMenuItem("%console");
        showCarInfo = new CheckMenuItem("%car-info");
        
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


        view = new Menu("%view");

        resetView = new MenuItem("%reset-view");
        resetView.setOnAction(e -> simulationDrawer.resetView());

        Menu lang = new Menu("%language");
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioMenuItem lang_en = new RadioMenuItem("%english");
        lang_en.setOnAction((e)->{
            I18n.update(new Locale("en", "CA"));
        });
        lang_en.setToggleGroup(toggleGroup);
        RadioMenuItem lang_fr = new RadioMenuItem("%french");
        lang_fr.setOnAction((e)->{
            I18n.update(new Locale("fr", "CA"));
        });
        lang_fr.setToggleGroup(toggleGroup);

        lang.getItems().addAll(lang_en, lang_fr);
        windowMenu.getItems().addAll(showConsole, showCarInfo);
        view.getItems().add(resetView);

        this.getMenus().addAll(windowMenu, view, lang);

        I18n.bind(windowMenu, showCarInfo, showConsole, view, resetView, lang, lang_en, lang_fr);
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
    
//    public void addRealTime(PhysicEngine simulation) {
//    	this.realTimeCheck = new CheckMenuItem("Real time");
//    	this.realTimeCheck.setSelected(false);
//    	simulationMenu = new Menu("Simulation");
//
//    	this.realTimeCheck.selectedProperty().addListener((observer, oldVal, newVal) -> {
//    		simulation.setRealTime(newVal);
//    	});
//
//    	simulationMenu.getItems().add(this.realTimeCheck);
//    	this.getMenus().add(simulationMenu);
//    }
}

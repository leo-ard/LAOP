package org.lrima.laop.core;

import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.network.carcontrollers.CarController;
import org.lrima.laop.ui.SimulationStage;
import org.lrima.laop.settings.Scope;
import org.lrima.laop.settings.Settings;
import org.lrima.laop.simulation.SimulationEngine;
import org.lrima.laop.simulation.buffer.SimulationBuffer;
import org.lrima.laop.utils.Console;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.HashMap;

/**
 * Main class to manage starting the simulation
 *
 * @author Léonard
 */
public class LAOP {
    //TODO : this is just for testing purpurses
    static final Class<? extends Object> FULLY_CONNECTED = Scope.class;

    Settings settings;

    public LAOP(){
        settings = new Settings();
        defaultSettings();

    }

    /**
     * Met les valeurs par défaut au settings
     *
     */
    private void defaultSettings(){
        settings.set(Settings.GLOBAL_SCOPE, KEY_NUMBER_OF_CARS, DEFAULT_NUMBER_OF_CARS);
        settings.set(Settings.GLOBAL_SCOPE, KEY_TIME_LIMIT, DEFAULT_TIME_LIMIT);
        settings.set(Settings.GLOBAL_SCOPE, KEY_NUMBER_OF_SIMULATION, DEFAULT_NUMBER_OF_SIMULATION);
        settings.set(Settings.GLOBAL_SCOPE, KEY_NUMBER_OF_GENERATIONS, DEFAULT_NUMBER_OF_GENERATIONS);
    }

    /**
     * Ajoute un algorithme à notre platforme
     *
     * @param label l'identifiant de l'algorithme
     * @param algorithmClass la classe de l'algorithme
     * @param settings les configurations de l'algorithme
     */
    public void addAlgorithm(String label, Class<? extends CarController> algorithmClass, Class<? extends LearningAlgorithm> learningClass, HashMap<String, Object> settings){
        if(settings.get(label) != null)
            throw new KeyAlreadyExistsException("The label "+label+" has already been assigned");

        this.settings.set(label, LAOP.KEY_NETWORK_CLASS, algorithmClass);
        this.settings.set(label, LAOP.KEY_LEARNING_CLASS, learningClass);
        settings.forEach((k, v) -> this.settings.set(label, k, v));
    }

    /**
     * Met a jour une valeur dans les settings
     *
     * @param label le nom de l'algorithme
     * @param key la valeur de la key
     * @param value la valeur à laquelle on veut attribué la clée. Peut etre null (signifiant qu'il y a rien à cette valeur)
     */
    public void set(String label, String key, Object value){
        if(settings.scopeExist(label))
            settings.set(label, key, value);
        else
            Console.err("Le scope " + label  +" n'existe pas. Il faut faire addAlgorithm() avant.");
    }

    /**
     * Affiche un panneau pour les modifier les settings
     */
    public void showConfigurations() {
        settings.showPanel();
    }

    /**
     * Checks if the settings are good to start the simulation
     * @return String - The error message
     */
    public String canStartSimulations() {
    	//Check if there are algorithms to run
    	if(this.settings.getLocalScopeKeys().size() <= 0) {
    		return "No algorithms to run.";
    	}
    	
    	return "";
    }
    
    public void startSimulation(SimulationDisplayMode simulationDisplayMode){
        SimulationBuffer simulationBuffer = new SimulationBuffer();
        SimulationEngine simulationEngine = new SimulationEngine(simulationBuffer, settings);

        if(simulationDisplayMode == simulationDisplayMode.WITH_INTERFACE){
            SimulationStage simulationStage = new SimulationStage(simulationEngine);
            simulationStage.show();
        }

        simulationEngine.setOnBatchFinished(sim->Console.info("Batch %s fini", sim.getBatchCount()));

        simulationEngine.start();
    }
    
    /**
     * @return The settings of the laop instance
     */
    public Settings getSettings() {
    	return this.settings;
    }

    public enum SimulationDisplayMode{
        WITH_INTERFACE,
        WITHOUT_INTERFACE
    }

    //ALL DEFAULT KEYS
    public static int DEFAULT_NUMBER_OF_SIMULATION = 10;
    public static String KEY_NUMBER_OF_SIMULATION = "NUMBER OF SIMULATIONS";

    public static final int DEFAULT_NUMBER_OF_CARS = 100;
    public static final String KEY_NUMBER_OF_CARS = "NUMBER OF CARS";

    public static final int DEFAULT_TIME_LIMIT = 100;
    public static final String KEY_TIME_LIMIT = "TIME LIMIT";

    public static final int DEFAULT_NUMBER_OF_GENERATIONS = 10;
    public static final String KEY_NUMBER_OF_GENERATIONS = "NUMBER OF GENERATIONS";

    public static final String KEY_NETWORK_CLASS = "NEURAL_NETWORK_CLASS";
    public static final String KEY_LEARNING_CLASS = "LEARNING_ALGORITHM_CLASS";



}

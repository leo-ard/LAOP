package org.lrima.laop.core;

import org.lrima.laop.controller.SimulationStage;
import org.lrima.laop.settings.Scope;
import org.lrima.laop.settings.Settings;
import org.lrima.laop.simulation.Simulation;
import org.lrima.laop.simulation.SimulationBuffer;
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
    HashMap<String, Class<? extends Object>> algorithms;

    public LAOP(){
        algorithms = new HashMap<>();
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


    }

    /**
     * Ajoute un algorithme à notre platforme
     *
     * @param label l'identifiant de l'algorithme
     * @param algorithmClass la classe de l'algorithme
     * @param settings les configurations de l'algorithme
     */
    public void addAlgorithm(String label, Class<? extends Object> algorithmClass, HashMap<String, Object> settings){
        if(settings.get(label) != null)
            throw new KeyAlreadyExistsException("The label "+label+" has already been assigned");

        algorithms.put(label, algorithmClass);

        this.settings.set(label, "algorithm", algorithmClass);
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

    public void startSimulation(SimulationDisplayMode simulationDisplayMode){
        SimulationBuffer simulationBuffer = new SimulationBuffer();
        Simulation simulation = new Simulation(simulationBuffer, algorithms, settings);

        if(simulationDisplayMode == simulationDisplayMode.WITH_INTERFACE){
            SimulationStage simulationStage = new SimulationStage(simulation);
            simulationStage.show();
        }

        simulation.start();
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

}

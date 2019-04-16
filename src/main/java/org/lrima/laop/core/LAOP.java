package org.lrima.laop.core;

import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.network.carcontrollers.CarController;
import org.lrima.laop.network.carcontrollers.ManualCarController;
import org.lrima.laop.network.concreteLearning.DL4JLearning;
import org.lrima.laop.network.concreteLearning.GeneticLearning;
import org.lrima.laop.network.concreteNetworks.DL4J;
import org.lrima.laop.network.concreteNetworks.FUCONN;
import org.lrima.laop.network.concreteNetworks.NEAT;
import org.lrima.laop.plugin.PluginLoader;
import org.lrima.laop.settings.Settings;
import org.lrima.laop.settings.option.OptionClass;
import org.lrima.laop.simulation.BetterEnvironnement;
import org.lrima.laop.simulation.Environnement;
import org.lrima.laop.simulation.LearningEngine;
import org.lrima.laop.simulation.buffer.SimulationBuffer;
import org.lrima.laop.ui.stage.MainSimulationStage;
import org.lrima.laop.utils.ClassUtils;
import org.lrima.laop.utils.Console;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Main class to manage starting the simulation
 *
 * @author Léonard
 * @version $Id: $Id
 */
public class LAOP {
    //TODO : this is just for testing purpurses
    private ArrayList<Class<? extends LearningAlgorithm>> learningAlgorithmsClasses;
    private ArrayList<Class<? extends CarController>> neuralNetworksClasses;
    private ArrayList<Class<? extends Environnement>> environnements;

    Settings settings;
    /**
     * <p>Constructor for LAOP.</p>
     */
    public LAOP(){
        learningAlgorithmsClasses = new ArrayList<>();
        learningAlgorithmsClasses.add(GeneticLearning.class);
        learningAlgorithmsClasses.add(DL4JLearning.class);

        neuralNetworksClasses = new ArrayList<>();
        neuralNetworksClasses.add(NEAT.class);
        neuralNetworksClasses.add(FUCONN.class);
        neuralNetworksClasses.add(ManualCarController.class);
        neuralNetworksClasses.add(DL4J.class);

        environnements = new ArrayList<>();
        environnements.add(BetterEnvironnement.class);

        //Load the algorithm's jar
        PluginLoader.addDir("algos/");
        try {
            PluginLoader.load(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        settings = new Settings();
        defaultSettings();
    }

    /**
     * Met les valeurs par défaut au settings
     */
    private void defaultSettings(){
        settings.set(Settings.GLOBAL_SCOPE, KEY_NUMBER_OF_CARS, DEFAULT_NUMBER_OF_CARS);
        settings.set(Settings.GLOBAL_SCOPE, KEY_TIME_LIMIT, DEFAULT_TIME_LIMIT);
        settings.set(Settings.GLOBAL_SCOPE, KEY_NUMBER_OF_SIMULATION, DEFAULT_NUMBER_OF_SIMULATION);
        settings.set(Settings.GLOBAL_SCOPE, KEY_NUMBER_OF_GENERATIONS, DEFAULT_NUMBER_OF_GENERATIONS);
        settings.set(Settings.GLOBAL_SCOPE, KEY_NUMBER_OF_SENSORS, DEFAULT_NUMBER_OF_SENSORS);
        settings.set(Settings.GLOBAL_SCOPE, KEY_ENVIRONNEMENT_CLASS, new OptionClass<>(DEFAULT_ENVIRONNEMENT_CLASS, environnements, clazz -> false));
    }

    /**
     * Ajoute un algorithme à notre platforme
     *
     * @param label l'identifiant de l'algorithme
     * @param algorithmClass la classe de l'algorithme
     * @param settings les configurations de l'algorithme
     * @param learningClass a {@link java.lang.Class} object.
     */
    public void addAlgorithm(String label, Class<? extends CarController> algorithmClass, Class<? extends LearningAlgorithm> learningClass, HashMap<String, Object> settings){
        if(this.settings.scopeExist(label))
        	//todo: show error dialog
            throw new KeyAlreadyExistsException("The label "+label+" has already been assigned");

        this.settings.set(label, LAOP.KEY_NETWORK_CLASS, new OptionClass<>(algorithmClass, neuralNetworksClasses, (clazz) -> ClassUtils.checkIfGenericOfInterface((Class)this.settings.get(label, LAOP.KEY_LEARNING_CLASS), (Class)clazz)));
        this.settings.set(label, LAOP.KEY_LEARNING_CLASS, new OptionClass<>(learningClass, learningAlgorithmsClasses, (clazz) -> ClassUtils.checkIfGenericOfInterface((Class)this.settings.get(label, LAOP.KEY_LEARNING_CLASS), (Class)clazz)));

        if(settings != null) settings.forEach((k, v) -> this.settings.set(label, k, v));
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
     * Checks if the settings are good to start the simulation
     *
     * @return String - The error message
     */
    public String canStartSimulations() {
    	//Check if there are algorithms to run
    	if(this.settings.getLocalScopeKeys().size() <= 0) {
    		return "No algorithms to run.";
    	}

    	return "";
    }

    /**
     * <p>startSimulation.</p>
     *
     * @param simulationDisplayMode a {@link org.lrima.laop.core.LAOP.SimulationDisplayMode} object.
     */
    public void startSimulation(SimulationDisplayMode simulationDisplayMode){
    	//check if all the global scopes of the scopes are good

        SimulationBuffer simulationBuffer = new SimulationBuffer();
        LearningEngine learningEngine = new LearningEngine(simulationBuffer, this.settings);

        if(simulationDisplayMode == simulationDisplayMode.WITH_INTERFACE){
            MainSimulationStage mainSimulationStage = new MainSimulationStage(learningEngine);
            mainSimulationStage.show();
        }

        learningEngine.start();
    }

    /**
     * <p>Getter for the field <code>settings</code>.</p>
     *
     * @return The settings of the laop instance
     */
    public Settings getSettings() {
    	return this.settings;
    }



    public enum SimulationDisplayMode{
        WITH_INTERFACE,
        WITHOUT_INTERFACE;
    }
    /**
     * <p>Getter for the field <code>neuralNetworksClasses</code>.</p>
     *
     * @return a {@link java.util.ArrayList} object.
     */
    public ArrayList<Class<? extends CarController>> getNeuralNetworksClasses() {
        return neuralNetworksClasses;
    }

    /**
     * <p>Getter for the field <code>learningAlgorithmsClasses</code>.</p>
     *
     * @return a {@link java.util.ArrayList} object.
     */
    public ArrayList<Class<? extends LearningAlgorithm>> getLearningAlgorithmsClasses() {
        return learningAlgorithmsClasses;
    }


    //ALL DEFAULT KEYS
    /** Constant <code>DEFAULT_NUMBER_OF_SIMULATION=10</code> */
    public static final int DEFAULT_NUMBER_OF_SIMULATION = 10;
    /** Constant <code>KEY_NUMBER_OF_SIMULATION="NUMBER OF SIMULATIONS"</code> */
    public static final String KEY_NUMBER_OF_SIMULATION = "NUMBER OF SIMULATIONS";

    /** Constant <code>DEFAULT_NUMBER_OF_CARS=100</code> */
    public static final int DEFAULT_NUMBER_OF_CARS = 100;
    /** Constant <code>KEY_NUMBER_OF_CARS="NUMBER OF CARS"</code> */
    public static final String KEY_NUMBER_OF_CARS = "NUMBER OF CARS";

    /** Constant <code>DEFAULT_TIME_LIMIT=100</code> */
    public static final int DEFAULT_TIME_LIMIT = 100;
    /** Constant <code>KEY_TIME_LIMIT="TIME LIMIT"</code> */
    public static final String KEY_TIME_LIMIT = "TIME LIMIT";

    /** Constant <code>DEFAULT_NUMBER_OF_GENERATIONS=10</code> */
    public static final int DEFAULT_NUMBER_OF_GENERATIONS = 10;
    /** Constant <code>KEY_NUMBER_OF_GENERATIONS="NUMBER OF GENERATIONS"</code> */
    public static final String KEY_NUMBER_OF_GENERATIONS = "NUMBER OF GENERATIONS";

    /** Constant <code>KEY_NETWORK_CLASS="NEURAL NETWORK CLASS"</code> */
    public static final String KEY_NETWORK_CLASS = "NEURAL NETWORK CLASS";
    /** Constant <code>KEY_LEARNING_CLASS="LEARNING ALGORITHM CLASS"</code> */
    public static final String KEY_LEARNING_CLASS = "LEARNING ALGORITHM CLASS";

    /** Constant <code>KEY_NUMBER_OF_SENSORS="NUMBER OF SENSORS"</code> */
    public static final String KEY_NUMBER_OF_SENSORS = "NUMBER OF SENSORS";
    /** Constant <code>DEFAULT_NUMBER_OF_SENSORS</code> */
    public static final int DEFAULT_NUMBER_OF_SENSORS = 5;

    /** Constant <code>KEY_ENVIRONNEMENT_CLASS = "ENVIRONNEMENT_CLASS"</code> */
    public static final String KEY_ENVIRONNEMENT_CLASS = "ENVIRONNEMENT_CLASS";
    /** Constant <code>DEFAULT_ENVIRONNEMENT_CLASS</code> */
    public static final Class<? extends Environnement> DEFAULT_ENVIRONNEMENT_CLASS = BetterEnvironnement.class;
}

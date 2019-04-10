package org.lrima.laop.simulation;

import org.lrima.laop.core.LAOP;
import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.network.concreteLearning.GeneticLearning;
import org.lrima.laop.network.genetics.GeneticNeuralNetwork;
import org.lrima.laop.physic.PhysicEngine;
import org.lrima.laop.physic.concreteObjects.SimpleCar;
import org.lrima.laop.simulation.data.GenerationData;
import org.lrima.laop.utils.Console;
import org.lrima.laop.utils.Actions.Action;

import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;

/**
 * A simulation that can take the LearningAlgorithms that uses generation bases learning
 *
 * @author Léonard
 */
public class GenerationBasedSimulation extends Simulation<GeneticLearning>{
    private int simulationCount = 1;
    private int generationCount = 1;

    ArrayList<Action<GenerationBasedSimulation>> onSimulationFinish;
    ArrayList<Action<GenerationBasedSimulation>> onGenerationFinish;

    private PhysicEngine physicEngine;

    private boolean autoRun;
    private BooleanProperty realTime;
    private LearningAlgorithm learningAlgorithm;

    private ArrayList<GeneticNeuralNetwork> cars;

    /**
     * Creates a new Generation
     *
     * @param simulationEngine the simulation engine to refer to
     */
    public GenerationBasedSimulation(SimulationEngine simulationEngine){
        super(simulationEngine);

        this.onSimulationFinish = new ArrayList<>();
        this.onGenerationFinish = new ArrayList<>();
        this.learningAlgorithm = simulationEngine.generateLearningAlgorithm();
        this.realTime = new SimpleBooleanProperty();
        autoRun = true;
    }

    /**
     * Starts the simulation
     *
     */
    @Override
    public void start() {
        this.simulateGeneration();
    }

    //temp
    @Override
    public void pause() {
        this.physicEngine.setPause(!this.physicEngine.getPause());
    }

    /**
     * Confifure all the cars for the simulation
     * @return
     */
    private ArrayList<SimpleCar> configureCar(){
        if(generationCount == 1){
        	//Reset the cars
            ArrayList<SimpleCar> cars = generateCarObjects(100, (i) -> simulationEngine.generateCurrentNetwork());
            learningAlgorithm.init(cars);
            return cars;
        }
        else{
        	//Make the cars learn
            cars = learningAlgorithm.learn(cars);
            ArrayList<SimpleCar> simpleCars = generateCarObjects(cars.size(), (i) -> cars.get(i));
            learningAlgorithm.init(simpleCars);
            return simpleCars;
        }
    }

    
    /**
     * Precedes to next generation
     */
    public void nextGen() {
    	incrementGeneration();
    	this.simulateGeneration();
    }

    /**
     *
     * Increment the generation count and batch count. Calls the listener accordingly.
     */
    private void incrementGeneration(){
    	int maxSimulations = (int) this.simulationEngine.getSettings().get(LAOP.KEY_NUMBER_OF_GENERATIONS);

        //CHECK IF GENERATION IS OUT OF BOUNDS
        if(generationCount > maxSimulations){
        	Console.info("Simulation " + this.simulationCount + " ended");
            //FIRE LISTENERS
        	this.onSimulationFinish.forEach(simulationAction -> simulationAction.handle(this));
            generationCount = 1;
            simulationCount++;
            

            //CHECK IF SIMULATION IS OUT OF BOUND
            if(simulationCount > (int) this.simulationEngine.getSettings().get(LAOP.KEY_NUMBER_OF_SIMULATION)){
                //FIRE END LISTENER
                this.end.forEach(e -> e.accept(this));
            }
        }else {
        	Console.info("Generation " + generationCount + " / " + maxSimulations + " completed");
        	this.onGenerationFinish.forEach(simulationAction -> simulationAction.handle(this));
            generationCount++;
        }
    }

    /**
     * Simulate a generation
     */
    private void simulateGeneration(){
        this.simulationEngine.getBuffer().clear();
        this.physicEngine = new PhysicEngine(this.simulationEngine.getBuffer(), this.simulationEngine.getMap());
        
        this.realTime.addListener((observer, oldVal, newVal) -> {
        	this.physicEngine.setRealTime(newVal);
        });
        
        this.physicEngine.setFinishingConditions(PhysicEngine.ALL_CARS_DEAD);
        this.physicEngine.getCars().addAll(configureCar());

        this.physicEngine.setOnPhysicEngineFinishOnce(engine -> {
            if(this.autoRun){
                cars = (ArrayList<GeneticNeuralNetwork>) engine.extractNetworks();
                this.nextGen();
            }
        });
        this.physicEngine.start();
    }

    /**
     * Get the generation data of the current generation
     * Used to populate the chart panel
     * @author Clement Bisaillon
     * @return the generation data of the current generation
     */
    public GenerationData getGenerationData() {
        GenerationData data = new GenerationData(this.generationCount);
        data.setAverageFitness(cars);
        
        return data;
    }

    public void setOnSimulationFinish(Action<GenerationBasedSimulation> onSimulationFinish) {
        this.onSimulationFinish.add(onSimulationFinish);
    }

    public void setOnGenerationFinish(Action<GenerationBasedSimulation> onGenerationFinish){
        this.onGenerationFinish.add(onGenerationFinish);
    }

    public int getGenerationCount() {
        return generationCount;
    }

    public int getSimulationCount() {
        return simulationCount;
    }

    //TODO
    /**
     *
     * @param autoRun false if the simulation should wait input before going to the next generation or going as soon as the generation is finished
     */
    public void setAutoRun(boolean autoRun) {
        this.autoRun = autoRun;
    }
    
    public BooleanProperty realTimeProperty() {
    	return this.realTime;
    }
    public final boolean getRealTime() {
    	return this.realTime.get();
    }
    public final void setRealTime(boolean value) {
    	this.realTime.set(value);
    }
}

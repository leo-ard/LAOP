package org.lrima.laop.simulation;

import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion;
import javafx.stage.Stage;
import org.lrima.laop.core.LAOP;
import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.physic.CarControls;
import org.lrima.laop.settings.LockedSetting;
import org.lrima.laop.settings.Settings;
import org.lrima.laop.simulation.buffer.SimulationBuffer;
import org.lrima.laop.simulation.data.AlgorithmData;
import org.lrima.laop.utils.Actions.Action;
import org.lrima.laop.utils.Console;

import java.util.ArrayList;

/**
 * A engine that will simulate all the learning engines
 *
 * @author Léonard
 */
public class LearningEngine implements Runnable{
    public static double DELTA_T = 0.05;
    private SimulationBuffer simulationBuffer;
    private Settings settings;

    private int batchCount;

    private ArrayList<Action<LearningEngine>> onBatchStarted;
    private ArrayList<Action<LearningEngine>> onEnd;

    private LearningAlgorithm learningAlgorithm;
    private Environnement environnement;
    private AlgorithmData learningData;
    private AlgorithmData trainedData;
    private Stage mainScene;

    private final Object lock = new Object();

    private boolean alive;
    private boolean pause;
    private boolean paused = false;

    /**
     * Creates a new learning engine
     *
     * @param simulationBuffer the simulation buffer to write to
     * @param settings the settings to get the learning algorithms from
     */
    public LearningEngine(SimulationBuffer simulationBuffer, Settings settings) {
        this.simulationBuffer = simulationBuffer;
        this.settings = settings;
        this.batchCount = 0;

        this.onBatchStarted = new ArrayList<>();
        this.onEnd = new ArrayList<>();
        this.learningData = new AlgorithmData();
        this.trainedData = new AlgorithmData();
    }

    /**
     * Start the learning engine by creating a new thread
     *
     */
    public void start(){
        Thread currentThread = new Thread(this);
        currentThread.start();
    }

    @Override
    public void run() {
        synchronized (lock){
            runAll();
        }
    }

    /**
     * Run all the learning algorithms one after the other
     */
    private void runAll(){
        this.environnement = generateEnvironnement();
        this.environnement.init(this);

        LearningAlgorithm[] trained = new LearningAlgorithm[this.settings.getLocalScopeKeys().size()];

        //train
        for (this.batchCount = 0; batchCount < this.settings.getLocalScopeKeys().size(); batchCount++) {
            alive = true;
            Console.info("Batch %s started", this.getBatchCount() + 1);
            this.onBatchStarted.forEach(b -> b.handle(this));

            learningAlgorithm = generateLearningAlgorithm();
            learningAlgorithm.train(environnement, this);


            trained[batchCount] = learningAlgorithm;


        }

        //TODO : faire le cas ou c'est pas un multi

        environnement = generateEnvironnement();
        environnement.init(this);

        if(environnement instanceof MultiAgentEnvironnement){
            int episode = 0;
            while(episode < 100){
                ArrayList<Agent> agents = ((MultiAgentEnvironnement) environnement).reset(trained.length);
                while (!environnement.isFinished()){
                    ArrayList<CarControls> carControls = new ArrayList<>();
                    for (int i = 0; i < trained.length; i++) {
                        carControls.add(trained[i].test(agents.get(i)));
                    }
                    agents = ((MultiAgentEnvironnement) environnement).step(carControls);

                    environnement.render();

                }
                for (int i = 0; i < trained.length; i++) {
                    trainedData.put(this.settings.getLocalScopeKeys().get(i), agents.get(i).reward);
                }
                episode++;
            }
        }
        else
            throw new RuntimeException("Do not support sigle environnemnt yet");

        onEnd.forEach((a) -> a.handle(this));
        learningData.toCsv("learning");
        trainedData.toCsv("training");
    }


    /**
     * Creates a new Environnement instance depending on the one selected in the settings
     *
     * @return the selected environnement
     */
    private Environnement generateEnvironnement() {
        Class<? extends Environnement> environnementClass = (Class<? extends Environnement>) settings.get(Settings.GLOBAL_SCOPE, LAOP.KEY_ENVIRONNEMENT_CLASS);

        try {
            return environnementClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        return new BetterEnvironnement();
    }

    /**
     * Evaluate a given learning algorithm
     *
     * @param learningAlgorithm the learning algorithm that must be tested
     */
    public void evaluate(LearningAlgorithm learningAlgorithm){
        if(pause){
            synchronized (lock){
                try {
                    paused = true;
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        int MAX = 10;
        int episode = 0;
        Agent agent = this.environnement.reset();
        double sum = 0;
        while(episode < MAX){
            while(!this.environnement.isFinished()) {
                agent = this.environnement.step(learningAlgorithm.test(agent));
                environnement.render();
            }
            sum += agent.reward;
            episode++;
            agent = this.environnement.reset();
        }

        learningData.put(getCurrentScopeName(), sum/MAX);
    }


    /**
     * The buffer used in this simulation
     *
     * @return the simulation buffer
     */
    public SimulationBuffer getBuffer() {
        return simulationBuffer;
    }

    private LearningAlgorithm generateLearningAlgorithm() {
        Class<? extends LearningAlgorithm> learningClass = (Class<? extends LearningAlgorithm>) settings.get(this.getCurrentScopeName(), LAOP.KEY_LEARNING_CLASS);

        try {
            return learningClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Pause the simulation
     */
    public void pause(){
        this.pause = true;
    }

    /**
     * Resume the simulation
     */
    public void resume(){
        if(paused){
            synchronized (lock){
                this.pause = false;
                lock.notifyAll();
            }
            paused = false;
        }
    }

    /**
     * Sets an action to do at the start of each batch
     *
     * @param onBatchFinished the action to perform
     */
    public void setOnBatchStarted(Action<LearningEngine> onBatchFinished) {
        this.onBatchStarted.add(onBatchFinished);
    }

    /**
     * Sets an action at the end of the all the simulations
     *
     * @param onEnd the action to perform
     */
    public void setOnEnd(Action<LearningEngine> onEnd) {
        this.onEnd.add(onEnd);
    }

    /**
     * gets the batch passed till now
     *
     * @return the batch count
     */
    public int getBatchCount() {
        return batchCount;
    }

    /**
     * Used to parse the listeners to the algorithms that need it (need key listeners)
     * @param mainScene the main JAVAFX scene
     */
    public void setMainScene(Stage mainScene) {
        this.mainScene = mainScene;
    }

    /**
     * Gets the settings
     *
     * @return the settings
     */
    public LockedSetting getSettings() {
        return this.settings.lock(this.getCurrentScopeName());
    }

    /**
     * Gets the environnement
     *
     * @return the environnement
     */
    public Environnement getEnvironnement() {
        return this.environnement;
    }

    /**
     * Gets the current scope name
     *
     * @return the current scope name
     */
    private String getCurrentScopeName() {
        if(batchCount >= this.settings.getLocalScopeKeys().size())
            return Settings.GLOBAL_SCOPE;
    	return this.settings.getLocalScopeKeys().get(this.batchCount);
    }

    /**
     * Gets the main scene
     *
     * @return the main scene
     */
    public Stage getMainScene() {
        return mainScene;
    }


    /**
     * Returns true if the simulation should continue
     *
     * @return
     */
    public boolean whileButtonNotPressed() {
        return alive;
    }

    /**
     * Goes to the next algorithm
     *
     */
    public void nextAlgorithm(){
        alive = false;
    }

    /**
     * Gets the training data
     *
     * @return the training data
     */
    public AlgorithmData getTrainingData() {
        return trainedData;
    }

    /**
     * Gets the learning data
     *
     * @return the learning data
     */
    public AlgorithmData getLearningData() {
        return learningData;
    }
}

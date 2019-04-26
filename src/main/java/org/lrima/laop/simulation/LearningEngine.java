package org.lrima.laop.simulation;

import javafx.stage.Stage;
import org.lrima.laop.core.LAOP;
import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.settings.LockedSetting;
import org.lrima.laop.settings.Scope;
import org.lrima.laop.settings.Settings;
import org.lrima.laop.simulation.buffer.SimulationBuffer;
import org.lrima.laop.simulation.data.AlgorithmsData;
import org.lrima.laop.utils.Actions.Action;
import org.lrima.laop.utils.Console;

import java.util.ArrayList;

public class LearningEngine implements Runnable{
    public static Stage mainScene;
    private SimulationBuffer simulationBuffer;
    private Settings settings;

    private int batchCount;

    ArrayList<Action<LearningEngine>> onBatchStarted;
    ArrayList<Action<LearningEngine>> onEnd;

    private LearningAlgorithm learningAlgorithm;
    private Environnement environnement;
    private AlgorithmsData learningData;
    private AlgorithmsData trainedData;

    private Thread currentThread;
    private SimulationBuffer displayBuffer;

    public static double DELTA_T = 0.05;

    public LearningEngine(SimulationBuffer simulationBuffer, Settings settings) {
        this.simulationBuffer = simulationBuffer;
        displayBuffer = new SimulationBuffer();
        this.settings = settings;
        this.batchCount = 0;

        this.onBatchStarted = new ArrayList<>();
        this.onEnd = new ArrayList<>();
        this.learningData = new AlgorithmsData();
    }

    public void start(){
        currentThread = new Thread(this);
        currentThread.start();
    }

    @Override
    public void run() {
        this.environnement = generateEnvironnement();
        this.environnement.init(this);

        LearningAlgorithm[] trained = new LearningAlgorithm[this.settings.getLocalScopeKeys().size()];

        //train
        for (this.batchCount = 0; batchCount < this.settings.getLocalScopeKeys().size(); batchCount++) {
            Console.info("Batch %s started", this.getBatchCount() + 1);
            this.onBatchStarted.forEach(b -> b.handle(this));

            learningAlgorithm = generateLearningAlgorithm();
            learningAlgorithm.train(environnement);

            learningData.put(getCurrentScopeName(), environnement.getData());
            trained[batchCount] = learningAlgorithm;
        }

        //TODO : faire le cas ou c'est pas un multi

        environnement = generateEnvironnement();
        environnement.init(this);
        trainedData = environnement.evaluate(trained,100);


        onEnd.forEach((a) -> a.handle(this));
        learningData.toCsv();
        trainedData.toCsv();
    }

    private Environnement generateEnvironnement() {
        Class<? extends Environnement> environnementClass = (Class<? extends Environnement>) settings.get(Settings.GLOBAL_SCOPE, LAOP.KEY_ENVIRONNEMENT_CLASS);

        try {
            return environnementClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        return new BetterEnvironnement();
    }


    public SimulationBuffer getBuffer() {
        return simulationBuffer;
    }

    LearningAlgorithm generateLearningAlgorithm() {
        Class<? extends LearningAlgorithm> learningClass = (Class<? extends LearningAlgorithm>) settings.get(this.getCurrentScopeName(), LAOP.KEY_LEARNING_CLASS);

        try {
            return learningClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setOnBatchStarted(Action<LearningEngine> onBatchFinished) {
        this.onBatchStarted.add(onBatchFinished);
    }

    public void setOnEnd(Action<LearningEngine> onEnd) {
        this.onEnd.add(onEnd);
    }

    public int getBatchCount() {
        return batchCount;
    }

    /**
     * ONLY USED TO CONTROL THE CAR WITH THE KEYBORD (need key listeners)
     * @param mainScene the main JAVAFX scene
     */
    public void setMainScene(Stage mainScene) {
        this.mainScene = mainScene;
    }

    public LockedSetting getSettings() {
        return this.settings.lock(this.getCurrentScopeName());
    }

    public Environnement getEnvironnement() {
        return this.environnement;
    }

    public void pause() {
//        this.environnement;
    }

    public String getCurrentScopeName() {
    	return this.settings.getLocalScopeKeys().get(this.batchCount);
    }
    
    public Scope getCurrentScope() {
    	return this.settings.getLocalScopes().get(this.batchCount);
    }

    public Stage getMainScene() {
        return mainScene;
    }

    public LearningAlgorithm getCurrentLearning() {
        return learningAlgorithm;
    }

    public SimulationBuffer getDisplayBuffer() {
        return displayBuffer;
    }
}

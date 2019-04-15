package org.lrima.laop.simulation;

import java.util.ArrayList;

import org.lrima.laop.core.LAOP;
import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.network.carcontrollers.CarController;
import org.lrima.laop.network.carcontrollers.ManualCarController;
import org.lrima.laop.settings.LockedSetting;
import org.lrima.laop.settings.Scope;
import org.lrima.laop.settings.Settings;
import org.lrima.laop.simulation.buffer.SimulationBuffer;
import org.lrima.laop.simulation.data.ResultData;
import org.lrima.laop.utils.Console;
import org.lrima.laop.utils.Actions.Action;

import javafx.stage.Stage;

public class SimulationEngine implements Runnable{
    private SimulationBuffer simulationBuffer;
    private Settings settings;

    private int batchCount;

    ArrayList<Action<SimulationEngine>> onBatchStarted;
    ArrayList<Action<SimulationEngine>> onEnd;

    private Stage mainScene;

    
    private ResultData data;
    private LearningAlgorithm learningAlgorithm;
    private Environnement environnement;

    private Thread currentThread;
    private SimulationBuffer displayBuffer;

    public SimulationEngine(SimulationBuffer simulationBuffer, Settings settings) {
        this.simulationBuffer = simulationBuffer;
        displayBuffer = new SimulationBuffer();
        this.settings = settings;
        this.batchCount = 0;

        this.onBatchStarted = new ArrayList<>();
        this.onEnd = new ArrayList<>();
        this.data = new ResultData(this.settings.getLocalScopes());
    }

    public void start(){
        currentThread = new Thread(this);
        currentThread.start();
    }

//    private void nextBatch() {
//
////        envrionnement = generateEnvironnement();
//
//
//
//
//        envrionnement.start();
//        envrionnement.setEnd((simulation) -> {
//        	this.currentScopeIndex++;
//        	//Check if all algorithms have been runned
//        	if(this.currentScopeIndex >= this.settings.getLocalScopeKeys().size()) {
//        		this.onEnd.forEach(b -> b.handle(this));
//
//        		return;
//        	}
//        	nextBatch();
//        });

//    }
    @Override
    public void run() {
        this.environnement = generateEnvironnement();
        this.environnement.initialise(this);

        for (this.batchCount = 0; batchCount < this.settings.getLocalScopeKeys().size(); batchCount++) {
            Console.info("Batch %s started", this.getBatchCount() + 1);
            this.onBatchStarted.forEach(b -> b.handle(this));

            learningAlgorithm = generateLearningAlgorithm();
            while (!this.environnement.isFinished()) {
                learningAlgorithm.cycle(environnement);
            }
           
            this.data.addData(this.settings.getLocalScopeKeys().get(batchCount), this.environnement.getBatchData());
            
            environnement.setFinished(false);
        }
        
        this.onEnd.forEach((a) -> a.handle(this));
    }

    private Environnement generateEnvironnement() {
        Class<? extends Environnement> environnementClass = (Class<? extends Environnement>) settings.get(Settings.GLOBAL_SCOPE, LAOP.KEY_ENVIRONNEMENT_CLASS);

        try {
            return environnementClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        return new GenerationBasedEnvironnement();
    }


    public SimulationBuffer getBuffer() {
        return simulationBuffer;
    }



    <T extends CarController> T generateCurrentNetwork() {
        Class<? extends CarController> carClass = (Class<? extends CarController>) settings.get(this.getCurrentScopeName(), LAOP.KEY_NETWORK_CLASS);

        try {
            T carController = (T) carClass.newInstance();

            try{
                carController.init(this.getSettings());
            }catch(Exception e){
                e.printStackTrace();
            }

            if(carController instanceof ManualCarController)
                ((ManualCarController) carController).configureListeners(this.mainScene);

            return carController;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return (T) new ManualCarController();
    }

    LearningAlgorithm<? extends CarController> generateLearningAlgorithm() {
        Class<? extends LearningAlgorithm> learningClass = (Class<? extends LearningAlgorithm>) settings.get(this.getCurrentScopeName(), LAOP.KEY_LEARNING_CLASS);

        try {
            return learningClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setOnBatchStarted(Action<SimulationEngine> onBatchFinished) {
        this.onBatchStarted.add(onBatchFinished);
    }

    public void setOnEnd(Action<SimulationEngine> onEnd) {
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
    
    public ResultData getData() {
    	return this.data;
    }

    public LearningAlgorithm getCurrentLearning() {
        return learningAlgorithm;
    }

    public SimulationBuffer getDisplayBuffer() {
        return displayBuffer;
    }
}

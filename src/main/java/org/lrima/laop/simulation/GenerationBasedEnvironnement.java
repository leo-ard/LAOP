//package org.lrima.laop.simulation;
//
//import javafx.beans.property.BooleanProperty;
//import javafx.beans.property.SimpleBooleanProperty;
//import org.lrima.laop.core.LAOP;
//import org.lrima.laop.network.carcontrollers.CarController;
//import org.lrima.laop.physic.PhysicEngine;
//import org.lrima.laop.physic.abstractObjects.AbstractCar;
//import org.lrima.laop.physic.concreteObjects.SimpleCar;
//import org.lrima.laop.simulation.buffer.SimulationBuffer;
//import org.lrima.laop.simulation.data.BatchData;
//import org.lrima.laop.simulation.data.GenerationData;
//import org.lrima.laop.simulation.data.SimulationData;
//import org.lrima.laop.simulation.map.AbstractMap;
//import org.lrima.laop.simulation.map.MazeMap;
//import org.lrima.laop.simulation.sensors.ProximityLineSensor;
//import org.lrima.laop.utils.Actions.Procedure;
//import org.lrima.laop.utils.Console;
//import org.lrima.laop.utils.NetworkUtils;
//import org.lrima.laop.utils.math.Vector2d;
//
//import java.awt.geom.Point2D;
//import java.util.ArrayList;
//import java.util.function.BiFunction;
//import java.util.function.Consumer;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
///**
// * A simulation that can take the LearningAlgorithms that uses generation bases learning
// *
// * @author Léonard
// */
//public class GenerationBasedEnvironnement implements Environnement, Runnable {
//    private static final int TIME_LIMIT = 300;
//    private int simulationCount = 1;
//    private int generationCount = 1;
//
//    ArrayList<Procedure> onSimulationFinish;
//    ArrayList<Procedure> onGenerationFinish;
//
//    private boolean autoRun;
//    private BooleanProperty realTime;
//
//    private ArrayList<? extends CarController> cars;
//    private boolean finished;
//
//    private SimulationBuffer buffer;
//    private MazeMap map;
//    private SimulationEngine simulationEngine;
//
//    private BatchData currentBatchData;
//    private SimulationData currentSimulationData;
//
//    private Thread parallelThread;
//    private boolean parallelThreadAlive;
//    private PhysicEngine parallelPhysicEngine;
//    private PhysicEngine physicEngine;
//    private BiFunction<Environnement, AbstractCar, Double> fitnessFunction = NetworkUtils.RANGE_FITNESS;
//
//    /**
//     * Creates a new Generation
//     *
//     */
//    public GenerationBasedEnvironnement() {
//        this.onSimulationFinish = new ArrayList<>();
//        this.onGenerationFinish = new ArrayList<>();
//        this.realTime = new SimpleBooleanProperty();
//        this.autoRun = true;
//        this.finished = false;
//        this.parallelThreadAlive = true;
//    }
//
//    @Override
//    public <T extends CarController> ArrayList<T> evaluate(ArrayList<T> cars) {
//        // resetCarArray
//        if(generationCount == 1){
//            cars = new ArrayList<>();
//            for (int i = 0; i < (int) this.simulationEngine.getSettings().get(LAOP.KEY_NUMBER_OF_CARS); i++) {
//                cars.add(this.simulationEngine.generateCurrentNetwork());
//            }
//        }
//
//        physicEngine = configureSimulation(cars);
//        physicEngine.run();
//
//        ArrayList<AbstractCar> cars1 = physicEngine.getCars();
//        calculateFitness(cars1);
//        this.cars = (ArrayList<T>) cars1.stream().map(AbstractCar::getController).collect(Collectors.toCollection(ArrayList::new));
//        incrementGeneration();
//
//        return (ArrayList<T>) this.cars;
//    }
//
//    private <T extends CarController> void calculateFitness(ArrayList<AbstractCar> cars) {
//        for (AbstractCar car : cars) {
//            double fitness = fitnessFunction.apply(this, car);
//            car.getController().setFitness(fitness);
//        }
//    }
//
//    @Override
//    public void parallelEvaluation(Consumer<ArrayList<? extends CarController>> disable) {
//        if(parallelThread == null){
////            this.buffer = new SimulationBuffer();
//            parallelThread = new Thread(this);
//            parallelThread.start();
//        }
//
//        if(generationCount == 1){
//            this.map.randomize();
//        }
//
//        System.out.println("Simulation 2");
//
//        if(parallelPhysicEngine != null){
//            this.cars = new ArrayList<>();
//            for (CarController carController : parallelPhysicEngine.extractNetworks()) {
//                this.cars.add(carController.copy());
//            }
//
//            disable.accept(this.cars);
//            physicEngine = configureSimulation(cars);
//            physicEngine.setRealTime(true);
//            physicEngine.run();
//            ArrayList<AbstractCar> cars = physicEngine.getCars();
//            calculateFitness(cars);
//            this.cars = cars.stream().map(AbstractCar::getController).collect(Collectors.toCollection(ArrayList::new));
//
//            incrementGeneration();
//        }
//        else{
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//
//    }
//
//    //ONLY FOR THE PARALLEL THREAD
//    @Override
//    public void run() {
//        ArrayList<? extends CarController> array = null;
//        while(!finished){
//            System.out.println("Simulation 1");
//            simulationEngine.getBuffer().clear();
//
//            if(array == null){
//                array = new ArrayList<>();
//                array.add(this.simulationEngine.generateCurrentNetwork());
//            };
//
//            parallelPhysicEngine = configureSimulation(array);
//            parallelPhysicEngine.setBuffer(this.simulationEngine.getBuffer());
//            parallelPhysicEngine.setRealTime(true);
//
//            parallelPhysicEngine.run();
//
//            array = parallelPhysicEngine.extractNetworks();
//        }
//    }
//
//    /**
//     * Increment the generation count and batch count. Calls the listener accordingly.
//     */
//    private void incrementGeneration() {
//        int maxGenerations = (int) this.getSetting(LAOP.KEY_NUMBER_OF_GENERATIONS);
//
//        this.currentSimulationData.addData(this.getGenerationData());
//
//        //CHECK IF GENERATION IS OUT OF BOUNDS
//        if (generationCount >= maxGenerations) {
//        	this.currentBatchData.addData(this.currentSimulationData);
//        	this.currentSimulationData = new SimulationData();
//
//            Console.info("Simulation " + this.simulationCount + " finished");
//            //FIRE LISTENERS
//            this.onSimulationFinish.forEach(Procedure::invoke);
//            generationCount = 1;
//            simulationCount++;
//
//
//            //CHECK IF SIMULATION IS OUT OF BOUND
//            if (simulationCount > (int) this.getSetting(LAOP.KEY_NUMBER_OF_SIMULATION)) {
//                //FIRE END LISTENER
//                this.finished = true;
//                this.currentSimulationData = new SimulationData();
//            }
//        } else {
//            Console.info("Generation " + generationCount + " / " + maxGenerations + " completed");
//            this.onGenerationFinish.forEach(Procedure::invoke);
//            generationCount++;
//        }
//    }
//
//    /**
//     * Simulate a generation
//     */
//    private PhysicEngine configureSimulation(ArrayList<? extends CarController> cars) {
//
//        this.buffer.clear();
//        PhysicEngine physicEngine = new PhysicEngine(this.buffer, this.map);
//
//        this.realTime.addListener((observer, oldVal, newVal) -> {
//            physicEngine.setRealTime(newVal);
//        });
//
//        physicEngine.setFinishingConditions(PhysicEngine.ALL_CARS_DEAD);
//        physicEngine.setTimeLimit(TIME_LIMIT);
//
//        physicEngine.getCars().addAll(generateCarObjects(cars.size(), (i) -> cars.get(i)));
//
//        return physicEngine;
//    }
//
//    public ArrayList<SimpleCar> generateCarObjects(int numberOfCars, Function<Integer, CarController> controllerFunction){
//        int numberOfSensors = (int) this.simulationEngine.getSettings().get(LAOP.KEY_NUMBER_OF_SENSORS);
//
//        ArrayList<SimpleCar> carObjects = new ArrayList<>();
//        for(int i = 0 ; i < numberOfCars ; i++) {
//            Point2D start = map.getStartPoint();
//            SimpleCar car = new SimpleCar(map, new Vector2d(start.getX(), start.getY()), controllerFunction.apply(i));
//
//            double orientationIncrement = Math.PI / numberOfSensors;
//            //Create the sensors and assign them to the car
//            for(int x = 0 ; x < numberOfSensors; x++) {
//                ProximityLineSensor sensor = new ProximityLineSensor(car, (x * orientationIncrement) + orientationIncrement/2);
//                car.addSensor(sensor);
//            }
//
//            carObjects.add(car);
//        }
//
//
//        return carObjects;
//    }
//
//    /**
//     * Get the generation data of the current generation
//     * Used to populate the chart panel
//     *
//     * @return the generation data of the current generation
//     * @author Clement Bisaillon
//     */
//    public GenerationData getGenerationData() {
//    	double[] fitnesses = new double[this.cars.size()];
//
//    	for(int i = 0 ; i < this.cars.size() ; i++) {
//    		fitnesses[i] = this.cars.get(i).getFitness();
//    	}
//
//        GenerationData data = new GenerationData(this.generationCount, this.simulationCount, fitnesses);
//        data.setAverageFitness(NetworkUtils.average(cars));
//
//        return data;
//    }
//
//    /**
//     * Get the data about the current batch
//     * @return the data
//     */
//    @Override public BatchData getBatchData() {
//    	return this.currentBatchData;
//    }
//
//    public void setOnSimulationFinish(Procedure onSimulationFinish) {
//        this.onSimulationFinish.add(onSimulationFinish);
//    }
//
//    public void setOnGenerationFinish(Procedure onGenerationFinish) {
//        this.onGenerationFinish.add(onGenerationFinish);
//    }
//
//    public int getGenerationCount() {
//        return generationCount;
//    }
//
//    public int getSimulationCount() {
//        return simulationCount;
//    }
//
//    /**
//     * Retrieve a setting from the settings
//     *
//     * @param key the key associated with the setting
//     * @return the value store in the setting
//     */
//    private Object getSetting(String key) {
//        return this.simulationEngine.getSettings().get(key);
//    }
//
//    //TODO
//
//    /**
//     * @param autoRun false if the simulation should wait input before going to the next generation or going as soon as the generation is finished
//     */
//    public void setAutoRun(boolean autoRun) {
//        this.autoRun = autoRun;
//    }
//
//    public BooleanProperty realTimeProperty() {
//        return this.realTime;
//    }
//
//    public final boolean getRealTime() {
//        return this.realTime.get();
//    }
//
//    public final void setRealTime(boolean value) {
//        this.realTime.set(value);
//    }
//
//    @Override
//    public boolean isFinished() {
//        return finished;
//    }
//
//    @Override
//    public void initialise(SimulationEngine simulationEngine) {
//        this.simulationEngine = simulationEngine;
//
//        this.currentBatchData = new BatchData(this.simulationEngine.getCurrentScope());
//        this.currentSimulationData = new SimulationData();
//
//        map = new MazeMap(10);
//        map.bake();
//        buffer = simulationEngine.getBuffer();
//    }
//
//    @Override
//    public void setFinished(boolean finished) {
//    	this.finished = finished;
//    }
//
//    @Override
//    public AbstractMap getMap() {
//        return map;
//    }
//
//    @Override
//    public BiFunction<Environnement, AbstractCar, Double> getFitenessFunction() {
//        return null;
//    }
//
//    public PhysicEngine getPhysicEngine() {
//        return physicEngine;
//    }
//}
//
//

package org.lrima.laop.simulation;

import javafx.scene.canvas.GraphicsContext;
import org.lrima.laop.core.LAOP;
import org.lrima.laop.physic.CarControls;
import org.lrima.laop.physic.SimpleCar;
import org.lrima.laop.simulation.buffer.SimulationBuffer;
import org.lrima.laop.simulation.buffer.SimulationSnapshot;
import org.lrima.laop.simulation.data.CarData;
import org.lrima.laop.simulation.map.MazeMap;
import org.lrima.laop.simulation.sensors.ProximityLineSensor;
import org.lrima.laop.utils.math.Vector2d;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * A environnement sample. It has a maze map containing cars.
 *
 * @author Léonard
 */
public class BetterEnvironnement implements MultiAgentEnvironnement {
    private MazeMap mazeMap;
    private ArrayList<MazeMap> testMaps;
    private int currentTestMap = 0;
    private SimulationBuffer buffer;
    private ArrayList<SimpleCar> simpleCars;
    private boolean finished;
    private int step = 0;
    //private int MAX_STEP = 500;
    private int numberOfSensors;
    private int mapSize;



    @Override
    public ArrayList<Agent> step(ArrayList<CarControls> carControls) {
        if(this.isFinished())
            return this.reset(carControls.size());

        ArrayList<Agent> agents = new ArrayList<>();
        finished = true;

        for (int i = 0; i < simpleCars.size(); i++) {
            SimpleCar simpleCar = simpleCars.get(i);
            CarControls carControl = carControls.get(i);

            if(simpleCar.getMaxStep() < step){
                simpleCar.kill();
            }

            simpleCar.nextStep(carControl);
            mazeMap.collide(simpleCar);

            agents.add(new Agent(simpleCar.getSensors(), evalFitness(simpleCar)));
            if(!simpleCar.isDead()){
                finished = false;
            }

        }

        step++;

        /*if(step > this.MAX_STEP){
            this.finished = true;
        }*/

        return agents;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public ArrayList<Agent> reset(int numberOfAgents) {
        step = 0;
        buffer.clear();
        finished = false;
        ArrayList<Agent> agents = new ArrayList<>();
        simpleCars = generateCarObjects(numberOfAgents);

        for (SimpleCar simpleCar : simpleCars) {
            mazeMap.collide(simpleCar);
            agents.add(new Agent(simpleCar.getSensors(), 0));
        }

        return agents;
    }

    private ArrayList<SimpleCar> generateCarObjects(int numberOfCars){
        ArrayList<SimpleCar> carObjects = new ArrayList<>();
        for(int i = 0 ; i < numberOfCars ; i++) {
            Point2D start = mazeMap.getStartPoint();
            SimpleCar car = new SimpleCar(new Vector2d(start.getX(), start.getY()), mazeMap.getStartingOrientation());

            double orientationIncrement = Math.PI / numberOfSensors;
            //attribute fitness
            car.setWallCountFunction((numberOfTimeHitted, currentCar) -> {
                currentCar.addFitness( 10 / numberOfTimeHitted);
                currentCar.addTime(25 / numberOfTimeHitted);
                /*System.out.println(currentCar.getFitnessWallCount());
                System.out.println(currentCar.getMaxStep());
                System.out.println(numberOfTimeHitted);*/

            });
            car.addTime(100);


            //Create the sensors and assign them to the car
            for(int x = 0 ; x < numberOfSensors; x++) {
                ProximityLineSensor sensor = new ProximityLineSensor(car, (x * orientationIncrement) + orientationIncrement/2);
                car.addSensor(sensor);
            }

            carObjects.add(car);
        }

        return carObjects;
    }

    @Override
    public void render() {
        if(buffer != null) {
            SimulationSnapshot snapshot = new SimulationSnapshot();
            ArrayList<CarData> carData = new ArrayList<>();

            int bestCarIndex = 0;
            int index = 0;

            for(SimpleCar car : this.simpleCars) {
                CarData data = new CarData(car);

                double fitness = this.evalFitness(car);
                data.setFitness(fitness);

                //Check if its the best
                if(fitness > evalFitness(this.simpleCars.get(bestCarIndex))){
                    bestCarIndex = index;
                }

                carData.add(data);
                index++;
            }

            //Assign the status of best to the best car
            CarData bestCarData = new CarData(simpleCars.get(bestCarIndex));
            bestCarData.setIsBest(true);
            carData.set(bestCarIndex, bestCarData);

            for(CarData data : carData){
                snapshot.addCar(data);
            }

            this.buffer.addSnapshot(snapshot);
        }
    }

    @Override
    public void init(LearningEngine learningEngine) {
        this.buffer = learningEngine.getBuffer();
        this.numberOfSensors = (int) learningEngine.getSettings().get(LAOP.KEY_NUMBER_OF_SENSORS);
        this.mapSize = (int) learningEngine.getSettings().get(LAOP.KEY_MAP_SIZE);
        this.testMaps = new ArrayList<>();

        //Create the test maps
        //Used so that the test maps are always the same
        for(int i = 0 ; i < this.getNumberTestMap() ; i++){
            MazeMap map = new MazeMap(mapSize);
            map.bake();
            this.testMaps.add(map);
        }
        this.newMap();
    }

    @Override
    public int getNumberTestMap() {
        return 100;
    }

    @Override
    public void newMap() {
        mazeMap = new MazeMap(mapSize);
        mazeMap.bake();

        if(simpleCars != null) {
            for (SimpleCar car : this.simpleCars) {
                car.setOrientation(mazeMap.getStartingOrientation());
            }
        }
    }

    @Override
    public void nextTestMap() {
        mazeMap = this.testMaps.get(currentTestMap);
        //Sets the good starting orientation of the cars
        if(simpleCars != null) {
            for (SimpleCar car : this.simpleCars) {
                car.setOrientation(mazeMap.getStartingOrientation());
            }
        }

        currentTestMap++;
    }

    @Override
    public void resetTest() {
        this.currentTestMap = 0;
    }

    @Override
    public void draw(GraphicsContext gc) {
        this.mazeMap.draw(gc);
    }

    private double evalFitness(SimpleCar car){
        return car.getFitnessWallCount();
    }
}

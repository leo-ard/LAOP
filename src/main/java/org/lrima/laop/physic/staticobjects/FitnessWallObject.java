package org.lrima.laop.physic.staticobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.bytedeco.javacpp.presets.opencv_core;
import org.lrima.laop.physic.SimpleCar;
import org.lrima.laop.simulation.map.LineCollidable;

import java.util.HashMap;

/**
 * A wall object that as a different color than the normal wall.
 * It also gives a fitness to cars that hit them
 * @author Clement Bisaillon
 */
public class FitnessWallObject extends StaticLineObject {

    /**
     * Hashmap containing objects that this wall collided with as the key and the number of times of collision as the value
     */
    private HashMap<LineCollidable, Integer> objectCollidedWith = new HashMap();

    /**
     * Creates a static line object with parameters x1, x2, y1, y2
     *
     * @param x1 The x value of the first coordinate
     * @param y1 The y value of the first coordinate
     * @param x2 The x value of the second coordinate
     * @param y2 The y value of the second coordinate
     */
    public FitnessWallObject(float x1, float y1, float x2, float y2) {
        super(x1, y1, x2, y2);
    }

    public int addCollidableToCollided(LineCollidable collidable){
        if(!objectCollidedWith.containsKey(collidable)){
            objectCollidedWith.put(collidable, 1);
            return 1;
        }else{
            //Increase the value by one
            int value = objectCollidedWith.get(collidable);
            objectCollidedWith.put(collidable, value + 1);
            return value + 1;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        Paint bakColor = gc.getStroke();
        Double bakLW = gc.getLineWidth();

        gc.setStroke(new Color(50.0/255.0, 200.0/255.0, 36.0/255.0, 1));
        gc.setLineWidth(0.2);
        gc.strokeLine(x1, y1, x2, y2);

        gc.setStroke(bakColor);
        gc.setLineWidth(bakLW);
    }
}

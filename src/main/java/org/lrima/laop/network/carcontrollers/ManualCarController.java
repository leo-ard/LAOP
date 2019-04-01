package org.lrima.laop.network.carcontrollers;

import org.lrima.laop.physic.CarControls;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * An concrete class of a CarController. Can control a car with the keys W-A-S-D.
 *
 * @author Léonard
 */
public class ManualCarController implements CarController {
    double[] values;

    /**
     * Creates a new CarController that controls the car with W-A-S-D
     *
     * @param mainScene the node were this class will add the key listener to.
     */
    public ManualCarController(Stage mainScene){
        values = new double[4];
        mainScene.getScene().setOnKeyPressed(this::handleKeyPress);
        mainScene.getScene().setOnKeyReleased(this::handleKeyReleased);
    }

    private void handleKeyPress(KeyEvent e) {
        if (e.getCode() == KeyCode.W) {
            values[0] = 1;
        } if (e.getCode() == KeyCode.S) {
            values[1] = 1;
        } if (e.getCode() == KeyCode.A) {
            values[2] = 0.5;
        } if (e.getCode() == KeyCode.D) {
            values[2] = -0.5;
        }
    }

    private void handleKeyReleased(KeyEvent e) {
        if (e.getCode() == KeyCode.W) {
            values[0] = 0;
        } if (e.getCode() == KeyCode.S) {
            values[1] = 0;
        } if (e.getCode() == KeyCode.A) {
            values[2] = 0;
        } if (e.getCode() == KeyCode.D) {
            values[2] = 0;
        }
    }

    @Override
    public CarControls control(double[] captorValues) {
    	CarControls controls = new CarControls();
    	
    	controls.setAcceleration(this.values[0]);
    	controls.setBreak(this.values[1]);
    	controls.setRotation(this.values[2]);
    	
        return controls;
    }
}

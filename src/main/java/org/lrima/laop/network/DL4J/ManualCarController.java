package org.lrima.laop.network.DL4J;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.lrima.laop.physic.CarControls;
import org.lrima.laop.utils.MathUtils;

/**
 * An concrete class of a CarController. Can control a car with the keys W-A-S-D.
 *
 * @author Léonard
 */
public class ManualCarController  {
    protected int[] controls;

    public ManualCarController(){
        controls = new int[2];
    }

    public void configureListeners(Stage mainScene){
        mainScene.getScene().setOnKeyPressed(this::handleKeyPress);
        mainScene.getScene().setOnKeyReleased(this::handleKeyReleased);
    }

    /**
     * Handles the key press event
     *
     * @param e the key event
     */
    private void handleKeyPress(KeyEvent e) {
        if (e.getCode() == KeyCode.W) {
            controls[0] = 1;
        } if (e.getCode() == KeyCode.S) {
            controls[0] = -1;
        } if (e.getCode() == KeyCode.A) {
            controls[1] = -1;
        } if (e.getCode() == KeyCode.D) {
            controls[1] = 1;
        }
    }

    /**
     * handles the key released event
     *
     * @param e the key event
     */
    private void handleKeyReleased(KeyEvent e) {
        if (e.getCode() == KeyCode.W) {
            controls[0] = 0;
        } if (e.getCode() == KeyCode.S) {
            controls[0] = 0;
        } if (e.getCode() == KeyCode.A) {
            controls[1] = 0;
        } if (e.getCode() == KeyCode.D) {
            controls[1] = 0;
        }
    }

    /**
     * Control the car depending on the controls. It does not take the captors values in consideration
     *
     * @param captorValues the captor values
     * @return the CarControl
     */
    public CarControls control(double[] captorValues) {
        return new CarControls(MathUtils.convertToDoubleArray(this.controls));
    }

    /**
     * Transform an array into car controls
     *
     * @param inputValues - an array with the values of each output value
     * @return the car controls
     */
    protected CarControls getControls(double[] inputValues){
        return new CarControls(inputValues);
    }
}

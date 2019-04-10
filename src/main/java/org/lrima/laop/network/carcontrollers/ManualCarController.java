package org.lrima.laop.network.carcontrollers;

import org.lrima.laop.physic.CarControls;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.lrima.laop.settings.LockedSetting;

/**
 * An concrete class of a CarController. Can control a car with the keys W-A-S-D.
 *
 * @author Léonard
 */
public class ManualCarController implements CarController {
    protected int[] controls;

    public ManualCarController(){
        controls = new int[4];
    }

    public void configureListeners(Stage mainScene){
        mainScene.getScene().setOnKeyPressed(this::handleKeyPress);
        mainScene.getScene().setOnKeyReleased(this::handleKeyReleased);
    }

    private void handleKeyPress(KeyEvent e) {
        if (e.getCode() == KeyCode.W) {
            controls[0] = 1;
        } if (e.getCode() == KeyCode.S) {
            controls[1] = 1;
        } if (e.getCode() == KeyCode.A) {
            controls[2] = 1;
        } if (e.getCode() == KeyCode.D) {
            controls[3] = 1;
        }
    }

    private void handleKeyReleased(KeyEvent e) {
        if (e.getCode() == KeyCode.W) {
            controls[0] = 0;
        } if (e.getCode() == KeyCode.S) {
            controls[1] = 0;
        } if (e.getCode() == KeyCode.A && controls[2] == 1) {
            controls[2] = 0;
        } if (e.getCode() == KeyCode.D && controls[2] == 0) {
            controls[3] = 0;
        }
    }

    @Override
    public CarControls control(double[] captorValues) {
        return getControls(this.controls);
    }

    protected CarControls getControls(int[] inputValues){
        CarControls controls = new CarControls();

        controls.setAcceleration(inputValues[0]);
        controls.setBreak(inputValues[1]);

        if(inputValues[2] == inputValues[3]){
            controls.setRotation(0.5);
        }
        else if(inputValues[2] == 1){
            controls.setRotation(1);
        }else if(inputValues[3] == 1){
            controls.setRotation(0);
        }


        return controls;
    }

    @Override
    public void init(LockedSetting settings) {

    }
}

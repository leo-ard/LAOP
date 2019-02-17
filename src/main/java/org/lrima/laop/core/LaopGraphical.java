package org.lrima.laop.core;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Launch the LAOP platform with a graphical interface
 *
 * @author Clement Bisaillon
 */
public class LaopGraphical extends Application {
    //TODO: Pouvoir changer entre different stage (settings, simulation et conclusion)

    private Scene currentScene;

    @Override
    public void start(Stage stage) {

        stage.show();
    }


}

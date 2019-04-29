package org.lrima.laop.core;

import javafx.application.Application;
import javafx.stage.Stage;
import org.lrima.laop.ui.stage.ConfigurationStage;

/**
 * Launch the LAOP platform with a graphical interface
 *
 * @author Clement Bisaillon
 */
public class LaopGraphical extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        ConfigurationStage configurationStage = new ConfigurationStage();
        configurationStage.show();
    }
}

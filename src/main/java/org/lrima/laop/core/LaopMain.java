package org.lrima.laop.core;

import org.lrima.laop.ui.stage.ConfigurationStage;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Exécute LAOP avec une interface visuelle
 *
 * @author Léonard
 */
public class LaopMain extends Application {

    public static void main(String[] args){
        runGraphical();
    }

    /**
     * Run the Laop platform with a graphical interface
     *
     * @return true if successful, false otherwise.
     */
    private static boolean runGraphical() {
        //Run the graphical interface
        new Thread(() -> Application.launch(LaopGraphical.class)).start();

        return true;
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage = new ConfigurationStage();
		primaryStage.show();
		
	}
}

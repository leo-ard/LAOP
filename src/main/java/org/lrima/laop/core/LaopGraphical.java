package org.lrima.laop.core;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Launch the LAOP platform with a graphical interface
 *
 * @author Clement Bisaillon
 */
public class LaopGraphical extends Application {
    //TODO: Pouvoir changer entre different stage (settings, simulation et conclusion)

    @Override
    public void start(Stage stage) {
        stage.setTitle("Configuration");

        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane, 500, 500);
        stage.setScene(scene);

        Text text = new Text("Bonjour");
        pane.setCenter(text);

        Line line = new Line(0, 0, 200, 200);

        pane.getChildren().add(line);


        stage.show();
    }


}

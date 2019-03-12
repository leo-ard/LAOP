package org.lrima.laop.controller;

import com.jfoenix.controls.JFXSlider;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.lrima.laop.controller.InspectorPane;
import org.lrima.laop.graphics.simulation.SimulationDrawer;
import org.lrima.laop.graphics.simulation.timeline.TimeLine;
import org.lrima.laop.simulation.CarInfo;
import org.lrima.laop.simulation.SimulationBuffer;
import org.lrima.laop.simulation.SimulationSnapshot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class that displays the simulation with the side panels
 *
 * @author Léonard
 */
public class SimulationStage extends Stage {
    private SimulationBuffer buffer;
    private Canvas canvas;
    private TimeLine timeLine;

    private SimulationDrawer simulationDrawer;
    private InspectorPane inspector;

    /**
     * Initialize a new simulation stage with a new simulation buffer
     */
    public SimulationStage(){
        this(new SimulationBuffer());
    }

    /**
     * Initialize a new simulation stage with a specific simulation buffer
     * @param buffer the simulation buffer to initialize the simulation stage with
     */
    public SimulationStage(SimulationBuffer buffer){
        this.setTitle("LAOP : Simulation");
        this.buffer = buffer;
        //todo: creer constantes pour width et height
        this.canvas = new Canvas(1280, 720);
        this.inspector = new InspectorPane();

        //TEMPORARY PROVIDER
        for(int i = 0; i < 100; i++){
            SimulationSnapshot snapshot = new SimulationSnapshot();

            for(int j = 0; j < 100; j++){
                snapshot.addCar(new CarInfo(i*10 + j*15, i*10, 10, 30, -45));

            }

            this.buffer.addSnapshot(snapshot);
        }

        this.loadAllScenes();
    }

    /**
     * Adds all the layouts with their components to root Pane
     */
    private void loadAllScenes() {
        BorderPane rootPane = new BorderPane();

        this.simulationDrawer = new SimulationDrawer(canvas, buffer, inspector);
        this.timeLine = new TimeLine(this.simulationDrawer, this.buffer);

        Pane canvasHolder = new Pane(canvas);

        //CANVAS
        ChangeListener<Number> updateWidthHeight = (observable, oldValue, newValue) -> {
            canvas.setHeight(canvasHolder.getHeight());
            canvas.setWidth(canvasHolder.getWidth());
            simulationDrawer.repaint();
        };

        canvasHolder.widthProperty().addListener(updateWidthHeight);
        canvasHolder.heightProperty().addListener(updateWidthHeight);

        Pane clickerPane = new Pane();
        clickerPane.setVisible(false);

        rootPane.setCenter(clickerPane);
        rootPane.setBottom(timeLine);
        rootPane.setRight(inspector);

        rootPane.setLeft(console());
        canvas.setPickOnBounds(false);
        rootPane.setPickOnBounds(false);

        StackPane rootrootPane = new StackPane(canvas, rootPane);


        Scene scene = new Scene(rootrootPane);
        scene.getStylesheets().add("/css/general.css");

        this.setScene(scene);
    }

    /**
     * Creates and returns all the nodes to create a timeline.
     *
     * @return The nodes to create the timeline
     */
    private HBox timeLine() {
        Button button = new Button(">");

        button.setOnAction(e ->{
            if(button.getText().equals(">")){
                this.simulationDrawer.startAutodraw(100);
                button.setText("||");
            }
            else{
                this.simulationDrawer.stopAutoDraw();
                button.setText(">");
            }
        });

        JFXSlider slider = new JFXSlider();
        slider.setMax(buffer.getSize()-1);
        slider.setValue(0);
        slider.setMinorTickCount(1);
        slider.setMaxWidth(Integer.MAX_VALUE);
        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int currentValue = (int)Math.round(newVal.doubleValue());
            int oldValue = (int)Math.round(oldVal.doubleValue());

            System.out.println(currentValue + " " + oldValue);

            if(currentValue != oldValue)
                setTime(currentValue);
        });


        HBox.setMargin(slider, new Insets(7,0,7,0));

        Button button1 = new Button("Next gen");

        HBox hBox = new HBox(button, slider, button1);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(5));

        HBox.setHgrow(slider, Priority.ALWAYS);

        hBox.setStyle("-fx-background-color: blue");
        this.simulationDrawer.drawStep(0);

        this.simulationDrawer.setSlider(slider);

        return hBox;

    }

    /**
     * Creates and return the console pane
     *
     * @return The console pane
     */
    private Node console() {
        VBox console = new VBox();
        console.setAlignment(Pos.BOTTOM_LEFT);

        ScrollPane scrollPane = new ScrollPane(console);
        scrollPane.setVvalue(1);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        System.setOut(new PrintInterseptor(System.out, (s)->{
            double oldValue = scrollPane.getVvalue();

            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            Text prefix = new Text(String.format("[%s] ", time));
            prefix.setStyle("-fx-font-weight: bold;");
            Text suffix = new Text(s);

            TextFlow textFlow = new TextFlow(prefix, suffix);
            console.getChildren().add(textFlow);

            scrollPane.layout();

            if(oldValue == 1.0) scrollPane.setVvalue(1.0);
        }));

        return scrollPane;
    }

    /**
     * Sets the time at which we want the simulation to be displayed at
     *
     * @param time The time
     */
    private void setTime(int time) {
        this.simulationDrawer.drawStep(time);
    }
}

package org.lrima.laop.ui.components;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.application.Platform;


import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.lrima.laop.utils.Console;

/**
 * Class to show the console panel
 * @author Leonard Oest OLeary
 */
public class ConsolePanel extends ScrollPane {
	private final int MAX_WIDTH = 200;
    private VBox vBox;

    public ConsolePanel() {
		super();

		vBox = new VBox();
		this.setContent(vBox);
        vBox.setAlignment(Pos.TOP_LEFT);
        
        setVvalue(1);
        setHbarPolicy(ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollBarPolicy.NEVER);
        this.vvalueProperty().bind(vBox.heightProperty());

        vBox.setPrefWidth(MAX_WIDTH);
        this.getStyleClass().add("panel");

        this.setOut();
	}

    /**
	 * Set how the system handles the message reception
	 */
	private void setOut() {
        Console.addListener((logType, s)->{
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            Text prefix = new Text(String.format("[%s] %s ", logType.getPrefix(), time));
            prefix.setFill(logType.getColor());
            prefix.setStyle("-fx-font-weight: bold;");
            Text suffix = new Text(s);
            suffix.setFill(logType.getColor().saturate());

            TextFlow textFlow = new TextFlow(prefix, suffix);
            textFlow.setMaxWidth(MAX_WIDTH);

            Platform.runLater(()-> {
                vBox.getChildren().add(textFlow);
                this.layout();
            });

        });
	}

}

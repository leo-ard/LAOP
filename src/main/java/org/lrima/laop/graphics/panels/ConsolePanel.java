package org.lrima.laop.graphics.panels;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.lrima.laop.controller.PrintInterseptor;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Class to show the console panel
 * @author Leonard Oest OLeary
 *
 */
public class ConsolePanel extends VBox {

	private ScrollPane scrollPane;
	private final int MAX_WIDTH = 200;
	
	public ConsolePanel() {
		super();
		this.setAlignment(Pos.TOP_LEFT);
        
        this.scrollPane = new ScrollPane(this);
        this.scrollPane.setVvalue(1);
        this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        this.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5)");
        this.setPrefWidth(MAX_WIDTH);
        
        //Todo: Ne fonctionne pas. Voir general.css
        this.getStyleClass().add("console-panel");
        
        this.setOut();
        
        System.err.println("ERROR TEST ASDFASDFASDFASDFASDFA");
	}
	
	/**
	 * Set how the system handles the message reception
	 */
	private void setOut() {
		System.setOut(new PrintInterseptor(System.out, (s)->{
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            Text prefix = new Text(String.format("[%s] ", time));
            prefix.setStyle("-fx-font-weight: bold;");
            Text suffix = new Text(s);

            TextFlow textFlow = new TextFlow(prefix, suffix);
            textFlow.setMaxWidth(MAX_WIDTH);
            this.getChildren().add(textFlow);

            scrollPane.layout();
        }));
		
		System.setErr(new PrintInterseptor(System.err, (s)->{
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            Text prefix = new Text(String.format("ERROR at [%s] ", time));
            prefix.setFill(Color.RED);
            prefix.setStyle("-fx-font-weight: bold;");
            Text suffix = new Text(s);
            suffix.setFill(Color.RED);

            TextFlow textFlow = new TextFlow(prefix, suffix);
            textFlow.setMaxWidth(MAX_WIDTH);
            this.getChildren().add(textFlow);

            scrollPane.layout();
        }));
	}

}

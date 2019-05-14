package org.lrima.laop.ui.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import org.lrima.laop.ui.I18n;
import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The controller of the home panel that shows the description of the platform and allow the user to go to the configuration stage
 * @author Clement Bisaillon
 */
public class HomeController implements Initializable {
    @FXML private Label text;
    @FXML private JFXButton viewGitHubBtn;
    @FXML private ChoiceBox<String> choiceBox;
    @FXML private Label selectLanguageLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String englishLabel = "English";
        String frenchLabel = "Fran\u00e7ais";
        choiceBox.getItems().addAll(englishLabel, frenchLabel);

        choiceBox.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal.intValue() == 0){
                I18n.update(new Locale("en", "CA"));
            }else if(newVal.intValue() == 1){
                I18n.update(new Locale("fr", "CA"));
            }
        });

        if(I18n.getLocal().getLanguage().equals("fr")){
            choiceBox.getSelectionModel().select(frenchLabel);
        }else {
            choiceBox.getSelectionModel().select(englishLabel);
        }
        I18n.bind(text, selectLanguageLabel, viewGitHubBtn);

        //Show the github page on view github btn click
        this.viewGitHubBtn.setOnMouseClicked((event) -> {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/lool01/LAOP_alpha"));
                }catch (Exception e){
                    System.out.println("Error opening github page");
                }
            }
        });

    }
}

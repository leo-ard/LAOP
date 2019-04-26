package org.lrima.laop.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import org.lrima.laop.ui.I18n;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class homeController implements Initializable {
    @FXML  Label text;
    @FXML ChoiceBox<String> choiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(choiceBox);
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

        I18n.bind(text);

    }
}

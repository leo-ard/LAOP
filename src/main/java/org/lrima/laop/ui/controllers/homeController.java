package org.lrima.laop.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class homeController implements Initializable {
    @FXML  Label text;
    @FXML ChoiceBox choiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(choiceBox);
        choiceBox.getItems().addAll("%english", "%french");


    }
}

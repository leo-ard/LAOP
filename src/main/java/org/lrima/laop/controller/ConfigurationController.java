package org.lrima.laop.controller;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.JFXTreeView;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import org.lrima.laop.core.LAOP;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfigurationController implements Initializable {
    LAOP laop;

    @FXML
    JFXTreeTableView table;

    @FXML
    public void onConfigurerClick(){
        System.out.println("hullo");
        System.out.println(laop);
        laop.showConfigurations();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //table.getColumns().add(new JFXTreeTableColumn<>());
        System.out.println("ii");

    }

    public void setLAOP(LAOP laop){
        System.out.println("setup LAOP" + laop);

        this.laop = laop;
    }



}

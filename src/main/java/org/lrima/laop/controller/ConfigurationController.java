package org.lrima.laop.controller;

import com.jfoenix.controls.JFXTreeTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.lrima.laop.core.LAOP;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class that controls the configuration panel (configuration.fxml)
 *
 * @author Léonard
 */
public class ConfigurationController implements Initializable {
    LAOP laop;

    @FXML
    JFXTreeTableView table;

    @FXML
    public void onConfigurerClick(){
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

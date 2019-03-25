package org.lrima.laop.ui.controllers;

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

    /**
     * Called on click of the button configure
     */
    @FXML
    public void onConfigurerClick(){
        laop.showConfigurations();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    public void setLAOP(LAOP laop){
        this.laop = laop;
    }



}

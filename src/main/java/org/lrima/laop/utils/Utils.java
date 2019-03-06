package org.lrima.laop.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.lrima.laop.controller.ConfigurationController;

import java.io.IOException;
import java.net.URL;

public class Utils {

    public static FXMLLoader load(String file) {
        URL url = Utils.class.getResource("/views/configuration/" + file + ".fxml");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(url);
            loader.load();


            return loader;
        } catch (IOException e) {
            System.err.println("Could not load " + file +".fxml");
            return null;
        }
    }
}

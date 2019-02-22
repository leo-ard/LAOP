package org.lrima.laop.core;

import com.sun.source.tree.Scope;
import org.lrima.laop.settings.Settings;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Main class to manage starting the simulation
 *
 * @author Léonard
 */
public class LAOP {
    //global keys
    static final int DEFAULT_NUMBER_OF_CARS = 100;
    static final String KEY_NUMBER_OF_CARS = "NUMBER OF CARS";

    static final int DEFAULT_TIME_LIMIT = 10;
    static final String KEY_TIME_LIMIT = "TIME LIMIT";


    static final Class<? extends Object> FULLY_CONNECTED = Scope.class;

    Settings settings;
    HashMap<String, Class<? extends Object>> algorithms;

    public LAOP(){
        algorithms = new HashMap<>();
        settings = new Settings();
        defaultSettings();

    }

    private void defaultSettings(){
        settings.set(Settings.GLOBAL_SCOPE, KEY_NUMBER_OF_CARS, DEFAULT_NUMBER_OF_CARS);
        settings.set(Settings.GLOBAL_SCOPE, KEY_TIME_LIMIT, KEY_TIME_LIMIT);

    }

    public void addAlgorithm(String label, Class<? extends Object> algorithmClass, HashMap<String, Object> settings){
        if(algorithms.get(label) != null)
            throw new KeyAlreadyExistsException("The label "+label+" has already been assigned");

        algorithms.put(label, algorithmClass);

        settings.forEach((k, v) -> this.settings.set(label, k, v));
    }

    public void showConfigurations() {
        settings.showPanel();
    }
}

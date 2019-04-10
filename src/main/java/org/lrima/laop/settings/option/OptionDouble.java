package org.lrima.laop.settings.option;

import javafx.scene.Node;
import javafx.scene.control.Spinner;

/**
 * Classe qui permet d'utiliser un double comme option
 *
 * @author Léonard
 */
public class OptionDouble implements Option<Double> {
    private Double value, max, min, step;

    /**
     * Creates a new OptionDouble
     *
     * @param defaultValue The default value of the OptionDouble
     */
    public OptionDouble(Double defaultValue){
        this.value = defaultValue;
    }

    /**
     * Creates a new OptionDouble
     *
     * @param defaultValue The default value of the OptionDouble
     * @param max ConstraDouble the value to this max
     * @param min ConstraDouble the value to this min
     */
    public OptionDouble(Double defaultValue, Double max, Double min){
        this(defaultValue);
        this.max = max;
        this.min = min;
    }

    /**
     *
     * @param defaultValue The default value of the OptionDouble
     * @param max ConstraDouble the value to this max
     * @param min ConstraDouble the value to this max
     * @param step The step that the JSpinner created with generateComponent must have
     */
    public OptionDouble(Double defaultValue, Double max, Double min, Double step){
        this(defaultValue, max, min);
        this.step = step;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public boolean setValue(Double value) {
        // Check if the value is inside the bounds. If it is, it returns false without changing the value
        if(max != null && value > max) return false;
        if(min != null && value < min) return false;

        this.value = value;

        return true;
    }

    @Override
    public Node generateComponent() {
        Spinner<Double> spinner = new Spinner<>();
        spinner.valueProperty().addListener((obs, oldVal, newVal)->{
            value = newVal;
        });
        
        //DO NOT REMOVE or else the value is not updated when unfocusing the spinner
        spinner.focusedProperty().addListener((obs, oldValue, newValue) -> {
        	if (!newValue) {
        	    spinner.increment(0);
        	}
        });
        //END DO NOT REMOVE

        return spinner;
    }

}

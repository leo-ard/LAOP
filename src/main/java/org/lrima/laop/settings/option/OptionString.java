package org.lrima.laop.settings.option;

import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import javax.swing.*;

public class OptionString implements Option<String>{
    private String value, regex;

    /**
     * Creates a String limited to this regex
     *
     * @param value
     * @param regex
     */
    public OptionString(String value, String regex) {
        this(value);
        this.regex = regex;
    }

    /**
     * Creates an OptionString
     *
     * @param value
     */
    public OptionString(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean setValue(String value) {
        if(!value.matches(regex)) return false;

        this.value = value;

        return true;
    }

    @Override
    public Node generateComponent() {
        TextField textField = new TextField();
        textField.textProperty().addListener((obs, oldVal, newVal)->{
            value = newVal;
        });

        return textField;
    }
}

package org.lrima.laop.settings.option;

import javax.swing.*;
import java.awt.*;

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
    public JComponent generateComponent() {
        JTextField textField = new JTextField();

        //TODO add code to limit with regex

        return textField;
    }
}

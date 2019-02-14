package org.lrima.laop.settings.option;

import javax.swing.*;

public class OptionInt implements Option<Integer>{
    private Integer value, max, min, step;

    /**
     * Creates a new OptionInt
     *
     * @param defaultValue The default value of the OptionInt
     */
    public OptionInt(Integer defaultValue){
        this.value = defaultValue;
    }

    /**
     * Creates a new OptionInt
     *
     * @param defaultValue The default value of the OptionInt
     * @param max Constraint the value to this max
     * @param min Constraint the value to this min
     */
    public OptionInt(Integer defaultValue, Integer max, Integer min){
        this(defaultValue);
        this.max = max;
        this.min = min;
    }

    /**
     *
     * @param defaultValue The default value of the OptionInt
     * @param max Constraint the value to this max
     * @param min Constraint the value to this max
     * @param step The step that the JSpinner created with generateComponent must have
     */
    public OptionInt(Integer defaultValue, Integer max, Integer min, Integer step){
        this(defaultValue, max, min);
        this.step = step;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public boolean setValue(Integer value) {
        // Check if the value is inside the bounds. If it is, it returns false without changing the value
        if(max != null && value > max) return false;
        if(min != null && value < min) return false;

        this.value = value;

        return true;
    }

    @Override
    public JComponent generateComponent() {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(value, min, max, step));
        spinner.addChangeListener(e -> {
            setValue((Integer) spinner.getValue());
        });


        return spinner;
    }
}

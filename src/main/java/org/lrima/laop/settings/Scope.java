package org.lrima.laop.settings;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.lrima.laop.settings.option.Option;
import org.lrima.laop.settings.option.OptionDouble;
import org.lrima.laop.settings.option.OptionInt;
import org.lrima.laop.settings.option.OptionString;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author Leonard Oeast OLeary and Clement Bisaillon
 */
class Scope extends LinkedHashMap<String, Option> {

    /**
     * Retrieve the option associated with a certain key
     *
     * @param key - The key to retrieve the option from
     * @return the Object stored in the option or null if an error occurred.
     */
    private Option getOption(String key) {
        return get(key);
    }

    /**
     * If the object is an Option, return its value else returns its value
     *
     * @param key - The key to retrive the option from
     * @return the Object stored in the option or null if the object doesnt exist
     */
    Object getValue(String key){
        Option value = this.get(key);
        if(value != null)
            return value.getValue();
        return null;
    }

    Object put(String key, Object value) {
        return super.put(key, transformToOption(value));
    }

    private Option transformToOption(Object value) {
        if(value instanceof Option) return (Option) value;
        if(value instanceof String) return new OptionString((String)value);
        if(value instanceof Integer) return new OptionInt((Integer)value);
        if(value instanceof Double) return new OptionDouble((Double) value);

        throw new UnsupportedOperationException("The type : " + value.getClass() + " is not supported by the Option");
    }

    /**
     * Generates a javafx layout depending on the type of the values in the scope
     *
     * @param globalScope The global scope to show the options that can be overrithen. If this is the globalScope, this variable must be null.
     * @return The javafx layout.
     */
     Node generatePanel(Scope globalScope) {

        GridPane centerPanel = new GridPane();
        int i = 0;

        SortedSet<String> allKeys = new ConcurrentSkipListSet(this.keySet());
        allKeys.addAll(globalScope.keySet());


        //For each key, adds an entry in the grid panel
        for(String key : allKeys){
            //TODO : format key
            Option value = getOption(key);
            Label keyLabel = new Label(key);
            GridPane.setHgrow(keyLabel, Priority.ALWAYS);
            centerPanel.add(keyLabel, 0, i);
            Node component;

            if(value == null){
                component = globalScope.getOption(key).generateComponent();
                keyLabel.setDisable(true);
                component.setDisable(true);
                component.setOnMouseClicked((e) ->{
                    System.out.println("jdjdj");
                    int row = GridPane.getRowIndex(component);
                    centerPanel.getChildren().remove(component);
                    this.put(key, value.getValue());
                    Node newComponent = value.generateComponent();
                    GridPane.setHgrow(newComponent, Priority.ALWAYS);
                    centerPanel.add(newComponent, 1, row);
                });
            } else{
                component = value.generateComponent();
            }
            GridPane.setHgrow(component, Priority.ALWAYS);
            centerPanel.add(component, 1, i);


            i++;
        }


        //VISUAL SETTINGS FOR THE GRID PANE
        centerPanel.setPadding(new Insets(10));
        centerPanel.setHgap(5);
        centerPanel.setVgap(5);

        return centerPanel;
    }
}

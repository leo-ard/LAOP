package org.lrima.laop.settings.option;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.network.LearningAnotation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class OptionClass<T> implements Option<Class<?>> {
    Class<?> value;
    HashMap<String, Class<? extends T>> allClasses;

    public OptionClass(Class<?> value, ArrayList<Class<? extends T>> allAvailableClasses) {
    	this.value = value;

    	allClasses = new HashMap<>();
        for (Class<? extends T> allAvailableClass : allAvailableClasses) {
            allClasses.put(allAvailableClass.getSimpleName(), allAvailableClass);
        }
    }

    @Override
    public Class<?> getValue() {
        return value;
    }

    @Override
    public boolean setValue(Class<?> value) {
        this.value = value;
        return true;
    }

    @Override
    public Node generateComponent() {
        // TODO : take the global liste instead
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(allClasses.keySet());

        ComboBox<String> stringComboBox = new ComboBox<>(observableList);
        stringComboBox.getSelectionModel().select(value.getSimpleName());
        stringComboBox.getSelectionModel().selectedItemProperty().addListener((s, newOb, oldOb) ->{
            setValue(allClasses.get(newOb));
        });
        return stringComboBox;
    }
}

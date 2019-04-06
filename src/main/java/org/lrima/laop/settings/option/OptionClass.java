package org.lrima.laop.settings.option;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;

import com.jfoenix.controls.JFXComboBox;
import javafx.scene.paint.Color;
import org.lrima.laop.ui.stage.DownloadAlgorithmDialog;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

public class OptionClass<T> implements Option<Class<?>> {
    Class<?> value;
    HashMap<String, Class<? extends T>> allClasses;
    Function<Class, Boolean> isValid;

    public OptionClass(Class<?> value, ArrayList<Class<? extends T>> allAvailableClasses, Function<Class, Boolean> isValid) {
    	this.value = value;
    	this.isValid = isValid;

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

        JFXComboBox<String> stringComboBox = new JFXComboBox<>(observableList);
        stringComboBox.getSelectionModel().select(value.getSimpleName());
        stringComboBox.getSelectionModel().selectedItemProperty().addListener((s, oldOb, newOb) ->{
            setValue(allClasses.get(newOb));
            if(isValid.apply(this.value)){
                stringComboBox.focusColorProperty().set(Color.BLUE);
            }
            else{
                stringComboBox.focusColorProperty().set(Color.RED);

            }
        });
        
        return stringComboBox;
    }
}

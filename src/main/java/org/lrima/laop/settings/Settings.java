package org.lrima.laop.settings;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import org.lrima.laop.core.LAOP;
import org.lrima.laop.settings.option.Option;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Stores the settings in different scopes and allows the user to get and set the values of the
 * different settings. The scope is used to differentiate for example the settings for one
 * algorithm and the others
 * @author Clement Bisaillon
 */
public class Settings {
    public final static String GLOBAL_SCOPE = "GLOBAL";

    /**
     * This is the main variable of the settings. It contains the value of each settings
     * The key of this HashMap represents the name of the scope.
     */
    private LinkedHashMap<String, Scope> scopes;

    public Settings(){
        scopes = new LinkedHashMap<>();
        scopes.put(GLOBAL_SCOPE, new Scope());
    }

    /**
     * Get the value of a setting in a certain scope and represented by a key
     * @param scope - The scope to get the setting from. This field is not case-sensitive.
     * @param key - The key associated with the setting. This field is not case-sensitive.
     * @return an object representing the value stored.
     */
    public Object get(String scope, String key){
        //If the scope the user is trying to access is non existant
        if (this.scopes.get(scope) == null) {
            //TODO throw exception
            return null;
        }

        //Return the valye associated with the key in the specified scope. If the key doesnt exist, check in the global scope
        Object value = this.scopes.get(scope).getValue(key);
        if(value == null){
            value = this.scopes.get(GLOBAL_SCOPE).getValue(key);
        }


        return value;
    }

    /**
     * Set the value of a setting in a certain scope and represented by a key.
     * If the scope if non existent, this method creates a new scope.
     * @param scope - The scope to get the setting from. This field is not case-sensitive.
     * @param key - The key associated with the setting. This field is not case-sensitive.
     * @param value - The value to assign the the setting
     * @return true if successful, false otherwise
     */
    public boolean set(String scope, String key, Object value){
        //Check if the specified scope exists. If not, create it
        if (this.scopes.get(scope) == null) {
            this.addScope(scope);
        }

        this.scopes.get(scope).put(key, value);

        return true;
    }

    /**
     * Add a new scope to the settings.
     * @param scope - the name of the scope. This field is not case-sensitive
     * @return true if successful, false otherwise
     */
    private boolean addScope(String scope){
        Scope newScope = new Scope();
        newScope.setGlobalScope(this.scopes.get(GLOBAL_SCOPE));
        this.scopes.put(scope, newScope);


        return true;
    }

    /**
     * Removes a scope from the settings
     * @param scope - the name of the scope. This field is not case-sensitive
     * @return true if successful, false otherwise
     */
    private boolean removeScope(String scope){
        //If the scope doesn't exists, return false
        if (this.scopes.get(scope) == null) {
            return false;
        }

        this.scopes.remove(scope);
        return true;
    }

    /**
     * Show a JPanel where the use can view the settings and change them. An javaFx application must be started in order to run this function.
     * @return true if successful, false otherwise
     */
    public boolean showPanel(){
        //INIT
        Stage stage = new Stage();
        HashMap<String, Node> panels = new HashMap<>();
        BorderPane rootNode = new BorderPane();
        Scope globalScope = this.scopes.get(GLOBAL_SCOPE);

        //Adding all the panels
        for(String scopeString : this.scopes.keySet()){
            panels.put(scopeString, this.scopes.get(scopeString).generatePanel());
        }

        //SAVE BUTTON AND ITS PANEL
        JFXButton saveButton = new JFXButton("Save");
        saveButton.getStyleClass().add("high");
        saveButton.setMaxWidth(Integer.MAX_VALUE);

        //BOTTOM BUTTON
        HBox bottom = new HBox(saveButton);
        HBox.setHgrow(saveButton, Priority.ALWAYS);
        bottom.setPadding(new Insets(10));

        //ADDING LEFT PANEL (ALL THE SCOPES IN A LIST)
        JFXListView<String> leftPanel = new JFXListView<>();
        leftPanel.getItems().addAll(this.scopes.keySet());
        leftPanel.getSelectionModel().select(0);
        leftPanel.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        leftPanel.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal == null) {
                leftPanel.getSelectionModel().select(0);
                rootNode.setCenter(panels.get(GLOBAL_SCOPE));
            }
            else{
                rootNode.setCenter(panels.get(newVal));
            }
        });

        //ADDING THE PANELS TO THE ROOT NODE
        Node centerPanel = panels.get(GLOBAL_SCOPE);
        rootNode.setCenter(centerPanel);
        rootNode.setLeft(leftPanel);
        rootNode.setBottom(bottom);

        //ADDING STYLE
        Scene scene = new Scene(rootNode, 600, 400);
        scene.getStylesheets().add("/css/general.css");
        stage.setScene(scene);

        stage.show();

        return true;
    }
}

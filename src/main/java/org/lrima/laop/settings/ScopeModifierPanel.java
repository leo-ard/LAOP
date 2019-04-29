package org.lrima.laop.settings;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.lrima.laop.settings.option.Option;

import java.util.Set;

/**
 * JavaFX Pane to display a scope. Within the pane, the controls of the scope can be modified
 * @author Léonard
 */
public class ScopeModifierPanel extends GridPane {
    Scope scope;

    /**
     * Creates a pane that displays the scope. Within the pane, the controls of the scope can be modified
     *
     * @param scope The scope of the pane to display
     */
    public ScopeModifierPanel(Scope scope) {
        super();
        this.scope = scope;
    }


    /**
     *  Initialise this panel to look good
     */
    public void init(){
        setPadding(new Insets(10));
        setHgap(5);
        setVgap(5);

        Set<String> specificKeySet = scope.specificKeySet();

        int i = 0;
        for(String key : scope.globalKeySet()){
            Option value = scope.get(key);
            
            Label keyLabel = new Label(key);

            Node component = value.generateComponent();
            GridPane.setHgrow(component, Priority.ALWAYS);

            this.add(keyLabel, 0, i);
            this.add(component, 1, i);

            if(!specificKeySet.contains(key) && !scope.isGlobalScope()) {
                CheckBox overriteGlobal = new CheckBox();
                component.setDisable(scope.existLocal(key));
                overriteGlobal.selectedProperty().setValue(!scope.existLocal(key));

                overriteGlobal.selectedProperty().addListener((b, old, newVal) -> handleCheckBoxListener(b, old,newVal, overriteGlobal, key));

                this.add(overriteGlobal, 2, i);

            }
            i++;
        }

        this.layout();

    }

    /**
     * Handles the checkboxes that make some controls global or local
     *
     * @param obs observer
     * @param old old value
     * @param newVal new value
     * @param overrideGlobal the checkbox override global
     * @param key key name
     */
    private void handleCheckBoxListener(ObservableValue<? extends Boolean> obs, Boolean old, Boolean newVal, CheckBox overrideGlobal, String key) {
        int row = GridPane.getRowIndex(overrideGlobal);
        Node componentToReplace = this.getNodeByRowColumnIndex(1, row);
        this.getChildren().remove(componentToReplace);

        if(newVal){
            scope.putWithGlobalScopeDefault(key);
        } else{
            scope.remove(key);
        }
        Node newComponent = scope.get(key).generateComponent();
        newComponent.setDisable(old);
        this.add(newComponent, 1, row);
    }

    private Node getNodeByRowColumnIndex (final int column, final int row) {
        Node result = null;
        ObservableList<Node> childrens = this.getChildren();

        for (Node node : childrens) {
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }


}

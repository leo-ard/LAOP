package org.lrima.laop.ui.components.inspector;

import org.lrima.laop.utils.Actions.ObjectGetter;

import com.jfoenix.controls.JFXDecorator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Inspect an element
 * @author Léonard
 */
public class InspectorPanel extends VBox {
    ObjectGetter<? extends Inspectable> objectGetter;
    private HBox header;
    private Label headerLabel;
    private ComboBox<String> categoryBox;

    public InspectorPanel(){
        super();

        this.setSpacing(20);
        this.setPadding(new Insets(10));
        generateUnselectPane();
        this.getStyleClass().add("panel");
        
        this.headerLabel = new Label("Information");
        this.headerLabel.setFont(new Font(18));
        this.categoryBox = new ComboBox<>();
        this.header = new HBox(headerLabel, categoryBox);
        
       
    }

    /**
     * Génère le panneau quand aucune voiture n'est selecté
     */
    private void generateUnselectPane() {
        this.setVisible(false);
    }

    /**
     * Generates a pane depending on selected element
     *
     * @param object
     */
    public void generatePane(Inspectable object){
        this.setVisible(true);
        this.getChildren().clear();
        
        
        this.add(header);

        this.setAlignment(Pos.TOP_LEFT);

        for(String key : this.objectGetter.getObject().getInformationHashmap().keySet()){
            this.add(new Label(key + " : "+ this.objectGetter.getObject().getInformationHashmap().get(key)));
        }
    }

    /**
     * Met à jour ce panneau
     */
    public void update(){
        if(this.objectGetter != null) {
        	ObservableList<String> categories = FXCollections.observableArrayList(this.objectGetter.getObject().getCategories());
        	this.categoryBox.setItems(categories);
        	this.categoryBox.getSelectionModel().select(0);
        
            generatePane(this.objectGetter.getObject());
        }
        else {
            generateUnselectPane();
        }
    }

    /**
     * Ajoute un élément aux enfants de ce panneau
     *
     * @param e l'élément
     */
    public void add(Node e){
        this.getChildren().add(e);
    }

    /**
     * Attribut l'object qui doit être utilisé par une interface
     *
     * @param objectGetter l'interface qui retourneras en tous temps l'objet à dessiner
     */
    public void setObject(ObjectGetter<? extends Inspectable> objectGetter) {
        this.objectGetter = objectGetter;
        update();
    }

    /**
     * @return l'objet associer à l'objet qui est retournée par l'ObjectGetter
     */
    public Object getSelectedObject(){
        if(objectGetter == null) return null;
        return this.objectGetter.getObject();
    }

}

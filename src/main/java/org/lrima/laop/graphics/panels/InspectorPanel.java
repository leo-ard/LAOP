package org.lrima.laop.graphics.panels;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.lrima.laop.simulation.CarInfo;
import org.lrima.laop.utils.ObjectGetter;

import java.util.Map;

/**
 * Inspect an élément
 * @author Léonard
 */
public class InspectorPanel extends VBox {
    ObjectGetter<? extends Inspectable> objectGetter;

    public InspectorPanel(){
        super();

        this.setSpacing(20);
        this.setPadding(new Insets(10));
        generateUnselectPane();
        this.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5)");
    }

    /**
     * Génère le panneau quand aucune voiture n'est selecté
     */
    private void generateUnselectPane() {
        this.setAlignment(Pos.CENTER);
        Label selectItem = new Label("Clique sur une voiture !");
        this.add(selectItem);
    }

    /**
     * Generates a pane depending on selected element
     *
     * @param object
     */
    public void generatePane(Inspectable object){
        this.getChildren().clear();
        object.generatePanel(this);
    }

    /**
     * Met à jour ce panneau
     */
    public void update(){
        if(this.objectGetter != null)
        generatePane(this.objectGetter.getObject());
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

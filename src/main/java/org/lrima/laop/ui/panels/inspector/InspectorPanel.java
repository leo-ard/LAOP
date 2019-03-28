package org.lrima.laop.ui.panels.inspector;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.lrima.laop.utils.Actions.ObjectGetter;

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
        this.getStyleClass().add("panel");
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
        object.generatePanel(this);
    }

    /**
     * Met à jour ce panneau
     */
    public void update(){
        if(this.objectGetter != null)
            generatePane(this.objectGetter.getObject());
        else
            generateUnselectPane();
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

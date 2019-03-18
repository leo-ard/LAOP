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

public class InspectorPanel extends VBox {
    ObjectGetter<Object> objectGetter;

    public InspectorPanel(){
        super();

        this.setSpacing(20);
        this.setPadding(new Insets(10));
        generateUnselectPane();
        this.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5)");
    }

    private void generateUnselectPane() {
        this.setAlignment(Pos.CENTER);
        Label selectItem = new Label("SELECT AN ITEM PLZ");
        this.add(selectItem);
    }

    /**
     * Generates a pane depending on selected element
     *
     * @param object
     */
    public void generatePane(Object object){
        this.getChildren().clear();
        if(object instanceof CarInfo){
            genCarInfo((CarInfo)object);
        }else{
            generateUnselectPane();
        }


    }

    private void genCarInfo(CarInfo car) {
        Label titleLabel = new Label("CAR INFORMATION");
        titleLabel.setFont(new Font(18));
        add(titleLabel);

        this.setAlignment(Pos.TOP_LEFT);

        for(String key : car.getInformationHashmap().keySet()){
            add(new Label(key + " : "+ car.getInformationHashmap().get(key)));
        }

    }

    public void update(){
        if(this.objectGetter != null)
        generatePane(this.objectGetter.getObject());
    }

    private void add(Node e){
        this.getChildren().add(e);
    }

    public void setObject(ObjectGetter<Object> objectGetter) {
        this.objectGetter = objectGetter;
        update();
    }

    public Object getSelectedObject(){
        if(objectGetter == null) return null;
        return this.objectGetter.getObject();
    }

}

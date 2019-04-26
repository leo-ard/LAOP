package org.lrima.laop.ui;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuItem;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class I18n {
    private static HashMap<String, Consumer<String>> allKeys = new HashMap<>();
    private static ResourceBundle messages = ResourceBundle.getBundle("lang/messages");

    public static void update(Locale locale){
        messages = ResourceBundle.getBundle("lang/messages", locale);
        allKeys.forEach((key, consumer) -> consumer.accept(messages.getString(key)));
    }

    public static void bind(Labeled labeled){
        String string = labeled.getText();
        if(string.startsWith("%")){
            allKeys.put(string.substring(1), labeled::setText);
            labeled.setText(messages.getString(string.substring(1)));
        }
    }

    public static void bind(Labeled ... labeleds){
        for (Labeled labeled : labeleds) {
            bind(labeled);
        }
    }

    public static void bind(StringBuilder string){
        if(string.toString().startsWith("%")){
            allKeys.put(string.substring(1), (key) -> string.replace(0, string.length(), key));
            string.replace(0, string.length(), messages.getString(string.substring(1)));
        }
    }

    public static void bind(MenuItem menuItem){
        String string = menuItem.getText();
        if(string.startsWith("%")){
            allKeys.put(string.substring(1), (key)->{
                menuItem.setText(key);
                System.out.println(key + " " + menuItem.getText());
            });
            menuItem.setText(messages.getString(string.substring(1)));
        }
    }

    public static void bind(MenuItem ... menuItems){
        for (MenuItem menuItem : menuItems) {
            bind(menuItem);
        }
    }

    public static void bind(String key, Consumer<String> value){
        value.accept(messages.getString(key));
        allKeys.put(key, value);
    }

    public static void remove(Labeled labeled){
        String label = labeled.getText();
        if(label.startsWith("%")){
            String key = label.substring(1);
            allKeys.remove(key);
        }
    }

    public static void reset(){
        allKeys = new HashMap<>();
    }

    public static void remove(Labeled ... labeleds) {
        for (Labeled labeled : labeleds) {
            remove(labeled);
        }
    }

    public static String getString(String s) {
        return messages.getString(s);
    }
}

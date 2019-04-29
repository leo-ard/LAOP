package org.lrima.laop.ui;

import javafx.scene.control.Labeled;
import javafx.scene.control.MenuItem;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * Utility class to make Internalisation possible
 *
 * @author Leonard
 */
public class I18n {
    private static HashMap<String, Consumer<String>> allKeys = new HashMap<>();
    private static ResourceBundle messages = ResourceBundle.getBundle("lang/messages");

    /**
     * Update the current language by the one specifies in parameters
     *
     * @param locale the new local to use
     */
    public static void update(Locale locale){
        messages = ResourceBundle.getBundle("lang/messages", locale);
        allKeys.forEach((key, consumer) -> consumer.accept(messages.getString(key)));
    }

    /**
     * Bind the text of the label in parameters to the key of the text.
     *
     * @param labeled
     */
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

    public static void reset(){
        allKeys = new HashMap<>();
    }

    public static void removeKey(String key) {
        allKeys.remove(key);
    }

    public static String getString(String s) {
        return messages.getString(s);
    }

    public static Locale getLocal() {
        return messages.getLocale();
    }
}

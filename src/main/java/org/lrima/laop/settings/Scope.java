package org.lrima.laop.settings;

import org.lrima.laop.settings.option.Option;

import java.util.HashMap;

/**
 * @author Leonard Oeast OLeary and Clement Bisaillon
 */
class Scope extends HashMap<String, Object> {

    /**
     * Retrieve the option associated with a certain key
     *
     * @param key - The key to retrieve the option from
     * @return the Object stored in the option or null if an error occurred.
     */
    Option getOption(String key) {
        Object value = get(key);
        if(value instanceof Option){
            return (Option) value;
        }
        return null;

    }

    /**
     * If the object is an Option, return its value else returns its value
     *
     * @param key - The key to retrive the option from
     * @return the Object stored in the option or null if the object doesnt exist
     */
    Object getValue(String key){
        Object value = this.get(key);
        if(value instanceof Option){
            return ((Option) value).getValue();
        }
        return value;
    }

    /**
     * Check if the key exists in the options.
     *
     * @param key - The key to check if it exists.
     * @return true if the key exists in this scope, false otherwise.
     */
    private boolean keyExists(String key) {
        return get(key) != null;
    }
}

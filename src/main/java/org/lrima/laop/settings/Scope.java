package org.lrima.laop.settings;

import javafx.scene.Node;
import org.lrima.laop.settings.option.*;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Leonard Oeast OLeary and Clement Bisaillon
 */
public class Scope extends LinkedHashMap<String, Option> {
    /**
     * The global scope of this scope. Can be null, meaning that this scope is the global scope
     */
    Scope globalScope;


    /**
     * Retrieve the option associated with a certain key. If this key doesnt exist, returns the one in the globalScope specified
     *
     * @param key - The key to retrieve the option from
     * @return the Object stored in the option or null if an error occurred.
     */
    public Option get(String key) {
        Option option = super.get(key);
        if (option == null && globalScope != null) {
            return globalScope.get(key);
        }
        return option;
    }

    /**
     * If the object is an Option, return its value else returns its value
     *
     * @param key - The key to retrive the option from
     * @return the Object stored in the option or null if the object doesnt exist
     */
    public Object getValue(String key) {
        if (exist(key)) {
            return this.get(key).getValue();
        }
        else if(globalScope != null && globalScope.exist(key)){
            return globalScope.get(key).getValue();
        }
        return null;

    }

    /**
     * Rewrites the put method to transform the values into Options before putting it in the array.
     *
     * @param key key with witch the value will be associated with
     * @param value value at that key
     * @return the object at the previous key (always an option)
     */
    public Object put(String key, Object value) {
        return super.put(key, transformToOption(value));
    }

    /**
     * Puts an entry with value of the global scope
     *
     * @param key key to retrieve the value in the global scope and to set it in the scope
     */
    public void putWithGlobalScopeDefault(String key){
        this.put(key, globalScope.get(key).getValue());
    }

    /**
     * Checks the type of the value and create an option according to it
     *
     * @param value The object to transform from
     * @return An option that has the type Value
     */
    private Option transformToOption(Object value) {
        if (value instanceof Option) return (Option) value;
        if (value instanceof String) return new OptionString((String) value);
        if (value instanceof Integer) return new OptionInt((Integer) value);
        if (value instanceof Double) return new OptionDouble((Double) value);
        if (value instanceof Class<?>) return new OptionClass((Class<?>) value);

        throw new UnsupportedOperationException("The type : " + value.getClass() + " is not supported by the Option");
    }

    /**
     * Generates a javafx layout depending on the type of the values in the scope
     *
     * @return The javafx layout.
     */
    public Node generatePanel() {
        ScopeModifierPanel scopeModifierPanel = new ScopeModifierPanel(this);
        scopeModifierPanel.init();
        return scopeModifierPanel;
    }

    /**
     * Sets a global scope to the current scope
     *
     * @param scope the global scope
     */
    public void setGlobalScope(Scope scope) {
        this.globalScope = scope;
    }


    /**
     * @param key the key
     * @return true if the key exist, false otherwise
     */
    private boolean exist(String key) {
        return get(key) != null;
    }

    /**
     * @return The global key.
     */
    public Set<String> globalKeySet(){
        if(globalScope == null)
            return this.keySet();

        Set<String> specificKeySet = new LinkedHashSet<>(super.keySet());
        specificKeySet.addAll(globalScope.keySet());
        return specificKeySet;
    }

    /**
     * @return a specific key set (every key that isn't in the global scope)
     */
    public Set<String> specificKeySet(){
        if(globalScope == null){
            return new LinkedHashSet<>();
        }

        Set<String> specificKeySet = new LinkedHashSet<>(super.keySet());
        specificKeySet.removeAll(globalScope.keySet());
        return specificKeySet;
    }

    /**
     * @return true if this scope is global, false other
     */
    public boolean isGlobalScope() {
        return globalScope == null;
    }

    /**
     * Checks if the this key is local or not
     *
     * @param key the key to check
     * @return true if the key is local, false otherwise
     */
    public boolean existLocal(String key) {
        return super.get(key) == null;
    }
}

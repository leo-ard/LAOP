package org.lrima.laop.settings;

import java.util.HashMap;

/**
 * Stores the settings in different scopes and allows the user to get and set the values of the
 * different settings. The scope is used to differentiate for example the settings for one
 * algorithm and the others
 * @author Clement Bisaillon
 */
public class Settings {

    /**
     * This is the main variable of the settings. It contains the value of each settings
     * The key of this HashMap represents the name of the scope.
     */
    private HashMap<String, Scope> scopes;

    public Settings(){
        scopes = new HashMap<>();
        addScope("global");
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
            return null;
        }

        //Return the option associated with the key in the specified scope
        return this.scopes.get(scope).getValue(key);
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
        this.scopes.put(scope, new Scope());

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
     * Show a JPanel where the use can view the settings and change them
     * @param scopes - The scopes to show the settings for. For each scope, a tab will be added to the panel
     * @return true if successful, false otherwise
     */
    public boolean showPanel(String... scopes){
        //TODO
        return false;
    }


}

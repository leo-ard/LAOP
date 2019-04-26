package org.lrima.laop.settings;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;

import com.jfoenix.controls.JFXListView;

import javafx.scene.control.TextInputDialog;

/**
 * Stores the settings in different scopes and allows the user to get and set the controls of the
 * different settings. The scope is used to differentiate for example the settings for one
 * algorithm and the others
 * @author Clement Bisaillon et Léonard Oest O'Leary
 */
public class Settings {
    public final static String GLOBAL_SCOPE = "GLOBAL";

    /**
     * This is the main variable of the settings. It contains the value of each settings
     * The key of this HashMap represents the name of the scope.
     */
    private LinkedHashMap<String, Scope> scopes;



    public Settings(){
        scopes = new LinkedHashMap<>();
        scopes.put(GLOBAL_SCOPE, new Scope());
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
            //TODO throw exception
            return null;
        }

        //Return the valye associated with the key in the specified scope. If the key doesnt exist, check in the global scope
        Object value = this.scopes.get(scope).getValue(key);
        if(value == null){
            value = this.scopes.get(GLOBAL_SCOPE).getValue(key);
        }

        
 

        return value;
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
     * @param name - the name of the scope. This field is not case-sensitive
     * @return true if successful, false otherwise
     */
    public void addScope(String name){
        Scope newScope = new Scope();
        newScope.setGlobalScope(this.scopes.get(GLOBAL_SCOPE));
        this.scopes.put(name, newScope);
    }
    
    /**
     * Add a scope when the user clicks the add algorithm button
     */
    public void addScope(JFXListView scopeListTable) {
    	//Ask for the name of the scope
    	TextInputDialog scopeNameDialog = new TextInputDialog();
    	scopeNameDialog.setTitle("Scope name");
    	scopeNameDialog.setHeaderText("Choose a name for the new scope");
    	Optional<String> scopeName = scopeNameDialog.showAndWait();
    	
    	scopeName.ifPresent(name -> {
    		this.addScope(name);
    	});
    }

    /**
     * Removes a scope from the settings
     * @param scope - the name of the scope. This field is not case-sensitive
     * @return true if successful, false otherwise
     */
    public boolean removeScope(String scope){
        //If the scope doesn't exists, return false
        if (this.scopes.get(scope) == null) {
            return false;
        }

        this.scopes.remove(scope);
        return true;
    }

    /**
     * Checks if the scope exist
     *
     * @param scope the scope to check
     * @return true if the scope exist, false otherwise
     */
    public boolean scopeExist(String scope){
        return this.scopes.get(scope) != null;
    }

    /**
     * @return all the scopes
     */
    public LinkedHashMap<String, Scope> getScopes(){
    	return this.scopes;
    }
    
    /**
     * @return all the scope keys of the settings
     */
    public ArrayList<String> getScopeKeys(){
    	return new ArrayList<>(this.scopes.keySet());
    }
    
    /**
     * Get all the scopes except for the global
     * @return the scopes without global
     */
    public ArrayList<String> getLocalScopeKeys(){
        ArrayList<String> scopeArray = new ArrayList<>(scopes.keySet());
        scopeArray.remove(GLOBAL_SCOPE);
        return scopeArray;
    }
    
    public LinkedHashMap<String, Scope> getLocalScopes(){
    	LinkedHashMap<String, Scope> scopes = new LinkedHashMap<String, Scope>(this.getScopes());
    	scopes.remove(GLOBAL_SCOPE);
    	return scopes;
    }
    
    /**
     * Creates a lockdowned version of this settings. The returned setting will only be limited to this scope.
     *
     * @param scope the scope to limit the setting to
     * @return the limited version of the settings.
     */
    public LockedSetting lock(String scope){
        return new LockedSetting(this, scope);
    }
}

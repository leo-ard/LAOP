package org.lrima.laop.settings;

/**
 * Locked down version of the Setting class. With this class, a user can only get keys.
 *
 * @author Leonard
 */
public class LockedSetting {
    private Settings settings;
    private String scope;

    /**
     * Takes a setting and locks it to the specified scope.
     *
     * @param settings the setting to lock the scope to
     * @param scope the scope that must be locked
     */
    public LockedSetting(Settings settings, String scope) {
        this.settings = settings;
        this.scope = scope;
    }

    /**
     * Returns the value at that key. The scope is defined in the constructor
     *
     * @param key the key
     * @return the object at that key
     */
    public Object get(String key){
        return this.settings.get(scope, key);
    }
}

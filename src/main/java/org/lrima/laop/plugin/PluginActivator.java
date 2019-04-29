package org.lrima.laop.plugin;

import org.lrima.laop.core.LAOP;

/**
 * New plugins must import this method
 *
 * @author Léonard
 */
public interface PluginActivator {
    void initiate(LAOP laop);
}

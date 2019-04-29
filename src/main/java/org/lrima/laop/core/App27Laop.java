package org.lrima.laop.core;

import javafx.application.Application;

import javax.swing.*;

/**
 * Temporary class to launch different parts of the application
 *
 * @author Clement Bisaillon
 * @version $Id: $Id
 */
public class App27Laop {
    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects.
     */
    public static void main(String[] args) {
        new Thread(() -> Application.launch(LaopGraphical.class)).start();
    }
}

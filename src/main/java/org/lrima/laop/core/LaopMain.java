package org.lrima.laop.core;

import javafx.application.Application;

public class LaopMain {

    public static void main(String[] args){
        runGraphical();
    }

    /**
     * Run the Laop platform with a graphical interface
     *
     * @return true if successful, false otherwise.
     */
    private static boolean runGraphical() {
        //Run the graphical interface
        new Thread(() -> Application.launch(LaopGraphical.class)).start();

        return true;
    }
}

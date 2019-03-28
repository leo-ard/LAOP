package org.lrima.laop.core;

import javafx.application.Application;

import javax.swing.*;

/**
 * Temporary class to launch different parts of the application
 * @author Clement Bisaillon
 */
public class App27Laop {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        
        JButton testConfig = new JButton("Test SimulationEngine");

        testConfig.addActionListener(e -> {
            frame.dispose();
            new Thread(() ->
                    Application.launch(LaopGraphical.class)
            ).start();
        });


        panel.add(testConfig);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setVisible(true);
    }
}

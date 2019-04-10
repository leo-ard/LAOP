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
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        
        JButton testConfig = new JButton("Test Configuration");
        JButton testSimulation = new JButton("Test SimulationEngine");

        testConfig.addActionListener(e -> {
        	frame.dispose();
        	new Thread(() -> 
        			Application.launch(LaopMain.class)
        	).start();
        });
       
        testSimulation.addActionListener(e -> {
            frame.dispose();
            new Thread(() ->
                    Application.launch(LaopGraphical.class)
            ).start();
        });


        panel.add(testConfig);
        panel.add(testSimulation);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setVisible(true);
    }
}

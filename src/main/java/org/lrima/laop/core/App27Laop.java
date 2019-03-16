package org.lrima.laop.core;

import javafx.application.Application;
import org.lrima.laop.physic.TestPhysicMain;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Temporary class to launch different parts of the application
 * @author Clement Bisaillon
 */
public class App27Laop {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        
        JButton testConfig = new JButton("Test configuration");
        JButton testPhysic = new JButton("Test Physique");

        testConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Thread(){
                    public void run() {
                        Application.launch(LaopGraphical.class);
                    }
                }.start();
            }
        });

        testPhysic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Thread(){
                    public void run() {
                        TestPhysicMain.main(new String[]{});}
                }.start();
            }
        });


        panel.add(testConfig);
        panel.add(testPhysic);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setVisible(true);
    }
}

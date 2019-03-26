package org.lrima.laop.physic.test;


import org.lrima.laop.physic.PhysicEngine;
import org.lrima.laop.physic.Physicable;
import org.lrima.laop.physic.concreteObjects.Car;
import org.lrima.laop.simulation.map.SimulationMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * Temporary class to test java.physic engine
 * @author Clement Bisaillon
 */
public class TestPhysicMain extends JPanel implements ActionListener {

    private static final int WORLD_WIDTH = 3000;
    //To repaint every x seconds
    private Timer timer=new Timer(10, this);


    private static PhysicEngine engine;

    //private static Car car1 = new Car();

    public static void main(String[] args) {
        JFrame window = new JFrame();
        TestPhysicMain drawPanel = new TestPhysicMain();
        drawPanel.timer.start();

        window.setSize(500, 900);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setBackground(Color.PINK);

        window.getContentPane().add(drawPanel);
        window.setVisible(true);
        engine = new PhysicEngine(null, new SimulationMap());
        
        engine.addObject(new Car());


        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    engine.togglePause();
                }

                //Move the car
                if(e.getKeyCode() == KeyEvent.VK_D){
                    //Right
                    engine.getObjects().forEach(car->((Car)car).addRotationToWheels(0.05));
                }
                if(e.getKeyCode() == KeyEvent.VK_A){
                    //Left
                    engine.getObjects().forEach(car->((Car)car).addRotationToWheels(-0.05));
                }
                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    //Thrust
                    engine.getObjects().forEach(car->((Car)car).addThrust(0.4));

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    engine.getObjects().forEach(car->((Car)car).stopThrust());
                }
            }
        });


        engine.run();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(255, 0, 0, 100));

        for(Physicable physicable : engine.getObjects()){
            g2d.fill(physicable.getArea());
        }

        g2d.drawString("Hold A and D to rotate car", 20, 20);
        g2d.drawString("Hold space to add a force to the car", 20, 40);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == timer){
            this.repaint();
        }
    }
}

package org.lrima.laop.physic;


import org.lrima.laop.math.Vector2d;
import org.lrima.laop.math.Vector3d;
import org.lrima.laop.physic.objects.Bloc;
import org.lrima.laop.physic.objects.Sphere;
import org.lrima.laop.simulation.objects.Car;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// THIS IS TEMPORARY

public class TestPhysicMain extends JPanel implements ActionListener {

    //To repaint every x seconds
    private Timer timer=new Timer(10, this);

    private final static int WORLD_WIDTH = 800;

    private static PhysicEngine engine = new PhysicEngine(WORLD_WIDTH);
    private static Bloc bloc = new Bloc(1, 100, 200);
    private static Bloc bloc2 = new Bloc(0.5, 200, 50);
    private static Sphere sphere1 = new Sphere(2, 70);

    private static Car car1 = new Car();

    public static void main(String[] args) {
        JFrame window = new JFrame();
        TestPhysicMain drawPanel = new TestPhysicMain();
        drawPanel.timer.start();

        window.setSize(WORLD_WIDTH, 900);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setBackground(Color.PINK);

        window.getContentPane().add(drawPanel);
        window.setVisible(true);

//        bloc.addForce(new Vector3d(1, 0, 0));
//        bloc.addForce(new Vector3d(0.05, 0.1, 0));
//        engine.addObject(bloc);
//
//        bloc2.setPosition(new Vector3d(600, 0, 0));
//        bloc2.addForce(new Vector3d(-0.02, 0.05, 0));
//        engine.addObject(bloc2);
//
//        sphere1.setPosition(new Vector3d(300, 400, 0));
//        sphere1.addForce(new Vector3d(-0.03, -0.05, 0));
//        engine.addObject(sphere1);

        car1.setPosition(new Vector3d(200, 200, 0));
        engine.addObject(car1);

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
                    car1.addRotationToWheels(0.1);
                }
                if(e.getKeyCode() == KeyEvent.VK_A){
                    //Left
                    car1.addRotationToWheels(-0.1);
                }
                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    //Thrust
                    car1.addThrust(0.01);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    car1.stopThrust();
                }
            }
        });


        engine.run();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

//        g2d.fill(bloc.getArea());
//        g2d.fill(bloc2.getArea());
//        g2d.fill(sphere1.getArea());

        g2d.setColor(new Color(255, 0, 0, 100));
        g2d.fill(car1.getArea());

        //Draw the vectors
//        g2d.setColor(new Color(0, 255, 0, 100));
//        for(Vector3d force : car1.getForces()){
////            g2d.fillOval((int)force.getX(), (int)force.getY(), 20, 20);
//            g2d.drawLine((int)car1.getPosition().getX(), (int)car1.getPosition().getY(), (int)force.getX(), (int)force.getY());
//        }
//        g2d.drawLine(0, 0, 100, 100);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == timer){
            this.repaint();
        }
    }
}

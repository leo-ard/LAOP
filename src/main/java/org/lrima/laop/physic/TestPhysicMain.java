package org.lrima.laop.physic;


import org.lrima.laop.math.Vector2d;
import org.lrima.laop.math.Vector3d;
import org.lrima.laop.physic.objects.Bloc;
import org.lrima.laop.physic.objects.Sphere;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// THIS IS TEMPORARY

public class TestPhysicMain extends JPanel implements ActionListener {

    //To repaint every x seconds
    private Timer timer=new Timer(10, this);

    private static PhysicEngine engine = new PhysicEngine();
    private static Bloc bloc = new Bloc(0.1, 150, 100);
    private static Bloc bloc2 = new Bloc(0.5, 150, 100);
    private static Sphere sphere1 = new Sphere(0.01, 70);

    public static void main(String[] args) {
        JFrame window = new JFrame();
        TestPhysicMain drawPanel = new TestPhysicMain();
        drawPanel.timer.start();

        window.setSize(800, 500);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setBackground(Color.PINK);

        window.getContentPane().add(drawPanel);
        window.setVisible(true);

        bloc.addForce(new Vector3d(200, 20, 0));
        engine.addObject(bloc);

        bloc2.setPosition(new Vector3d(600, 0, 0));
        bloc2.addForce(new Vector3d(-200, 40, 0));
        engine.addObject(bloc2);

        sphere1.setPosition(new Vector3d(300, 400, 0));
        sphere1.addForce(new Vector3d(-30, -50, 0));
        engine.addObject(sphere1);

        engine.run();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.fill(bloc.getArea());
        g2d.fill(bloc2.getArea());
        g2d.fill(sphere1.getArea());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == timer){
            this.repaint();
        }
    }
}

package main;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Graphics;

public class mainPanel extends JPanel {
    public mainPanel() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));
        try {
            Thread.sleep(200);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public void startRefresh() {
        int interval = 1000; //milliseconds
        Timer timer = new Timer(interval, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                repaint();
            }
        });
        timer.start();
    }

}
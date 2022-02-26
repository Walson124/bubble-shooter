package main;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class mainPanel extends JPanel {

    public bubble[][] bubbleArray = null;
    public Point[] arrow = new Point[] { new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0) };
    public projectile projectile = new projectile();

    public void setBubbleArray(bubble[][] bubbleArray) {
        this.bubbleArray = bubbleArray;
    }

    public void setArrow(Point[] arrow) {
        this.arrow = arrow;
    }

    public void setProjectile(projectile projectile) {
        this.projectile = projectile;
    }

    public mainPanel() {
        setBackground(new Color(198, 198, 255));
        startRefresh();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            Thread.sleep(5);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        this.setBubbleArray(projectile.getBubbleArray());

        paintBubbles(g);
        drawBorders(g);
        drawArrow(g);
        drawMisc(g);
        drawBubble(g, (bubble) projectile);

    }

    public void paintBubbles(Graphics g) {
        if (bubbleArray != null) {
            for (int i = 0; i < bubbleArray.length; i++) {
                for (bubble temp : bubbleArray[i]) {
                    drawBubble(g, temp);
                }
            }
        }
    }

    public void drawBorders(Graphics g) {
        g.setColor(new Color(225, 225, 225));
        g.drawRoundRect(9, 9, 525, 480, 10, 10);
    }

    public void drawArrow(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(10));
        g2d.setColor(new Color(225, 225, 225));
        // g2d.setColor(Color.black);
        g2d.fillOval(arrow[0].x, arrow[0].y, 1, 1);

        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(arrow[0].x, arrow[0].y, arrow[1].x, arrow[1].y);
        g2d.drawLine(arrow[1].x, arrow[1].y, arrow[2].x, arrow[2].y);
        g2d.drawLine(arrow[1].x, arrow[1].y, arrow[3].x, arrow[3].y);

    }

    public void drawMisc(Graphics g) {
        g.setColor(new Color(225, 225, 225));
        g.drawOval(arrow[0].x - 14, arrow[0].y - 14, 28, 28);
        g.setColor(new Color(184, 184, 184));
        g.fillOval(arrow[0].x - 14, arrow[0].y - 14, 28, 28);
    }

    public void drawBubble(Graphics g, bubble b) {
        if (b.isActive()) {
            g.setColor(b.getColor());
            g.fillOval(b.getLocation().x, b.getLocation().y, b.getRadius(), b.getRadius());
        }
    }

    public void startRefresh() {
        int interval = 10; // milliseconds
        Timer timer = new Timer(interval, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                repaint();
            }
        });
        timer.start();
    }

    

}
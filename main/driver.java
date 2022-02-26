package main;

import java.awt.Point;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class driver {

    public static void main(String[] args) {

        bubble[][] bubbleArray = generateBubbles();
        bubbleArray = activateBubbles(bubbleArray, 9); // 9 rows default, 16 max

        arrow arrow = new arrow();
        projectile projectile = new projectile(bubbleArray);

        JFrame mainFrame = new JFrame();
        frameSetup(mainFrame);

        mainPanel mainPanel = new mainPanel();
        panelSetup(mainFrame, mainPanel, arrow, projectile);
        mainPanel.setBubbleArray(bubbleArray);

    }

    private static bubble[][] generateBubbles() {
        int radius = 28;
        bubble[][] bubbleArray = new bubble[17][16];
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 16; j++) {
                int xShift = 10;
                int yShift = 10;
                if (j % 2 == 1)
                    xShift += 15;
                bubbleArray[i][j] = new bubble();
                bubbleArray[i][j].setLocation(new Point(30 * i + xShift, 30 * j + yShift));
                bubbleArray[i][j].setRadius(radius);
                bubbleArray[i][j].setColor(bubbleArray[i][j].randColor());
            }
        }
        return bubbleArray;
    }

    private static void frameSetup(JFrame jframe) {
        jframe.setSize(800, 600);
        jframe.setResizable(false);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setVisible(true);
    }

    private static void panelSetup(JFrame mainFrame, mainPanel mainPanel, arrow arrow, projectile projectile) {
        mainPanel.setSize(mainFrame.getSize());
        mainFrame.getContentPane().add(mainPanel);
        mainPanel.setProjectile(projectile);
        mainFrame.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent me) {
                arrow.update(me.getX(), me.getY());
                mainPanel.setArrow(arrow.getArrow());
            }
        });
        mainFrame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                if (projectile.isShooting == false || projectile.isActive() == false) {
                    projectile.setLocation(new Point(arrow.getArrow()[0].x - 14, arrow.getArrow()[0].y - 14));
                    projectile.setStart(new Point(arrow.getArrow()[0].x, arrow.getArrow()[0].y));
                    projectile.directionX = arrow.getArrow()[1].x;
                    projectile.directionY = arrow.getArrow()[1].y;
                    projectile.shoot();
                }
            }
        });
    }

    private static bubble[][] activateBubbles(bubble[][] bubbleArray, int rows) {
        // sets first 9 rows to active
        for (int i = 0; i < bubbleArray.length; i++) {
            for (int j = 0; j < rows; j++)
                bubbleArray[i][j].setActive(true);
        }
        return bubbleArray;
    }

}
package main;

import java.awt.Insets;
import java.awt.Point;
import java.awt.event.*;
import java.awt.Color;

import javax.swing.JButton;
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

        buttonSetup(mainPanel);

    }

    private static bubble[][] generateBubbles() {
        int radius = 28;
        bubble[][] bubbleArray = new bubble[17][17]; // 17*16 visible
        for (int i = 0; i < bubbleArray.length; i++) {
            for (int j = 0; j < bubbleArray[i].length; j++) {
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
                if (projectile.gameOver) {
                    projectile.gameOver = false;
                    projectile.setBubbleArray(activateBubbles(generateBubbles(), 9));
                    mainPanel.setBubbleArray(projectile.getBubbleArray());
                    projectile.setPenalties(5);
                    projectile.resetProjectile();
                    mainPanel.startRefresh();
                } else if ((projectile.isShooting == false || projectile.isActive() == false)
                        && me.getX() < 525
                        && me.getX() > 9
                        && me.getY() > 9 && me.getY() < 525
                        && mainPanel.showAbout == false
                        && mainPanel.showHelp == false) {
                    projectile.setLocation(new Point(arrow.getArrow()[0].x - 14, arrow.getArrow()[0].y - 14));
                    projectile.setStart(new Point(arrow.getArrow()[0].x, arrow.getArrow()[0].y));
                    projectile.directionX = arrow.getArrow()[1].x;
                    projectile.directionY = arrow.getArrow()[1].y;
                    projectile.shoot();
                }
            }
        });
    }

    private static void buttonSetup(mainPanel mainPanel) {
        JButton[] buttonArr = new JButton[5];
        for (int i = 0; i < buttonArr.length; i++) {
            buttonArr[i] = new JButton();
            buttonArr[i].setContentAreaFilled(false);
            buttonArr[i].setBorderPainted(false);
            buttonArr[i].setFocusPainted(false);
            buttonArr[i].setMargin(new Insets(0, 0, 0, 0));
        }

        buttonArr[0].setText("Restart");
        buttonArr[0].setSize(60, 60);
        buttonArr[0].setLocation(555, 55);
        buttonArr[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bubble[][] tempBubbleArr = generateBubbles();
                tempBubbleArr = activateBubbles(tempBubbleArr, 9);
                mainPanel.setBubbleArray(tempBubbleArr);
                mainPanel.projectile.resetProjectile();
                mainPanel.projectile.setBubbleArray(tempBubbleArr);
            }
        });

        buttonArr[1].setText("Help");
        buttonArr[1].setSize(55, 55);
        buttonArr[1].setLocation(685, 55);
        buttonArr[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (mainPanel.showHelp == true)
                    mainPanel.showHelp = false;
                else
                    mainPanel.showHelp = true;
            }
        });

        buttonArr[2].setText("Sound");
        buttonArr[2].setSize(55, 55);
        buttonArr[2].setLocation(625, 115);
        buttonArr[2].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        buttonArr[3].setText("Top 10");
        buttonArr[3].setSize(50, 50);
        buttonArr[3].setLocation(570, 180);
        buttonArr[3].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        buttonArr[4].setText("About");
        buttonArr[4].setSize(60, 60);
        buttonArr[4].setLocation(700, 160);
        buttonArr[4].setForeground(Color.yellow);
        buttonArr[4].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (mainPanel.showAbout == true)
                    mainPanel.showAbout = false;
                else
                    mainPanel.showAbout = true;
            }
        });

        mainPanel.addButtons(buttonArr);
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
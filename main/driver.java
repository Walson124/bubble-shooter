package main;

import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class driver {

    public static void main(String[] args) {

        bubble[][] bubbleArray = generateBubbles();

        JFrame mainFrame = new JFrame();
        frameSetup(mainFrame);

        JPanel mainPanel = new mainPanel();
        panelSetup(mainFrame, mainPanel);

    }

    private static bubble[][] generateBubbles() {
        int radius = 10;
        bubble[][] bubbleArray = new bubble[17][16];
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 16; j++) {
                bubbleArray[i][j] = new bubble();
                bubbleArray[i][j].setLocation(new Point(1, 2));
                bubbleArray[i][j].setRadius(10);
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

    private static void panelSetup(JFrame mainFrame, JPanel mainPanel) {
        mainPanel.setSize(mainFrame.getSize());
        mainFrame.getContentPane().add(mainPanel);
        mainPanel.setBackground((new bubble()).randColor());
    }

}
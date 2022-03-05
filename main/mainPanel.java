package main;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class mainPanel extends JPanel {

    public bubble[][] bubbleArray = null;
    public bubble bubbleQueue = null;
    public Point[] arrow = new Point[] { new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0) };
    public projectile projectile = new projectile();
    public bubble[] buttonBG = new bubble[] { new bubble(true, new Point(550, 50), 70, Color.black),
            new bubble(true, new Point(695, 155), 70, Color.black),
            new bubble(true, new Point(620, 110), 65, Color.black),
            new bubble(true, new Point(565, 175), 60, Color.black),
            new bubble(true, new Point(680, 50), 65, Color.black) };
    public boolean showHelp = false, showAbout = false;
    public JTextField[] helpText = new JTextField[17], aboutText = new JTextField[1];
    public int penalties = 5;

    final int interval = 10; // milliseconds
    private Timer timer = new Timer(interval, new ActionListener() {
        @Override
        public void actionPerformed(final ActionEvent e) {
            repaint();
        }
    });

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

        setupHelp();
        setupAbout();

        setBackground(new Color(198, 198, 255));
        startRefresh();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.setBubbleArray(projectile.getBubbleArray());
        this.bubbleQueue = projectile.getBubbleQueue();
        this.penalties = projectile.getPenalties();

        paintBubbles(g);
        drawBorders(g);
        if (!showHelp)
            drawArrow(g);
        drawMisc(g);
        drawBubble(g, (bubble) projectile);
        if ((showHelp && !helpText[0].isVisible()) || (!showHelp && helpText[0].isVisible()))
            drawHelp(g);
        drawAbout(g);

        if (projectile.gameOver) {
            drawGameOver(g, false);
        } else if (projectile.gameWon) {
            drawGameOver(g, true);
        } else
            setBackground(new Color(198, 198, 255));

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

        if (helpText[0].isVisible() || aboutText[0].isVisible()) {
            g.setColor(new Color(198, 198, 255));
            g.fillRoundRect(20, 20, 500, 460, 30, 30);
            g.setColor(new Color(225, 225, 225));
            g.drawRoundRect(20, 20, 500, 460, 30, 30);
        }

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
        // shooter oval
        g.setColor(new Color(225, 225, 225));
        g.drawOval(arrow[0].x - 14, arrow[0].y - 14, 28, 28);
        g.setColor(new Color(184, 184, 184));
        g.fillOval(arrow[0].x - 14, arrow[0].y - 14, 28, 28);

        // queue oval
        g.setColor(new Color(225, 225, 225));
        g.drawOval(20, arrow[0].y - 14, 28, 28);
        g.setColor(new Color(184, 184, 184));
        g.fillOval(20, arrow[0].y - 14, 28, 28);
        drawBubble(g, projectile.getBubbleQueue());

        // penalty ovals
        if (penalties >= 1) {
            g.setColor(new Color(225, 225, 225));
            g.drawOval(60, arrow[0].y - 14, 28, 28);
            g.setColor(new Color(184, 184, 184));
            g.fillOval(60, arrow[0].y - 14, 28, 28);
        }
        if (penalties >= 2) {
            g.setColor(new Color(225, 225, 225));
            g.drawOval(98, arrow[0].y - 14, 28, 28);
            g.setColor(new Color(184, 184, 184));
            g.fillOval(98, arrow[0].y - 14, 28, 28);
        }
        if (penalties >= 3) {
            g.setColor(new Color(225, 225, 225));
            g.drawOval(136, arrow[0].y - 14, 28, 28);
            g.setColor(new Color(184, 184, 184));
            g.fillOval(136, arrow[0].y - 14, 28, 28);
        }
        if (penalties >= 4) {
            g.setColor(new Color(225, 225, 225));
            g.drawOval(174, arrow[0].y - 14, 28, 28);
            g.setColor(new Color(184, 184, 184));
            g.fillOval(174, arrow[0].y - 14, 28, 28);
        }
        if (penalties >= 5) {
            g.setColor(new Color(225, 225, 225));
            g.drawOval(212, arrow[0].y - 14, 28, 28);
            g.setColor(new Color(184, 184, 184));
            g.fillOval(212, arrow[0].y - 14, 28, 28);
        }

        // draw button backgrounds
        buttonBG[0].setColorCode(3);
        buttonBG[1].setColorCode(4);
        buttonBG[2].setColorCode(1);
        buttonBG[3].setColorCode(2);
        buttonBG[4].setColorCode(6);
        for (bubble b : buttonBG)
            drawBubble(g, b);

    }

    public void addButtons(JButton[] buttonArr) {
        for (int i = 0; i < buttonArr.length; i++) {
            if (buttonArr[i] != null)
                add(buttonArr[i]);
        }
    }

    public void setupHelp() {

        Font textFont = new Font("Sans Serif", Font.BOLD, 10);

        for (int i = 0; i < helpText.length; i++) {
            helpText[i] = new JTextField();
            helpText[i].setVisible(false);
            helpText[i].setOpaque(false);
            helpText[i].setEditable(false);
            helpText[i].setFont(textFont);
            helpText[i].setBorder(javax.swing.BorderFactory.createEmptyBorder());
            this.add(helpText[i]);
        }
        helpText[0].setText("Bubble Shooter");
        helpText[1].setText("A puzzle game that will keep you busy for a while");

        // paragraph 1
        helpText[2].setText(
                " Your goal is to clear all the bubbles from the board, scoring as many points as possible. How? you");
        helpText[3].setText(
                "shoot at them with more bubbles, and when three or more of the same color come together, they all");
        helpText[4].setText(
                "explode. Point your mouse to where you want tht enext bubble to go (the arrow at the bottom will");
        helpText[5].setText("indicate that direction), and click to fire the shot.");

        // paragraph 2
        helpText[6].setText(
                " The more bubbles blow up at one shot, the increasingly more points you gain (six ones at one");
        helpText[7].setText(
                "shot is better than two three-bubble shots). Besides, those bubbles that fell apart from the rest will");
        helpText[8].setText(
                "explode too. It is well worth trying to arrange such avalanches, as they will give you ten times more");
        helpText[9].setText("points than ordinary bursts.");

        // paragraph 3
        helpText[10].setText(
                " If your shot fails to detonate any bubbles, you get a foul, and when there are several fouls, a new");
        helpText[11].setText(
                "line of bubbles appears at the top. The number of misfires allowed before bubbles will advance");
        helpText[12].setText("again, is shown by the number of silver balls in the bottom left corner.");

        // paragraph 4
        helpText[13].setText(
                " There is no draw in this game, either you clean all the bubbles off and your score doubles, or they");
        helpText[14].setText("move all the way down on you. No compromise.");

        helpText[15].setText("Good Luck!");
        helpText[16].setText("Click \"Help\" to go back");

        // set locations
        helpText[0].setLocation(150, 15);
        helpText[0].setSize(300, 100);
        helpText[0].setFont(textFont.deriveFont(30F));

        helpText[1].setLocation(140, 50);
        helpText[1].setSize(300, 100);

        // paragraph 1
        helpText[2].setLocation(27, 85);
        helpText[2].setSize(490, 100);
        helpText[3].setLocation(27, 100);
        helpText[3].setSize(490, 100);
        helpText[4].setLocation(27, 115);
        helpText[4].setSize(490, 100);
        helpText[5].setLocation(27, 130);
        helpText[5].setSize(490, 100);
        // paragraph 2
        helpText[6].setLocation(27, 160);
        helpText[6].setSize(490, 100);
        helpText[7].setLocation(27, 175);
        helpText[7].setSize(490, 100);
        helpText[8].setLocation(27, 190);
        helpText[8].setSize(490, 100);
        helpText[9].setLocation(27, 205);
        helpText[9].setSize(490, 100);
        // paragraph 3
        helpText[10].setLocation(27, 235);
        helpText[10].setSize(490, 100);
        helpText[11].setLocation(27, 250);
        helpText[11].setSize(490, 100);
        helpText[12].setLocation(27, 265);
        helpText[12].setSize(490, 100);
        // paragraph 4
        helpText[13].setLocation(27, 295);
        helpText[13].setSize(490, 100);
        helpText[14].setLocation(27, 310);
        helpText[14].setSize(490, 100);

        // last two senctences
        helpText[15].setLocation(233, 355);
        helpText[15].setSize(200, 100);
        helpText[15].setFont(textFont.deriveFont(13F));
        helpText[16].setLocation(210, 385);
        helpText[16].setSize(200, 100);

    }

    public void drawHelp(Graphics g) {
        for (JTextField temp : helpText)
            temp.setVisible(showHelp);
    }

    public void setupAbout() {
        Font textFont = new Font("Sans Serif", Font.BOLD, 30);

        for (int i = 0; i < aboutText.length; i++) {
            aboutText[i] = new JTextField();
            aboutText[i].setVisible(false);
            aboutText[i].setOpaque(false);
            aboutText[i].setEditable(false);
            aboutText[i].setFont(textFont);
            aboutText[i].setBorder(javax.swing.BorderFactory.createEmptyBorder());
            this.add(aboutText[i]);
        }

        aboutText[0].setText("Hi");
        aboutText[0].setLocation(255, 195);
        aboutText[0].setSize(100, 100);

    }

    public void drawAbout(Graphics g) {
        for (JTextField temp : aboutText)
            temp.setVisible(showAbout);

    }

    public void drawGameOver(Graphics g, boolean won) {

        JTextField gameOverTextField = new JTextField();

        if (won) {
            setBackground(Color.green);
            gameOverTextField.setText("Congratulations");
        } else {
            setBackground(Color.red);
            gameOverTextField.setText("Game Over");
        }

        gameOverTextField.setEditable(false);
        gameOverTextField.setFont(new Font("Sans Serif", Font.BOLD, 30));
        gameOverTextField.setVisible(true);

        timer.stop();
    }

    public void drawBubble(Graphics g, bubble b) {
        if (b.isActive()) {
            int x = b.getLocation().x;
            int y = b.getLocation().y;
            int radius = b.getRadius();

            g.setColor(b.getColor());
            g.fillOval(x, y, radius, radius);

            g.setColor(b.getSubColor());
            g.fillOval((int) (x + .05 * radius), (int) y, (int) (.85 * radius), (int) (.85 * radius));

            g.setColor(new Color(225, 225, 225));
            g.fillPolygon(
                    new int[] { (int) (x + .3 * radius), (int) (x + .2 * radius), (int) (x + .32 * radius),
                            (int) (x + .4 * radius) },
                    new int[] { (int) (y + .15 * radius), (int) (y + .25 * radius), (int) (y + .35 * radius),
                            (int) (y + .15 * radius) },
                    4);
            g.fillPolygon(
                    new int[] { (int) (x + .12 * radius), (int) (x + .15 * radius), (int) (x + .23 * radius),
                            (int) (x + .22 * radius) },
                    new int[] { (int) (y + .35 * radius), (int) (y + .5 * radius), (int) (y + .7 * radius),
                            (int) (y + .45 * radius) },
                    4);

        }
    }

    public void startRefresh() {
        timer.start();
    }

}
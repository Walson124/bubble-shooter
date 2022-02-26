package main;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Timer;

public class projectile extends bubble {

    private bubble[][] bubbleArray = null;
    private bubble bubbleQueue = new bubble(true, new Point(20, 501), 28, Color.black);

    final int interval = 1;
    private Timer timer = new Timer(interval, new ActionListener() {
        @Override
        public void actionPerformed(final ActionEvent e) {
            checkBounds();
            checkContact();
            if (isShooting == true)
                move();
        }
    });

    private Point start = new Point();

    public int directionX, directionY;
    public double angle;
    public int speed = 10;

    private int penalties = 5;

    private double actualX, actualY;

    public boolean isShooting;
    public boolean isTouching;
    public boolean gameOver = false;

    private Set<Point> sameColor = new HashSet<>();
    private Set<Point> safeBubbles = new HashSet<>();

    public projectile() {
        super();
        bubbleQueue.setColor(bubbleQueue.randColor());
        resetProjectile();
    }

    public projectile(bubble[][] bubbleArray) {
        super();
        bubbleQueue.setColor(bubbleQueue.randColor());
        this.bubbleArray = bubbleArray;
        resetProjectile();
    }

    public void setStart(Point p) {
        this.start = p;
    }

    public bubble[][] getBubbleArray() {
        return this.bubbleArray;
    }

    public void setBubbleArray(bubble[][] bubbleArray) {
        this.bubbleArray = bubbleArray;
    }

    public bubble getBubbleQueue() {
        return this.bubbleQueue;
    }

    public void setBubbleQueue(bubble bubbleQueue) {
        this.bubbleQueue = bubbleQueue;
    }

    public int getPenalties() {
        return this.penalties;
    }

    public void setPenalties(int penalties) {
        this.penalties = penalties;
    }

    public void resetProjectile() {
        timer.stop();
        this.setLocation(new Point(267 - 14, 515 - 14));
        this.setStart(new Point(267, 515));
        this.setRadius(28);
        this.setColor(bubbleQueue.getColor());
        this.setSubColor(bubbleQueue.getSubColor());
        this.setActive(true);
        this.sameColor = new HashSet<>();
        this.safeBubbles = new HashSet<>();
        isShooting = false;
        isTouching = false;
        bubbleQueue.setColorCode((int) (Math.random() * 7));
    }

    public void shoot() {
        this.isShooting = true;
        this.actualX = this.getLocation().getX();
        this.actualY = this.getLocation().getY();
        this.angle = calculateAngle();
        timer.start();
    }

    private void move() {
        actualX += (speed * Math.cos(angle));
        actualY -= (speed * Math.sin(angle));
        this.setLocation(new Point((int) actualX, (int) actualY));
    }

    private double calculateAngle() {
        double temp = Math.atan(
                (double) (this.start.getY() - directionY)
                        / (double) (directionX - this.start.getX()));
        if (temp < 0)
            temp = ((Math.PI / 2) - Math.abs(temp)) + Math.PI / 2;

        return temp;
    }

    private void checkBounds() {
        if (this.getLocation().getX() + 28 >= 525 || this.getLocation().getX() <= 9) {
            angle = Math.PI - angle;
        }
        if (this.getLocation().getY() < 9) {
            resetProjectile();
        }
    }

    private void checkContact() {
        double[] data = findMinDistance(true); // [i, j, minDistance]
        double minDistance = data[2];

        // if it's touching something
        if (minDistance <= 28) {
            data = findMinDistance(false);
            bubbleArray[(int) data[0]][(int) data[1]].setColor(this.getColor());
            bubbleArray[(int) data[0]][(int) data[1]].setSubColor(this.getSubColor());
            bubbleArray[(int) data[0]][(int) data[1]].setActive(true);

            gameOver();

            findSameColor((int) data[0], (int) data[1], true);
            if (sameColor.size() >= 3) {
                for (Point p : sameColor) {
                    bubbleArray[p.x][p.y].setActive(false);
                }
                popLoose();
            } else
                penalty();
            resetProjectile();
        }

    }

    private void gameOver(){
        for (int i = 0; i < bubbleArray.length; i++){
            if (bubbleArray[i][bubbleArray[0].length - 1].isActive()){
                gameOver = true;
                resetProjectile();
            }
        }
    }

    private void penalty() {
        penalties -= 1;
        if (penalties == -1) {
            for (int i = 0; i < bubbleArray.length; i++) {
                for (int j = bubbleArray[0].length - 1; j >= 0; j--) {
                    if (j == 0) {
                        bubbleArray[i][j].setColor(bubbleArray[i][j].randColor());
                    } else {
                        bubbleArray[i][j].setColor(bubbleArray[i][j - 1].getColor());
                        bubbleArray[i][j].setSubColor(bubbleArray[i][j - 1].getSubColor());
                        bubbleArray[i][j].setActive(bubbleArray[i][j - 1].isActive());
                    }
                }
            }
            penalties = (int) (Math.random() * 4) + 2;
            popLoose();
            gameOver();
        }
    }

    private void popLoose() {
        // popping loose bubbles
        for (int i = 0; i < bubbleArray.length; i++)
            findSafe(i, 0);
        for (int i = 0; i < bubbleArray.length; i++) {
            for (int j = 0; j < bubbleArray[i].length; j++) {
                if (!safeBubbles.contains(new Point(i, j)))
                    bubbleArray[i][j].setActive(false);
            }
        }
    }

    private void findSameColor(int i, int j, boolean searchActive) {
        if (i >= 0 && i < bubbleArray.length && j >= 0 && j < bubbleArray[0].length) {
            if (bubbleArray[i][j].isActive() && this.getColor().equals(bubbleArray[i][j].getColor())) {
                if (sameColor.contains(new Point(i, j))) {
                    return;
                }
                sameColor.add(new Point(i, j));
                findSameColor(i - 1, j, searchActive);
                findSameColor(i + 1, j, searchActive);
                findSameColor(i, j - 1, searchActive);
                findSameColor(i, j + 1, searchActive);
                if (j % 2 == 0) {
                    findSameColor(i - 1, j - 1, searchActive);
                    findSameColor(i - 1, j + 1, searchActive);
                } else {
                    findSameColor(i + 1, j - 1, searchActive);
                    findSameColor(i + 1, j + 1, searchActive);
                }

            }
        }
    }

    private void findSafe(int i, int j) {
        if (i >= 0 && i < bubbleArray.length && j >= 0 && j < bubbleArray[0].length && bubbleArray[i][j].isActive()) {
            if (safeBubbles.contains(new Point(i, j))) {
                return;
            }
            safeBubbles.add(new Point(i, j));
            findSafe(i - 1, j);
            findSafe(i + 1, j);
            findSafe(i, j - 1);
            findSafe(i, j + 1);
            if (j % 2 == 0) {
                findSafe(i - 1, j - 1);
                findSafe(i - 1, j + 1);
            } else {
                findSafe(i + 1, j - 1);
                findSafe(i + 1, j + 1);
            }

        }
    }

    private double[] findMinDistance(boolean checkActive) {
        double[] data = new double[] { -1, -1, 2000 };
        for (int i = 0; i < bubbleArray.length; i++) {
            for (int j = 0; j < bubbleArray[i].length; j++) {
                if (bubbleArray[i][j].isActive() == checkActive
                        && calculateDistance(this.getLocation(), bubbleArray[i][j].getLocation()) < data[2]) {
                    data[0] = i;
                    data[1] = j;
                    data[2] = calculateDistance(this.getLocation(), bubbleArray[i][j].getLocation());
                }
            }
        }
        return data;
    }

    private double calculateDistance(Point p1, Point p2) {
        int xDiff = p1.x - p2.x;
        int yDiff = p1.y - p2.y;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

}

package main;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Timer;

public class projectile extends bubble {

    private bubble[][] bubbleArray = null;

    final int interval = 5;
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
    public int speed = 5;

    private double actualX, actualY;

    public boolean isShooting;
    public boolean isTouching;

    private Set<Point> sameColor = new HashSet<>();

    public projectile() {
        super();
        resetProjectile();
    }

    public projectile(bubble[][] bubbleArray) {
        super();
        this.bubbleArray = bubbleArray;
        resetProjectile();
    }

    public void setStart(Point p) {
        this.start = p;
    }

    public bubble[][] getBubbleArray() {
        return this.bubbleArray;
    }

    private void resetProjectile() {
        timer.stop();
        this.setLocation(new Point(267 - 14, 515 - 14));
        this.setStart(new Point(267, 515));
        this.setRadius(28);
        this.setColor(randColor());
        isShooting = false;
        isTouching = false;
        this.setActive(true);
        this.sameColor = new HashSet<>();
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
        double[] data = findMinDistance(true);
        double minDistance = data[2];

        // if it's touching something
        if (minDistance <= 28) {
            data = findMinDistance(false);
            bubbleArray[(int) data[0]][(int) data[1]].setColor(this.getColor());
            bubbleArray[(int) data[0]][(int) data[1]].setActive(true);

            findSameColor((int) data[0], (int) data[1], true);
            if (sameColor.size() >= 3) {
                for (Point p : sameColor) {
                    bubbleArray[p.x][p.y].setActive(false);
                }
            }
            resetProjectile();
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

package main;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class projectile1 {

    private bubble projectile = new bubble();

    private int mouseX;
    private int mouseY;

    final int interval = 10;

    private int speed = 5;
    private double angle;

    private boolean notShooting = true;

    public boolean isNotShooting() {
        return notShooting;
    }

    public bubble getProjectile() {
        return projectile;
    }

    public projectile1() {

    }

    public projectile1(bubble bubble, bubble[][] bubbleArray) {
        this.projectile = bubble;
    }

    private void resetProjectile() {
        notShooting = true;
        this.projectile = new bubble();
        timer.stop();
    }

    public void shoot(int x, int y) {
        notShooting = false;
        mouseX = x;
        mouseY = y;
        startRefresh();
    }

    private Timer timer = new Timer(interval, new ActionListener() {
        @Override
        public void actionPerformed(final ActionEvent e) {
            // sweet it works
            angle = calculateAngle(projectile.getLocation().getX(), projectile.getLocation().getY(), mouseX, mouseY);
            int newX = (int) (speed * Math.cos(angle) + projectile.getLocation().getX());
            int newY = (int) (projectile.getLocation().getY() - speed * Math.sin(angle));
            projectile.setLocation(new Point(newX, newY));

            if (outOfBounds()) {
                resetProjectile();
            }

        }
    });

    private void startRefresh() {
        timer.start();
    }

    private double calculateAngle(double xi, double yi, int xf, int yf) {
        double tempAngle = Math.atan((double) (yi - yf) / (double) (xf - xi));
        if (tempAngle < 0)
            tempAngle = ((Math.PI / 2) - Math.abs(tempAngle)) + Math.PI / 2;
        return tempAngle;
    }

    private boolean outOfBounds() {
        if (projectile.getLocation().getX() <= 10 || projectile.getLocation().getX() >= 790
                || projectile.getLocation().getY() <= 10) {
            return true;
        }
        return false;
    }

}

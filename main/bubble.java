package main;

import java.awt.Color;
import java.awt.Point;

public class bubble {

    private boolean active = false;
    private Point location = new Point(0, 0);
    private int radius = 0;
    private Color color = Color.black;

    public bubble() {

    }

    public bubble(boolean active, Point location, int radius, Color color) {
        this.active = active;
        this.location = location;
        this.radius = radius;
        this.color = color;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Point getLocation() {
        return this.location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public int getRadius() {
        return this.radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color randColor() {
        int rand = (int) (Math.random() * 5); // gives 0-4
        if (rand == 0) {
            return Color.red;
        }
        if (rand == 1) {
            return Color.orange;
        }
        if (rand == 2) {
            return Color.yellow;
        }
        if (rand == 3) {
            return Color.green;
        } else {
            return Color.blue;
        }
    }

}

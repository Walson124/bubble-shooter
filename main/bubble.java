package main;

import java.awt.Color;
import java.awt.Point;

public class bubble {

    private boolean active = false;
    private Point location = new Point(0, 0);

    private int radius = 0;
    private Color color = Color.black;
    private Color subColor = Color.black;

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

    public Color getSubColor() {
        return this.subColor;
    }

    public void setSubColor(Color subColor) {
        this.subColor = subColor;
    }

    public Color randColor() {
        int rand = (int) (Math.random() * 6); // gives 0-5
        if (rand == 0) {
            this.subColor = new Color(255, 0, 0, 100);
            return new Color(185, 0, 0);
        }
        else if (rand == 1) {
            this.subColor = new Color(230, 200, 100, 100);
            return new Color(230, 140, 50);
        }
        else if (rand == 2) {
            this.subColor = new Color(255, 255, 50, 100);
            return new Color(185, 185, 0);
        }
        else if (rand == 3) {
            this.subColor = new Color(0, 255, 0, 100);
            return new Color(0, 185, 0);
        }
        else if (rand == 4) {
            this.subColor = new Color(210, 150, 240, 100);
            return new Color(170, 50, 230);
        } else {
            this.subColor = new Color(0, 0, 255, 100);
            return new Color(0, 0, 185);
        }
    }

    public void setColorCode(int code) {
        if (code == 0) {
            // red
            this.subColor = new Color(255, 0, 0, 100);
            this.color = new Color(185, 0, 0);
        }
        else if (code == 1) {
            // orange
            this.subColor = new Color(230, 200, 100, 100);
            this.color = new Color(230, 140, 50);
        }
        else if (code == 2) {
            // yellow
            this.subColor = new Color(255, 255, 50, 100);
            this.color = new Color(185, 185, 0);
        }
        else if (code == 3) {
            // green
            this.subColor = new Color(0, 255, 0, 100);
            this.color = new Color(0, 185, 0);
        }
        else if (code == 4) {
            // blue
            this.subColor = new Color(0, 0, 255, 100);
            this.color = new Color(0, 0, 185);
        } else {
            // purple
            this.subColor = new Color(210, 150, 240, 100);
            this.color = new Color(170, 50, 230);
        }
    }

}

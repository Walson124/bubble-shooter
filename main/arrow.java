package main;

import java.awt.Point;

public class arrow {

    private static int mouseX = 0;
    private static int mouseY = 0;

    // [start, tip, left of tip, right of tip]
    private Point[] arrow = new Point[] { new Point(), new Point(), new Point(), new Point() };

    public Point[] getArrow() {
        return arrow;
    }

    public arrow() {
        arrow[0].setLocation(267, 515);
        arrow[1].setLocation(0, 0);
    }

    public void update(int x, int y) {
        updateArrow(x, y);
    }

    public void updateArrow(int x, int y) {
        mouseX = x;
        mouseY = y;

        int arrowLength = 100;
        int diffX = mouseX - arrow[0].x;
        int diffY = Math.abs(mouseY - arrow[0].y);

        double ratio = arrowLength / (Math.sqrt(diffX * diffX + diffY * diffY));

        if (mouseY <= arrow[0].getY()) {
            arrow[1].x = arrow[0].x + (int) (ratio * diffX);
            arrow[1].y = arrow[0].y - (int) (ratio * diffY);
        }

        if (arrow[1].y > 489)
            arrow[1].y = 489;

        int arrowLFLength = (int) (.9 * arrowLength);

        double angle = Math.atan((double) (arrow[0].y - arrow[1].y) / (double) (arrow[1].x - arrow[0].x));
        if (angle < 0)
            angle = ((Math.PI / 2) - Math.abs(angle)) + Math.PI / 2;

        arrow[2].x = arrow[0].x + (int) (arrowLFLength * Math.cos(angle - Math.toRadians(5.0)));
        arrow[2].y = arrow[0].y - (int) (arrowLFLength * Math.sin(angle - Math.toRadians(5.0)));

        arrow[3].x = arrow[0].x + (int) (arrowLFLength * Math.cos(angle + Math.toRadians(5.0)));
        arrow[3].y = arrow[0].y - (int) (arrowLFLength * Math.sin(angle + Math.toRadians(5.0)));

    }

}

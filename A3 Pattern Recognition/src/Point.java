/*************************************************************************
 * Name: Marianna Rapoport
 * Email: mrapoport@uwaterloo.ca
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import edu.princeton.cs.introcs.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {
            double slope1 = slopeTo(o1);
            double slope2 = slopeTo(o2);
            if (slope1 > slope2)
                return 1;
            if (slope1 < slope2)
                return -1;
            return 0;
        }
    };

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        double y1 = that.y;
        double y0 = y;
        double x1 = that.x;
        double x0 = x;
        double deltaY = y1 - y0;
        double deltaX = x1 - x0;
        if (deltaX == 0 && deltaY == 0)
            return Double.NEGATIVE_INFINITY;
        if (deltaY == 0)
            return +0.0;
        if (deltaX == 0)
            return Double.POSITIVE_INFINITY;
        return deltaY / deltaX;
    }

    public int compareTo(Point that) {
        int y1 = that.y;
        int x1 = that.x;
        if (y < y1)
            return -1;
        if (y > y1)
            return 1;
        if (x < x1)
            return -1;
        if (x > x1)
            return 1;
        return 0;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}
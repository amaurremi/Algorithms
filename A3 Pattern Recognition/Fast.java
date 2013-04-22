import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;

import java.util.*;

public class Fast {

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        List<List<Point>> equalSlopePoints = new ArrayList<List<Point>>();
        for (int i = 0; i < n; i++) {
            Point[] sortedPoints = new Point[n];
            System.arraycopy(points, 0, sortedPoints, 0, n);
            Point point = points[i];
            Arrays.sort(sortedPoints, point.SLOPE_ORDER);
            List<Point> equalSlopes = new ArrayList<Point>();
            equalSlopes.add(point);
            Point firstSortedPoint = sortedPoints[0];
            if (point.compareTo(firstSortedPoint) != 0) {
                equalSlopes.add(firstSortedPoint);
            }
            double lastSlope = point.slopeTo(firstSortedPoint);
            for (int k = 1; k < n; k++) {
                Point sortedPoint = sortedPoints[k];
                if (point.compareTo(sortedPoint) == 0) {
                    continue;
                }
                double newSlope = point.slopeTo(sortedPoint);
                if (newSlope == lastSlope) {
                    equalSlopes.add(sortedPoint);
                    if (k == n - 1 && equalSlopes.size() >= 4) {
                        addToEqualSlopes(equalSlopes, equalSlopePoints);
                    }
                } else {
                    if (equalSlopes.size() >= 4) {
                        addToEqualSlopes(equalSlopes, equalSlopePoints);
                    }
                    equalSlopes = new LinkedList<Point>();
                    equalSlopes.add(point);
                    equalSlopes.add(sortedPoint);
                    lastSlope = newSlope;
                }
            }
        }
        print(equalSlopePoints);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        for (List<Point> ps : equalSlopePoints) {
            ps.get(0).drawTo(ps.get(ps.size() - 1));
        }
    }

    private static void addToEqualSlopes(List<Point> equalSlopes, List<List<Point>> equalSlopePoints) {
        Collections.sort(equalSlopes);
        if (notContained(equalSlopes, equalSlopePoints)) {
            equalSlopePoints.add(equalSlopes);
        }
    }

    private static void print(List<List<Point>> equalSlopePoints) {
        for (List<Point> ps : equalSlopePoints) {
            int size = ps.size();
            for (int i = 0; i < size; i++) {
                StdOut.print(ps.get(i));
                if (i != size - 1) {
                    StdOut.print(" -> ");
                }
            }
            StdOut.println();
        }
    }

    private static boolean notContained(List<Point> equalSlopes, List<List<Point>> equalSlopePoints) {
        for (List<Point> ps : equalSlopePoints) {
            int i;
            int size = ps.size();
            for (i = 0; i < size; i++) {
                if (ps.get(i).compareTo(equalSlopes.get(i)) != 0) {
                    break;
                }
            }
            if (i == size)
                return false;
        }
        return true;
    }
}

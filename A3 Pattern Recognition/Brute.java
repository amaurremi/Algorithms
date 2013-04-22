import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Brute {

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        List<Point[]> equalSlopePoints = new ArrayList<Point[]>();
        for (int i = 0; i + 3 < n; i++) {
            for (int j = i + 1; j + 2 < n; j++) {
                for (int k = j + 1; k + 1 < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        Point p1 = points[i];
                        Point p2 = points[j];
                        Point p3 = points[k];
                        Point p4 = points[l];
                        double abs12 = p1.slopeTo(p2);
                        double abs23 = p2.slopeTo(p3);
                        double abs34 = p3.slopeTo(p4);
                        if (abs12 == abs23 && abs23 == abs34) {
                            Point[] equalSlopes = new Point[]{p1, p2, p3, p4};
                            Arrays.sort(equalSlopes);
                            if (notContained(equalSlopes, equalSlopePoints)) {
                                equalSlopePoints.add(equalSlopes);
                            }
                        }
                    }
                }
            }
        }
        for (Point[] ps : equalSlopePoints) {
            for (int i = 0; i < 4; i++) {
                StdOut.print(ps[i]);
                if (i != 3) {
                    StdOut.print(" -> ");
                }
            }
            StdOut.println();
        }
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        for (Point[] ps : equalSlopePoints) {
            ps[0].drawTo(ps[3]);
        }
    }

    private static boolean notContained(Point[] equalSlopes, List<Point[]> equalSlopePoints) {
        for (Point[] ps : equalSlopePoints) {
            int i;
            for (i = 0; i < 4; i++) {
                if (ps[i].compareTo(equalSlopes[i]) != 0) {
                    break;
                }
            }
            if (i == 4)
                return false;
        }
        return true;
    }
}

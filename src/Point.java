/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import java.util.Random;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point implements Comparable<Point> {

    private final Integer x;     // x-coordinate of this point
    private final Integer y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {

        double deltaY = y.doubleValue() - that.y.doubleValue();
        double deltaX = x.doubleValue() - that.x.doubleValue();
        if (deltaY == 0 && deltaX == 0) return Double.NEGATIVE_INFINITY;
        if (deltaY == 0) return (double) +0;
        else if (deltaX == 0) return Double.POSITIVE_INFINITY;
        else return deltaY / deltaX;

    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        if (y.compareTo(that.y) == 0)
            return x.compareTo(that.x);
        else
            return y.compareTo(that.y);
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        Point invokingPoint = new Point(0, 0);
        return new PointComparator();
    }

    private class PointComparator implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            return Double.compare(Point.this.slopeTo(p1), Point.this.slopeTo(p2));
        }
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    private void out(Point[] p)//remove
    {
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-10, 10);
        StdDraw.setYscale(-10, 10);
        Point.this.draw();
        Point zero = new Point(0, 0);
        for (Point point : p) {

            point.draw();
        }
        StdDraw.show();
        for (Point point : p) {
            System.out.print(Point.this.toString()  + " Slope to " + point.toString() + " " + Point.this.slopeTo(point));

            LineSegment seg = new LineSegment(Point.this, point);
            seg.draw();
            System.out.println();
        }

        StdDraw.show();


        System.out.println();
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point[] points = new Point[200];
        Random r = new Random();
        for (int i = 0; i < points.length; ++i) {
            points[i] = new Point(r.nextInt(100), r.nextInt(100));
        }

        FastCollinearPoints b = new FastCollinearPoints(points);

        for (LineSegment segment : b.segments()) {
            System.out.println(segment.toString());
        }
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-10, 100);
        StdDraw.setYscale(-10, 100);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();


    }
}
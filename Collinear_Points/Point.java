/*  
* @file: Point.java
* @brief: 
* @author: Antonio Gutierrez
* @date: 2015-02-27
*/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point one, Point two) {
            double slope1 = slopeTo(one);
            double slope2 = slopeTo(two);
            if (slope1 < slope2) {
                return -1;
            } else if (slope1 == slope2) {
                return 0;
            } else {
                return 1;
            }
        }
    }


    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        int difX = that.x - this.x;
        int difY = that.y - this.y;
        if (difX == 0) {
            // degenrate line (point to itself)
            if (difY == 0) {
                return Double.NEGATIVE_INFINITY;
                // vertical line
            } else {
                return Double.POSITIVE_INFINITY;
            }
            // horizontal line
        } else if (difY == 0) {
            return 0.0;
        } else {
            return difY / (double) difX;
        }
    }


    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        // invoking point is less than that point
        if ((this.y < that.y) || (this.y == that.y && this.x < that.x)) {
            return -1;
        }  else if ((this.y == that.y) && (this.x == that.x)) {
            return 0;
        // invoking point is more than that point
        } else {
            return 1;
        }

    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */

        Point a = new Point(5, 5);
        Point b = new Point(1, 5);
        Point x = new Point(0, 0);
        Point y = new Point(0, 1);
        a.draw();
        b.draw();
        a.drawTo(b);
        assert (a.compareTo(b) == 1);
        assert (a.slopeTo(b) == 0.0);
        assert (a.slopeTo(a) == Double.NEGATIVE_INFINITY);
        assert (x.slopeTo(y) == Double.POSITIVE_INFINITY);
        assert (x.slopeTo(b) == 5.0);
        System.out.println("End");
    }

}

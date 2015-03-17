/*  
* @file: Brute.java
* @brief: 
* @author: Antonio Gutierrez
* @date: 2015-02-27
*/

public class Brute {
    public static void main(String[] args) {


        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        // read from input file not stdin
        In inputFile = new In(args[0]);

        // get number of points
        int num = inputFile.readInt();

        // all the points to be evaluated
        Point[] points = new Point[num];

        // get all points
        for (int i = 0; i < num; i++) {
            int x = inputFile.readInt();
            int y = inputFile.readInt();
            Point p = new Point(x, y);
            points[i] = p;
            p.draw();
        }

        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                if (points[i].compareTo(points[j]) != -1 
                        || points[i].slopeTo(points[j]) 
                        == Double.NEGATIVE_INFINITY) {
                    continue;
                }
                for (int k = 0; k < num; k++) {
                    if (points[j].compareTo(points[k]) != -1 
                            || points[j].slopeTo(points[k]) 
                            == Double.NEGATIVE_INFINITY) {
                        continue;
                    }
                    for (int l = 0; l < num; l++) {
                        if (points[k].compareTo(points[l]) != -1 
                                || points[k].slopeTo(points[l]) 
                                == Double.NEGATIVE_INFINITY) {
                            continue;
                        }

                        double slopeOne = points[i].slopeTo(points[j]);
                        double slopeTwo = points[i].slopeTo(points[k]);
                        double slopeThree = points[i].slopeTo(points[l]);

                        if (slopeOne == slopeTwo  && slopeOne == slopeThree) {
                            System.out.println(points[i].toString() + " -> " 
                                    + points[j].toString() + " -> " 
                                    + points[k].toString() + " -> " 
                                    + points[l].toString());
                            points[i].drawTo(points[l]);
                        }
                    }
                }
            }
        }
    }
}

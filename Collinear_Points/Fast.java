/*  
* @file: Fast.java
* @brief: 
* @author: Antonio Gutierrez
* @date: 2015-02-27
*/

import java.util.Arrays;

public class Fast {
    public static void main(String[] args) {

       StdDraw.setXscale(0, 32768);
       StdDraw.setYscale(0, 32768);

       In input = new In(args[0]);

       int num = input.readInt();

       Point[] points = new Point[num];
       for (int i = 0; i < num; i++) {
           int x = input.readInt();
           int y = input.readInt();
           Point p = new Point(x, y);
           points[i] = p;
           p.draw();
           // System.out.println("Point " + i + " = " + p.toString());
       }

       // array holding 
       Point[] sortedPoints = new Point[num];

       // copy array
       System.arraycopy(points, 0, sortedPoints, 0, num);

       // choosing point p
       for (int i = 0; i < num; i++) {

           // reference point p
           Point p = points[i];

           // System.out.println("For p  = " + p.toString() );
           // sort according to slopes
           Arrays.sort(sortedPoints, 0, num, p.SLOPE_ORDER);

           // printArrays(sortedPoints);
           // printSlopes(p, sortedPoints);
           // System.out.println();

           // we start from 1 because the first element (i,e 0) will always be the 
           // point itself (-Infinity when degenerate line)
           // because of the sorting done before
           double currentSlope;
           int collinears;
           
           if (num > 1) {
               currentSlope = p.slopeTo(sortedPoints[1]);
               collinears = 1;             // number of collinear points with p
           }  else {
               break;
           }

           int j;
           for (j = 2; j < num; j++) {

               double slope = p.slopeTo(sortedPoints[j]);

               // found collinear point. increment count
               if (slope == currentSlope) {
                   collinears++;

                   // found a point with a different slope as before
               } else {

                   // check the number of collinears points are bigger or equal to 
                   // 3 then draw it and print it
                   if (collinears >= 3) {
                       int dontPrint = 0;

                       // check if point used as reference is lexicographically 
                       // lower than the rest of the points that it is collinear with
                       for (int k = 0; k < collinears; k++) {
                           if (p.compareTo(sortedPoints[j - k - 1]) != -1) {
                               dontPrint = 1;
                               break;
                           }
                       }

                       // print and draw line iff reference point p, is 
                       // lexicographically lower than the rest of the collinear 
                       // points so as to not print/draw the same segment several 
                       // times
                       if (dontPrint == 0) {

                           sort(sortedPoints, j - collinears, j);

                           // print points
                           System.out.print(p.toString() + " -> ");
                           int k;
                           for (k = collinears; k > 1; k--) {
                               System.out.print(sortedPoints[j - k].toString() 
                                       + " -> ");
                           }
                           System.out.println(sortedPoints[j - k].toString());

                           // draw line
                           p.drawTo(sortedPoints[j - k]);
                       }
                   }

                   // reset count and set to currentslope to the one with new point
                   currentSlope = slope;
                   collinears = 1;
               }
           }

           if (collinears >= 3) {
               int dontPrint = 0;

               // check if point used as reference is lexicographically lower than 
               // the rest of the points that it is collinear with
               for (int k = 0; k < collinears; k++) {
                   if (p.compareTo(sortedPoints[j - k - 1]) != -1) {
                       dontPrint = 1;
                       break;
                   }
               }

               // print and draw line iff reference point p, is lexicographically 
               // lower than the rest of the collinear points
               // so as to not print/draw the same segment several times
               if (dontPrint == 0) {

                   sort(sortedPoints, j - collinears, j);

                   // print points
                   System.out.print(p.toString() + " -> ");
                   int k;
                   for (k = collinears; k > 1; k--) {
                       System.out.print(sortedPoints[j - k].toString() + " -> ");
                   }
                   System.out.println(sortedPoints[j - k].toString());

                   // draw line
                   p.drawTo(sortedPoints[j - k]);
               }
           }
       }
    }

    private static void printArrays(Point[] P) {
        System.out.println();
        for (int i = 0; i < P.length - 1; i++) {
            System.out.println(P[i].toString() + " + ");
        }
        System.out.println(P[P.length - 1].toString()); 
    }

    private static void printSlopes(Point p, Point[] P) {
        System.out.println();
        for (int i = 0; i < P.length; i++) {
            System.out.println(p.slopeTo(P[i]) + " + ");
        }
        System.out.println(p.slopeTo(P[P.length - 1]));
    }

    private static void sort(Point[] points, int beg, int end) {
        for (int i = beg; i < end; i++) {
            for (int j = i + 1; j < end; j++) {
                if (points[i].compareTo(points[j]) != -1) {
                    Point temp = points[i];
                    points[i] = points[j];
                    points[j] = temp;
                }
            }
        }
    }

    private static void printPoints(Point[] points, int beg, int end) {
        for (int i = beg; i < end; i++) {
            System.out.print(points[i].toString() + " -> ");
        }
        System.out.println(points[end].toString());
    }
}

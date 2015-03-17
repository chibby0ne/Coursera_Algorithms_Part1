/*  
* @file: PercolationStats.java
* @brief: PercolationStats Programming Assigment
* @author: Antonio Gutierrez
* @date: 2015-02-08
* @usage: java-algs4 PercolationStats 200 1000
*/

public class PercolationStats {
    private int size;
    private int experiments;
    private double[] fractions;


    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Not a valid argument");
        }

        size = N;
        experiments = T;
        fractions = new double[experiments];


        for (int i = 0; i < experiments; i++) {
            Percolation p = new Percolation(N);

            int x, y;
            int sitesOpen = 0;

            // while it hasn't percolated keep opening new sites
            // and keep count of openSites
            while (!p.percolates()) {
                do {
                    x = StdRandom.uniform(N) + 1;
                    y = StdRandom.uniform(N) + 1;
                } while (p.isOpen(x, y));
                p.open(x, y);
                sitesOpen++;
            }
            fractions[i] = (double) sitesOpen / (size * size);
        }

    }

    public double mean() {
        return StdStats.mean(fractions);
    }


    public double stddev() {
        return StdStats.stddev(fractions);
    }

    public double confidenceLo() {
        return mean() -  1.96 * stddev() / Math.sqrt(experiments);
    }

    public double confidenceHi() {
        return mean() +  1.96 * stddev() / Math.sqrt(experiments);
    }


    public static void main(String[] args) {
        int N =  Integer.parseInt(args[0]);
        int T =  Integer.parseInt(args[1]);
        PercolationStats per = new PercolationStats(N, T);
        System.out.println("mean\t\t\t= " + per.mean());
        System.out.println("stddev\t\t\t= " + per.stddev());
        System.out.println("95% confidence interval\t\t= " + per.confidenceLo()
                + ", " + per.confidenceHi());

    }
}

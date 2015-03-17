/*  
* @file: Percolation.java
* @brief: Percolation programming assigment
* @author: Antonio Gutierrez
* @date: 2015-02-08
* @usage: java Percolation
*/

/*
 *  false = blocked, true = open
 */

public class Percolation {
    private WeightedQuickUnionUF uf;    // which sites are connected with top 
                                        // and bottom dummy
    private WeightedQuickUnionUF uf1;    // which sites are connected with top dummy
    private boolean[] grid;             // open or block sites
    private int size;                   // grid size

    // create an N x N grid with all the sites full
    public Percolation(int N) {
        if (N < 1) {
            throw new IllegalArgumentException("Size of grid can't be less than 0");
        }

        size = N;

        // create two additonal nodes to facilitate functions for checking if 
        // full or percolates (one is if connected to dummy top and the other 
        // is true if connected to dummy bottom node)

        uf = new WeightedQuickUnionUF(N * N + 2);
        uf1 = new WeightedQuickUnionUF(N * N + 2);
        grid = new boolean[N * N + 2];
        for (int i = 0; i < N * N; i++) {
            grid[i] = false;
        }

        grid[N * N] = true;         // Bottom dummy node
        grid[N * N + 1] = true;     // Top dummy node
    }

    private void checkBounds(int i, int j) {
        if (i < 1 || i > size) {
            throw new IndexOutOfBoundsException("Row index i out of bounds");
        }         
        if (j < 1 || j > size) {
            throw new IndexOutOfBoundsException("Column index j out of bounds");
        }         
    }

    private int xyto1D(int x, int y) {
        return size * adjustInput(x) + adjustInput(y);
    }

    private int adjustInput(int x) {
        return x - 1;
    }

    private void tryConnectRight(int site, int i, int j) {
        if (isOpen(i, j + 1)) {
            uf.union(site, xyto1D(i, j + 1));
            uf1.union(site, xyto1D(i, j + 1));
        }
    }

    private void tryConnectLeft(int site, int i, int j) {
        if (isOpen(i, j - 1)) {
            uf.union(site, xyto1D(i, j - 1));
            uf1.union(site, xyto1D(i, j - 1));
        }
    }


    private void tryConnectDown(int site, int i, int j) {
        if (isOpen(i + 1, j)) {
            uf.union(site, xyto1D(i + 1, j));
            uf1.union(site, xyto1D(i + 1, j));
        }
    }

    private void tryConnectUp(int site, int i, int j) {
        if (isOpen(i - 1, j)) {
            uf.union(site, xyto1D(i - 1, j));
            uf1.union(site, xyto1D(i - 1, j));
        }
    }

   
    // open site (row i, column j) if it is not open
    public void open(int i, int j) {
        checkBounds(i, j);

        int site = xyto1D(i, j);

        // set grid as open
        if (!grid[site]) {
            grid[site] = true;

            // now connect if we can with other nodes at top, bottom, left, right

            // site below this one
            if (i < size && isOpen(i + 1, j)) {
                tryConnectDown(site, i, j);

                // if we are in the floor (in the bottom)
            } else if (i == size) {
                uf.union(site, size * size + 1);
            }

            // site above this one 
            if (i > 1 && isOpen(i - 1, j)) {
                tryConnectUp(site, i, j);

                // if we are in the ceiling (in the top)
            } else if (i == 1) {
                // tryConnectUp(site, i, j);
                uf.union(site, size * size);
                uf1.union(site, size * size);
            }

            // site to the left of this one
            if (j > 1 && isOpen(i, j - 1)) {
                tryConnectLeft(site, i, j);
            }

            // site to the right of this one
            if (j < size && isOpen(i, j + 1)) {
                tryConnectRight(site, i, j);
            }
        }
    }


    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        checkBounds(i, j);

        return grid[xyto1D(i, j)];

    }

    // is site (row i, column j) full?

    /*
    /* here we have to check if this is a top row site or 
    /* if this site is now conected to a site that is full
    */
    public boolean isFull(int i, int j) {

        checkBounds(i, j);
        int site = xyto1D(i, j);

        // check if connected to the top row (i. e: full) and that site is open
        if (isOpen(i, j)) {
            if (uf.connected(site, size * size) 
                    && uf1.connected(site, size * size)) {
                return true;
            }
        }
        return false;
    }

    // does the system percolate?
    public boolean percolates() {
        if (uf.connected(size * size, size * size + 1)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        int N = StdIn.readInt();
        Percolation p = new Percolation(N);
        for (int i = 1; i <= N; i++) {
            System.out.print("iterating with i = " + i + "\n");
            p.open(i, N/2);
        }
        if (p.percolates()) {
            System.out.print("It percolates\n");
        } else {
            System.out.print("It does not percolate\n");
        }

    }
}

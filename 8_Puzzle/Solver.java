/*  
* @file: Solver.java
* @brief: 
* @author: Antonio Gutierrez
* @date: 2015-03-13
*/

public class Solver {

    /* solution == null, not solvable */
    private SearchNode solution; 

    /* according to the description of the problem, a search node is:
     * an initial board, number of  moves made and a reference to the 
     * previous search node */
    /* in case of an initial search node: initial board, 0 moves made,
     * and a null reference */
    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int priority;
        private int movesMade;
        private SearchNode previous;

        SearchNode(Board b, SearchNode p) {
            /* SN consists of board, link to previous SN, 
             * moves made (including this SN) and priority */

            board = b;
            previous = p;

            // if it's an initial search node, then 0 moves made
            if (p == null) {
                movesMade = 0;
            // else increment the moves made
            } else {
                movesMade = previous.movesMade + 1;
                
            }
            // test with manhattan function
            priority = movesMade + board.manhattan();
        }

        public int compareTo(SearchNode that) {
            return priority - that.priority;
        }
    }

    /* find a solution for the initial board (using A* algorithm) */
    public Solver(Board initial) {
        if (initial == null) {
            throw new java.lang.NullPointerException();
        }

        /* if board is already solved? */
        if (initial.isGoal()) {
            solution = new SearchNode(initial, null);
        } else {
            /* create a twin and solve both of them */
            Board twin = initial.twin();

            solution = solve(initial, twin);
        }

    }

    /* solve both twin and original, and depeding on which is solved return null 
     * or the goal search node  */
    private SearchNode solve(Board original, Board twin) {
        MinPQ<SearchNode> queue = new MinPQ<SearchNode>();
        MinPQ<SearchNode> queueTwin = new MinPQ<SearchNode>();

        /* intially it has no previous search node */
        /* so we need to insert the search nodes with null previous SN reference */
        SearchNode originalSN = new SearchNode(original, null);
        SearchNode twinSN = new SearchNode(twin, null);
        queue.insert(originalSN);
        queueTwin.insert(twinSN);

        /* calculate the next search nodes in each board until we reach goal 
         * with either of them  */
        int i = 0;
        while (true) {

            // System.out.println("Iteration number: " + i++);
            /* for original board */

            // System.out.println("original board");
            SearchNode last = leastCostSearchNode(queue);
            // System.out.println("twin board");
            SearchNode lastTwin = leastCostSearchNode(queueTwin);

            if (last.board.isGoal()) {
                return last;
            }

            /* for twin board */
            if (lastTwin.board.isGoal()) {
                return null;
            }
        }

    }

    private SearchNode leastCostSearchNode(MinPQ<SearchNode> queue) {
        SearchNode leastCost = queue.delMin();
        for (Board b : leastCost.board.neighbors()) {

            // System.out.println(b);
            // System.out.println();
            /* if one of the neighbors boards is the same as the previous one, 
             * don't store in the priority queue but if the previous SN was null,
             * then store it */
            if (leastCost.previous == null || !b.equals(leastCost.previous.board)) {
                SearchNode possibleSN = new SearchNode(b, leastCost);
                queue.insert(possibleSN);
            }
        }
        return leastCost;
    }


    /* is the initial board solvable */
    public boolean isSolvable() {
        return solution != null;
    }

    /* minimum number of moves to solve initial board: -1 if unsolvable */
    public int moves() {
        if (solution != null) {
            return solution.movesMade;
        }
        return -1;
    }

    /* sequence of boards with the shortest solution; null if unsolvable */
    public Iterable<Board> solution() {
        /* solution() must return null for unsolvable */
        if (solution == null) {
            return null;
        }
        /* store all SN boards from result to initial in a stack and return it */
        Stack<Board> stack = new Stack<Board>();
        for (SearchNode sn = solution; sn != null; sn = sn.previous) {
            stack.push(sn.board);
        }
        return stack;
    }


    /* solve a slider puzzle */
    public static void main(String[] args) {

        /* create initial board form file */
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] =  in.readInt();
            }
        }
        Board initial = new Board(blocks);

        /* solve puzzle */
        Solver solver = new Solver(initial);

        /* print solution to standard output */
        if (!solver.isSolvable()) {
            System.out.println("No solution possible");
        } else {
            System.out.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                System.out.println(board);
            }
        }


    }

}



/*  
* @file: Board.java
* @brief: 
* @author: Antonio Gutierrez
* @date: 2015-03-13
*/
public class Board {
    private int[][] board;
    private int dimension;

    /* construct a board from an N by N array of blocks */
    public Board(int[][] blocks) {
        board = copyArray(blocks);
        dimension = board.length;
        // toPrint(board);
        // System.out.println(dimension);
    }

    /* helper function: used to copy a N-by-N array */
    private int[][] copyArray(int[][] array) {
        int len = array.length;
        int[][] newArray = new int[len][len];
        for (int i = 0; i < len; i++) {
            assert array[i].length == len;
            for (int j = 0; j < len; j++) {
                newArray[i][j] = array[i][j];
            }
        }
        return newArray;
    }


    /* board dimension N */
    public int dimension() {
        return dimension;
    }
    
    /* number of blocks out of place */
    public int hamming() {
        int outOfPlace = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {

                /* value of position is the linear value of its coordinate + 1 
                 * (because boards begins with 1 and not 0)*/
                int correctVal = i * dimension + j + 1;

                /* check if block is not the position and is not the last block 
                 * as last block is empty (represented by 0) */
                if ((board[i][j] != correctVal) 
                        && (dimension * dimension != correctVal)) {
                    outOfPlace++;
                }
            }
        }
        return outOfPlace;
    }

    /* sum of manhattan distances between blocks and goals */
    public int manhattan() {
        int sumOfdistances = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int correctVal = i * dimension + j + 1;
                int block = board[i][j];
                if (block != correctVal && block != 0) {
                    int correctRow;
                    int correctColumn;
                    if (block % dimension == 0) {
                        correctRow = block / dimension - 1;
                        correctColumn = dimension - 1;
                    } else {
                        correctRow = block / dimension;
                        correctColumn = (block % dimension - 1);
                    }
                    int diffRows = Math.abs(i - correctRow);
                    int diffColumns = Math.abs(j - correctColumn);
                    sumOfdistances += diffRows + diffColumns;
                }
            }
        }   
        return sumOfdistances;
    }


    /* is this board the goal board? */
    public boolean isGoal() {
        return hamming() == 0;
    }

    /* A board that is obtained by changing two adjacent blocks in the same row */
    public Board twin() {
        int[][] twinBoard = copyArray(board);
        Block z = findSpace();
        int spaceRow = z.getRow();
        int spaceColumn = z.getColumn();

        if (spaceRow == 0) {
            if (spaceColumn == 0) {
                swap(spaceRow + 1, spaceColumn, 
                        spaceRow + 1, spaceColumn + 1, twinBoard);
            } else if (spaceColumn == dimension - 1) {
                swap(spaceRow + 1, spaceColumn, 
                        spaceRow + 1, spaceColumn - 1, twinBoard);
            } else {
                swap(spaceRow + 1, spaceColumn, 
                        spaceRow + 1, spaceColumn + 1, twinBoard);
            }
        }  else if (spaceRow == dimension - 1) {
            if (spaceColumn == 0) {
                swap(spaceRow - 1, spaceColumn, 
                        spaceRow - 1, spaceColumn + 1, twinBoard);
            } else if (spaceColumn == dimension - 1) {
                swap(spaceRow - 1, spaceColumn, 
                        spaceRow - 1, spaceColumn - 1, twinBoard);
            } else {
                swap(spaceRow - 1, spaceColumn, 
                        spaceRow - 1, spaceColumn + 1, twinBoard);
            }
        } else {
            if (spaceColumn == 0) {
                swap(spaceRow + 1, spaceColumn, 
                        spaceRow + 1, spaceColumn + 1, twinBoard);
            } else if (spaceColumn == dimension - 1) {
                swap(spaceRow + 1, spaceColumn, 
                        spaceRow + 1, spaceColumn - 1, twinBoard);
            } else {
                swap(spaceRow + 1, spaceColumn, 
                        spaceRow + 1, spaceColumn + 1, twinBoard);
            }
        }
        return new Board(twinBoard);
    }

    /* helper class: used to hold coordinates of a block */
    private final class Block {
        private int row;
        private int col;

        public Block(int i, int j) {
            row = i;
            col = j;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return col;
        }
    }


    /* helper function: find the space in a board */
    private Block findSpace() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (board[i][j] == 0) {
                    return new Block(i, j);
                }
            } 
        }
        return null;
    }


    /* Does this board equal y? */
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        } 
        if (y == null) {
            return false;
        }
        if (this.getClass() != y.getClass()) {
            return false;
        }
        Board b = (Board) y;
        if (board.length != b.board.length) {
            return false;
        }
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (board[i][j] != b.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }


    /* all neighboring boards */
    public Iterable<Board> neighbors() {
        Stack<Board> stack = new Stack<Board>();
        Block z = findSpace();

        int spaceRow = z.getRow();
        int spaceColumn = z.getColumn();


        /* moving up */
        if (spaceRow > 0) {
            // System.out.println("moving up");
            int[][] newNeighbor = copyArray(board);
            swap(spaceRow, spaceColumn, spaceRow - 1, spaceColumn, newNeighbor);
            Board b = new Board(newNeighbor);
            stack.push(b);
        } 
        /* moving right */
        if (spaceColumn < dimension - 1) {
            // System.out.println("moving right");
            int[][] newNeighbor = copyArray(board);
            swap(spaceRow, spaceColumn, spaceRow, spaceColumn + 1, newNeighbor);
            Board b = new Board(newNeighbor);
            stack.push(b);
        } 
        /* moving down */
        if (spaceRow < dimension - 1) {
            // System.out.println("moving down");
            int[][] newNeighbor = copyArray(board);
            swap(spaceRow, spaceColumn, spaceRow + 1, spaceColumn, newNeighbor);
            Board b = new Board(newNeighbor);
            stack.push(b);
        } 
        /* moving left */
        if (spaceColumn > 0) {
            // System.out.println("moving left");
            int[][] newNeighbor = copyArray(board);
            swap(spaceRow, spaceColumn, spaceRow, spaceColumn - 1, newNeighbor);
            Board b = new Board(newNeighbor);
            stack.push(b);
        }

        // System.out.println("Number of neighbors is: " + stack.size());
        return stack;
    }

    /* helper function: swaps two ints */
    private void swap(int firstRow, int firstColumn, int secondRow, 
            int secondColumn, int[][] b)
    {
        int temp = b[firstRow][firstColumn];
        b[firstRow][firstColumn] = b[secondRow][secondColumn];
        b[secondRow][secondColumn] = temp;
    }

    private int numberOfPossibleMoves(int[][] b) {
        /* find space (i.e: 0) and count the number of neighbors it has */
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (b[i][j] == 0) {
                    /* when it is in corners there are 2 possible moves */
                    if (isInCorner(i, j)) {
                        return 2;
                        /* when it is in a side there are 3 possible moves */
                    } else if (isInSide(i, j)) {
                        return 3;
                        /* when it is in surrounded by blocks there are 
                         * 4 possible moves */
                    } else {
                        return 4;
                    }
                }
            }
        }
        return 0;
    }

    private boolean isInCorner(int i, int j) {
        return (i == 0 && j == 0 || i == dimension - 1 && j == dimension - 1
                || i == 0 && j == dimension - 1 || i == dimension - 1 && j == 0);
    }

    private boolean isInSide(int i, int j) {
        return (i == 0 || j == 0 || i == dimension - 1 || j == dimension - 1);
    }

    /* stirng representation of this board */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            int j;
            for (j = 0; j < dimension - 1; j++) {
                s.append(String.format("%d ", board[i][j]));
            } 
            s.append(String.format("%d\n", board[i][j]));
        }
        return s.toString();
    }

    private void toPrint(int[][] b) {
        System.out.println(Integer.toString(dimension) + "\n");
        for (int i = 0; i < dimension; i++) {
            int j;
            for (j = 0; j < dimension - 1; j++) {
                System.out.print(Integer.toString(b[i][j]) + " ");
            } 
            System.out.println(Integer.toString(b[i][j]) + "\n");
        }
    }


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
        Board copyInitial = initial;

        // test toString
        System.out.println("This is the inital board");
        System.out.println(initial);

        // test equal
        if (initial.equals(copyInitial)) {
            System.out.println("Yes they are the same");
        } else {
            System.out.println("Something went wrong!!!");
        }
        
        // test twin
        System.out.println("This is initial's twin");
        System.out.println(initial.twin());
        
        // test dimension
        System.out.println("Board size is: " + initial.dimension());

        // test manhattan
        System.out.println("This is the manhattan distance of the initial " 
                + initial.manhattan());
        
        // test hamming
        System.out.println("This is the hamming distance of the initial " 
                + initial.hamming());
        
        System.out.println();

        // test neighbors
        System.out.println("These are the neighbors");
        for (Board neighbor :  initial.neighbors()) {
            if (neighbor.isGoal()) {
                System.out.println("This is the goal board");
            }
            System.out.println(neighbor);
        } 
    }
}



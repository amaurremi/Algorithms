import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Board {

    private final int[][] blocks;
    private final int manhattan;
    private int space;

    public int hamming() {
        return hamming;
    }

    public int manhattan() {
        return manhattan;
    }

    private final int hamming;

    public Board(int[][] blocks) {
        int n = blocks.length;
        this.blocks = new int[n][n];
        int space = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.blocks[i][j] = blocks[i][j];
                if (blocks[i][j] == 0) {
                    space = i * n + j;
                }
            }
        }
        this.space = space;
        manhattan = evalManhattan();
        hamming = evalHamming();
    }

    public static void main(String[] args) {
        Board b = new Board(new int[][]{
            {8, 1, 3},
            {4, 0, 2},
            {7, 6, 5}
        });
        Board b2 = new Board(new int[][]{
            {3, 2},
            {0, 1}
        });
        System.out.println(b2.neighbors());
    }

    public int dimension() {
        return blocks.length;
    }

    private int evalHamming() {
        int hamming = 0;
        int next = 0;
        int n = dimension();
        for (int[] block : blocks) {
            for (int i = 0; i < n; i++) {
                next++;
                int el = block[i];
                if (el != 0 && (!(next == el) || next == n * n)) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    private int evalManhattan() {
        int manhattan = 0;
        int next = 0;
        int n = dimension();
        for (int[] block : blocks) {
            for (int i = 0; i < n; i++) {
                next++;
                int cell = block[i];
                if (cell != 0 && (!(next == cell) || next == n * n)) {
                    int row = Math.abs(row(next - 1) - row(cell - 1));
                    int col = Math.abs(col(next - 1) - col(cell - 1));
                    manhattan += row + col;
                }
            }
        }
        return manhattan;
    }

    public boolean isGoal() {
        int next = 1;
        int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == n - 1 && j == n - 1)
                    return true;
                if (!(next++ == blocks[i][j]))
                    return false;
            }
        }
        return true;
    }

    private boolean isSpace(int i, int j) {
        return blocks[i][j] == 0;
    }

    public Board twin() {
        if (dimension() < 2) {
            return this;
        }
        Board twin = new Board(blocks);
        if (isSpace(0, 0) || isSpace(0, 1)) {
            twin.swap(1, 0, 1, 1);
        } else {
            twin.swap(0, 0, 0, 1);
        }
        return twin;
    }

    private void swap(int i1, int j1, int i2, int j2) {
        int b1 = blocks[i1][j1];
        int b2 = blocks[i2][j2];
        blocks[i1][j1] = b2;
        blocks[i2][j2] = b1;
        if (b1 == 0) {
            space = i2 * dimension() + j2;
        } else if (b2 == 0) {
            space = i1 * dimension() + j1;
        }
    }

    public boolean equals(Object y) {
        return y == this
            || y instanceof Board
               && Arrays.deepEquals(this.blocks, ((Board) y).blocks);
    }

    private boolean isRight(int index) {
        return (index + 1) % dimension() == 0;
    }

    private boolean isLeft(int index) {
        return index % dimension() == 0;
    }

    public Iterable<Board> neighbors() {
        int index = space;
        List<Board> boards = new LinkedList<Board>();
        int n = dimension();
        int above = index - n;
        int below = index + n;
        int left = index - 1;
        int right = index + 1;
        int row = row(index);
        int col = col(index);
        if (above >= 0) {
            Board newBoard = new Board(blocks);
            newBoard.swap(row, col, row(above), col(above));
            boards.add(newBoard);
        }
        if (below < n * n) {
            Board newBoard = new Board(blocks);
            newBoard.swap(row, col, row(below), col(below));
            boards.add(newBoard);
        }
        if (!isRight(index)) {
            Board newBoard = new Board(blocks);
            newBoard.swap(row, col, row(right), col(right));
            boards.add(newBoard);
        }
        if (!isLeft(index)) {
            Board newBoard = new Board(blocks);
            newBoard.swap(row, col, row(left), col(left));
            boards.add(newBoard);
        }
        return boards;
    }

    private int row(int index) {
        return index / dimension();
    }

    private int col(int index) {
        return index % dimension();
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        b.append(dimension()).append(newLine);
        for (int[] block : blocks) {
            for (int i : block) {
                b.append(" ").append(i);
            }
            b.append(newLine);
        }
        return b.toString();
    }
}

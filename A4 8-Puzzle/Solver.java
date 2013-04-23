import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

import java.util.*;

public class Solver {

    private Board initial;
    private final Iterable<Board> solution;
    private int moves;
    private Map<Board, Board> pathMap = new HashMap<Board, Board>();

    public Solver(Board initial) {
        this.initial = initial;
        solution = solve();
    }

    private Iterable<Board> solve() {
        Stack<Board> solution = new Stack<Board>();
        Stack<Board> path = getSolutionPath();
        while (!path.isEmpty()) {
            Board b = path.pop();
            solution.push(b);
            moves++;
            Board prev = pathMap.get(b);
            while (prev != null && !path.peek().equals(prev)) {
                path.pop();
            }
        }
        return solution;
    }

    public boolean isSolvable() {
        return solution != null;
    }

    public int moves() {
        return moves - 1;
    }

    public Iterable<Board> solution() {
        return solution;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        Solver solver = new Solver(initial);

        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private Stack<Board> getSolutionPath() {
        Board twin = initial.twin();
        Stack<Board> solution = new Stack<Board>();
        MinPQ<BoardMove> pq = new MinPQ<BoardMove>(new BoardMoveComparator());
        MinPQ<BoardMove> twinPq = new MinPQ<BoardMove>(new BoardMoveComparator());
        SET<BoardComparator> processed = new SET<BoardComparator>();
        SET<BoardComparator> twinProcessed = new SET<BoardComparator>();
        int moves = 0;
        pq.insert(new BoardMove(initial, moves));
        processed.add(new BoardComparator(initial));
        int twinMoves = 0;
        twinPq.insert(new BoardMove(twin, twinMoves));
        while (true) {
            initial = pq.delMin().board;
            solution.push(initial);
            twin = twinPq.delMin().board;
            twinProcessed.add(new BoardComparator(twin));
            if (initial.isGoal())
                return solution;
            if (twin.isGoal())
                return null;
            for (Board b : initial.neighbors()) {
                BoardComparator boardComparator = new BoardComparator(b);
                if (!processed.contains(boardComparator)) {
                    pq.insert(new BoardMove(b, ++moves));
                    processed.add(boardComparator);
                    pathMap.put(b, initial);
                }
            }
            for (Board b : twin.neighbors()) {
                BoardComparator boardComparator = new BoardComparator(b);
                if (!twinProcessed.contains(boardComparator)) {
                    twinPq.insert(new BoardMove(b, ++twinMoves));
                    twinProcessed.add(boardComparator);
                }
            }
        }
    }

    private class BoardMove {
        Board board;
        int moves;

        BoardMove(Board board, int moves) {
            this.board = board;
            this.moves = moves;
        }
    }

    private class BoardMoveComparator implements Comparator<BoardMove> {
        public int compare(BoardMove o1, BoardMove o2) {
            int manh = o1.board.manhattan() - o2.board.manhattan();
            int hamm = o1.board.hamming() - o2.board.hamming();
            return o1.moves - o2.moves + (manh > hamm ? hamm : manh);
        }
    }

    private class BoardComparator implements Comparable<BoardComparator> {
        private Board board;

        BoardComparator(Board board) {
            this.board = board;
        }

        public int compareTo(BoardComparator board2) {
            int m1 = board.manhattan();
            int m2 = board2.board.manhattan();
            if (m1 != m2)
                return m1 - m2;
            int h1 = board.hamming();
            int h2 = board2.board.hamming();
            if (h1 != h2)
                return h1 - h2;
            return board.toString().compareTo(board2.toString());
        }
    }
}

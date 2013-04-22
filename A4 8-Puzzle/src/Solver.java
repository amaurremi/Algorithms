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
        MinPQ<BoardMove> pq = new MinPQ<BoardMove>(new BoardComparator());
        MinPQ<BoardMove> twinPq = new MinPQ<BoardMove>(new BoardComparator());
        List<Board> processed = new LinkedList<Board>();
        List<Board> twinProcessed = new LinkedList<Board>();
        int moves = 0;
        pq.insert(new BoardMove(initial, moves));
        processed.add(initial);
        int twinMoves = 0;
        twinPq.insert(new BoardMove(twin, twinMoves));
        while (true) {
            initial = pq.delMin().board;
            solution.push(initial);
            twin = twinPq.delMin().board;
            twinProcessed.add(twin);
            if (initial.isGoal())
                return solution;
            if (twin.isGoal())
                return null;
            for (Board b : initial.neighbors()) {
                if (!processed.contains(b)) {
                    pq.insert(new BoardMove(b, ++moves));
                    processed.add(b);
                    pathMap.put(b, initial);
                }
            }
            for (Board b : twin.neighbors()) {
                if (!twinProcessed.contains(b)) {
                    twinPq.insert(new BoardMove(b, ++twinMoves));
                    twinProcessed.add(b);
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

    private class BoardComparator implements Comparator<BoardMove> {
        public int compare(BoardMove o1, BoardMove o2) {
            int manh = o1.board.manhattan() - o2.board.manhattan();
            int hamm = o1.board.hamming() - o2.board.hamming();
            return o1.moves - o2.moves + (manh > hamm ? hamm : manh);
        }
    }
}

package eightpuzzle;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver{
	private MinPQ<Board> pqH;		// Priority Queue using Hamming
	private MinPQ<Board> pqM;		// Priority Queue using Mahattan
	private List<Board> solu;		// Solution to goal Board
	private Board curr;				// Current search Node
	private Board prev;				// Previous search Node
	private boolean sovable;		// Is there a solution?
	private int moves;				// Steps to current search node 
	
	public Solver(Board initial) {
		this.solu 	 = new ArrayList<Board>();
		this.sovable = false;
		this.moves 	 = 0;
		this.pqH 	 = new MinPQ<Board>(initial.hamming(), initial.BY_HAMMING);
		this.pqM 	 = new MinPQ<Board>(initial.manhattan(), initial.BY_MANHATTAN);
		this.curr	 = initial;
		this.prev	 = null;
		
		this.pqM.insert(initial);		// insert initial into queue
		
		while (!sovable && !this.pqM.isEmpty()) {			// repeat until queue empty
			Board tmp = this.pqM.delMin();
			prev = (curr == initial) ? null : curr;		// cache previous board
			curr = tmp;
			if (!tmp.isGoal()) {
				for (Board b : tmp.neighbors()) {
					if (b.equals(prev))	continue;
					if (b.equals(initial)) {
						sovable = false;
						break;
					}
					this.pqM.insert(b);
				}
				solu.add(tmp);
				++moves;
			} else {
				sovable = true;
				solu.add(tmp);
				break;
			}
		}
	}

	public boolean isSolvable() {
		return sovable;
	}

	public int moves() {
		return moves;
	}

	public Iterable<Board> solution() {
		return solu;
	}

//	public static void main(String[] args) {
//		int[][] boardQ = {{1, 0, 3},
//							{4, 2, 5},
//							{7, 8, 6}};
//		
//		Board q  = new Board(boardQ);
//		Solver s = new Solver(q);
//		System.out.println("Sovable: " + s.isSolvable());
//		System.out.println("------------------");
//		System.out.println("Moves: " + s.moves);
//		System.out.println("------------------");
//		System.out.println("Initial Matrix: ");
//		System.out.println(q.toString());
//		System.out.println("------------------");
//		System.out.println("Solution: ");
//		
//		for (Board b : s.solution()) {
//			System.out.println("------------------");
//			System.out.println(b.toString());
//			System.out.println("------------------");
//		}
//	}
	
	public static void main(String[] args) {
		In in = new In(args[0]);
		int N = in.readInt();
		
		int[][] blocks = new int[N][N]; 
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) 
				blocks[i][j] = in.readInt();
		
		Board initial = new Board(blocks); 			// solve the puzzle

		Solver solver = new Solver(initial);
	
		if (!solver.isSolvable()) 					// print solution to standard output
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves()); 
			for (Board board : solver.solution()) {
				StdOut.println(board.toString());
			}
		}
	}
}

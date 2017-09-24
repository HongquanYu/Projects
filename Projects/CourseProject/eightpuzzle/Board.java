package eightpuzzle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.StdRandom;

public class Board{
	public static final Comparator<Board> BY_HAMMING = new ByHamming();
	public static final Comparator<Board> BY_MANHATTAN = new ByManhattan();
	private int[][] board;
	private int dim;
	int rBlank;
	int cBlank;
	
	public Board(int[][] blocks) {
		this.dim	= blocks.length;
		this.rBlank = this.cBlank = -1;
		
		this.board  = new int[dim][dim]; 			// make of copy of Matrix.
		for (int i = 0; i < dim; ++i)
			for (int j = 0; j < dim; ++j)
				this.board[i][j] = blocks[i][j];

		for (int i = 0; i < dim; ++i)				// find Blank position
			for (int j = 0; j < dim; ++j)
				if (this.board[i][j] == 0) {
					this.rBlank = i;
					this.cBlank = j;
				}
	}

	public int dimension() {
		return dim;
	}

	public int hamming() {
		int n = 0;
		for (int i = 0 ; i < dim; ++i) 
			for (int j = 0; j < dim; ++j) {
				if (this.board[i][j] != 0 && this.board[i][j] != (i * dim + j + 1) )
					++n;
			}
		
		return n;
	}

	public int manhattan() {
		int manhattan = 0;
		
		for (int i = 0; i < dim; ++i)
			for (int j = 0; j < dim; ++j) {
				if (this.board[i][j] == 0 || this.board[i][j] == (i * dim + j + 1) )	// right position
					continue;
				
				// wrong position, calculate its Manhattan distance
				int col, row; 
				if ((col = board[i][j] % dim) == 0) {	// last position of a row
					row = board[i][j] / (dim + 1);
					col = dim - 1;
				} else {								// else positions
					row = board[i][j] / dim;
					--col;
				}

				int dist = Math.abs(i - row) + Math.abs(j - col);	// calculate Manhattan moves
				manhattan += dist;
			}
		
		return manhattan;
	}

	public boolean isGoal() {
		for (int i = 0; i < dim; ++i)
			for (int j = 0; j < dim; ++j)
				if (this.board[i][j] != (i * dim + j + 1) ) {
					if (this.board[i][j] == 0)
						return i == (dim - 1) && j == (dim - 1);
					return false;
				}
		return true;
	}

	public Board twin() {
		int r1 = StdRandom.uniform(dim);
		int c1 = StdRandom.uniform(dim);
		
		int r2 = StdRandom.uniform(dim);
		int c2 = StdRandom.uniform(dim);
		
		if (r1 == r2 || c1 == c2)
			return twin();
		else {
			Board newB = new Board(this.board);
			int tmp 		   = newB.board[r1][c1];
			newB.board[r1][c1] = newB.board[r2][c2];
			newB.board[r2][c2] = tmp;
			return newB;
		}
	}

	public boolean equals(Object y) {
		if (this == y)					return true;
		if (!(y instanceof Board))		return false;
		
		Board yy = (Board) y;
		if (yy.dim != this.dim)			return false;
		
		for (int i = 0; i < dim; ++i)
			for (int j = 0; j < dim; ++j)
				if (yy.board[i][j] != this.board[i][j]) return false;
		return true;
	}

	public Iterable<Board> neighbors() {
		List<Board> ls = new ArrayList<Board>();
		if (rBlank >= dim || rBlank < 0 || cBlank >= dim || cBlank < 0)
			throw new IndexOutOfBoundsException("Blank outside of matrix");
		
//		System.out.println("Blank: " + "Row: " + rBlank + ", Col: " + cBlank);
		
		// let alone the same node as previous node, filter it when enqueue
		// forget update blank of new board is a disaster!!!!!!!!!!!!!!!!!!
		if (rBlank + 1 < dim) {
			Board b = new Board(this.board);
			b.board[rBlank][cBlank] = b.board[rBlank + 1][cBlank];
			b.board[rBlank + 1][cBlank] = 0;
			++b.rBlank;		// VERY IMPORTANT!!!
			ls.add(b);
		}
		if (rBlank - 1 >= 0) {
			Board q = new Board(this.board);
			q.board[rBlank][cBlank] = q.board[rBlank - 1][cBlank];
			q.board[rBlank - 1][cBlank] = 0;
			--q.rBlank;		// VERY IMPORTANT!!!
			ls.add(q);
		}
		if (cBlank + 1 < dim) {
			Board r = new Board(this.board);
			r.board[rBlank][cBlank] = r.board[rBlank][cBlank + 1];
			r.board[rBlank][cBlank + 1] = 0;
			++r.cBlank;		// VERY IMPORTANT!!!
			ls.add(r);
		}
		if (cBlank - 1 >= 0) {
			Board s = new Board(this.board);
			s.board[rBlank][cBlank] = s.board[rBlank][cBlank - 1];
			s.board[rBlank][cBlank - 1] = 0;
			--s.cBlank;		// VERY IMPORTANT!!!
			ls.add(s);
		}
		return ls;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(' ');
		for (int i = 0; i < dim; ++i) {
			for (int j = 0; j < dim; ++j) {
				sb.append(this.board[i][j]);
				sb.append("  ");
			}
			if (i == dim - 1)	break;	// do not add a new line when there is no data
			sb.append("\n");
			sb.append(' ');
		}

		return sb.toString();
	}
	
	private static class ByHamming implements Comparator<Board> {

		@Override
		public int compare(Board o1, Board o2) {
			if (o1.hamming() < o2.hamming())		return -1;
			else if (o1.hamming() > o2.hamming())	return 1;
			else									return 0;
		}
	}
	
	private static class ByManhattan implements Comparator<Board> {

		@Override
		public int compare(Board o1, Board o2) {
			if (o1.manhattan() < o2.manhattan())		return -1;
			else if (o1.manhattan() > o2.manhattan())	return 1;
			else										return 0;
		}
		
	}

	public static void main(String[] args) {
		int[][] boardT = {{1, 2, 3},
							{4, 0, 5},
							{7, 8, 6}};
		
		int[][] boardQ = {{1, 0, 3},
							{4, 2, 5},
							{7, 8, 6}};
		
		int[][] boardR = {{1, 2, 3},
							{4, 5, 6},
							{7, 8, 0}};
		
		Board b = new Board(boardT);
		Board q = new Board(boardQ);
		Board r = new Board(boardR);
		
		System.out.println("The Matrix: ");
		System.out.println(q.toString());
		System.out.println("------------------");
		System.out.println("The hamming: " + q.hamming());
		System.out.println("------------------");
		System.out.println("The manhattan: " + q.manhattan());
		System.out.println("------------------");
		System.out.println("Dimension: " + q.dimension());
		System.out.println("------------------");
		System.out.println("is goal?: " + q.isGoal());
		System.out.println("------------------");
		System.out.println("Equal?: " + q.equals(q));
		System.out.println("------------------");
		
		for (Board bb : q.neighbors()) {
			System.out.println("------------------");
			System.out.println(bb.toString());
			for (Board qq : bb.neighbors()) {
				System.out.println("+++++++++++++++++++");
				System.out.println(qq.toString());
				System.out.println("+++++++++++++++++++");
			}
			System.out.println("------------------");
		}
		System.out.println("The Matrix: ");
		System.out.println(q.toString());
		System.out.println("------------------");
		
		System.out.println("Find a cousin: ");
		System.out.println(q.twin().toString());
	}
}

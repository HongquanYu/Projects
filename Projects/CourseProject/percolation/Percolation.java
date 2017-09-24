package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	int[][] sites;
	int rank;
	int count;
	boolean isOpen;
	int top, bottom;
	WeightedQuickUnionUF wqu;

	/**
	 * Create n-by-n grid, with all sites blocked
	 * @param n , number of rows and collon
	 */
	public Percolation(int n){	//initialize the class
		rank = n;
		sites = new int[rank + 1][rank + 1];
		wqu = new WeightedQuickUnionUF(rank * rank + 2);
		
		for(int i = 1; i <= rank; ++i)		//initialize every element to 1
			for (int j = 1; j <= rank; ++j)
				sites[i][j] = 1;
				
		top = 0;
		bottom = rank * rank + 1;
		isOpen = false;
		count = 0;
		
		for (int i = 1; i <= rank; ++i) {	//open sites and wqu data structure
			wqu.union(top, mapping(1, i));
			wqu.union(bottom, mapping(rank, i));
		}
	}
	
	/**
	 * open site (row, col) if it is not open already
	 * @param row
	 * @param col
	 */
	public void open(int row, int col) {
		validate(row, col);
		if (sites[row][col] == 0)	return;
		sites[row][col] = 0;
		count++;
		
		if ((row > 1 && row < rank) && (col > 1 && col < rank)) {	// adjacent to 4 valid sites
			if (isOpen(row - 1, col))
				wqu.union(mapping(row - 1, col), mapping(row, col));
			if (isOpen(row + 1, col))
				wqu.union(mapping(row + 1, col), mapping(row, col));
			if (isOpen(row, col - 1))
				wqu.union(mapping(row, col - 1), mapping(row, col));
			if (isOpen(row, col + 1))
				wqu.union(mapping(row, col + 1), mapping(row, col));
		}
		else if (((row == 1) || (row == rank )) && ((col == 1) || (col == rank)) ) { //adjacent to 2 valid sites
			if ((row == 1) && (col == 1)) {
				if (isOpen(row + 1, col))
					wqu.union(mapping(row + 1, col), mapping(row, col));
				if (isOpen(row, col + 1))
					wqu.union(mapping(row, col + 1), mapping(row, col));
			}
			else if ((row == 1) && (col == rank)) {
				if (isOpen(row, col - 1))
					wqu.union(mapping(row, col - 1), mapping(row, col));
				if (isOpen(row + 1, col))
					wqu.union(mapping(row + 1, col), mapping(row, col));
			}
			else if ((row == rank) && (col == 1)) {
				if (isOpen(row - 1, col))
					wqu.union(mapping(row - 1, col), mapping(row, col));
				if (isOpen(row, col + 1))
					wqu.union(mapping(row, col + 1), mapping(row, col));
			}
			else {
				if (isOpen(row - 1, col))
					wqu.union(mapping(row - 1, col), mapping(row, col));
				if (isOpen(row, col - 1))
					wqu.union(mapping(row, col - 1), mapping(row, col));
			}
				
//			if (validate(row + 1, col) && isOpen(row + 1, col))
//				wqu.union(mapping(row + 1, col), mapping(row, col));
//			else if (validate(row, col + 1) && isOpen(row, col + 1))
//				wqu.union(mapping(row, col + 1), mapping(row, col));
//			else if (validate(row, col - 1) && isOpen(row, col - 1))
//				wqu.union(mapping(row, col - 1), mapping(row, col));
//			else {
//				if (isOpen(row - 1, col))
//					wqu.union(mapping(row - 1, col), mapping(row, col));
//			}
		}
		else {														//adjacent to 3 valid sites
			if (row == 1) {
				if (isOpen(row, col - 1))
					wqu.union(mapping(row, col - 1), mapping(row, col));
				if (isOpen(row, col + 1))
					wqu.union(mapping(row, col + 1), mapping(row, col));
				if (isOpen(row + 1, col))
					wqu.union(mapping(row + 1, col), mapping(row, col));
			}
			else if (row == rank) {
				if (isOpen(row, col - 1))
					wqu.union(mapping(row, col - 1), mapping(row, col));
				if (isOpen(row, col + 1))
					wqu.union(mapping(row, col + 1), mapping(row, col));
				if (isOpen(row - 1, col))
					wqu.union(mapping(row - 1, col), mapping(row, col));
			}
			else if (col == 1) {
				if (isOpen(row - 1, col))
					wqu.union(mapping(row - 1, col), mapping(row, col));
				if (isOpen(row + 1, col))
					wqu.union(mapping(row + 1, col), mapping(row, col));
				if (isOpen(row, col + 1))
					wqu.union(mapping(row, col + 1), mapping(row, col));
			}
			else {
				if (isOpen(row - 1, col))
					wqu.union(mapping(row - 1, col), mapping(row, col));
				if (isOpen(row + 1, col))
					wqu.union(mapping(row + 1, col), mapping(row, col));
				if (isOpen(row, col - 1))
					wqu.union(mapping(row, col - 1), mapping(row, col));
			}
		}
	}
	
	/**
	 * is site (row, col) open?
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isOpen(int row, int col) {
		return sites[row][col] == 0;
	}
	
	/**
	 * is site (row, col) full?
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isFull(int row, int col) {
		return wqu.connected(mapping(row, col), top);
	}
	
	//---------------------------------helper methods---------------------------------
	
	private boolean validate(int r, int c) {
		if (!((r <= rank) && (r >= 1) && (c <= rank) && (c >= 1)))
			throw new IllegalArgumentException("index out of range");
		return true;
	}
	
	
	/**
	 * mapping elements in the site to union find
	 * @param row
	 * @param col
	 */
	private int mapping(int row, int col) {
		return (row - 1) * rank + col;
	}
	
	/**
	 * number of open sites
	 * @return
	 */
	public int numberOfOpenSites() {
//		for (int i = 1; i <= rank; ++i)
//			for (int j = 1; j <= rank; ++j)
//				if (sites[i][j] == 0)
//					++count;
		return count;
	}
	
	/**
	 * does the system percolate?
	 * @return
	 */
	public boolean percolates() {
		return wqu.connected(top, bottom);
	}
	
	/**
	 * test client
	 * @param args
	 */

	public static void main(String[] args) {
		Percolation p = new Percolation(100);
		do {
			int xi = StdRandom.uniform(1, 101);
			int yj = StdRandom.uniform(1, 101);
			p.open(xi, yj);
		} while (!p.percolates());
		System.out.println("Rank: " + p.rank + ", Percolate threshold number: " + p.numberOfOpenSites());
		System.out.println("ratio " + (double)p.count / (p.rank * p.rank));
	}
}

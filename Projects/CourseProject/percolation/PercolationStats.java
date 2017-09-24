package percolation;

import java.util.Arrays;
import edu.princeton.cs.algs4.StdStats;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;;

public class PercolationStats {
	double[] stats;
	int times;
	int rank;
	/**
	 * perform T independent experiments on an N-by-N grid
	 * @param N
	 * @param T
	 */
	@SuppressWarnings("deprecation")
	public PercolationStats(int N, int T) {
		rank = N;
		times = T;
		stats = new double [T];
		int t = T; //counter
		int i = 0;
		while (t > 0) {
			Percolation p = new Percolation(rank);
			while (!p.percolates()) {
				int row = (int) (StdRandom.uniform() * (rank + 1));
				int col = (int) (StdRandom.uniform() * (rank + 1));
				if (row == 0 || col == 0) continue;
				p.open(row, col);
			}
			stats[i++] = (double)p.count / (rank * rank);
			--t;
		}
	}
	
	public void show() {
		System.out.print("Array: ");
		for (double o: stats)
			System.out.print(" " + o + " ");
		System.out.println();
	}
	
	/**
	 * sample mean of percolation threadhold
	 * @return
	 */
	public double mean() {
		return StdStats.mean(stats);
	}
	
	/**
	 * sample standard deviation of percolation threshold
	 * @return
	 */
	public double stddev() {
		return StdStats.stddev(stats);
	}
	
	/**
	 * low endpoint of 95% confidence interval
	 * @return
	 */
	public double confidenceLow() {
		double mean = mean();
		return mean - ((1.96 * stddev() / Math.sqrt(times)));
	}
	
	/**
	 * high endpoint of 95% confidence interval
	 * @return
	 */
	public double confidenceHigh() {
		double mean = mean();
		return mean + ((1.96 * stddev() / Math.sqrt(times)));
	}
	
	
	public static void main(String [] args) {
		
		Stopwatch sw = new Stopwatch();
		PercolationStats ps = new PercolationStats(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
		StdOut.println("Execute time is: " + sw.elapsedTime() + " seconds.");
//		PercolationStats ps = new PercolationStats(100, 50);
//		ps.show();

		StdOut.printf("%-24s", "mean");
		StdOut.printf("%2s", "= ");
		StdOut.printf("%18.16f\n", ps.mean());
		
		StdOut.printf("%-24s", "stddev");
		StdOut.printf("%2s", "= ");
		StdOut.printf("%20.18f\n", ps.stddev());
		
		StdOut.printf("%-24s", "95% confidence interval");
		StdOut.printf("%2s", "= ");
		StdOut.printf("%18.16f, %18.16f\n", ps.confidenceLow(), ps.confidenceHigh());
	}
}

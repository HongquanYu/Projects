package percolation;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class QuickFind {
	int[] id;
	int count;

	// initializer
	public QuickFind(int n) {
		id = new int[n];
		for (int i = 0; i < n; ++i) {
			id[i] = i;
		}
		count = n;
	}

	// add connections between p and q
	public void union(int p, int q) {
		int rootP = find(p);
		int rootQ = find(q);
		if (rootQ == rootP)
			return;
		id[rootQ] = rootP;
		count--;
	}

	// component identifier for p
	public int find(int p) {
		validate(p);
		while (p != id[p])
			p = id[p];
		return p;
	}

	public boolean validate(int p) {
		int n = id.length;
		if (p >= n || p < 0)
			throw new IllegalArgumentException();
		return true;
	}

	// return true if p and q are in the same component
	public boolean isConnected(int p, int q) {
		return find(p) == find(q);
	}

	// number of component
	public int count() {
		return count;
	}

	public static void main(String[] args) {
		int n = StdIn.readInt();
		QuickFind uf = new QuickFind(n);
		while (!StdIn.isEmpty()) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			if (uf.isConnected(p, q))
				continue;
			uf.union(p, q);
			StdOut.println(p + " " + q);
		}
		StdOut.println(uf.count() + " components");
	}

}

package percolation;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class WeightedQuickUnion {
	private int[] parent;
	private int[] size;
	private int count;
	
	public WeightedQuickUnion(int n) {
		parent = new int[n];
		size = new int[n];
		for (int i = 0; i < n; ++i) {
			parent[i] = i;
			size[i] = i;
		}
		count = n;
	}
	
	public int count() {
		return count;
	}
	
	public int find(int p) {
		validate(p);
		while (p != parent[p])
			p = parent[p];
		return p;
	}
	
	private void validate(int p) {
		int n = parent.length;
		if (p < 0 || p >= n)
			throw new IndexOutOfBoundsException();
	}
	
	public boolean isConnected(int p, int q) {
		return find(p) == find(q);
	}
	
	public void union(int p, int q) {
		int rootP = find(p);
		int rootQ = find(q);
		
		if (rootQ == rootP) return;
		if (size[rootP] > size[rootQ]) {
			parent[rootQ] = rootP;
			size[rootP] += size[rootQ];
		}
		else {
			parent[rootP] = rootQ;
			size[rootQ] += size[rootP];
		}
		count--;
	}
	
    public static void main(String[] args) {
        int n = StdIn.readInt();
        WeightedQuickUnion uf = new WeightedQuickUnion(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.isConnected(p, q)) continue;
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
    }
}

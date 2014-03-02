package utilities.graphs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

public class ShortestPath {
	/**
	 * shortest path from r to all points
	 * 
	 * @param r
	 *            the start node (index)
	 * @param in
	 *            the adjacency matrix
	 * @return the length of the path from r to each node
	 */
	public int[] dijkstraAM(int r, int[][] in) {
		final int[] out = new int[in.length], prev = new int[in.length];
		TreeSet<Integer> pq = new TreeSet<Integer>(new Comparator<Integer>() {
			public int compare(Integer i0, Integer i1) {
				if (out[i0] != out[i1])
					return Double.compare(out[i0], out[i1]);
				// note: the method is for a set (won't insert the element if this method returns 0)
				return i0 - i1;
			}
		});
		Arrays.fill(out, Integer.MAX_VALUE / 2);
		out[r] = 0;
		prev[r] = -1;
		pq.add(r);
		while (!pq.isEmpty()) {
			int t = pq.first();
			pq.remove(pq.first());
			for (int i = 0; i < in.length; i++)
				if (Math.max(out[t], in[t][i]) < out[i]) {
					pq.remove(i);
					out[i] = Math.max(out[t], in[t][i]);
					prev[i] = t;
					pq.add(i);
				}
		}
		return out;
	}
}

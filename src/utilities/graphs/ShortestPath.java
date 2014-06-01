package utilities.graphs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class ShortestPath {
	/**
	 * shortest path from r to all points
	 * 
	 * @param src
	 *            the start node (index)
	 * @param adjMat
	 *            the adjacency matrix
	 * @return the length of the path from r to each node
	 */
	public int[] dijkstra(int src, int[][] adjMat) {
		// inSet is not necessary, just to prevent inf loop when there is negative weight
		//boolean[] inSet = new boolean[adjMat.length];
		final int[] dists = new int[adjMat.length];
		int[] prev = new int[adjMat.length];
		TreeSet<Integer> pq = new TreeSet<Integer>(new Comparator<Integer>() {
			public int compare(Integer i0, Integer i1) {
				if (dists[i0] != dists[i1])
					return Double.compare(dists[i0], dists[i1]);
				// note: the method is for a set (won't insert the element if this method returns 0)
				return i0 - i1;
			}
		});
		Arrays.fill(dists, Integer.MAX_VALUE / 2);
		dists[src] = 0;
		prev[src] = -1;
		pq.add(src);
		//inSet[src] = true;
		while (!pq.isEmpty()) {
			int t = pq.first();
			//inSet[t] = true;
			pq.remove(pq.first());
			for (int i = 0; i < adjMat.length; i++) {
				//if (inSet[i])
				//	continue;
				if (dists[t] + adjMat[t][i] < dists[i]) {
					pq.remove(i);
					dists[i] = dists[t] + adjMat[t][i];
					prev[i] = t;
					pq.add(i);
				}
			}
		}
		return dists;
	}
	
	public int[] bellmanFord(int src, List<HashMap<Integer, Integer>> adjList) {
		final int[] dists = new int[adjList.size()];
		int[] prev = new int[adjList.size()];
		Arrays.fill(dists, Integer.MAX_VALUE);
		dists[src] = 0;
		
		for (int i = 0; i < adjList.size(); i++) {
			for (int j = 0; j < adjList.size(); j++)
				for (Integer k: adjList.get(j).keySet())
					if (dists[k] > dists[j] + adjList.get(j).get(k)) {
						dists[k] = dists[j] + adjList.get(j).get(k);
						prev[k] = j;
					}
		}
		
		// check negative cycles
		for (int i = 0; i < adjList.size(); i++) {
			for (Integer j: adjList.get(i).keySet())
				if (dists[j] > dists[i] + adjList.get(i).get(j)) {
					// negative cycle!
				}
		}
		return dists;
	}
}

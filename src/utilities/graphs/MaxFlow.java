package utilities.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Java implementations of Ford-Fulkerson method.
 * Find augmenting path until it fails
 */
public class MaxFlow {

	/**
	 * Max flow - Edmond Karp.
	 * The input graph is not altered.
	 * @param src source index
	 * @param sink sink index
	 * @param graph
	 * @return maximum flow
	 */
	public int maxFlow(int src, int sink, ArrayList<HashMap<Integer, Integer>> graph) {
		// residue graph
		ArrayList<HashMap<Integer, Integer>> residue = new ArrayList<HashMap<Integer, Integer>>();
		for (int i = 0; i < graph.size(); i++) { //init
			residue.add(new HashMap<Integer, Integer>());
			for (int j: graph.get(i).keySet())
				residue.get(i).put(j, 0);
		}
		// max flow found so far
		int flow = 0;
		// search for new path with BFS (yielding the shortest augmenting path in terms of jumps)
		while (true) {
			// search queue
			Queue<Integer> q = new LinkedList<Integer>();
			// prev node in the augmenting path
			int[] prev = new int[graph.size()];
			Arrays.fill(prev, -1);
			
			int[] fill = new int[graph.size()];
			q.offer(src);
			fill[src] = Integer.MAX_VALUE;
			while (prev[sink] == -1) {
				if (q.isEmpty())
					return flow;
				int p = q.poll();
				for (int i = 0; i < graph.size(); i++) {
					if ((p != i) && (prev[i] == -1)) {
						boolean hasEdge = false;
						// check forward edge - existing flow
						if ((graph.get(p).containsKey(i)) && (residue.get(p).get(i) < graph.get(p).get(i))) {
							// the edge has not reached maximum capacity
							hasEdge = true;
							prev[i] = p;
							fill[i] = graph.get(p).get(i) - residue.get(p).get(i);
						}
						// check backward edge - potential
						if ((graph.get(i).containsKey(p)) && (residue.get(i).get(p) > 0)) {
							prev[i] = p;
							hasEdge = true;
							if (!hasEdge)
								fill[i] = residue.get(i).get(p);
							else
								fill[i] += residue.get(i).get(p);
						}
						if (hasEdge) {
							fill[i] = Math.min(fill[p], fill[i]);
							q.offer(i);
						}
					}
				}
			}
			
			flow += fill[sink];
			int t = sink;
			// update residue graph
			while (t != src) {
				int pre = prev[t];
				int excess = fill[t];
				if (graph.get(pre).containsKey(t)) {
					excess = Math.max(residue.get(pre).get(t) + fill[t] - graph.get(pre).get(t), 0);
					residue.get(pre).put(t, residue.get(t).get(pre) - excess);
				}
				if (excess > 0)
					residue.get(t).put(pre, residue.get(t).get(pre) - excess);
				t = pre;
			}
		}
	}

}


package utilities.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * A cut vertex is a vertex that when removed (with its boundary edges) from a graph 
 * creates more components than previously in the graph.
 * 
 * The input graph is assumed to be connected
 * @author Rex
 *
 */
public class CutVertex {
	
	private boolean[] visited;
	// vertex number based on numVerts value when a vertex is being visited
	private int[] num;
	// low[v] is the lowest numbered vertex v or any vertex in v's subtree is connected to
	private int[] low;
	// parents[id] is the id of the parent of v
	private int[] parents;
	// number of vertices already visited so far
	private int numVerts;

	/**
	 * Find all cut vertices
	 * DFS spanning tree has the property that a vertex can only has back edge to vertices that are
	 * ancestors of this vertex
	 * 
	 * In the DFS spanning, tree, a cut vertex (except root) does not have any subtree which contains
	 * at least a vertex that has a back edge to the cut vertex's ancestors.
	 * 
	 * A node has an id (as in the index in adjList) as well as its number, which is num[id].
	 * 
	 * @param adjList connected, undirected graph as adjacency list
	 * @return list of all cut vertices
	 */
	public List<Integer> cutVertexUndirected(ArrayList<ArrayList<Integer>> adjList) {
		List<Integer> cutVertices = new ArrayList<Integer>();
		visited = new boolean[adjList.size()];
		num = new int[adjList.size()];
		low = new int[adjList.size()];
		parents = new int[adjList.size()];
		numVerts = 0;
		boolean[] isCutVerts = new boolean[adjList.size()];
		cutVertDfs(0, adjList, isCutVerts);
		
		// fill in all cutVertices
		for (int i = 0; i < isCutVerts.length; i++) {
			if (isCutVerts[i])
				cutVertices.add(i);
		}
		return cutVertices;
	}

	/**
	 * DFS spanning tree root: if there are more than 1 child, it is cut vertex.
	 * For non-root v, if w is v's child, and low[w] < num[v], v has a subtree (rooted at w) which contains
	 * at least a vertex that has a back edge to v's ancestors.
	 * So v is a cut vertex if and only if low[w] >= num[v]
	 * @param vertex
	 * @param adjList
	 */
	private void cutVertDfs(int vertex, ArrayList<ArrayList<Integer>> adjList, boolean[] isCutVerts) {
		// number of children of vertex
		int numChildren = 0;
		visited[vertex] = true;
		numVerts++;
		low[vertex] = numVerts;
		num[vertex] = numVerts;
		for (Integer neighbor: adjList.get(vertex)) {
			if (!visited[neighbor]) {
				// neighbor is a child of vertex
				numChildren++;
				parents[neighbor] = vertex;
				cutVertDfs(neighbor, adjList, isCutVerts);
				if (vertex == 0) {
					// root
					if (numChildren > 1) {
						isCutVerts[vertex] = true;
					}
					// the below test of low and num is not applicable to root
					continue;
				}
				if (low[neighbor] >= num[vertex]) {
					isCutVerts[vertex] = true;
				}
				/*
				 * If neighbor or any node in its subtree has a back to vertex's ancestors,
				 * vertex, as neighbor's parent, has the same property.
				 */
				low[vertex] = Math.min(low[vertex], low[neighbor]);
			}
			else {
				if (neighbor != parents[vertex]) {
					// neighbor is vertex's ancestor. (vertex, neighbor) is a back edge
					low[vertex] = Math.min(low[vertex], num[neighbor]);
				}
			}
		}
	}
	
	
}

package utilities.graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TopologicalSort {

	/**
	 * topological sort
	 * @param adjList adjacency list (in the form of 0-1 directed graph)
	 * @return array of one of the possible results of toposort
	 * Return new int[0] if there exists a cycle in the graph.
	 */
	public int[] topologicalSort(ArrayList<ArrayList<Integer>> adjList) {
		int[] result = new int[adjList.size()];
		int[] indegrees = new int[adjList.size()];
		// queue of all node that has an in-degree of 0
		Queue<Integer> zeroInNode = new LinkedList<Integer>();
		// init O(|E|)
		for (ArrayList<Integer> list: adjList) {
			for (Integer node: list)
				indegrees[node]++;
		}
		for (int i = 0; i < indegrees.length; i++)
			if (indegrees[i] == 0)
				zeroInNode.offer(i);
		
		// fill in the result array starting from nodes that have in-degree of 0; O(|V|+|E|)
		int resultInd = 0;
		for (int i = 0; i < adjList.size(); i++) {
			if (zeroInNode.isEmpty())
				return new int[0];
			else {
				Integer node = zeroInNode.poll();
				result[resultInd] = node;
				resultInd++;
				for (Integer neighbor: adjList.get(node))
					indegrees[neighbor]--;
			}
		}
		return result;
	}

	public int[] topologicalSortDfs(ArrayList<List<Integer>> adjList) {
		int[] result = new int[adjList.size()];
		
		return result;
	}
}

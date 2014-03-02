package utilities.trees;

import java.util.ArrayList;

public class DisjointSet {
	
	private class SetElem {
		int parent;
		int rank;
		
		SetElem(int index) {
			parent = index;
			rank = 1;
		}
	}
	
	private ArrayList<SetElem> disjointSet;

	public DisjointSet(int n) {
		disjointSet = new ArrayList<SetElem>();
		for (int i = 0; i < n; i++) {
			disjointSet.add(new SetElem(i));
		}
	}	

	/**
	 * implementation with union by rank and path compression
	 * @param elem elem/index
	 * @return the representative (root) of elem
	 */
	public int find(int elem) {
		int root = elem;
		while (disjointSet.get(root).parent != root) {
			root = disjointSet.get(root).parent;
		}
		while (disjointSet.get(elem).parent != elem) {
			// attach below the representative
			disjointSet.get(elem).parent = root;
			disjointSet.get(elem).rank = 2;
			elem = disjointSet.get(elem).parent;
		}
		return root;
	}
	
	public int findIncRank(int elem) {
		int root = elem;
		while (disjointSet.get(root).parent != root) {
			root = disjointSet.get(root).parent;
		}
		disjointSet.get(root).rank = 2;
		while (disjointSet.get(elem).parent != elem) {
			// attach below the representative
			disjointSet.get(elem).parent = root;
			disjointSet.get(elem).rank = 3;
			elem = disjointSet.get(elem).parent;
		}
		return root;
	}
	
	public void union(int elem1, int elem2) {
		int root = -1;
		int child = -1;
		if (disjointSet.get(elem1).rank >= disjointSet.get(elem2).rank) {
			root = find(elem1);
			child = find(elem2);
		}
		else {
			root = find(elem2);
			child = find(elem1);
		}
		disjointSet.get(child).parent = root;
	}
}

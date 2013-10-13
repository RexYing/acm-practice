package utilities.trees;

import java.util.ArrayList;
import java.util.List;

public class DisjointSet {
	
	private class SetElem {
		int parent;
		int rank;
		
		SetElem(int index) {
			parent = index;
			rank = 1;
		}
	}
	
	private List<SetElem> disjointSet;

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
			elem = disjointSet.get(elem).parent;
		}
		return root;
	}
	
	public boolean isSameSet(int elem1, int elem2) {
		if (find(elem1) == find(elem2)) {
			return true;
		}
		else
			return false;
	}
	
	public void union(int elem1, int elem2) {
		int rep1 = find(elem1);
		int rep2 = find(elem2);
		// union by rank:
		// attach the rep with lower rank to the other with high rank
		if (disjointSet.get(rep1).rank >= disjointSet.get(rep2).rank) {
			disjointSet.get(rep2).parent = rep1;
			disjointSet.get(rep1).rank += 1;
		}
		else {
			disjointSet.get(rep1).parent = rep2;
			disjointSet.get(rep2).rank += 1;
		}
	}
}

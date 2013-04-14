package topcoder.srm576;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * my approach: every platform is a vertex. an edge means Manao can go from one
 * platform to the other, and the weight is the length of the ladder. Construct
 * the adjacent matrix, and calculate shortest distance (Dijkstra) Distance is
 * defined as the maximum weight of the edges in the path.
 * 
 * @author rex
 * 
 */
public class ArcadeManao {
	public int shortestLadder(String[] level, int coinRow, int coinColumn) {
		int numRows = level.length;
		int numCols = level[0].length();
		int coinIndex = -1;
		ArrayList<Platform> platforms = new ArrayList<Platform>();
		for (int i = 0; i < numRows; i++) {
			int j = 0;
			while (j < numCols) {
				if (level[i].charAt(j) == '.') {
					j++;
					continue;
				}
				int k = j;
				while ((k < numCols) && (level[i].charAt(k) == 'X')) {
					if ((coinRow - 1 == i) && (coinColumn - 1 == k))
						coinIndex = platforms.size();
					k++;
				}
				Platform p = new Platform(i, j, k - 1);
				platforms.add(p);
				j = k;
			}
		}
		int[][] adjMatrix = new int[platforms.size()][platforms.size()];
		for (int[] row : adjMatrix)
			Arrays.fill(row, Integer.MAX_VALUE / 2);
		for (int i = 0; i < platforms.size(); i++)
			for (int j = 0; j < platforms.size(); j++) {
				if (i == j)
					continue;
				if ((platforms.get(i).leftEnd > platforms.get(j).rightEnd)
						|| (platforms.get(i).rightEnd < platforms.get(j).leftEnd))
					continue;
				adjMatrix[i][j] = Math.abs(platforms.get(i).height - platforms.get(j).height);
			}
		int[] out = dijkstra2(platforms.size() - 1, adjMatrix);
		return out[coinIndex];
	}

	class Platform {
		int height;
		int leftEnd;
		int rightEnd;

		Platform(int h, int l, int r) {
			height = h;
			leftEnd = l;
			rightEnd = r;
		}
	}

	public static void main(String[] args) {
		String[] level = { "XXXX....", "...X.XXX", "XXX..X..", "......X.", "XXXXXXXX" };
		System.out.println(new ArcadeManao().shortestLadder(level, 2, 4));
	}

	/**
	 * shortest path from r to all points
	 * 
	 * @param r
	 *            the start node (index)
	 * @param in
	 *            the adjacency matrix
	 * @return the length of the path from r to each node
	 */
	public int[] dijkstra2(int r, int[][] in) {
		final int[] out = new int[in.length], prev = new int[in.length];
		TreeSet<Integer> pq = new TreeSet<Integer>(new Comparator<Integer>() {
			public int compare(Integer i0, Integer i1) {
				if (out[i0] != out[i1])
					return Double.compare(out[i0], out[i1]);
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
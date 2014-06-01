package utilities.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import utilities.graphs.CutVertex;
import utilities.graphs.ShortestPath;

public class GraphTest {
	
	public ArrayList<ArrayList<Integer>> buildTestGraph(String filename) {
		Scanner fScanner = null;
		try {
			fScanner = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			System.err.println(filename + " not found.");
			System.exit(1);
		}
		fScanner.nextLine();
		ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();
		int size = fScanner.nextInt();
		fScanner.nextLine();
		for (int i = 0; i < size; i++) {
			ArrayList<Integer> neighbors = new ArrayList<Integer>();
			String[] neighborStr = fScanner.nextLine().split(" ");
			for (String str: neighborStr) {
				neighbors.add(Integer.parseInt(str));
			}
			graph.add(neighbors);
		}
		fScanner.close();
		return graph;
	}
	
	public int[][] buildTestMat(String filename) throws IOException {
		Scanner fScanner = null;
		try {
			fScanner = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			System.err.println(filename + " not found.");
			System.exit(1);
		}
		String type = fScanner.nextLine();
		if (!type.equals("Weighted AdjMat"))
			throw new IOException("expecting Weighted AdjMat");
		int size = fScanner.nextInt();
		int[][] adjMat = new int[size][size];
		fScanner.nextLine();
		for (int i = 0; i < size; i++) {
			String[] neighborStr = fScanner.nextLine().split(" ");
			for (int j = 0; j < size; j++) {
				adjMat[i][j] = Integer.parseInt(neighborStr[j]);
				if (adjMat[i][j] == -1)
					adjMat[i][j] = Integer.MAX_VALUE / 2;
			}
		}
		return adjMat;
	}
	
	@Test
	public void testCutVertex() {
		ArrayList<ArrayList<Integer>> graph = buildTestGraph("src/utilities/test/test_conn_graph1");
		int[] answer = new int[] {0, 1, 3};
		List<Integer> cutVertices = new CutVertex().cutVertexUndirected(graph);
		for (int i = 0; i < cutVertices.size(); i++)
			assertEquals("The " + i + " element: ", answer[i], (int)cutVertices.get(i));
	}

	@Test
	public void testShortestPath() throws IOException {
		ShortestPath sp = new ShortestPath();
		int[][] adjMat = buildTestMat("src/utilities/test/neg_w_graph1");
		int[] dists = sp.dijkstra(0, adjMat);
		System.out.println(Arrays.toString(dists));
	}
}

package utilities.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import utilities.graphs.CutVertex;

public class GraphTest {
	
	public ArrayList<ArrayList<Integer>> buildTestGraph(String filename) {
		Scanner fScanner = null;
		try {
			fScanner = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			System.err.println(filename + " not found.");
			System.exit(1);
		}
		ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();
		fScanner.nextLine();
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
	
	@Test
	public void testCutVertex() {
		ArrayList<ArrayList<Integer>> graph = buildTestGraph("src/utilities/test/test_conn_graph1");
		int[] answer = new int[] {0, 1, 3};
		List<Integer> cutVertices = new CutVertex().cutVertexUndirected(graph);
		for (int i = 0; i < cutVertices.size(); i++)
			assertEquals("The " + i + " element: ", answer[i], (int)cutVertices.get(i));
	}

}

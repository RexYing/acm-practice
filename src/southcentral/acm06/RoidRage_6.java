package southcentral.acm06;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Scanner;

public class RoidRage_6 {
	
	private ArrayList<Polygon> polygons;

	public static void main(String[] args) {
		RoidRage_6 RR6 = new RoidRage_6();
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		for (int i = 0; i < n; i++) {
			int m = scanner.nextInt(); // number of polygons
			for (int j = 0; j < m; j++) {
				int v = scanner.nextInt();
				int[] xPoints = new int[v];
				int[] yPoints = new int[v];
				scanner.nextLine();
				String[] points = scanner.nextLine().trim().split(",");
				for (int k = 0; k < v; k++) {
					points[k].split(" ");
				}
			}
		}
	}
}

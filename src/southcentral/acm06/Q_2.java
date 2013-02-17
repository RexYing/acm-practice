package southcentral.acm06;

import java.util.Scanner;

/**
 * south central regional 2006
 * array manipulation
 * @author Rex
 *
 */
public class Q_2 {

	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		int x = scanner.nextInt();
		for (int i = 0; i < x; i++) {
			int m = scanner.nextInt();
			int n = scanner.nextInt();
			scanner.nextLine();
			String[] items = scanner.nextLine().split(" ");
			boolean[] specified = new boolean[m];
			int[] new_pos = new int[m];
			for (int j = 0; j < m; j++) {
				specified[j] = false;
				new_pos[j] = -1;
			}
			for (int j = 0; j < n; j++) {
				int starting_pos = scanner.nextInt();
				int requested_pos = scanner.nextInt();
				specified[starting_pos - 1] = true;
				new_pos[requested_pos - 1] = starting_pos - 1;
			}
			int pos = 0;
			for (int j = 0; j < m; j++)
				if (!specified[j]) {
					while (new_pos[pos] >= 0)
						pos++;
					new_pos[pos] = j;
					pos++;
				}
			for (int j = 0; j < m; j++)
				System.out.print(items[new_pos[j]] + " ");
			System.out.println();
		}
	}
}

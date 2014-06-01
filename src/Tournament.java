import java.util.Scanner;


/**
 * Sort according to points, find the point value for kth person (say, p)
 * greedy break the people into 2 categories: people who have points p or p-1,
 * and the rest of the people
 * Do greedy picking on two sides~
 * @author yzt
 *
 */
public class Tournament {

	public int solve(int n) {
		return 0;
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int k = scanner.nextInt();
		int[] p = new int[n];
		int[] e = new int[n];
		for (int i = 0; i < n; i++) {
			p[i] = scanner.nextInt();
			e[i] = scanner.nextInt(); 
		}
	}
}

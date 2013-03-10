package acmGNY.year2009;

import java.util.Scanner;

/**
 * DP
 * state transition equation: 
 * f(ball, floor) = min{1 + max{f(ball-1, i-1), f(ball, floor-i)}} (i = 1 ... floor)
 * 
 * @author Rex
 * 
 * @date 02/23/2013
 *
 */
public class C {

	public static void main(String[] args) {
		final int MAX_BALL = 50;
		final int MAX_FLOOR = 1000;
		Scanner scanner = new Scanner(System.in);
		int p = scanner.nextInt();
		int[][] dp = new int[MAX_BALL + 1][MAX_FLOOR + 1];
		for (int i = 0; i < p; i++) {
			int setNumber = scanner.nextInt();
			int b = scanner.nextInt(); // number of balls
			int m = scanner.nextInt(); // number of floors
			// initialize
			for (int j = 1; j <= m; j++) {
				dp[1][j] = j; // one ball
				dp[0][j] = 0; // no ball
			}
			for (int j = 1; j <= b; j++) {
				dp[j][1] = 1; // one floor
				dp[j][0] = 0; // no floor
			}
			//dp
			for (int j = 2; j <= b; j++)
				for (int k = 2; k <= m; k++) {
					int min_drops = Integer.MAX_VALUE;
					for (int l = 1; l < k; l++) {
						int drops = 1 + Math.max(dp[j - 1][l - 1], dp[j][k - l]); // first: ball broken; second: not broken
						if (min_drops > drops)
							min_drops = drops;
					}
					dp[j][k] = min_drops;
				}
			System.out.printf("%d %d\n", setNumber, dp[b][m]);
		}
	}
}

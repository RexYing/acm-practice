package categories.linear_algebra;

import info.Source;

import java.util.Scanner;

@Source ("Topcoder srm 488")
/**
 * 
 * DP, connected to basic Gaussian elimination
 * DP is possible because a person who is already bored cannot become unbored anymore.
 * n of them are bored; m of them are not:
 * dp state: expected number of rounds
 * (C(n, k): n choose k)
 * DP[n, m] = 1 + C(n, 2)/C(m+n, 2) * dp[n, m] + nm /C(m+n, 2) * dp[n-1, m+1]
 *            + C(m, 2)/C(m+n, 2) * dp[n-2, m+2].
 * Solve the equation. (one directional, weakly coupled)
 * @author Rex
 *
 */
public class TheBoredomDivOne {
	
	public double find(int n, int m) {
		int totalPpl = n + m;
		// n choose 2
		int totalComb = (totalPpl) * (totalPpl -1) / 2;
		// expectation for init state (n, m) is 0
		double prev1 = 0;
	    double prev2 = 0;
		double current = 0;
		
		// go backwards: start from 1 people not bored
		for (int i = 1; i <= m; i++) {
			int j = totalPpl - i;
			current = totalComb + i * j * prev1 + i * (i - 1) / 2 * prev2;
			current /= totalComb - j * (j - 1) / 2;
			prev2 = prev1;
			prev1 = current;
			//System.out.println(current);
		}
		return current;
	}
	
	public static void main(String[] args) {
		TheBoredomDivOne boredom = new TheBoredomDivOne();
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int m = scanner.nextInt();
		System.out.println(boredom.find(n, m));
		scanner.close();
	}
}

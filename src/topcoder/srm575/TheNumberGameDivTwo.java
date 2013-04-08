package topcoder.srm575;

/**
 * dp
 * 
 * @author rex
 * 
 */
public class TheNumberGameDivTwo {

	// 1: the guy that operates on this number will win; -1 otherwise; 0 if
	// undecided yet
	int[] dp = new int[1001];

	public TheNumberGameDivTwo() {
		dp[1] = -1;
		for (int i = 2; i < 1000; i++) {
			boolean isPrime = true;
			for (int j = 2; j <= Math.sqrt(i); j++) {
				if (i % j == 0) {
					isPrime = false;
					break;
				}
			}
			if (isPrime) {
				dp[i] = -1;
			}
		}
	}

	public int calculateState(int n) {
		if (dp[n] != 0)
			return dp[n];
		for (int i = 2; i < n; i++) {
			if (n % i == 0) {
				if (calculateState(n - i) == -1) {
					dp[n] = 1;
					break;
				}
			}
		}
		if (dp[n] == 0)
			dp[n] = -1;
		return dp[n];
	}

	public String find(int n) {
		if (calculateState(n) == 1)
			return "John";
		else
			return "Brus";
	}

	public static void main(String[] args) {
		TheNumberGameDivTwo problem = new TheNumberGameDivTwo();
		System.out.println(problem.find(447));
	}
}

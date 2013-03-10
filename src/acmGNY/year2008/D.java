package acmGNY.year2008;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Recursively Palindromic Partitions
 * DP/memoization
 * simple
 * 
 * 
 * @author Rex
 *
 * Spring break!!!:D
 * @date 03/09/2013
 */
public class D {
	
	private static final int MAX_N = 1001;
	private int[] dp = new int[MAX_N];
	
	public D() {
		Arrays.fill(dp,-1);
		dp[1] = 1;
		dp[2] = 2;
	}
	
	public int countRecursivelyPalindromicPartitions(int number) {
		if (number <= 0)
			System.err.println("invalid parameter less or equal to 0.");
		if (dp[number] != -1)
			return dp[number];
		int result;
		if (number % 2 == 0) {
			result = 1 + countRecursivelyPalindromicPartitions(number / 2);
			for (int i = 2; i < number; i += 2)
				result += countRecursivelyPalindromicPartitions((number - i) / 2);
		}
		else {
			result = 0;
			for (int i = 1; i < number; i += 2)
				result += countRecursivelyPalindromicPartitions((number - i) / 2);
			result++;
		}
		dp[number] = result;
		return result;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		D rpp = new D();
		for (int i = 0; i < n; i++) {
			int number = scanner.nextInt();
			int answer = rpp.countRecursivelyPalindromicPartitions(number);
			System.out.printf("%d %d\n", i + 1, answer);
		}
	}
}

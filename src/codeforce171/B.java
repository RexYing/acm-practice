package codeforce171;

import java.util.Scanner;

/**
 * LDOC
 * @author rex
 *
 */
public class B {

	public long solve(long n) {
		if (n == 1)
			return 1;
		long sideLen = n * 3 - 2;
		return sideLen * (sideLen + 1) / 2 + (n - 1) * n / 2 * 3;
	}

	public static void main(String[] args) {
		B problem = new B();
		Scanner scanner = new Scanner(System.in);
		long n = scanner.nextInt();
		System.out.println(problem.solve(n));
	}
}

package codeforce171;

import java.util.Scanner;

/**
 * wrong lol
 * @author rex
 *
 */
public class G {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		long a = scanner.nextLong();
		long b = scanner.nextLong();
		long n = scanner.nextLong();
		if (n % 2 == 0)
			System.out.println((a + b) * n / 2);
		else
			System.out.println((a + b) * (n - 1) / 2 + b);
	}
}

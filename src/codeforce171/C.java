package codeforce171;

import java.util.Scanner;

public class C {

	public static void main(String[] args) {
		long n;
		long ans = 0;
		Scanner scanner = new Scanner(System.in);
		n = scanner.nextLong();
		for (int i = 0; i < n; i++) {
			ans += scanner.nextLong() * (i + 1);
		}
		System.out.println(ans);
	}
}

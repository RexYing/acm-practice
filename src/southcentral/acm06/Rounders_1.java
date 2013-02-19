package southcentral.acm06;

import java.util.Scanner;

public class Rounders_1 {

	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		for (int i = 0; i < n; i++) {
			int x = scanner.nextInt();
			int base = 10;
			while (x > base) {
				if (x % base >= base / 2) {
					x = (x / base) * base + base;
				}
				else {
					x = (x / base) * base;
				}
				base = base * 10;
			}
			System.out.println(x);
		}
		scanner.close();
	}
}

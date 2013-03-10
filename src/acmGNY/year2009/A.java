package acmGNY.year2009;

import java.util.Scanner;

/**
 * Simple
 * @author Rex
 *
 * @date 02/23/2013
 */
public class A {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		for (int i = 0; i < n; i++) {
			int setNumber = scanner.nextInt();
			int[] data = new int[n];
			for (int j = 0; j < 10; j++)
				data[j] = scanner.nextInt();
			int result = 0;
			for (int j = 0; j < 3; j++) {
				result = data[0];
				int kValue = 0;
				for (int k = 1; k < 10 - j; k++)
					if (data[k] > result) {
						result = data[k];
						kValue = k;
					}
				data[kValue] = data[10 - j - 1];
				data[10 - j - 1] = result;
			}
			System.out.printf("%d %d\n", setNumber, result);
		}
	}
}

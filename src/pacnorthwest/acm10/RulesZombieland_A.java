package pacnorthwest.acm10;

import java.util.Scanner;

/**
 * Typical A problem...
 * 
 * @author Rex
 *
 * @date 02/25/2013
 */
public class RulesZombieland_A {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int q = scanner.nextInt();
		String[] rules = new String[q];
		scanner.nextLine();
		for (int i = 0; i < q; i++) {
			rules[i] = scanner.nextLine();
		}
		int r = scanner.nextInt();
		for (int i = 0; i < r; i++) {
			int index = scanner.nextInt();
			if (index >= 1 && index <= q)
				System.out.printf("Rule %d: %s\n", index, rules[index - 1]);
			else
				System.out.printf("Rule %d: No such rule\n", index);
		}
	}
}

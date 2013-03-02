package pacnorthwest.acm10;

import java.util.Scanner;

/**
 * Enumeration
 * 
 * @author Rex
 * 
 * @date 03/02/2013
 */
public class MesmerizedCount_C {

	public static void main(String[] args) {
		final double EPSILON = 0.00000001;
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		while (n != 0) {
			boolean found = false;
			long a = 0;
			long c = 0;
			enumerate: {
				for (a = 1; a < 4000; a++) {
					// a is greater than c
					// a, c cannot be 0 (natural numbers)
					long lastNumber = Math.min(a, 4000 - a);
					for (c = 1; c <= lastNumber; c++) {
						double b = Math.cbrt((Math.pow(a, 3) + Math.pow(c, 3))
								/ n);
						if (a + 2 * b + c >= 4000)
							break;
						if ((Math.abs(b - Math.round(b)) < EPSILON)
								|| (Math.abs(b - Math.round(b)) > 1 - EPSILON)) {
							System.out.printf("(%d/%d)^3 + (%d/%d)^3 = %d\n", a,
									Math.round(b), c, Math.round(b), n);
							found = true;
							break enumerate;
						}
					}
				}
			}
			if (!found)
				System.out.println("No value.");
			n = scanner.nextInt();
		}
	}
}

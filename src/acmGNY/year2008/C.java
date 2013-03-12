package acmGNY.year2008;

import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Binary Clock
 * Java API: String.format; Integer.toBinaryString.
 * 
 * @author Rex
 * 
 *         daylight saving~
 * @date 03/10/2012
 */
public class C {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int numberOfDataSets = scanner.nextInt();
		scanner.nextLine();
		for (int i = 0; i < numberOfDataSets; i++) {
			StringTokenizer timeTokenizer = new StringTokenizer(scanner.nextLine(), ":");
			if (timeTokenizer.countTokens() != 3)
				System.err.println("Invalid time format!");
			char[][] clockValue = new char[3][6];
			for (int j = 0; j < 3; j++) {
				int temp = Integer.parseInt(timeTokenizer.nextToken());
				// Convert to binary string, and left-pad with '0'
				String binaryValue = String.format("%6s", Integer.toBinaryString(temp)).replace(' ', '0');
				clockValue[j] = binaryValue.toCharArray();
			}
			System.out.print((i + 1) + " ");
			for (int j = 0; j < 6; j++)
				for (int k = 0; k < 3; k++)
					System.out.print(clockValue[k][j]);
			System.out.print(" ");
			for (int j = 0; j < 3; j++)
				for (int k = 0; k < 6; k++)
					System.out.print(clockValue[j][k]);
			System.out.println();
		}
		scanner.close();
	}
}

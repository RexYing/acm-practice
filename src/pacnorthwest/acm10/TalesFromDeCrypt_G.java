package pacnorthwest.acm10;

import java.util.Scanner;

/**
 * simple simulation
 * 
 * @author Rex
 *
 * @date 03/02/2013
 */
public class TalesFromDeCrypt_G {

	private long seed;
	private long tentativeSeed;
	private int a;
	private int m;

	private void solve() {
		Scanner scanner = new Scanner(System.in);
		a = scanner.nextInt();
		m = scanner.nextInt();
		seed = scanner.nextInt();
		scanner.nextLine();
		String line = scanner.nextLine();
		while (line != null) {
			char[] encryptedText = line.toCharArray();
			for (int i = 0; i < encryptedText.length; i++) {
				boolean isPrinted = false;
				// try all the characters
				for (char j = 0x20; j <= 0x7e; j++) {
					if (encryptedText[i] == encrypt(j)) {
						seed = tentativeSeed;
						System.out.print((char) j);
						isPrinted = true;
						break;
					}
				}
				if (!isPrinted)
					System.err.printf("char %c not printed!", encryptedText[i]);
			}
			System.out.println();
			line = scanner.nextLine();
		}
	}

	private char encrypt(char j) {
		if (j < 0x20 || j > 0x7e) // not in the range
			return j;
		char c = (char) (Math.round(((j - 32) + Math.ceil(95 - r() * 95))) % 95 + 32);
		return c;
	}

	private double r() { // seed modified here
		double value = (seed % m) / (double) m;
		tentativeSeed = (a * seed + 1) % m;
		return value;
	}

	public static void main(String args[]) {
		TalesFromDeCrypt_G decrypt = new TalesFromDeCrypt_G();
		decrypt.solve();
	}
}

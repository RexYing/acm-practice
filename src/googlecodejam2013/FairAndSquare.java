package googlecodejam2013;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class FairAndSquare {

	private ArrayList<BigInteger> fairPalindromes;
	private ArrayList<BigInteger> basePalindromes = new ArrayList<BigInteger>();

	public FairAndSquare() {
		fairPalindromes = new ArrayList<BigInteger>();
		for (int i = 1; i < 99999; i++) {
			String base = i + "";
			// odd digits
			String palindromeString = base
					+ new StringBuffer(base.substring(0, base.length() - 1)).reverse().toString();
			BigInteger palindrome = new BigInteger(palindromeString);
			BigInteger square = palindrome.multiply(palindrome);
			if (isPalindrome(square)) {
				basePalindromes.add(palindrome);
				fairPalindromes.add(square);
			}
			// even digits
			palindromeString = base + new StringBuffer(base).reverse().toString();
			palindrome = new BigInteger(palindromeString);
			square = palindrome.multiply(palindrome);
			if (isPalindrome(square)) {
				basePalindromes.add(palindrome);
				fairPalindromes.add(square);
			}
		}
		Collections.sort(fairPalindromes);
		 Collections.sort(basePalindromes);
		 for (BigInteger integer: basePalindromes)
		 System.out.println(integer);
		 System.out.println("done");
	}

	private boolean isPalindrome(BigInteger number) {
		String string = number.toString();
		for (int i = 0; i < string.length() / 2; i++)
			if (string.charAt(i) != string.charAt(string.length() - 1 - i))
				return false;
		return true;
	}

	public int findNumberOfPalindromes(BigInteger lower, BigInteger upper) {
		int lowerIndex = Collections.binarySearch(fairPalindromes, lower);
		if (lowerIndex < 0)
			lowerIndex = -(lowerIndex + 1);
		int upperIndex = Collections.binarySearch(fairPalindromes, upper);
		if (upperIndex < 0)
			upperIndex = -(upperIndex + 1) - 1;
		return upperIndex - lowerIndex + 1;
	}

	public static void main(String[] args) throws FileNotFoundException {
		FairAndSquare problem = new FairAndSquare();
		Scanner scanner = new Scanner(System.in);
		int numSets = scanner.nextInt();
		System.setOut(new PrintStream(new FileOutputStream("out.txt", true)));
		for (int i = 0; i < numSets; i++) {
			BigInteger lower = scanner.nextBigInteger();
			BigInteger upper = scanner.nextBigInteger();
			System.out.println("Case #" + (i + 1) + ": " +
					problem.findNumberOfPalindromes(lower, upper));
		}
	}
}

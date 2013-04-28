package googlecodejam2013.qualification;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class FairAndSquareLarge {

	private ArrayList<BigInteger> fairPalindromes = new ArrayList<BigInteger>();
	private ArrayList<BigInteger> basePalindromes = new ArrayList<BigInteger>();
	private HashMap<BigInteger, Boolean> checkRepeat = new HashMap<BigInteger, Boolean>();

	public FairAndSquareLarge() {
		basePalindromes.add(new BigInteger("1"));
		basePalindromes.add(new BigInteger("2"));
		basePalindromes.add(new BigInteger("3"));
		basePalindromes.add(new BigInteger("11"));
		basePalindromes.add(new BigInteger("22"));
		for (BigInteger integer : basePalindromes) {
			fairPalindromes.add(integer.multiply(integer));
			checkRepeat.put(integer, true);
		}
		int oddBegin = 0;
		int oddEnd = 3; // last index of odd digits pal + 1
		int evenBegin = 3;
		int evenEnd = 5;
		int maxDigits = 2;
		while (maxDigits <= 50) {
			// odd expansion
			for (int i = oddBegin; i < oddEnd; i++) {
				String base = basePalindromes.get(i).toString();
				for (int j = 0; j < maxDigits / 2; j++) {
					for (int k = 0; k <= 3; k++) {
						String newBase = base.substring(0, j) + (k + "") + base.substring(j, base.length() - j)
								+ (k + "") + base.substring(base.length() - j);
						int sumOfDigits = calcSumOfDigits(newBase);
						// if (sumOfDigits >= 10)
						// continue;
						BigInteger palindrome = new BigInteger(newBase);
						if (checkRepeat.containsKey(palindrome))
							continue;
						BigInteger square = palindrome.multiply(palindrome);
						if (isPalindrome(square)) {
							basePalindromes.add(palindrome);
							fairPalindromes.add(square);
							checkRepeat.put(palindrome, true);
						}
					}
				}
			}
			// even expansion
			// insert one digit into middle to form odd digits base
			// palindromes
			for (int i = evenBegin; i < evenEnd; i++) {
				String base = basePalindromes.get(i).toString();
				for (int k = 0; k <= 3; k++) {
					String newBase = base.substring(0, base.length() / 2) + (k + "")
							+ base.substring(base.length() / 2);
					int sumOfDigits = calcSumOfDigits(newBase);
					// if (sumOfDigits >= 10)
					// continue;
					BigInteger palindrome = new BigInteger(newBase);
					if (checkRepeat.containsKey(palindrome))
						continue;
					BigInteger square = palindrome.multiply(palindrome);
					if (isPalindrome(square)) {
						basePalindromes.add(palindrome);
						fairPalindromes.add(square);
						checkRepeat.put(palindrome, true);
					}
				}
			}
			oddBegin = evenEnd;
			oddEnd = basePalindromes.size();
			// even digits palindromes
			for (int i = evenBegin; i < evenEnd; i++) {
				String base = basePalindromes.get(i).toString();
				for (int j = 0; j <= maxDigits / 2; j++) {
					for (int k = 0; k <= 3; k++) {
						String newBase = base.substring(0, j) + (k + "") + base.substring(j, base.length() - j)
								+ (k + "") + base.substring(base.length() - j);
						int sumOfDigits = calcSumOfDigits(newBase);
						if (sumOfDigits >= 10)
							continue;
						BigInteger palindrome = new BigInteger(newBase);
						if (checkRepeat.containsKey(palindrome))
							continue;
						BigInteger square = palindrome.multiply(palindrome);
						if (isPalindrome(square)) {
							basePalindromes.add(palindrome);
							fairPalindromes.add(square);
							checkRepeat.put(palindrome, true);
						}
					}
				}
			}
			evenBegin = oddEnd;
			evenEnd = basePalindromes.size();
			maxDigits += 2;
			System.out.println(basePalindromes.size());
		}

		Collections.sort(basePalindromes);
		Collections.sort(fairPalindromes);

		/*
		 * for (BigInteger integer : basePalindromes)
		 * System.out.println(integer); System.out.println("done");
		 */
	}

	private int calcSumOfDigits(String numberString) {
		int sum = 0;
		for (int i = 0; i < numberString.length(); i++)
			sum += (numberString.charAt(i) - '0');
		return sum;
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
		FairAndSquareLarge problem = new FairAndSquareLarge();
		Scanner scanner = new Scanner(System.in);
		int numSets = scanner.nextInt();
		System.setOut(new PrintStream(new FileOutputStream("out.txt", true)));
		for (int i = 0; i < numSets; i++) {
			BigInteger lower = scanner.nextBigInteger();
			BigInteger upper = scanner.nextBigInteger();
			System.out.println("Case #" + (i + 1) + ": " + problem.findNumberOfPalindromes(lower, upper));
		}
	}
}

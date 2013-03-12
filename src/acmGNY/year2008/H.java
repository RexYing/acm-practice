package acmGNY.year2008;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Two Note Rag Idea in the paper "What Should We Compute", F. J.
 * Gruenberger, 1963 Note the BigInteger (immutable like String), BigDecimal
 * APIs
 * 
 * @author yzt
 * 
 * @date 03/11/2013
 */
public class H {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		TwoNoteRag twoNoteRag = new TwoNoteRag(20);
		int n = scanner.nextInt();
		for (int i = 0; i < n; i++) {
			int inputData = scanner.nextInt();
			twoNoteRag.calculate(inputData);
			System.out.printf("%d %d %s\n", i + 1, inputData, twoNoteRag.getValue(inputData));
		}
		scanner.close();
	}
}

class TwoNoteRag {
	private static final BigInteger INIT_POWER = new BigInteger("100");
	private static final BigInteger BASE = new BigInteger("2");
	/*
	 * according to the paper, it is guaranteed to have another int that has the
	 * property for current length + 1 by multiplying the current multiplier for
	 * 5 times. It could be 0 time (eg. the third and forth exponentials are
	 * both 89).
	 */
	private static final int MAX_LOOP = 5;
	private BigInteger myInitMultipler;
	// the last myMaxRequest digits that only contain 1s and 2s
	private int myMaxRequest;
	private ArrayList<BigInteger> myPowers;
	// length of each element in myExponentials is at most myMaxRequest digits.
	private ArrayList<BigInteger> myExponentials;
	private BigInteger myCurrentIncrementPower;
	private BigInteger myCurrentMultiplier;

	public TwoNoteRag(int maxRequest) {
		myMaxRequest = maxRequest;
		// these ArrayLists does not use 0 index
		myPowers = new ArrayList<BigInteger>();
		myExponentials = new ArrayList<BigInteger>();
		preprocess();
	}

	private void preprocess() {
		// small values
		myPowers.add(new BigInteger("1"));
		myPowers.add(new BigInteger("9"));
		myPowers.add(new BigInteger("89"));
		for (BigInteger power : myPowers)
			myExponentials.add(restrictLength(BASE.pow(power.intValue()), myMaxRequest));
		myCurrentIncrementPower = INIT_POWER;
		initMultiplier();
		myCurrentMultiplier = myInitMultipler;
	}

	// only retain the last "length" number of digits
	private BigInteger restrictLength(BigInteger bigInteger, int length) {
		String intString = bigInteger.toString();
		if (intString.length() <= length)
			return bigInteger;
		return new BigInteger(intString.substring(intString.length() - myMaxRequest));
	}

	private void initMultiplier() {
		BigInteger multiplier = BASE.pow(100);
		myInitMultipler = restrictLength(multiplier, myMaxRequest);
	}

	public void calculate(int length) {
		if (length < myPowers.size())
			return;
		else {
			// extend ArrayList to this requested length
			int currentLength = myPowers.size();
			BigInteger currentPower = myPowers.get(currentLength - 1);
			BigInteger result = myExponentials.get(currentLength - 1);
			while (currentLength < length) {
				// according to the theorem it will succeed within 5 rounds
				for (int i = 0; i <= MAX_LOOP; i++) {
					if (checkSuccess(result, currentLength + 1))
						break;
					result = restrictLength(result.multiply(myCurrentMultiplier), myMaxRequest);
					currentPower = currentPower.add(myCurrentIncrementPower);
				}
				myCurrentIncrementPower = myCurrentIncrementPower.multiply(new BigInteger(MAX_LOOP + ""));
				myCurrentMultiplier = restrictLength(myCurrentMultiplier.pow(5), myMaxRequest);
				currentLength++;
				myPowers.add(currentPower);
				myExponentials.add(result);
			}
		}
	}

	private boolean checkSuccess(BigInteger result, int length) {
		String intString = result.toString();
		for (int i = 0; i < length; i++) {
			char digitToCheck = intString.charAt(intString.length() - i - 1);
			if (!(digitToCheck == '1' || digitToCheck == '2'))
				return false;
		}
		return true;
	}

	public String getValue(int length) {
		if (length <= 0) {
			System.err.println("The length has to be positive!");
			return null;
		}
		return myPowers.get(length - 1).toString();
	}
}
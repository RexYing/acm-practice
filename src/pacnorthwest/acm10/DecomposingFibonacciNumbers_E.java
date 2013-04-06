package pacnorthwest.acm10;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Simple! Use %.6f for 6 decimal places. Long.parseLong(stringWithout0x, 16)
 * for reading hex finding prime factors is actually not that inefficient:D
 * tried Pair...
 * 
 * @author Rex
 * 
 * @date 03/02/2013
 */
public class DecomposingFibonacciNumbers_E {

	ArrayList<Long> myFibonacciNumbers;
	ArrayList<NumberProperties> myFibonacciNumberProperties;

	private void solve() {
		preprocess();
		Scanner scanner = new Scanner(System.in);
		String line = scanner.nextLine();
		while (line != null) {
			String[] splitLine = line.split(" ");
			long low = Long.parseLong(splitLine[0].substring(2), 16);
			long high = Long.parseLong(splitLine[1].substring(2), 16);
			System.out.println("Range " + low + " to " + high + ":");
			Integer lowerBoundIndex = findIndexRange(low, myFibonacciNumbers).second;
			Integer upperBoundIndex = findIndexRange(high, myFibonacciNumbers).first;
			if ((lowerBoundIndex == null) || (upperBoundIndex == null) || (lowerBoundIndex > upperBoundIndex)) {
				// print with a blank line
				System.out.println("No Fibonacci numbers in the range\n");
			} else {
				for (int i = lowerBoundIndex; i <= upperBoundIndex; i++) {
					System.out.printf("Fib(%d) = %d, ", i, myFibonacciNumbers.get(i));
					Double logValue = myFibonacciNumberProperties.get(i).logValue;
					if (logValue == null)
						System.out.println("lg does not exist");
					else
						System.out.printf("lg is %.6f\n", logValue);
					// prime factors
					String printedFactors = myFibonacciNumberProperties.get(i).printFactorization();
					if (printedFactors.equals(""))
						System.out.println("No prime factors");
					else
						System.out.printf("Prime factors:%s\n", printedFactors);
				}
				System.out.println();
			}
			try {
				line = scanner.nextLine();
			} catch (NoSuchElementException e) {
				break;
			}
		}
	}

	/**
	 * find the number between pair.first and pair.second: return (null, 0) if
	 * less than first element; return (numbers.size() - 1, null) if greater
	 * than last element.
	 * 
	 * @param request
	 *            long to be looked up
	 * @param numbers
	 *            list of numbers
	 * @return pair of lower and upper index range
	 */
	private Pair<Integer, Integer> findIndexRange(long request, List<Long> numbers) {
		if (request < 0)
			return new Pair<Integer, Integer>(-1, -1);
		int begin = 0;
		int end = numbers.size() - 1;
		if (numbers.get(begin) > request)
			return new Pair<Integer, Integer>(null, begin);
		if (numbers.get(end) < request)
			return new Pair<Integer, Integer>(end, null);
		while (begin < end - 1) {
			int mid = (begin + end) / 2;
			if (numbers.get(mid) == request)
				return new Pair<Integer, Integer>(mid, mid);
			else if (numbers.get(mid) > request)
				end = mid;
			else
				begin = mid;
		}
		if (numbers.get(begin) == request)
			return new Pair<Integer, Integer>(begin, begin);
		else if (numbers.get(end) == request)
			return new Pair<Integer, Integer>(end, end);
		return new Pair<Integer, Integer>(begin, end);
	}

	private void preprocess() { // myFibonacciNumbers is sorted
		myFibonacciNumbers = new ArrayList<Long>();
		myFibonacciNumbers.add((long) 0);
		myFibonacciNumbers.add((long) 1);
		long num1 = 0;
		long num2 = 1;
		while (num2 <= Long.MAX_VALUE / 2) {
			long result = num1 + num2;
			myFibonacciNumbers.add(result);
			num1 = num2;
			num2 = result;
		}
		Long result = checkedLongAdd(num1, num2);
		if (result != null)
			myFibonacciNumbers.add(result);
		myFibonacciNumberProperties = new ArrayList<NumberProperties>();
		for (Long num : myFibonacciNumbers) {
			myFibonacciNumberProperties.add(new NumberProperties(num));
		}
	}

	/**
	 * check overflow when adding long number
	 * 
	 * @param num1
	 *            number1
	 * @param num2
	 *            number2
	 * @return null if overflow
	 */
	public Long checkedLongAdd(long num1, long num2) {
		long result = num1 + num2;
		if (((num1 ^ result) & (num2 ^ result)) < 0) // overflow
			return null;
		return result;
	}

	public static void main(String[] args) {
		DecomposingFibonacciNumbers_E dfn = new DecomposingFibonacciNumbers_E();
		dfn.solve();
	}
}

class NumberProperties {

	long value;
	Double logValue;
	ArrayList<Long> primeFactors;

	NumberProperties(long value) {
		this.value = value;
		if (value == 0)
			logValue = null;
		else
			logValue = Math.log(value) / Math.log(2); // not calculated
		primeFactors = new ArrayList<Long>();
		factorize();
	}

	private void factorize() {
		long number = 2;
		long tempValue = this.value;
		while (number * number <= tempValue) {
			while (tempValue % number == 0) {
				primeFactors.add(number);
				tempValue = tempValue / number;
			}
			number++;
		}
		// now tempValue could be 0 when value is 0; 1 when the previous
		// tempValue is equal to number
		if (tempValue > 1)
			primeFactors.add(tempValue);
	}

	/**
	 * print in the form of " n n n n ..."
	 * 
	 * @return string printed
	 */
	public String printFactorization() {
		StringBuffer stringBuffer = new StringBuffer();
		for (Long factor : primeFactors)
			stringBuffer.append(" " + factor);
		return stringBuffer.toString();
	}
}

class Pair<U, V> {

	U first;
	V second;

	/**
	 * Constructs a new Pair with the given values.
	 * 
	 * @param first
	 *            the first element
	 * @param second
	 *            the second element
	 */
	public Pair(U first, V second) {
		this.first = first;
		this.second = second;
	}
}

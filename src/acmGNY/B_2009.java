package acmGNY;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 2 possible ways I could think of:
 * -> enumerate over possible length for the first partition
 * -> enumerate over possible divisors (the approach I used)
 * 
 * @author Rex
 *
 * @date 02/23/2013
 */
public class B_2009 {
	private int[] myData; // number of int in the sequence
	private int mySum;
	private ArrayList<Integer> myDivisors;

	public B_2009(int[] data, int sum) {
		myData = data;
		mySum = sum;
	}

	public int solve() {
		myDivisors = findDivisors();
		int result = 0;
		for (Integer divisor: myDivisors) {
			boolean success = true;
			result = divisor;
			if (divisor == mySum)
				return divisor; // the sum is always possible (no partition)
			int sum = 0;
			for (int i = 0; i < myData.length; i++) {
				sum += myData[i];
				if (sum > divisor) { // fail
					success = false;
					break;
				}
				else if (sum == divisor)
					sum = 0;
			}
			if (success)
				break;
		}
		return result;
	}

	private ArrayList<Integer> findDivisors() {
		ArrayList<Integer> divisors = new ArrayList<Integer>();
		for (int i = 1; i <= Math.sqrt(mySum); i++) {
			if (mySum % i == 0)
				divisors.add(i);
		}
		int j = divisors.size() - 1;
		for (int i = j; i >= 0; i--)
			divisors.add(mySum / divisors.get(i));
		return divisors;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int p = scanner.nextInt();
		for (int i = 0; i < p; i++) {
			int setNumber = scanner.nextInt();
			int m = scanner.nextInt();
			int sum = 0;
			int[] data = new int[m];
			for (int j = 0; j < m; j++) {
				data[j] = scanner.nextInt();
				sum += data[j];
			}
			B_2009 b2009 = new B_2009(data, sum);
			int result = b2009.solve();
			System.out.printf("%d %d\n", setNumber, result);
		}
	}
}

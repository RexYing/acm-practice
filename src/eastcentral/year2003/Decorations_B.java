package eastcentral.year2003;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * DP Preprocessing: find all possible pairs of patterns that could be put
 * together
 * 
 * @author Rex
 * 
 * @date 03/23/2013
 */
public class Decorations_B {

	private int myLength;
	private String[] myCombinations;
	// link.get(i) stores the index of String in myCombinations that could
	// follow the String myCombination[i]
	private List<List<Integer>> myLinks;

	public Decorations_B(int length, String[] combinations) {
		myLength = length;
		myCombinations = combinations;
		myLinks = new ArrayList<List<Integer>>();
		for (int i = 0; i < myCombinations.length; i++)
			myLinks.add(new ArrayList<Integer>());
	}

	public int solve() {
		int wordLength = myCombinations[0].length();
		// if myLength is even less than wordLength, return 0 (impossible)
		if (myLength < wordLength)
			return 0;
		preprocess();
		// rolling array
		int currentBeadLength = wordLength; // length for dp1
		int[] dp1 = new int[myCombinations.length];
		int[] dp2 = new int[myCombinations.length];
		// initialize
		Arrays.fill(dp1, 1);
		while (currentBeadLength < myLength) {
			for (int i = 0; i < myCombinations.length; i++)
				for (Integer index: myLinks.get(i)) {
					dp2[index] += dp1[i];
				}
			currentBeadLength++;
			dp1 = dp2;
			dp2 = new int[myCombinations.length];
			Arrays.fill(dp2, 0);
		}
		int sum = 0;
		for (int i = 0; i < dp1.length; i++)
			sum += dp1[i];
		return sum;
	}

	private void preprocess() {
		for (int i = 0; i < myCombinations.length; i++) {
			for (int j = 0; j < myCombinations.length; j++) {
				String string = myCombinations[j].substring(0, myCombinations[j].length() - 1);
				//check if the two string could be put together
				if (myCombinations[i].substring(1).equals(string))
					myLinks.get(i).add(j);
			}
		}
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			int n = scanner.nextInt();
			int length = scanner.nextInt();
			int numCombinations = scanner.nextInt();
			if (n == 0)
				break;
			String[] combinations = new String[numCombinations];
			scanner.nextLine();
			combinations = scanner.nextLine().trim().split(" ");
			Decorations_B problem = new Decorations_B(length, combinations);
			System.out.println(problem.solve());
		}
		scanner.close();
	}
}

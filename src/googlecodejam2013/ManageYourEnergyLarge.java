package googlecodejam2013;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ManageYourEnergyLarge {

	int totalE;
	int regain;
	long[] activities;

	public ManageYourEnergyLarge(int e, int r, long[] acts) {
		totalE = e;
		regain = r;
		activities = acts;
		if (totalE < regain)
			regain = totalE;
	}

	public static void main(String[] args) throws FileNotFoundException {
		System.setOut(new PrintStream(new FileOutputStream("ManageYourEnergy.out", true)));
		Scanner scanner = new Scanner(System.in);
		int t = scanner.nextInt();
		for (int i = 0; i < t; i++) {
			int e = scanner.nextInt();
			int r = scanner.nextInt();
			int n = scanner.nextInt();
			long[] acts = new long[n];
			for (int j = 0; j < n; j++) {
				acts[j] = scanner.nextLong();
			}
			System.out.println("Case #" + (i + 1) + ": " + new ManageYourEnergyLarge(e, r, acts).solve());
		}
	}

	private String solve() {
		Long maxValue = findMax(0, totalE, activities.length, 0);
		return maxValue.toString();
	}

	/**
	 * divide and conquer.
	 * 
	 * @param startAct
	 *            the index of the starting activity
	 * @param startE
	 *            Energy at startAct
	 * @param endAct
	 *            the index+1 of the last asctivity
	 * @param endE
	 *            Energy at endAct
	 * @return Max value obtained from start activity to end activity
	 */
	private Long findMax(int startAct, int startE, int endAct, int endE) {
		if (startAct == endAct)
			return new Long(0);
		if (startAct + 1 == endAct)
			return activities[startAct] * (startE - endE);
		int maxValueIndex = startAct;
		for (int i = startAct + 1; i < endAct; i++) {
			if (activities[i] > activities[maxValueIndex])
				maxValueIndex = i;
		}
		// max energy possible at the maxValueIndex activity
		// maxEnergy >= regain because starE >= regain
		int maxEnergy = Math.min(totalE, startE + regain * (maxValueIndex - startAct));
		// min energy possible at the maxValueIndex activity
		// minEnergy <= totalE - regain because endE <= totalE - regain
		int minEnergy = Math.max(0, endE - regain * (endAct - maxValueIndex - 1));
		return findMax(startAct, startE, maxValueIndex, maxEnergy - regain) + activities[maxValueIndex]
				* (maxEnergy - minEnergy) + findMax(maxValueIndex + 1, minEnergy + regain, endAct, endE);
	}
}

package midatlantic.year2005;

import java.util.Arrays;
import java.util.Scanner;

/**
 * DP i: number of words; j: last word; k: what words have been used Use a
 * binary mask for indicating which words have been used. Rolling Array (only
 * current i and previous i are stored)
 * 
 * @author Rex
 * 
 * @date 04/08/2013
 */
public class WordStack_H {

	private static final int INVALID_SCORE = -1;
	private String[] myWords;
	private int[][] dpState;
	private int[][] prevDpState;
	// scores[i][j] stores the score obtained by putting ith word before jth
	// word
	private int[][] scores;

	public WordStack_H(String[] words) {
		myWords = words;
		dpState = createDpState();
		initDpState();
		scores = new int[myWords.length][myWords.length];
		for (int i = 0; i < myWords.length; i++)
			for (int j = 0; j < myWords.length; j++)
				scores[i][j] = INVALID_SCORE;
	}

	private int[][] createDpState() {
		int[][] state = new int[myWords.length][(int) (Math.pow(2, myWords.length))];
		for (int[] row : state)
			Arrays.fill(row, INVALID_SCORE);
		return state;
	}

	private void initDpState() {
		prevDpState = createDpState();
		int power = 1;
		for (int i = 0; i < myWords.length; i++) {
			prevDpState[i][power] = 0;
			power *= 2;
		}
	}

	private int solve() {
		int maxScore = 0;
		for (int i = 2; i <= myWords.length; i++) {
			int power = 1;
			for (int lastWordIndex = 0; lastWordIndex < myWords.length; lastWordIndex++) {
				for (int j = 1; j < prevDpState[0].length; j++) { // mask
					if ((j & power) != 0) {
						int previousMask = ~power & j; // make kth bit 0
						int score = bestArrangement(previousMask, lastWordIndex);
						dpState[lastWordIndex][j] = score;
					}
				}
				power *= 2;
			}
			prevDpState = dpState;
			dpState = createDpState();
		}
		int finalMask = (int) Math.pow(2, myWords.length) - 1;
		for (int i = 0; i < myWords.length; i++)
			if (prevDpState[i][finalMask] > maxScore)
				maxScore = prevDpState[i][finalMask];
		return maxScore;
	}

	private int bestArrangement(int mask, int currentLastWordIndex) {
		int maxScore = INVALID_SCORE;
		// myWord[currentLastWordIndex] has already been used
		if (((int) Math.pow(2, currentLastWordIndex) & mask) != 0)
			return INVALID_SCORE;
		for (int i = 0; i < myWords.length; i++) {
			if (i == currentLastWordIndex) // same number can't be used for
											// twice
				continue;
			if (prevDpState[i][mask] == INVALID_SCORE)
				continue;
			int score = prevDpState[i][mask] + calculateScore(i, currentLastWordIndex);
			if (maxScore < score)
				maxScore = score;
		}
		return maxScore;
	}

	private int calculateScore(int prevLastWordIndex, int currentLastWordIndex) {
		if (scores[prevLastWordIndex][currentLastWordIndex] != INVALID_SCORE)
			return scores[prevLastWordIndex][currentLastWordIndex];
		String prevWord = myWords[prevLastWordIndex];
		String currentWord = myWords[currentLastWordIndex];
		int score = 0;
		for (int offset = -prevWord.length() + 1; offset < currentWord.length(); offset++) {
			int count = 0;
			for (int i = 0; i < prevWord.length(); i++) {
				if (i + offset >= currentWord.length())
					break;
				if (i + offset < 0)
					continue;
				if (prevWord.charAt(i) == currentWord.charAt(i + offset))
					count++;
			}
			if (count > score)
				score = count;
		}
		scores[prevLastWordIndex][currentLastWordIndex] = score;
		scores[currentLastWordIndex][prevLastWordIndex] = score;
		return score;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			int N = scanner.nextInt();
			if (N <= 0)
				break;
			scanner.nextLine();
			String[] words = new String[N];
			for (int i = 0; i < N; i++) {
				words[i] = scanner.nextLine();
			}
			WordStack_H problem = new WordStack_H(words);
			System.out.println(problem.solve());
		}
	}

}

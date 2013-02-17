package southcentral.acm06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

/**
 * south central regional 2006
 * depth first search with pre-processing
 * @author Rex
 *
 */
public class ECimproved {
	final int MAX_LENGTH = 15;
	
	private char[][] puzzle;
	private Scanner scanner = new Scanner(System.in);
	private ArrayList<FreeSpace> wordSpaces;
	private ArrayList<FreeSpace> wordSpaceCrossed;
	private String[] words;
	private int weight;
	private int height;
	private int count;
	private ArrayList<ArrayList<String>> wordCategories;
	private HashMap<String, Boolean> wordUsed;

	/**
	 * solve the puzzle
	 */
	public void solve() {
		int n = scanner.nextInt();
		for (int i = 0; i < n; i++) {
			weight = scanner.nextInt();
			height = scanner.nextInt();
			puzzle = new char[height][weight];
			wordSpaces = new ArrayList<FreeSpace>(MAX_LENGTH + 1);
			wordCategories = new ArrayList<ArrayList<String>>(MAX_LENGTH + 1);
			wordUsed = new HashMap<String, Boolean>();
			wordSpaceCrossed = new ArrayList<FreeSpace>();
			for (int j = 0; j <= MAX_LENGTH; j++) {
				wordCategories.add(new ArrayList<String>());
			}
			scanner.nextLine();
			// input
			for (int j = 0; j < height; j++) {
				String line = scanner.nextLine();
				puzzle[j] = line.toCharArray();
			}
			int totalNumberWordSpaces = preprocess();
			Collections.sort(wordSpaces, new Comparator<FreeSpace>() {
				// descending order with respect to length
				@Override
				public int compare(FreeSpace arg0, FreeSpace arg1) {
					return arg1.length - arg0.length;
				}
			});
			crossWordSpace();
			count = scanner.nextInt();
			scanner.nextLine();
			words = new String[count];
			for (int j = 0; j < count; j++) {
				String word = scanner.nextLine();
				words[j] = word;
				wordCategories.get(word.length()).add(word);
				wordUsed.put(word, false);
			}
			System.out.println("Puzzle #" + (i+1));
			if ((totalNumberWordSpaces == count) && dfs_improved(0)) {
				printPuzzle();
			}
			else
				System.out.println("I cannot generate this puzzle.");
		}
	}
	
	private void crossWordSpace() {
		int horizontalIndex = 0;
		int verticalIndex = 0;
		int count = 0;
		while (count < wordSpaces.size()) {
			while (horizontalIndex < wordSpaces.size()) {
				if (wordSpaces.get(horizontalIndex).isHorizontal)
					break;
				else
					horizontalIndex++;
			}
			if (horizontalIndex < wordSpaces.size()) {
			    wordSpaceCrossed.add(wordSpaces.get(horizontalIndex));
			    horizontalIndex++;
			    count++;
			}
			while (verticalIndex < wordSpaces.size()) {
				if (!wordSpaces.get(verticalIndex).isHorizontal)
					break;
				else
					verticalIndex++;
			}
			if (verticalIndex < wordSpaces.size()) {
			    wordSpaceCrossed.add(wordSpaces.get(verticalIndex));
			    verticalIndex++;
			    count++;
			}
		}
	}

	private void printPuzzle() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < weight; j++)
				System.out.print(puzzle[i][j]);
			System.out.println();
		}
	}
	
	private boolean dfs_improved(int index) {
		//System.out.println(index);
		if (index >= wordSpaceCrossed.size())
			return true;
		int[] changes = new int[MAX_LENGTH];
		int change_amount;
		FreeSpace ws = wordSpaceCrossed.get(index);
		for (String s: wordCategories.get(ws.length)) {
			if (wordUsed.get(s))
				continue;
			char[] word = s.toCharArray();
			if (ws.isHorizontal) {
				boolean fit = true;
				for (int i = 0; i < ws.length; i++) {
					if ((puzzle[ws.x][ws.y + i] != '#') && (word[i] != puzzle[ws.x][ws.y + i])) {
						fit = false;
						break;
					}
				}
				if (!fit)
					continue;
				Arrays.fill(changes, -1);
				change_amount = 0;
				for (int i = 0; i < ws.length; i++) {
					if (puzzle[ws.x][ws.y + i] == '#') {
						changes[change_amount] = ws.y + i;
						change_amount++;
						puzzle[ws.x][ws.y + i] = word[i];
					}
				}
				wordUsed.put(s, true);
				if (dfs_improved(index + 1))
					return true;
				else { // restore
					for (int i = 0; i < change_amount; i++)
						puzzle[ws.x][changes[i]] = '#';
					wordUsed.put(s, false);
				}
			}
			else {
				boolean fit = true;
				for (int i = 0; i < ws.length; i++) {
					if ((puzzle[ws.x + i][ws.y] != '#') && (word[i] != puzzle[ws.x + i][ws.y])) {
						fit = false;
						break;
					}
				}
				if (!fit)
					continue;
				Arrays.fill(changes, -1);
				change_amount = 0;
				for (int i = 0; i < ws.length; i++) {
					if (puzzle[ws.x + i][ws.y] == '#') {
						changes[change_amount] = ws.x + i;
						change_amount++;
						puzzle[ws.x + i][ws.y] = word[i];
					}
				}
				wordUsed.put(s, true);
				if (dfs_improved(index + 1))
					return true;
				else { // restore
					for (int i = 0; i < change_amount; i++)
						puzzle[changes[i]][ws.y] = '#';
					wordUsed.put(s, false);
				}
			}
		}
		return false;
	}
	
	private int preprocess() {
		int length;
		int i = 0;
		int j = 0;
		int totalNumberWordSpaces = 0;
		// horizontal pre-processing
		while (i < height) {
			length = 0;
			while (j < weight && (puzzle[i][j] == '#')) {
				length++;
				j++;
			}
			if (length >= 2) {
				FreeSpace wordSpace = new FreeSpace(i, j - length, true, length);
				wordSpaces.add(wordSpace);
				totalNumberWordSpaces++;
			}
			if (j < weight)
				j++;
			else { // go to next line
				i++;
				j = 0;
			}
		}
		// vertical pre-processing
		i = 0;
		j = 0;
		while (i < weight) {
			length = 0;
			while (j < height && (puzzle[j][i] == '#')) {
				length++;
				j++;
			}
			if (length >= 2) {
				FreeSpace wordSpace = new FreeSpace(j - length, i, false, length);
				wordSpaces.add(wordSpace);
				totalNumberWordSpaces++;
			}
			if (j < height)
				j++;
			else { // go to next line
				i++;
				j = 0;
			}
		}
		return totalNumberWordSpaces;
	}

	public static void main(String args[]) {
		ECimproved ec = new ECimproved();
		ec.solve();
	}
}

class FreeSpace {
	int x;
	int y;
	boolean isHorizontal;
	int length;
	FreeSpace(int x, int y, boolean isH, int length) {
		this.x = x;
		this.y = y;
		isHorizontal = isH;
		this.length = length;
	}
}
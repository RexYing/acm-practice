package southcentral.acm06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * south central regional 2006
 * depth first search with pre-processing
 * @author Rex
 *
 */
public class EnigmatologicallyCruciverbalistic_3 {
	final int MAX_LENGTH = 15;
	
	private char[][] puzzle;
	private Scanner scanner = new Scanner(System.in);
	private ArrayList<ArrayList<WordSpace>> wordSpaces;
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
			// initialize
			// for simplicity, the index i of array list corresponds to the word spaces with length of i
			wordSpaces = new ArrayList<ArrayList<WordSpace>>(MAX_LENGTH + 1);
			wordCategories = new ArrayList<ArrayList<String>>(MAX_LENGTH + 1);
			wordUsed = new HashMap<String, Boolean>();
			for (int j = 0; j <= MAX_LENGTH; j++) {
				wordSpaces.add(new ArrayList<WordSpace>());
				wordCategories.add(new ArrayList<String>());
			}
			scanner.nextLine();
			// input
			for (int j = 0; j < height; j++) {
				String line = scanner.nextLine();
				puzzle[j] = line.toCharArray();
			}
			int totalNumberWordSpaces = preprocess();
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
			//if ((totalNumberWordSpaces == count) && dfs(0)) {
			if ((totalNumberWordSpaces == count) && dfs_improved(MAX_LENGTH, 0)) {
				printPuzzle();
			}
			else
				System.out.println("I cannot generate this puzzle.");
		}
	}
	
	private void printPuzzle() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < weight; j++)
				System.out.print(puzzle[i][j]);
			System.out.println();
		}
	}
	
	private boolean dfs_improved(int spaceLength, int index) {
		if (spaceLength < 2)
			return true;
		ArrayList<WordSpace> wordSpacesFixedLength = wordSpaces.get(spaceLength);
		if (wordSpacesFixedLength.isEmpty())
			return dfs_improved(spaceLength - 1, 0);
		int[] changes = new int[MAX_LENGTH];
		int change_amount;
		WordSpace ws = wordSpaces.get(spaceLength).get(index);
		for (String s: wordCategories.get(spaceLength)) {
			if (wordUsed.get(s))
				continue;
			char[] word = s.toCharArray();
			if (ws.isHorizontal) {
				boolean fit = true;
				for (int i = 0; i < spaceLength; i++) {
					if ((puzzle[ws.x][ws.y + i] != '#') && (word[i] != puzzle[ws.x][ws.y + i])) {
						fit = false;
						break;
					}
				}
				if (!fit)
					continue;
				Arrays.fill(changes, -1);
				change_amount = 0;
				for (int i = 0; i < spaceLength; i++) {
					if (puzzle[ws.x][ws.y + i] == '#') {
						changes[change_amount] = ws.y + i;
						change_amount++;
						puzzle[ws.x][ws.y + i] = word[i];
					}
				}
				wordUsed.put(s, true);
				int newSpaceLength = spaceLength;
				int newIndex = index + 1;
				if (newIndex == wordSpacesFixedLength.size()) {
					newIndex = 0;
					newSpaceLength--;
				}
				if (dfs_improved(newSpaceLength, newIndex))
					return true;
				else { // restore
					for (int i = 0; i < change_amount; i++)
						puzzle[ws.x][changes[i]] = '#';
					wordUsed.put(s, false);
				}
			}
			else {
				boolean fit = true;
				for (int i = 0; i < spaceLength; i++) {
					if ((puzzle[ws.x + i][ws.y] != '#') && (word[i] != puzzle[ws.x + i][ws.y])) {
						fit = false;
						break;
					}
				}
				if (!fit)
					continue;
				Arrays.fill(changes, -1);
				change_amount = 0;
				for (int i = 0; i < spaceLength; i++) {
					if (puzzle[ws.x + i][ws.y] == '#') {
						changes[change_amount] = ws.x + i;
						change_amount++;
						puzzle[ws.x + i][ws.y] = word[i];
					}
				}
				wordUsed.put(s, true);
				int newSpaceLength = spaceLength;
				int newIndex = index + 1;
				if (newIndex == wordSpacesFixedLength.size()) {
					newIndex = 0;
					newSpaceLength--;
				}
				if (dfs_improved(newSpaceLength, newIndex))
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

	private boolean dfs(int depth) {
		//System.out.println(depth);
		if (depth >= count)
			return true;
		char[] word = words[depth].toCharArray();
		int[] changes = new int[MAX_LENGTH];
		int change_amount;
		int wordLength = words[depth].length();
		if (wordLength > MAX_LENGTH)
			return false;
		ArrayList<WordSpace> wordSpacesFixedLength = wordSpaces.get(wordLength);
		for (WordSpace ws: wordSpacesFixedLength) {
			if (ws.isOccupied)
				continue;
			if (ws.isHorizontal) {
				boolean fit = true;
				for (int i = 0; i < wordLength; i++) {
					if ((puzzle[ws.x][ws.y + i] != '#') && (word[i] != puzzle[ws.x][ws.y + i])) {
						fit = false;
						break;
					}
				}
				if (!fit)
					continue;
				Arrays.fill(changes, -1);
				change_amount = 0;
				for (int i = 0; i < wordLength; i++) {
					if (puzzle[ws.x][ws.y + i] == '#') {
						changes[change_amount] = ws.y + i;
						change_amount++;
						puzzle[ws.x][ws.y + i] = word[i];
					}
				}
				ws.isOccupied = true;
				if (dfs(depth + 1))
					return true;
				else { // restore
					for (int i = 0; i < change_amount; i++)
						puzzle[ws.x][changes[i]] = '#';
					ws.isOccupied = false;
				}
			}
			else { // vertical word space
				boolean fit = true;
				for (int i = 0; i < word.length; i++) {
					if ((puzzle[ws.x + i][ws.y] != '#') && (word[i] != puzzle[ws.x + i][ws.y])) {
						fit = false;
						break;
					}
				}
				if (!fit)
					continue;
				Arrays.fill(changes, -1);
				change_amount = 0;
				for (int i = 0; i < word.length; i++) {
					if (puzzle[ws.x + i][ws.y] == '#') {
						changes[change_amount] = ws.x + i;
						change_amount++;
						puzzle[ws.x + i][ws.y] = word[i];
					}
				}
				ws.isOccupied = true;
				if (dfs(depth + 1))
					return true;
				else { // restore
					for (int i = 0; i < change_amount; i++)
						puzzle[changes[i]][ws.y] = '#';
					ws.isOccupied = false;
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
				WordSpace wordSpace = new WordSpace(i, j - length, true);
				wordSpaces.get(length).add(wordSpace);
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
				WordSpace wordSpace = new WordSpace(j - length, i, false);
				wordSpaces.get(length).add(wordSpace);
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
		EnigmatologicallyCruciverbalistic_3 ec = new EnigmatologicallyCruciverbalistic_3();
		ec.solve();
	}
}

class WordSpace {
	int x;
	int y;
	boolean isHorizontal;
	boolean isOccupied;
	WordSpace(int x, int y, boolean isH) {
		this.x = x;
		this.y = y;
		isHorizontal = isH;
		isOccupied = false;
	}
}
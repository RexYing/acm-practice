package topcoder.srm;

import java.util.Scanner;

public class Shuffling {

	public int position(int cards, int[] shuffles) {
		int position = 0;
		for (int i = 0; i < shuffles.length; i++) {
			int n = shuffles[i];
			int absN = Math.abs(n);
			if (n == 0)
				continue;
			// ace is in the deck which is going to be put below the other
			if (((n < 0) && (position >= cards / 2)) || ((n > 0) && (position < cards / 2))) {
				if (position >= cards / 2)
					position -= cards / 2;
				if (position >= absN) // if position less than abs(n), it does not change
					position = absN + (position - absN) * 2 + 1;
			} else {
				if (position >= cards / 2)
					position -= cards / 2;
				if (position >= cards / 2 - absN)
					position = cards / 2 + position;
				else
					position = absN + position * 2;
			}
		}
		return position;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Shuffling problem = new Shuffling();
		int cards = scanner.nextInt();
		scanner.nextLine();
		String[] shuffleStrings = scanner.nextLine().split(" ");
		int[] shuffles = new int[shuffleStrings.length];
		for (int i = 0; i < shuffles.length; i++)
			shuffles[i] = Integer.parseInt(shuffleStrings[i]);
		System.out.println(problem.position(cards, shuffles));
	}
}

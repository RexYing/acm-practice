package topcoder.srm;

import java.util.Scanner;

public class ThreeCardMonte {
	
	public String position(String swaps) {
		int position = 2;
		for (int i = 0; i < swaps.length(); i++) {
			if (swaps.charAt(i) == 'L') {
				if (position == 2)
					position = 1;
				else if (position == 1)
					position = 2;
			}
			else if (swaps.charAt(i) == 'R') {
				if (position == 3)
					position = 2;
				else if (position == 2)
					position = 3;
			}
			else if (swaps.charAt(i) == 'E') {
				if (position == 1)
					position = 3;
				else if (position == 3)
					position = 1;
			}
		}
		String[] returnString = new String[4];
		returnString[1] = "L";
		returnString[2] = "M";
		returnString[3] = "R";
		return returnString[position];
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ThreeCardMonte problem = new ThreeCardMonte();
		String input = scanner.nextLine();
		System.out.println(problem.position(input));
	}

}

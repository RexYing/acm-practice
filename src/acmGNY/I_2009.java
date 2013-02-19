package acmGNY;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class I_2009 {

	public String thetaPuzzle(String initPosition) {
		String result = "";

		return result;
	}

	public static void main() throws NumberFormatException, IOException {
		I_2009 i2009 = new I_2009();
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		int P = 0;
		P = Integer.parseInt(bf.readLine());
		for (int i = 0; i < P; i++) {
			try {
				String data[] = bf.readLine().split(" ");
				int count = Integer.parseInt(data[0]);
				String initPosition = data[1];
				i2009.thetaPuzzle(initPosition);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

class SearchState {
	SearchState(String solution, String state, int depth) {
		this.solution = solution;
		this.state = state;
		this.depth = depth;
	}

	String solution;
	String state;
	int depth;
}
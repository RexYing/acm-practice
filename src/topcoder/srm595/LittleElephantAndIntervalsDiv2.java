package topcoder.srm595;

public class LittleElephantAndIntervalsDiv2 {

	public int getNumber (int m, int[] l, int[] r) {
		int result = 0;
		boolean[] colored = new boolean[m];
		for (int i = l.length - 1; i >= 0; i--) {
			boolean diff = false;
			for (int j = l[i] - 1; j < r[i]; j++) {
				if (!colored[j]) {
					colored[j] = true;
					if (!diff) {
						result++;
						diff = true;
					}
				}
			}
		}
		return 1 << result;
	}
	
	public static void main(String[] args) {
		int[] l = new int[] {1, 2, 3};
		int[] r = new int[] {1, 2, 3};
		System.out.println(new LittleElephantAndIntervalsDiv2().getNumber(4, l, r));
	}

}

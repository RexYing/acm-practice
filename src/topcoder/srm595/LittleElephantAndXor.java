package topcoder.srm595;

import java.util.Arrays;

public class LittleElephantAndXor {

	public int getNumber(int a, int b, int c) {
		int digits = Math.max(Integer.bitCount(a), Integer.bitCount(b));
		digits = Math.max(digits, Integer.bitCount(c));
		int[] binA, binB, binC;
		binA = toBin(digits, a);
		binB = toBin(digits, b);
		binC = toBin(digits, c);
		return find(binA, binB, binC);
	}
	
	public int[] toBin(int digits, int num) {
		int[] result = new int[digits];
		int i = digits - 1;
		while (num > 0) {
			result[i] = num % 2;
			num /= 2;
			i--;
		}
		return result;
	}
	
	public int find(int[] a, int[] b, int[] c) {
		int value = 0;
		if (a.length == 0)
			return 1;
		int[] aNext = new int[a.length - 1];
		int[] bNext = new int[b.length - 1];
		int[] cNext = new int[c.length - 1];
		int[] allOnes = new int[a.length - 1];
		for (int i = 0; i < a.length - 1; i++) {
			aNext[i] = a[i + 1];
			bNext[i] = b[i + 1];
			cNext[i] = c[i + 1];
			allOnes[i] = 1;
		}
		if (c[0] == '1') {
			if (a[0] == 0) {
				if (b[0] == 0)
					value = find(aNext, bNext, cNext);
				else
					value = find(aNext, bNext, cNext) + find(aNext, allOnes, cNext);
			}
			else {
				if (b[0] == 0)
					value =  find(aNext, bNext, cNext) + find(allOnes, bNext, cNext);
				else
					value = find(aNext, bNext, cNext) + find(aNext, allOnes, cNext) +
							find(allOnes, bNext, cNext) + find(allOnes, allOnes, cNext);
			}
		}
		else {
			if (a[0] == 0) {
				if (b[0] == 0)
					value = find(aNext, bNext, cNext);
				else
					value = find(aNext, allOnes, cNext);
			}
			else {
				if (b[0] == 0)
					value = find(allOnes, bNext, cNext);
				else
					value = find(allOnes, allOnes, cNext) + find(aNext, bNext, cNext);
			}
		}
		return value;
	}

	
	public static void main(String[] args) {
		System.out.println(new LittleElephantAndXor().getNumber(4, 7, 3));
	}

}

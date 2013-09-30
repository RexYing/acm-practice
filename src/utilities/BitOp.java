package utilities;

public class BitOp {
	
	/**
	 * Replace the ith to jth bits in n to m.
	 * @return the resulting integer
	 */
	public int replaceBits(int n, int m, int i, int j) {
		if ((i < 0) || (j > 31))
			return new Integer(null);
		if ((m < 0) || (m >> (j - i + 1) > 0))
			return new Integer(null);
		n = n & (Integer.MAX_VALUE - (2 << (j + 1)) + (2 << (i)));
		return n | (m << i);
	}

	/**
	 * @param numStr String representing a decimal number (eg. 1.2)
	 * @return binary representation of decimal number numStr. 
	 */
	public String toBinaryDecimal(String numStr) {
		final double EPSILON = 0.00000001;
		
		StringBuffer buffer = new StringBuffer();
		String[] parts = numStr.split("\\.");
		
		buffer.append(Integer.toBinaryString(Integer.parseInt(parts[0])));
		
		if (parts.length == 1)
			return buffer.toString();
		buffer.append('.');
		double num = Double.parseDouble("0." + parts[1]);
		int count = 0;
		while (Math.abs(num) > EPSILON) {
			num *= 2;
			if (num >= 1) {
				buffer.append("1");
				num -= 1;
			}
			else
				buffer.append("0");
			count++;
			if (count >= 64)
				return "ERROR";
		}
		return buffer.toString();
	}
	
	/**
	 * next smallest integer whose binary representation has the same number of 1s
	 */
	public int nextSameNumOfOnes(int num) {
		// first occurrence of "01".
		int index = 0;
		int tmp = num;
		int numOnes = 0;
		while (tmp % 4 != 1) {
			if (tmp % 2 == 1)
				numOnes++;
			tmp /= 2;
			if (tmp == 0)
				return new Integer(null); // not possible. eg. 11...111
			index++;
		}
		tmp++;
		return (tmp << index) + ((1 << numOnes) - 1);
	}
	
	public int swapOddEvenDigits(int num) {
		return ((num & 0xaaaaaaaa) >> 1) | ((num & 0x55555555) << 1); 
	}
}

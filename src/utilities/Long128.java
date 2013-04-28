package utilities;

/**
 * actually only upto 10^32, which is about 2^109
 * Java doesn't support unsigned
 * 
 * @author rex
 *
 */
public class Long128 implements Comparable<Long128> {
	
	// decimal, not binary
	private static final long MAX_LOWER = 9999999999999999L;
	public long myLowerDigits = 0;
	public long myUpperDigits = 0;

	public Long128(long lowerDigits, long upperDigists) {
		myLowerDigits = lowerDigits;
		myUpperDigits = upperDigists;
	}
	
	public Long128(long number) {
		this(number, 0);
	}
	
	public Long128 add(Long128 number) {
		 long lowerSum = myLowerDigits + number.myLowerDigits;
		 boolean carry = false;
		 // overflow
		 if ((lowerSum > 0 && myLowerDigits < 0 && number.myLowerDigits < 0) ||
				 (lowerSum < 0 && myLowerDigits > 0 && number.myLowerDigits > 0)) {
			 lowerSum = lowerSum & Long.MAX_VALUE;
			 carry = true;
		 }
		 if (lowerSum > MAX_LOWER)
			 carry = true;
		 long upperSum = myUpperDigits + number.myUpperDigits;
		 if (carry)
			 upperSum++;
		 return new Long128(lowerSum, upperSum);
	}

	@Override
	public int compareTo(Long128 number) {
		if (myUpperDigits > number.myUpperDigits)
			return 1;
		else if (myUpperDigits < number.myUpperDigits)
			return -1;
		else {
			if (myLowerDigits > number.myLowerDigits)
				return 1;
			else if (myLowerDigits < number.myLowerDigits)
				return -1;
			else
				return 0;
		}
	}
	
	@Override
	public boolean equals(Object number) {
		if (this.compareTo((Long128)number) == 0)
			return true;
		else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		if (myUpperDigits == 0)
			return  ((Long)myLowerDigits).toString();
		return String.format("%d%016d", myUpperDigits, myLowerDigits);
	}
}

package utilities;

import java.util.Arrays;
import java.util.Random;

/**
 * Quick select and deterministic select
 * 
 * @author Rex Ying
 * 
 */
public class Select {

	/**
	 * Quick select: T(n) = T(n/2) + O(n). Quicksort like but only recurse on
	 * one side.
	 * 
	 * @param array
	 *            input array
	 * @param k k-th smallest value requested
	 * @return The k-th smallest in array; -1 if invalid
	 */
	public int quickSelect(int[] array, int k) {
		if (array.length < k)
			return -1;
		return quickSelect(array, 0, array.length, k);
	}
	
	
	public int quickSelect(int[] array, int begin, int end, int k) {
		int SIZE_LOW_BOUND = 3;
		// if end - begin <= SIZE_LOW_BOUND, just sort it.
		if (end - begin <= SIZE_LOW_BOUND) {
			Arrays.sort(array, begin, end);
			return array[k];
		}
		int pivotIndex = choosePivotRandom(array);
		int pivotPos = Sort.partition(array, begin, end, pivotIndex);
		if (pivotPos < k)
			return quickSelect(array, pivotPos + 1, end, k);
		else if (pivotPos > k)
			return quickSelect(array, begin, pivotPos - 1, k);
		else
			return array[pivotPos];
	}

	private int choosePivotRandom(int[] array) {
		return Math.abs(new Random().nextInt() % array.length);
	}

}

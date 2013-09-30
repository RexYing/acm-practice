package utilities;

public class Sort {
	
	public static void swap(int[] array, int ind1, int ind2) {
		int temp = array[ind1];
		array[ind1] = array[ind2];
		array[ind2] = temp;
	}
	
	public static int partition(int[] array, int begin, int end, int pivotInd) {
	    int pivotPos = 0;
	    int index = 0;
	    int pivot = array[pivotInd];
	    swap(array, pivotInd, end - 1);
	    while (index < end) {
	        if (array[index] < pivot) {
	            swap(array, pivotPos, index);
	            pivotPos++;
	        }
	        index++;
	    }
	    swap(array, pivotPos, end - 1);
	    return pivotPos;
	}

	/**
	 * Quick Sort
	 */
	public static void quicksort(int[] array, int begin, int end) {
		if (begin + 1 >= end)
			return;
	    int pivotInd = (int) (Math.random() * (end - begin)) + begin;
	    int newPivotPos = partition(array, begin, end, pivotInd);
	    quicksort(array, begin, newPivotPos);
	    quicksort(array, newPivotPos + 1, end);
	}
	
	public static int putItemAfterDup(int[] array, int pos, int prevItem) {
		while (prevItem == array[pos])
			pos++;
		int item = array[pos];
		array[pos] = prevItem;
		return item;
	}
	
	/**
	 * Cycle Sort
	 * Sort by rotating cycles. O(n^2)
	 * Guarantee the least amount of writes
	 * @param array
	 * @return writes the number of writes
	 */
	public static int cycleSort(int[] array) {
		int writes = 0;
		// Loop through the array to find cycles to rotate.
		for (int cycleStart = 0; cycleStart < array.length; cycleStart++) {
			int item = array[cycleStart];
			int pos = cycleStart;
			
			for (int i = cycleStart + 1; i < array.length; i++) {
				if (array[i] < item)
					pos++;
			}
			// if item is already there, it is not a cycle
			if (pos == cycleStart)
				continue;
			
			// Otherwise, put the item there or right after any duplicates.
			item = putItemAfterDup(array, pos, item);
			writes++;
			
		    // Rotate the rest of the cycle.
			while (pos != cycleStart) {
				// Find where to put the item.
				pos = cycleStart;
				for (int i = cycleStart + 1; i < array.length; i++)
					if (array[i] < item)
						pos++;
				
				// Put the item there or right after any duplicates.
				item = putItemAfterDup(array, pos, item);
				writes++;
			}
		}
		return writes;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}

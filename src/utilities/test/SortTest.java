package utilities.test;

import java.util.Arrays;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import utilities.Sort;

public class SortTest {

	@Test
	public void testSort() {
		int numElem = 10000;
		Random gen = new Random();
		int[] arr1 = new int[numElem];
		for (int i = 0; i < numElem; i++) {
			arr1[i] = gen.nextInt();
		}
		int[] arr2 = arr1.clone();
		Arrays.sort(arr2);
		
		Sort.quicksort(arr1, 0, arr1.length);
		//int numWrites = Sort.cycleSort(arr1); 
		//System.out.println("Number of writes: " + numWrites);
		
		Assert.assertArrayEquals(arr2, arr1);
	}

}

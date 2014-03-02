package utilities.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import utilities.Select;

public class SelectTest {

	@Test
	public void testSelect() {
		int numElem = 1000;
		int max = 10000;
		Random gen = new Random();
		// int[] arr1 = {5, 3, 4, 2, 1};
		int[] arr1 = new int[numElem];
		for (int i = 0; i < numElem; i++) {
			arr1[i] = gen.nextInt() % max;
		}
		int[] arr2 = arr1.clone();
		Arrays.sort(arr2);
		int select = Math.abs(gen.nextInt() % numElem);
		// int numWrites = Sort.cycleSort(arr1);
		// System.out.println("Number of writes: " + numWrites);

		assertEquals(arr2[select], new Select().quickSelect(arr1, select));
	}
}

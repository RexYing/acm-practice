package utilities.test;

import junit.framework.Assert;

import org.junit.Test;

import utilities.trees.DisjointSet;

public class disjointSetTest {
	
	private DisjointSet dSet;

	@Test
	public void DisjointSetTest() {
		final int setSize = 100;
		dSet = new DisjointSet(setSize);
		testUnion();
		testFind();
	}
	
	private void testUnion() {
		dSet.union(1, 5);
		dSet.union(5,  12);
		for (int i = 0; i < 10; i++)
			dSet.union(i, i * 2);
	}
	
	private void testFind() {
		if (!dSet.isSameSet(1, 10))
			Assert.fail("1 and 10 are supposed to be in the same set!!");
		if (!dSet.isSameSet(6, 12))
			Assert.fail("6 and 12 are supposed to be in the same set!!");
		if (!dSet.isSameSet(5, 6))
			Assert.fail("5 and 6 are supposed to be in the same set!!");
		if (dSet.isSameSet(14, 6))
			Assert.fail("6 and 14 are NOT supposed to be in the same set!!");
	}

}

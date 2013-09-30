package utilities.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import utilities.BitOp;

public class BitOpTest {
	private BitOp test = new BitOp();

	@Test
	public void testReplaceBits() {		
		// 188: 10111100; 5: 0101; result: 148: 10010100
		assertEquals("10111100 repBits 0101, 2-5 is 10010100: ", 148, test.replaceBits(188, 5, 2, 5));
	}
	
	@Test
	public void testToBinaryDecimal() {
		assertEquals("5.125 -> 101.001: ", "101.001", test.toBinaryDecimal("5.125"));
	}
	
	@Test
	public void testNextSameNumOfOnes() {
		assertEquals("next of 11011 is 11101: ", 29, test.nextSameNumOfOnes(Integer.parseInt("11011", 2)));
	}

}

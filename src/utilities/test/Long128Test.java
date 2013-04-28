package utilities.test;

import static org.junit.Assert.*;

import org.junit.Test;

import utilities.Long128;

public class Long128Test {

	@Test
	public void testAdd() {
		Long128 number1 = new Long128(1, Integer.MAX_VALUE);
		Long128 number2 = new Long128(5);
		Long128 sum = number1.add(number2);
		assertEquals("21474836470000000000000006", sum.toString());
	}

	@Test
	public void testToString() {
		Long128 number = new Long128(100, 100);
		assertEquals("1000000000000000100", number.toString());
		number = new Long128(700000000000001L, Integer.MAX_VALUE);
		assertEquals("21474836470700000000000001", number.toString());
	}
}

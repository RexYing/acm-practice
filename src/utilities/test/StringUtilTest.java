package utilities.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import utilities.strings.Matching;

public class StringUtilTest {

	@Test
	public void testKMP() {
		String matchStr = "participate in parachute";
		char[] chars = matchStr.toCharArray();
		int[] table = Matching.partialMatch(chars);
		System.out.println("Partial match table: " + Arrays.toString(table));
		
		String text = "I don't want to pppparticipate in parachute events";
		char[] textArr = text.toCharArray();
		
		int ans = text.indexOf(matchStr);
		assertEquals("KMP matching result: ", ans, Matching.KMP(textArr, chars));
	}
}

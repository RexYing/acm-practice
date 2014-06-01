package topcoder.srm595;

/**
 * 
 * 
 * @author rex
 *
 */
public class LittleElephantAndBallsAgain
{
	public int getNumber(String colors)
	{
		if (colors.length() == 0)
			return 0;
		int result = 1;
		int i = 0;
		while (i < colors.length()) {
			int max = 1;
			i++;
			while ((i < colors.length()) && (colors.charAt(i) == colors.charAt(i - 1)) ) {
				i++;
				max++;
			}
			if (max > result)
				result = max;
		}
		return colors.length() - result;
	}
	
	public static void main(String[] args) {
		System.out.println(new LittleElephantAndBallsAgain().getNumber("RGGGBB"));
	}
}
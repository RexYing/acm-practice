package topcoder.srm576;

/**
 * lalala
 * Its Thursday night. 
 * 
 * @author rex
 *
 */
public class TheExperimentDiv2
{
	public int[] determineHumidity(int[] intensity, int L, int[] leftEnd)
	{
		boolean[] drop = new boolean[intensity.length];
		int[] humidity = new int[leftEnd.length];
		for (int i = 0; i < leftEnd.length; i++) {
			for (int j = 0; j < L; j++) {
				if (drop[j + leftEnd[i]])
					continue;
				humidity[i] += intensity[j + leftEnd[i]];
				drop[j + leftEnd[i]] = true;
			}
		}
		return humidity;
	}
}
package topcoder.srm;

public class TheSwapsDivTwo575
{
	public int find(int[] sequence)
	{
		if (sequence.length == 2)
			return 1;
		int count = 0;
		for (int i = 0; i < sequence.length; i++) {
			for (int j = i + 1; j < sequence.length; j++) {
				if (sequence[i] != sequence[j])
					count++;
			}
		}
		return count + 1;
	}
}

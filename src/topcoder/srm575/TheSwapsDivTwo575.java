package topcoder.srm575;

public class TheSwapsDivTwo575
{
	public int find(int[] sequence)
	{
		boolean hasSameNum = false;
		if (sequence.length == 2)
			return 1;
		int count = 0;
		for (int i = 0; i < sequence.length; i++) {
			for (int j = i + 1; j < sequence.length; j++) {
				if (sequence[i] != sequence[j])
					count++;
				else
					hasSameNum = true;
			}
		}
		if (hasSameNum)
			return count + 1;
		else
			return count;
	}
}

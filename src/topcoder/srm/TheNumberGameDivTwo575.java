package topcoder.srm;

public class TheNumberGameDivTwo575 {

	public String find(int n)
	{
		int numPrime = 0;
		int[] prime = new int[200];
		for (int i = 2; i < 1000; i++) {
			boolean isPrime = true;
			for (int j = 2; j <= Math.sqrt(i); j++) {
				if (i % j == 0) {
					isPrime = false;
					break;
				}
			}
			if (isPrime) {
				prime[numPrime] = i;
				numPrime++;
			}
		}
		int count = 0;
		while (n > 1) {
			for (int i = 0; i < prime.length; i++) {
				if (n % prime[i] == 0) {
					n = n / prime[i];
					count++;
					break;
				}
			}
		}
		if (count % 2 == 0)
			return "John";
		else
			return "Brus";
	}
}

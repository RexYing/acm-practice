package midatlantic.year2005;

import info.Date;

import java.util.Scanner;

/**
 * store all possible combinations: a naive way. Not sure if there is a
 * mathematical solution
 * 
 * @author Rex
 * 
 * @date 04/10/2013
 */
@Date ("04/10/2013")
public class ContextFreeClock_F {

	private static final double EPSILON = 0.00000001;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		double[] angles = new double[12 * 60 * 60];
		for(int i = 1;i < angles.length; i++)
			angles[i]=(angles[i - 1] + 1D / 10- 1D / (10 * 12) + EPSILON) % 360 - EPSILON;
		while (scanner.hasNext()) {
			int n = scanner.nextInt();
			if (n < 0)
				break;
			String[] s = scanner.next().split(":");
		}
	}
}
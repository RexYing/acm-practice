package googlecodejam2013.round1A;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Scanner;

/**
 * solve equation
 * n: number of circles
 * 
 * to print n circles, total area at least:
 * (2r + 2n - 1)n = 2n^2 + (2r - 1)n
 * 
 * BigDecimal sqrt... not a good one lol
 * but can pass (r <= 10^18, vol <= 2*10^18)
 * 
 * @author rex
 *
 */
public class Bullseye {
	
	private long solve (long radius, long volume) {
		Long a = (long)2;
		Long b = 2 * radius - 1;
		Long c = -volume;
		BigInteger temp = new BigInteger(b.toString());
		temp = temp.pow(2);
		//System.out.println(temp);
		Long tempLong = 4 * a;
		BigInteger temp1 = new BigInteger(tempLong.toString()).multiply(new BigInteger(c.toString()));
		//System.out.println(temp1);
		temp = temp.add(temp1.negate());
		//System.out.println(temp);
		double n = (sqrt(new BigDecimal(temp), 0).add(new BigDecimal(b.toString()).negate())).doubleValue() / (2 * a);
		return (long) (Math.floor(n));
	}

	public BigDecimal sqrt(BigDecimal in, int scale){
	    BigDecimal sqrt = new BigDecimal(1);
	    sqrt.setScale(scale + 3, RoundingMode.FLOOR);
	    BigDecimal store = new BigDecimal(in.toString());
	    boolean first = true;
	    int count = 0;
	    do{
	    	count ++;
	        if (!first){
	            store = new BigDecimal(sqrt.toString());
	        }
	        else first = false;
	        store.setScale(scale + 3, RoundingMode.FLOOR);
	        sqrt = in.divide(store, scale + 3, RoundingMode.FLOOR).add(store).divide(
	                BigDecimal.valueOf(2), scale + 3, RoundingMode.FLOOR);
	    }while (!store.equals(sqrt) && count < 10000);
	    return sqrt.setScale(scale, RoundingMode.FLOOR);
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		System.setOut(new PrintStream(new FileOutputStream("bullseye.out", true)));
		Scanner scanner = new Scanner(System.in);
		int t = scanner.nextInt();
		for (int i = 0; i < t; i++) {
			long r = scanner.nextLong();
			long vol = scanner.nextLong();
			System.out.println("Case #" + (i + 1) + ": " + new Bullseye().solve(r, vol));
		}
	}
}

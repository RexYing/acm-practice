package northcentral.year2011;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * DP / memoization
 * used descendingSet() for SortedSet
 * 
 * @author Rex
 * 
 * @date 03/22/2013
 */
public class ChangeByMass_E {

	private static final int MAX_NUM_HALF_DOLLAR = 1;
	private static final int MAX_NUM_COINS = 100;
	private static final int MAX_VALUE = 100;
	private HashMap<Integer, Double> myCoinMasses;
	private DpState[][][] dp; // numCoins, value, numHalfDollar
	private int myNumHalfDollar;

	public ChangeByMass_E() {
		myCoinMasses = new HashMap<Integer, Double>();
		myCoinMasses.put(1, 2.5);
		myCoinMasses.put(5, 5.0);
		myCoinMasses.put(10, 2.268);
		myCoinMasses.put(25, 5.67);
		myCoinMasses.put(50, 11.34);
		dp = new DpState[MAX_NUM_COINS][MAX_VALUE][MAX_NUM_HALF_DOLLAR + 1];
	}

	public void handleChangeRequest(int requestValue, int numHalfDollar) {
		myNumHalfDollar = Math.min(numHalfDollar, MAX_NUM_HALF_DOLLAR);
		initializeDpState();
		DpState bestState = new DpState();
		int bestNumCoins = 0;
		int totalValue = requestValue;
		int bestNumHalfDollar = 0;
		double bestMassValue = Double.MAX_VALUE;
		outerLoop:
		for (int i = 0; i < MAX_NUM_COINS; i++)
			for (int j = 0; j <= myNumHalfDollar; j++) {
				bestState = memoization(i, requestValue, j);
				if (bestState.isValid() && bestState.mass < bestMassValue) {
					bestNumCoins = i;
					bestNumHalfDollar = j;
					bestMassValue = bestState.mass;
					break outerLoop;
				}
			}
		//System.out.println(bestState.mass + " " + bestState.lastCoinValue);
		HashMap<Integer, Integer> numCoinForEach = new HashMap<Integer, Integer>();
		for (Integer coinValue: myCoinMasses.keySet()) {
			numCoinForEach.put(coinValue, 0);
		}
		DpState stateIterator = bestState;
		// while the DpState is valid and is not edge cases (lastCoinValue = 0 when number of coins is 0)
		while (stateIterator.isValid() && stateIterator.lastCoinValue != 0) {
			int number = numCoinForEach.get(stateIterator.lastCoinValue);
			numCoinForEach.put(stateIterator.lastCoinValue, number + 1); // increment the number by 1
			bestNumCoins--;
			totalValue -= stateIterator.lastCoinValue;
			if (stateIterator.lastCoinValue == 50)
				bestNumHalfDollar--;
			stateIterator = dp[bestNumCoins][totalValue][bestNumHalfDollar];
		}
		printChange(numCoinForEach);
	}

	private void printChange(Map<Integer, Integer> numCoinForEach) {
		SortedSet<Integer> coinValues = (new TreeSet<Integer>(numCoinForEach.keySet())).descendingSet();
		boolean isFirstCoin = true;
		for (Integer coinValue: coinValues) {
			if (numCoinForEach.get(coinValue) != 0) {
				// the first one does not need a space before the output
				if (isFirstCoin)
					isFirstCoin = false;
				else 
				    System.out.print(" ");
				System.out.printf("%dx%d", numCoinForEach.get(coinValue), coinValue);
			}
		}
		System.out.println();
	}

	private void initializeDpState() {
		for (int i = 0; i < MAX_NUM_COINS; i++)
			for (int j = 0; j <= myNumHalfDollar; j++) {
				dp[i][0][j] = new DpState();
			}
		for (int i = 0; i < MAX_VALUE; i++)
			for (int j = 0; j <= myNumHalfDollar; j++) {
				dp[0][i][j] = new DpState();
			}
		DpState initState = new DpState(0, 0.0);
		dp[0][0][0] = initState;
	}

	// return smallest mass to get requestValue
	private DpState memoization(int numCoins, int value, int numHalfDollar) {
		if ((value < 0 || numCoins < 0))
			return null;
		if (dp[numCoins][value][numHalfDollar] != null)
			return dp[numCoins][value][numHalfDollar];
		DpState bestDpState = new DpState();
		for (Integer coinValue : myCoinMasses.keySet()) {
			// the number of half dollars is limited
			if (coinValue == 50) {
				if (numHalfDollar > 0) {
					DpState state = memoization(numCoins - 1, value - coinValue, numHalfDollar - 1);
					if ((state == null) || (!state.isValid()))
						continue;
					if (state.mass + myCoinMasses.get(coinValue) < bestDpState.mass) {
						bestDpState.mass = state.mass + myCoinMasses.get(coinValue);
						bestDpState.lastCoinValue = coinValue;
					}
				}
			} else {
				DpState state = memoization(numCoins - 1, value - coinValue, numHalfDollar);
				if ((state == null) || (!state.isValid()))
					continue;
				if (state.mass + myCoinMasses.get(coinValue) < bestDpState.mass) {
					bestDpState.mass = state.mass + myCoinMasses.get(coinValue);
					bestDpState.lastCoinValue = coinValue;
				}
			}
		}
		dp[numCoins][value][numHalfDollar] = bestDpState;
		return bestDpState;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ChangeByMass_E problem = new ChangeByMass_E();
		while (scanner.hasNext()) {
			int requestValue = scanner.nextInt();
			int numHalfDollar = scanner.nextInt();
			problem.handleChangeRequest(requestValue, numHalfDollar);
		}
	}

	private class DpState {
		final int IMPOSSIBLE_COIN_VALUE = -1;
		int lastCoinValue; // value of the last coin added
		double mass;

		// return an impossible state
		DpState() {
			lastCoinValue = IMPOSSIBLE_COIN_VALUE;
			mass = Double.MAX_VALUE;
		}

		DpState(int coinValue, double mass) {
			this.lastCoinValue = coinValue;
			this.mass = mass;
		}

		boolean isValid() {
			return lastCoinValue != IMPOSSIBLE_COIN_VALUE;
		}
	}
}

package midatlantic.year2005;

import java.awt.Point;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Brute Force Search?! ugly code~~~ Next time treat 4 directions equally (no
 * horizontal/vertical; no reverse)
 * 
 * @author rex
 * 
 * @date 04/05/2013
 */
public class PackingDominoes_B {

	private static final int EMPTY = -1;
	private int[][] myBoard;
	private int myWidth;
	private int myHeight;
	private Domino[] myDominoes;

	private boolean solve(int K, int width, int height) {
		myWidth = width;
		myHeight = height;
		if (K < 0)
			return false;
		System.out.println(K + " " + myWidth + " " + myHeight);
		if ((K + 1) * (K + 2) / 2 > myWidth * myHeight) {
			System.out.println("No packing is possible");
			return true;
		}
		myBoard = new int[myWidth][myHeight];
		for (int[] rows : myBoard)
			Arrays.fill(rows, EMPTY);
		myDominoes = createDominoes(K + 1);
		if (dfs(0)) {
			for (Domino domino : myDominoes)
				System.out.println(domino);
		} else {
			System.out.println("No packing is possible");
		}
		return true;
	}

	private Domino[] createDominoes(int numDominoes) {
		Domino[] dominoes = new Domino[numDominoes * (numDominoes + 1) / 2];
		int index = 0;
		// without loss of generality, let the first number be the smaller one
		for (int i = 0; i < numDominoes; i++)
			for (int j = i; j < numDominoes; j++) {
				dominoes[index] = new Domino(i, j);
				index++;
			}
		return dominoes;
	}

	private boolean dfs(int depth) {
		if (depth == myDominoes.length)
			return true;
		for (int i = 0; i < myWidth; i++)
			for (int j = 0; j < myHeight; j++) {
				if (checkPositions(depth, i, j))
					return true;
				myDominoes[depth].reverse();
				if (checkPositions(depth, i, j))
					return true;
				myDominoes[depth].reverse();
			}
		return false;
	}

	private boolean checkPositions(int depth, int i, int j) {
		Point position = new Point(i, j);
		if (canBePlacedAt(myDominoes[depth], position, true)) {
			myBoard[i][j] = myDominoes[depth].number1;
			myBoard[i + 1][j] = myDominoes[depth].number2;
			myDominoes[depth].place(position, true);
			if (dfs(depth + 1))
				return true;
			myBoard[i][j] = EMPTY;
			myBoard[i + 1][j] = EMPTY;
		}
		// vertical
		if (canBePlacedAt(myDominoes[depth], position, false)) {
			myBoard[i][j] = myDominoes[depth].number1;
			myBoard[i][j + 1] = myDominoes[depth].number2;
			myDominoes[depth].place(position, false);
			if (dfs(depth + 1))
				return true;
			myBoard[i][j] = EMPTY;
			myBoard[i][j + 1] = EMPTY;
		}
		return false;
	}

	/**
	 * check if a domino can be placed at this position isHorizontal = true:
	 * place domino at (x, y) and (x + 1, y) isHorizontal = false: place domino
	 * at (x, y) and (x, y + 1)
	 * 
	 * @param domino
	 *            the domino to be placed
	 * @param position
	 *            it is placed at (always the Point with smaller x or y value)
	 * @param isHorizontal
	 * @return true if this domino can be placed here
	 */
	private boolean canBePlacedAt(Domino domino, Point position, boolean isHorizontal) {
		int x = position.x;
		int y = position.y;
		if (myBoard[x][y] != EMPTY)
			return false;
		if (!noDifferentNeighbor(position.x, position.y, domino.number1, isHorizontal))
			return false;
		if (isHorizontal) {
			if (x + 1 >= myWidth)
				return false;
			if (myBoard[x + 1][y] != EMPTY)
				return false;
			if (noDifferentNeighbor(position.x + 1, position.y, domino.number2, isHorizontal))
				return true;
			else
				return false;
		} else {
			if (y + 1 >= myHeight)
				return false;
			if (myBoard[x][y + 1] != EMPTY)
				return false;
			if (noDifferentNeighbor(position.x, position.y + 1, domino.number2, isHorizontal))
				return true;
			else
				return false;
		}
	}

	private boolean noDifferentNeighbor(int x, int y, int number, boolean isHorizontal) {
		if (!sameNumberOnBoard(x - 1, y, number))
			return false;
		if (!sameNumberOnBoard(x + 1, y, number))
			return false;
		if (!sameNumberOnBoard(x, y - 1, number))
			return false;
		if (!sameNumberOnBoard(x, y + 1, number))
			return false;
		return true;
	}

	private boolean sameNumberOnBoard(int x, int y, int number) {
		// out of bound
		if ((x >= myWidth) || (y >= myHeight) || (x < 0) || (y < 0))
			return true;
		// equal to number other than EMPTY or number specified
		if (!((myBoard[x][y] == EMPTY) || (myBoard[x][y] == number)))
			return false;
		return true;
	}

	public static void main(String[] args) {
		PackingDominoes_B problem = new PackingDominoes_B();
		Scanner scanner = new Scanner(System.in);
		int K, width, height;
		do {
			K = scanner.nextInt();
			width = scanner.nextInt();
			height = scanner.nextInt();
		} while (problem.solve(K, width, height));
	}
}

class Domino {
	int number1;
	int number2;
	Point position;
	boolean isHorizontal;
	boolean isReversed = false;

	Domino(int d1, int d2) {
		position = new Point(-1, -1);
		number1 = d1;
		number2 = d2;
	}

	void place(Point position, boolean isHorizontal) {
		this.position = position;
		this.isHorizontal = isHorizontal;
	}

	void reverse() {
		int temp = number1;
		number1 = number2;
		number2 = temp;
		isReversed = !isReversed;
	}
	
	int getFirstNumber() {
		if (isReversed)
			return number2;
		else 
			return number1;
	}
	
	int getSecondNumber() {
		if (isReversed)
			return number1;
		else 
			return number2;
	}
	
	Point getFirstPosition() {
		return position;
	}

	Point getSecondPosition() {
		if (isHorizontal)
			return new Point(position.x + 1, position.y);
		else
			return new Point(position.x, position.y + 1);
	}

	@Override
	public String toString() {
		if (!isReversed)
			return String.format("%d %d %d %d %d %d", number1, number2, getFirstPosition().x,
					getFirstPosition().y, getSecondPosition().x, getSecondPosition().y);
		else
			return String.format("%d %d %d %d %d %d", number2, number1, getSecondPosition().x,
					getSecondPosition().y, getFirstPosition().x, getFirstPosition().y);
	}
}
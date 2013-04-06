package topcoder.srm;

public class TheTilesDivTwo575 {
	
	private int numRows;
	private int numCols;
	private int maxPossible = 0;
	private int maxTiles = 0;
	private boolean[][] myBoard;
	
	public int find(String[] board)
	{
		numRows = board.length;
		numCols = board[0].length();
		if (numRows <= 1)
			return 0;
		myBoard = new boolean[numRows][numCols];
		for (int i = 0; i < numRows; i++)
			for (int j = 0; j < numCols; j++) {
				if (board[i].charAt(j) == 'X')
					myBoard[i][j] = false;
				else {
					myBoard[i][j] = true;
					if ((i + j) % 2 == 0)
						maxPossible++;
				}
			}
		dfs(0, board);
		return maxTiles;
	}
	
	public void dfs(int depth, String[] board) {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				if ((i + j) % 2 == 1)
					continue;
				if ((i > 0) && (j > 0))
					if ((myBoard[i - 1][j]) && (myBoard[i][j]) && (myBoard[i][j - 1])) {
						myBoard[i][j] = false;
						myBoard[i - 1][j] = false;
						myBoard[i][j - 1] = false;
						dfs(depth + 1, board);
						myBoard[i][j] = true;
						myBoard[i - 1][j] = true;
						myBoard[i][j - 1] = true;
					}
				if ((i > 0) && (j < numCols - 1))
					if ((myBoard[i - 1][j]) && (myBoard[i][j]) && (myBoard[i][j + 1])) {
						myBoard[i][j] = false;
						myBoard[i - 1][j] = false;
						myBoard[i][j + 1] = false;
						dfs(depth + 1, board);
						myBoard[i][j] = true;
						myBoard[i - 1][j] = true;
						myBoard[i][j + 1] = true;
					}
				if ((i < numRows - 1) && (j < numCols - 1))
					if ((myBoard[i + 1][j]) && (myBoard[i][j]) && (myBoard[i][j + 1])) {
						myBoard[i][j] = false;
						myBoard[i + 1][j] = false;
						myBoard[i][j + 1] = false;
						dfs(depth + 1, board);
						myBoard[i][j] = true;
						myBoard[i + 1][j] = true;
						myBoard[i][j + 1] = true;
					}
				if ((i < numRows - 1) && (j > 0))
					if ((myBoard[i + 1][j]) && (myBoard[i][j]) && (myBoard[i][j - 1])) {
						myBoard[i][j] = false;
						myBoard[i + 1][j] = false;
						myBoard[i][j - 1] = false;
						dfs(depth + 1, board);
						myBoard[i][j] = true;
						myBoard[i + 1][j] = true;
						myBoard[i][j - 1] = true;
					}
			}
		}
		if (depth > maxTiles)
			maxTiles = depth;
	}
	
	public static void main(String[] args) {
		TheTilesDivTwo575 problem = new TheTilesDivTwo575();
		String[] board = new String[3];
		board[0] = "X.X";
		board[1] = "...";
		board[2] = "X.X";
		System.out.println(problem.find(board));
	}
}

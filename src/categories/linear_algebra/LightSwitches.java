package categories.linear_algebra;

import info.Source;

@Source ("Topcoder srm 306")
/**
 * A'x = c
 * All possible combinations of c are in the span of the column space of A.
 * 2^rank of A
 * @author Rex
 *
 */
public class LightSwitches {
	
	public long countPossibleConfigurations(String[] switches) {
		// own matrix: cor[i][j] is the correlation between bulb i and switch j
		// 1 indicates effective; 0 indicates non-effective
		int[][] cor = new int[switches[0].length()][switches.length];
		for (int i = 0; i < switches.length; i++) {
			for (int j = 0; j < switches[i].length(); j++)
				if (switches[i].charAt(j) == 'Y')
					cor[j][i] = 1;
		}
		int rank = findRankBinary(cor);
			
		// this following line is nonsense...
		//return (long) (Math.pow(2, rank)); 
		return 1L << rank;
	}
	
	/**
	 * Find rank of a matrix in the field Z/2Z (the field of integer mod 2) 
	 * @param matrix matrix represented by 2-dim array
	 * @return rank
	 */
	public int findRankBinary(int[][] matrix) {
		int n = matrix.length;
		int m = matrix[0].length;
		// number of linearly independent row vector so far
		int row = 0;
		for (int i = 0; i < m; i++) {
			// for every column, scan the entire column
			int max = 0;
			int maxRow = 0;
			for (int j = row; j < n; j++) {
				if (max < matrix[j][i]) {
					max = matrix[j][i];
					maxRow = j;
					break; // because it's binary
				}
			}
			if (max == 0)
				continue;
			// swap such that matrix[row] is another linearly independent vector with
			// the ith entry non-zero
			int[] temp = matrix[maxRow];
			matrix[maxRow] = matrix[row];
			matrix[row] = temp;
			
			// subtract multiple of ith row so that all other rows have the ith entry 0
			// since its binary, the non-zero element is always 1
			for (int j = row + 1; j < n; j++) {
				if (matrix[j][i] != 0) {
					for (int k = i; k < m; k++) {
						matrix[j][k] = matrix[j][k] - matrix[row][k];
						// mod 2
						if (matrix[j][k] == -1)
							matrix[j][k] = 1;
					}
				}
			}
			row++;
			if (row == n)
				break;
		}
		// row = rank now
		return row;
	}

	public static void main(String[] args) {
		//String[] switches = {"YYN", "NNY", "YYY", "NNN"};	
		//String[] switches = {"NNYYYN", "YYYYNY", "NNNNYN", "YYNNYY", "NNYNNN", "NYYNYN", "YNYNYN"};
		String[] switches = {"NNNNNNNNNNNNN", "NNNNNNNNNNNNYN", "NNNNNNNNNNNNNN"};
		System.out.println(new LightSwitches().countPossibleConfigurations(switches));
	}
}

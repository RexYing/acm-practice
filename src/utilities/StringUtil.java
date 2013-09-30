package utilities;

public class StringUtil {

	public static int[] partialMatch(char[] arr) {
		int[] table = new int[arr.length];
		table[0] = -1;
		int fallback = 0;
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] == arr[fallback]) {
				table[i] = fallback;
				fallback++;
			} else {
				table[i] = fallback;
				fallback = 0;
			}
		}
		return table;
	}

	/**
	 * KMP String matching
	 * Good choice if we want to search for the same pattern 
	 * repeatedly in many different texts
	 * 
	 * @return the index of the first match (-1 if not found)
	 */
	public static int KMP(char[] text, char[] match) {
		int[] table = partialMatch(text);
		int currentInd = 0;
		int matchInd = 0;
		while (currentInd < text.length) {
			if (text[currentInd] == match[matchInd]) {
				currentInd++;
				matchInd++;
				if (matchInd == match.length)
					return currentInd - matchInd;
			}
			else {
				currentInd -= table[matchInd];
				matchInd = 0;
			}
		}
		return -1;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO BM string search; Sunday algorithm; Suffix tree

	}

}

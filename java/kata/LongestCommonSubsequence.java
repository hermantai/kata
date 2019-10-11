package kata;

import java.util.*;

/**
 *
 */
public class LongestCommonSubsequence {
  static int[] longestCommonSubsequence(int[] ar1, int[] ar2) {
    int m = ar1.length;
    int n = ar2.length;
    // table[i][j] means the LCS with i of ar1 chars and j of ar2 chars.
    int[][] table = new int[m + 1][n + 1];

    for (int i = 0; i < m + 1; i++) {
      for (int j = 0; j < n + 1; j++) {
        if (i == 0 || j == 0) {
          table[i][j] = 0;
          continue;
        }

        if (ar1[i - 1] == ar2[j - 1]) {
          table[i][j] = 1 + table[i - 1][j - 1];
        } else {
          table[i][j] = Math.max(table[i - 1][j], table[i][j - 1]);
        }
      }
    }

    List<Integer> reversedCommonSeq = new ArrayList<>();
    int i = m;
    int j = n;
    int len = table[m][n];
    while (len > 0) {
      while (i - 1 >= 0 && table[i - 1][j] == len) {
        i--;
      }
      while (j - 1 >= 0 && table[i][j - 1] == len) {
        j--;
      }
      reversedCommonSeq.add(ar1[i - 1]);
      len--;
    }
    Collections.reverse(reversedCommonSeq);

    return reversedCommonSeq.stream().mapToInt(k -> k).toArray();
  }

  public static void main(String args[]) {
    runSample(
        new int[]{3, 4, 6, 10, 13, 18, 20},
        new int[]{2, 4, 9, 13, 18, 20, 24}
    );
  }

  static void runSample(int[] ar1, int[] ar2) {
    System.out.printf(
        "%s,%s = %s\n",
        Arrays.toString(ar1),
        Arrays.toString(ar2),
        Arrays.toString(longestCommonSubsequence(ar1, ar2)));
  }
}

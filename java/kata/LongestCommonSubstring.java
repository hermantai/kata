package kata;

import java.util.*;

/**
 * Cracking the coding interview 6th ed. p.XX(TODO)
 */
public class LongestCommonSubstring {
  static String longestCommonSubstring(String s1, String s2) {
    int m = s1.length();
    int n = s2.length();

    if (m == 0 || n == 0) {
      return "";
    }
    // table[i][j]: the maximum length of substrings between s1 with i chars and s2 with s2 chars
    int[][] table = new int[m+1][n+1];

    int max = 0;
    int start = 0;
    int end = 0;
    for (int i = 0; i < m + 1; i++) {
      for (int j = 0; j < n + 1; j++) {
        if (i == 0 || j == 0) {
          // = 0
          continue;
        }
        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
          table[i][j] = table[i - 1][j - 1] + 1;
          if (table[i][j] > max) {
            max = table[i][j];
            start = i - max;
            end = i;
          }
        } else {
          table[i][j] = 0;
        }
      }
    }

    // get the string back

    return s1.substring(start, end);
  }

  public static void main(String args[]) {
    runSample("ababcde", "aba");
    runSample("ababababc", "babc");
    runSample("abababdefghikababab", "defghik");
    runSample("", "defghik");
  }

  static void runSample(String s1, String s2) {
    System.out.printf("%s,%s = %s\n", s1, s2, longestCommonSubstring(s1, s2));
  }
}

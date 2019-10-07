package kata;

import java.util.*;

/**
 * Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*'.
 *
 * <ul>
 * <li>'.' Matches any single character.
 * <li>'*' Matches zero or more of the preceding element.
 * </ul>
 *
 * <p>The matching should cover the entire input string (not partial).
 * <p>https://leetcode.com/problems/regular-expression-matching/solution/
 * <p>Leetcode solution is basically recursion with memoization, which is not
 * really a dp. I like a real dp approach better because it does not have
 * the large stack space needed.
 */
public class RegexMatching {
  static boolean debugThis(String s, String p) {
    //return s.equals("aa") && p.equals("a*");
    return false;
  }

  static boolean regexMatching(String s, String p) {
		// Dynamic programming such that
    // matched[i][j] is true if s[i:] and p[i:] is a match. The answer is
    // at matched[i][j].
    int m = s.length();
    int n = p.length();
    boolean[][] matched = new boolean[m + 1][n + 1];

    for (int i = m; i >= 0; i--) {
      for (int j = n; j >= 0; j--) {
        if (i == m && j == n) {
          matched[i][j] = true;
          continue;
        }
        if (j == n) {
          matched[i][j] = false;
          continue;
        }

        if (i == m) {
          if (j == n-2 && p.charAt(n - 1) == '*') {
            // special case, s[i] is empty and p[n-2:] is "anything and *"
            matched[i][j] = true;
          } else {
            matched[i][j] = false;
          }
          continue;
        }

        char pchar = p.charAt(j);
        if (j < n - 1 && p.charAt(j+1) == '*') {
          // Either we take zero of p.charAt(j) or one of p.charAt(j)
          matched[i][j] = matched[i][j+2] || (matchedChar(s.charAt(i), pchar) && matched[i+1][j]);
          continue;
        }

        if (pchar == '*') {
          // This is false becasue a leading '*' in pattern is invalid.
          // The condition above should make sure this does not happen.
          matched[i][j] = false;
        } else if (matchedChar(s.charAt(i), pchar)) {
          matched[i][j] = matched[i+1][j+1];
        } else {
          matched[i][j] = false;
        }
      }
    }

    return matched[0][0];
  }

  static boolean matchedChar(char a, char b) {
    return a == b || b == '.';
  }

  public static void main(String args[]) {
    runSample("aa", "a"); // false
    runSample("aa", "a*"); // true
    runSample("ab", ".*"); // true
    runSample("aab", "c*a*b"); // true
    runSample("mississippi", "mis*is*p*."); // false
  }

  static void runSample(String s, String p) {
    System.out.printf("%s,%s = %s\n", s, p, regexMatching(s, p));
  }
}

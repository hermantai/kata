package kata;

import java.util.*;

/**
 * https://leetcode.com/problems/wildcard-matching/
 *
 * <p>Given an input string (s) and a pattern (p), implement wildcard pattern
 * matching with support for '?' and '*'.
 * '?' Matches any single character.
 * '*' Matches any sequence of characters (including the empty sequence).
 * The matching should cover the entire input string (not partial).
 */
public class PatternMatching {
  static boolean patternMatching(String s, String p) {

    int m = s.length();
    int n = p.length();
    // matched[i][j] = s[i...m] and p[j...n] matched
    boolean[][] matched = new boolean[m+1][n+1];

    for (int i = m; i >= 0; i--) {
      for (int j = n; j >=0; j--) {
        if (i == m) {
          if (j == n) {
            matched[i][j] = true;
            continue;
          }
          if (p.charAt(j) == '*') {
            matched[i][j] = matched[i][j + 1];
            continue;
          }
          // matched[i][j] = false
          continue;
        }
        if (j == n) {
          // matched[i][j] = false
          continue;
        }

        char schar = s.charAt(i);
        char pchar = p.charAt(j);
        if (pchar == '*') {
          // either * takes no characters or it takes at least one character
          matched[i][j] = matched[i][j + 1] || matched[i + 1][j];
        } else if (schar == pchar || pchar == '?') {
          matched[i][j] = matched[i + 1][j + 1];
        } else {
          matched[i][j] = false;
        }
      }
    }
    return matched[0][0];
  }

  public static void main(String args[]) {
    runSample("aa", "a"); // false
    runSample("aa", "*"); // true
    runSample("cb", "?a"); // false
    runSample("aa", "?*"); // true
    runSample("aa", "*a"); // true
    runSample("ab", "**"); // true
    runSample("ab", "??"); // true
    runSample("abc", "a?c"); // true
    runSample("abceb", "*a*b"); // true
    runSample("acdcb", "a*c?b"); // false
  }

  static void runSample(String s, String p) {
    System.out.printf("%s, %s = %s\n", s, p, patternMatching(s, p));
  }
}

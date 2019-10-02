package kata;

import java.util.Set;
import java.util.HashSet;

/**
 * https://leetcode.com/problems/longest-substring-without-repeating-characters
 */
public class LongestNonrepeatingSubstring {
  static int longestNonrepeatingSubstring(String str) {
    int s = 0;
    int e = 0;
    int n = str.length();

    int longestSoFar = 0;
    Set<Character> seen = new HashSet<>();
    while (s < n && e < n) {
      char c = str.charAt(e);
      if (!seen.contains(c)) {
        e++;
        seen.add(c);
        continue;
      }


      seen.remove(c);
      s++;
      longestSoFar = Math.max(e - s + 1, longestSoFar);
    }
    longestSoFar = Math.max(e - s, longestSoFar);

    return longestSoFar;
  }

  public static void main(String args[]) {
    runSample("abcabcdd");
    runSample("abcabca");
    runSample("aaa");
    runSample("aaa");
    runSample("abc");
    runSample("dabab");
  }

  public static void runSample(String s) {
    System.out.printf("%s = %s\n", s, longestNonrepeatingSubstring(s));
  }
}

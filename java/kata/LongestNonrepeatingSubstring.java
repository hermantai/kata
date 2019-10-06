package kata;

import java.util.Set;
import java.util.HashSet;

/**
 * https://leetcode.com/problems/longest-substring-without-repeating-characters
 * <p>Sliding window
 */
public class LongestNonrepeatingSubstring {
  static int longestNonrepeatingSubstring(String str) {
    int s = 0;
    int e = 0;
    int n = str.length();

    int longestSoFar = 0;
    Set<Character> seen = new HashSet<>();
    while (e < n) {
      // str[s]...str[e -1] are non-repeating. Check str[e].
      char c = str.charAt(e);
      if (!seen.contains(c)) {
        e++;
        seen.add(c);
        longestSoFar = Math.max(e - s, longestSoFar);
        continue;
      }

      seen.remove(c);
      s++;
    }

    return longestSoFar;
  }

  public static void main(String args[]) {
    runSample("abcabcdd");
    runSample("abcabca");
    runSample("aaa");
    runSample("abc");
    runSample("dabab");
  }

  public static void runSample(String s) {
    System.out.printf("%s = %s\n", s, longestNonrepeatingSubstring(s));
  }
}

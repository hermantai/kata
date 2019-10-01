package kata;

import java.util.Set;
import java.util.HashSet;

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


      longestSoFar = Math.max(e - s, longestSoFar);
      seen.remove(c);
      s++;
    }
    longestSoFar = Math.max(e - s, longestSoFar);

    return longestSoFar;
  }

  public static void main(String args[]) {
    runSample("abcabcdd");
    runSample("aaa");
    runSample("abc");
    runSample("dabab");
  }

  public static void runSample(String s) {
    System.out.printf("%s = %s\n", s, longestNonrepeatingSubstring(s));
  }
}

package kata;

import java.util.*;

/**
 * https://leetcode.com/problems/minimum-window-substring
 *
 * <p>Given two strings s and p, return the shortest substring in s such that
 * it has all characters in p.
 */
public class MinimumWindowSubstring {
  static int[] minimumWindowSubstring(String s, String p) {
    int m = s.length();
    int n = p.length();

    if (n == 0) {
      return new int[]{};
    }
    
    if (m < n) {
      return null;
    }

    char[] schars = s.toCharArray();
    char[] pchars = p.toCharArray();

    Map<Character, Integer> pCharsCounts = new HashMap<>();
    for (char c : pchars) {
      pCharsCounts.put(c, pCharsCounts.getOrDefault(c, 0) + 1);
    }
    int required = pCharsCounts.size();

    Map<Character, Integer> sCharsCounts = new HashMap<>();

    int left = 0;
    int right = 0;
    int start = -1;
    int end = -1;
    int shortest = Integer.MAX_VALUE;
    // System.out.printf("pCharsCounts=%s, required=%s\n", pCharsCounts, required);
    while (right < m) {
      char c = schars[right];

      if (!pCharsCounts.containsKey(c)) {
        right++;
        continue;
      }

      int newCount = sCharsCounts.getOrDefault(c, 0) + 1;
      sCharsCounts.put(c, newCount);
      // System.out.printf("sCharsCounts=%s\n", sCharsCounts);
      if (newCount == pCharsCounts.get(c)) {
        required--;
      }

      while (required == 0 && left <= right) {
        int len = right - left + 1;
        if (len < shortest) {
          shortest = len;
          start = left;
          end = right;
        }

        char oldC = schars[left];
        left++;
        if (!pCharsCounts.containsKey(oldC)) {
          continue;
        }

        newCount = sCharsCounts.get(oldC) - 1;
        sCharsCounts.put(oldC, newCount);
        if (newCount < pCharsCounts.get(oldC)) {
          required++;
          break;
        }
      }
      right++;
    }

    if (start == -1) {
      // no substrings in s can satisfy p
      return null;
    }
    return new int[]{start, end};
  }

  public static void main(String args[]) {
    runSample("abaacbab", "abc");
  }

  static void runSample(String s, String p) {
    System.out.printf("%s,%s = %s\n", s, p, Arrays.toString(minimumWindowSubstring(s, p)));
  }
}

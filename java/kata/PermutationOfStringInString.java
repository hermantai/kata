package kata;

import java.util.*;

/**
 * Cracking the coding interview 6th ed. p.70
 * Given a smaller string 5 and a bigger string b, design an algorithm to
 * find all permutations of the shorter string within the longer one. Print the
 * location of each permutation. 
 *
 * <p>Sliding window.
 */
public class PermutationOfStringInString {
  static void permutationOfStringInString(String smallStr, String bigStr) {
    int n1 = smallStr.length();
    int n2 = bigStr.length();
    if (n2 < n1) {
      return;
    }

    Map<Character, Integer> smallStrCounts = getCharsCount(smallStr);

    int s = 0;
    int e = 0;
    for (;e < n1; e++) {
      char c = bigStr.charAt(e);
      if (!smallStrCounts.containsKey(c)) {
        continue;
      }
      int count = smallStrCounts.get(c);
      count--;
      smallStrCounts.put(c, count);
    }

    if (allZeros(smallStrCounts)) {
      System.out.printf("%s,", s);
    }
    for (;e<n2;e++, s++) {
      // bigStr[0..e-1] are taken care of, adjust the smallStrCounts with
      // bigStr[s] and bigStr[e], then check if the result is all zeros before
      // going to the next loop.
      char c = bigStr.charAt(e);
      if (smallStrCounts.containsKey(c)) {
        int count = smallStrCounts.get(c);
        count--;
        smallStrCounts.put(c, count);
      }

      c = bigStr.charAt(s);
      if (smallStrCounts.containsKey(c)) {
        int count = smallStrCounts.get(c);
        count++;
        smallStrCounts.put(c, count);
      }
      if (allZeros(smallStrCounts)) {
        System.out.printf("%s %s,", s+1, bigStr.substring(s+1, s+1+n1));
      }
    }
    System.out.println();
  }

  static Map<Character, Integer> getCharsCount(String s) {
    Map<Character, Integer> counts = new HashMap<>();
    for (char c: s.toCharArray()) {
      int count = counts.getOrDefault(c, 0);
      counts.put(c, count + 1);
    }

    return counts;
  }

  static boolean allZeros(Map<Character, Integer> counts) {
    for (int c : counts.values()) {
      if (c != 0) {
        return false;
      }
    }
    return true;
  }

  public static void main(String args[]) {
    runSample("abbc", "cbabadcbbabbcbabaabccbabc");
  }

  public static void runSample(String smallStr, String bigStr) {
    System.out.printf("%s, %s\n", smallStr, bigStr);
    permutationOfStringInString(smallStr, bigStr);
  }
}

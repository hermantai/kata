package kata;

import java.util.*;

/**
 * Find sub-strings using a suffix array.
 * 
 * For example, if our input string is "BANANA", we'd make an array like {0, 1,
2, 3, 4, 5}, then sort it by the suffixes to get:
 * 
 * {
 *   5, // A
 *   3, // ANA
 *   1, // ANANA
 *   0, // BANANA
 *   4, // NA
 *   2, // NANA
 * };
 * 
 * Implement:
 * 
 * // find the index of s that contains substr
 * 
 * int find(String s, int[] suffixArray, String substr);
 * 
 * For example (given to the candidate as well), find("banana", {...}, "nan") returns 2, because "banana".substring(2) = "nana", that contains "nan".
 *
 * Extension: find the range
 */
public class FindInSuffixArray {
  static int findInSuffixArray(String s, int[] suffixArray, String substr) {
    if (substr.length() >= s.length()) {
      return -1;
    }

    char[] schars = s.toCharArray();
    char[] pchars = substr.toCharArray();
    int left = 0;
    int right = suffixArray.length;
    // find the result left <= result <= right
    while (left <= right) {
      int mid = left + (right - left) / 2;

      int res = compare(schars, suffixArray[mid], pchars);
      if (res == -1) {
        right = mid - 1;
      } else if (res == 1) {
        left = mid + 1;
      } else {
        return suffixArray[mid];
      }
    }
    return -1;
  }

  // substr is before (-1), a substring of (0) or later (1) than s
  static int compare(char[] schars, int p, char[] pchars) {
    int len;
    if (schars.length - p < pchars.length) {
      len = schars.length - p;
    } else {
      len = pchars.length;
    }

    for (int i = 0; i < len; i++) {
      if (schars[p + i] > pchars[i]) {
        return -1;
      }
      if (schars[p + i] < pchars[i]) {
        return 1;
      }
    }

    if (len == pchars.length) {
      return 0;
    }
    // suffix is shorter than substr (pchars)
    return -1;
  }

  public static void main(String args[]) {
    runSample("banana", new int[]{5, 3, 1, 0, 4, 2}, "nan");
    runSample("banana", new int[]{5, 3, 1, 0, 4, 2}, "nanad");
    runSample("banana", new int[]{5, 3, 1, 0, 4, 2}, "an");
    runSample("banana", new int[]{5, 3, 1, 0, 4, 2}, "ab");
  }

  static void runSample(String s, int[] suffixArray, String substr) {
    System.out.printf("%s,%s,%s = %s\n", s, Arrays.toString(suffixArray), substr, findInSuffixArray(s, suffixArray, substr));
  }
}

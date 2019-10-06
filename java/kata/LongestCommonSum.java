package kata;

import java.util.*;

/**
 * Longest Span with same Sum in two Binary arrays.
 * <p> Given two binary arrays arr1[] and arr2[] of same size n. Find length of the
 * longest common span (i, j) where j >= i such that arr1[i] + arr1[i+1] + …. +
 * arr1[j] = arr2[i] + arr2[i+1] + …. + arr2[j].
 *
 * <p> https://www.geeksforgeeks.org/longest-span-sum-two-binary-arrays/
 */
public class LongestCommonSum {
  static int longestCommonSum(int[] ar1, int[] ar2) {
    int prefixsum1 = 0;
    int prefixsum2 = 0;

    int maxLength = 0;
    Map<Integer, Integer> diffsIndexes = new HashMap<>();

    for (int i = 0; i < ar1.length; i++) {
      prefixsum1 += ar1[i];
      prefixsum2 += ar2[i];

      int diff = prefixsum1 - prefixsum2;
      if (diff == 0) {
        maxLength = i + 1;
      } else if (diffsIndexes.containsKey(diff)) {
        maxLength = Math.max(maxLength, i - diffsIndexes.get(diff));
      } else {
        diffsIndexes.put(diff, i);
      }
    }
    return maxLength;
  }

  public static void main(String args[]) {
    runSample(new int[]{0, 1, 0, 0, 0, 0}, new int[]{1, 0, 1, 0, 0, 1});  // 4
    runSample(new int[]{0, 1, 0, 1, 1, 1, 1}, new int[]{1, 1, 1, 1, 1, 0, 1});  // 6
    runSample(new int[]{0, 0, 0}, new int[]{1, 1, 1});  // 0
    runSample(new int[]{0, 0, 1, 0}, new int[]{1, 1, 1, 1});  // 1
  }

  static void runSample(int[] ar1, int[] ar2) {
    System.out.printf("%s, %s = %s\n", Arrays.toString(ar1), Arrays.toString(ar2), longestCommonSum(ar1, ar2));
  }
}

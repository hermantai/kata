package kata;

import java.util.*;

/**
 * Median of two sorted arrays.
 *
 * <p>https://leetcode.com/problems/median-of-two-sorted-arrays/solution/
 */
public class MedianOfTwoSortedArrays {
  static double medianOfTwoSortedArrays(int[] ar1, int[] ar2) {
    int m = ar1.length;
    int n = ar2.length;
    if (m > n) {
      int[] tmp = ar1;
      ar1 = ar2;
      ar2 = tmp;
      int t = m;
      m = n;
      n = t;
    }
    int half = (m + n + 1) / 2;

    int left = 0;
    int right = m;
    while (left <= right) {
      int a = left + (right - left) / 2;
      int b = half - a;

      if (a > left && ar1[a - 1] > ar2[b]) {
        // a is too large, we took too many ar1
        right = a - 1;
      } else if (a < right && ar2[b - 1] > ar1[a]) {
        // a is too small, we took not enough ar1
        left = a + 1;
      } else {
        // we take the correct number of ar1 and ar2, we then need to find out
        // what the median is. It's done by figuring out the maxLeft and
        // minRight, and maxLeft is the answer if odd number of items.
        // Otherwise, it's (maxLeft + minRight) / 2.0.
        int maxLeft = 0;
        if (a == 0) {
          maxLeft = ar2[b - 1];
        } else if (b == 0) {
          maxLeft = ar1[a - 1];
        } else {
          maxLeft = Math.max(ar1[a - 1], ar2[b - 1]);
        }

        if ((m + n) % 2 == 1) {
          return maxLeft;
        }

        int minRight = 0;
        if (a == m) {
          minRight = ar2[b];
        } else if (b == n) {
          minRight = ar1[a];
        } else {
          minRight = Math.min(ar1[a], ar2[b]);
        }

        return (maxLeft + minRight) / 2.0;
      }
    }
    return 0;
  }

  public static void main(String args[]) {
    runSample(new int[]{2, 7, 12}, new int[]{5, 9, 14, 18, 20}); // 10.5
    runSample(new int[]{2, 7, 12, 17}, new int[]{5, 9, 14, 18, 20}); // 12
    runSample(new int[]{2, 7, 12, 15, 17}, new int[]{23, 27}); // 15
    runSample(new int[]{2, 7, 12}, new int[]{13, 15, 17, 19, 23, 27}); // 15
    runSample(new int[]{2, 7, 12, 17}, new int[]{25, 29, 34, 38}); // 21
  }

  static void runSample(int[] ar1, int[] ar2) {
    System.out.printf("%s, %s = %s\n", Arrays.toString(ar1), Arrays.toString(ar2), medianOfTwoSortedArrays(ar1, ar2));
  }
}

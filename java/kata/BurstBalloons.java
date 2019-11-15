package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * https://leetcode.com/problems/burst-balloons/
 *
 * <p>Given n balloons, indexed from 0 to n-1. Each balloon is painted with a
 * number on it represented by array nums. You are asked to burst all the
 * balloons. If the you burst balloon i you will get nums[left] * nums[i] *
 * nums[right] coins. Here left and right are adjacent indices of i. After the
 * burst, the left and right then becomes adjacent.
 *
 * Find the maximum coins you can collect by bursting the balloons wisely.
 */
public class BurstBalloons {
  static int burstBalloons(int[] ar) {
    int n = ar.length + 2;
    // avoid the edge cases on the left and right
    int[] paddedAr = new int[n];
    System.arraycopy(ar, 0, paddedAr, 1, ar.length);
    paddedAr[0] = paddedAr[n - 1] = 1;

    int[][] memo = new int[n][n];

    return recurse(paddedAr, 0, n - 1, memo);
  }

  static int recurse(int[] ar, int left, int right, int[][] memo) {
    if (left + 1 >= right) {
      return 0;
    }
    if (memo[left][right] != 0) {
      return memo[left][right];
    }

    int max = 0;
    // only care about internal ballons
    for (int i = left + 1; i < right; i++) {
      max = Math.max(
          max,
          ar[left] * ar[i] * ar[right] + recurse(ar, left, i, memo) + recurse(ar, i, right, memo));
    }
    memo[left][right] = max;
    System.out.printf("left=%s, right=%s, = %s\n", left, right, max);

    return max;
  }

  public static void main(String args[]) {
    // runSample(new int[]{3, 1, 5, 8}); // 167
    runSample(new int[]{2, 3, 4}); // 36
  }

  static void runSample(int[] ar) {
    System.out.printf("%s = %s\n", Arrays.toString(ar), burstBalloons(ar));
  }
}

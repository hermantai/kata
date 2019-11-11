package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * https://leetcode.com/problems/split-array-largest-sum/
 *
 * <p>Given an array which consists of non-negative integers and an integer m,
 * you can split the array into m non-empty continuous subarrays. Write an
 * algorithm to minimize the largest sum among these m subarrays.
 *
 * <p>Solved by dynamic programming.
 */
public class SplitArrayLargestSum {
  static int splitArrayLargestSum(int[] ar, int m) {

    int n = ar.length;
    int[] prefixSum = new int[n + 1];
    for (int i = 0; i < n; i++) {
      prefixSum[i+1] = prefixSum[i] + ar[i];
    }

    // mat[i][j] = The min of the largest sum of j sub-arrays for ar with size i
    int[][] mat = new int[n+1][m+1];

		for (int i = 0; i <= n; i++) {
      for (int j = 0; j <= m; j++) {
				mat[i][j] = Integer.MAX_VALUE;
			}
		}
    mat[0][0] = 0;

    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= m; j++) {
        for (int k = j - 1; k < i; k++)  {
          // try ar[k] + ar[k+1]...ar[i-1] (sum of the j'th subarray) for all k
          // < i, so we can calculate find out the min.
          mat[i][j] = Math.min(mat[i][j], Math.max(mat[k][j - 1], prefixSum[i] - prefixSum[k]));
        }
      }
    }
    return mat[n][m];
  }

  public static void main(String args[]) {
    runSample(new int[]{7,2,5,10,8}, 2);
  }

  static void runSample(int[] ar, int k) {
    System.out.printf("%s,%s = %s\n", Arrays.toString(ar), k, splitArrayLargestSum(ar, k));
  }
}

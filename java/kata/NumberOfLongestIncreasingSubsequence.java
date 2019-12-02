package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * Given an unsorted array of integers, find the number of longest increasing subsequence
 * and also the length of those subsequences.
 *
 * <p>https://leetcode.com/problems/number-of-longest-increasing-subsequence/
 */
public class NumberOfLongestIncreasingSubsequence {
  static int[] numberOfLongestIncreasingSubsequence(int[] nums) {
    int n = nums.length;
    int[] longests = new int[n];
    int[] counts = new int[n];

    Arrays.fill(longests, 1);
    Arrays.fill(counts, 1);

    for (int i = 1; i < n; i++) {
      for (int j = 0; j < i; j++) {
        if (nums[i] > nums[j]) {
          // nums[i] can be placed afer nums[j] to form a longer sequence
          if (longests[j] >= longests[i]) {
            // the length of the sequence will grow by 1
            longests[i] = longests[j] + 1;
            counts[i] = counts[j];
          } else if (longests[j] + 1 == longests[i]) {
            // we need to increase the counts of i by counts[j]
            counts[i] += counts[j];
          }
        }
      }
    }

    // ans[0] = length, ans[1] = count
    int[] ans = new int[2];
    for (int i = 0; i < n; i++) {
      if (longests[i] > ans[0]) {
        ans[0] = longests[i];
        ans[1] = counts[i];
      } else if (longests[i] == ans[0]) {
        ans[1] += counts[i];
      }
    }

    return ans;
  }

  public static void main(String args[]) {
    runSample(new int[]{1, 3, 5, 4, 7}, new int[]{4, 2});
    runSample(new int[]{2, 2, 2}, new int[]{1, 3});
    runSample(new int[]{1, 1, 2, 2}, new int[]{2, 4});
  }

  static void runSample(int[] nums, int[] ans) {
    System.out.printf(
        "%s = %s(%s)\n",
        Arrays.toString(nums),
        Arrays.toString(numberOfLongestIncreasingSubsequence(nums)),
        Arrays.toString(ans));
  }
}

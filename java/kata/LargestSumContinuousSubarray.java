package kata;

import java.util.*;

/**
 * https://www.geeksforgeeks.org/largest-sum-contiguous-subarray/
 *
 * Kadane’s Algorithm
 */
public class LargestSumContinuousSubarray {
  static Result largestSumContinuousSubarray(int[] nums) {
    Result result = new Result();

    int sumSoFar = 0;
    int start = 0;
    for (int i = 0; i < nums.length; i++) {
      sumSoFar += nums[i];
      if (sumSoFar < 0) {
        start = i + 1;
        sumSoFar = 0;
        continue;
      }
      if (sumSoFar > result.max) {
        result.max = sumSoFar;
        result.start = start;
        result.end = i;
      }
    }

    if (result.max < 0) {
      // all numbers are negative, so pick the least negative number
      result.max = nums[0];
      result.start = 0;
      result.end = 0;
      for (int i = 1; i < nums.length; i++) {
        int n = nums[i];
        if (n > result.max) {
          result.max = n;
          result.start = i;
          result.end = i;
        }
      }
    }

    return result;
  }

  public static void main(String args[]) {
    runSample(new int[]{-2,-3,4,-1,-2,1,5,-3});
    runSample(new int[]{-2,-3,-4,-1,-2,-1,-5,-3});
  }

  public static void runSample(int[] input) {
    Result result = largestSumContinuousSubarray(input);
    System.out.printf("%s = %s, %s\n", Arrays.toString(input), result.max,
        Arrays.toString(Arrays.copyOfRange(input, result.start, result.end+1)));
  }

  static class Result {
    int max = -1;
    int start = 0;
    int end = 0;
  }
}

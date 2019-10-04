package kata;

import java.util.*;

/**
 * https://www.geeksforgeeks.org/maximum-sum-rectangle-in-a-2d-matrix-dp-27/
 *
 * Kadane's algoritm applied to matrix.
 */
public class MaximumSumRectangleInMatrix {
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

  static MatrixResult maximumSumRectangleInMatrix(int[][] m) {
    MatrixResult result = new MatrixResult();
    int cols = m[0].length;
    int rows = m.length;

    for (int left = 0; left < cols; left++) {
      int[] temp = new int[rows];
      for (int r=0; r < rows; r++) {
        temp[r] = 0;
      }

      for (int right = left; right < cols; right++) {
        for (int r = 0; r < rows; r++) {
          temp[r] += m[r][right];
        }

        Result arrayResult = largestSumContinuousSubarray(temp);
        
        if (arrayResult.max > result.max) {
          result.max = arrayResult.max;
          result.r1 = arrayResult.start;
          result.r2 = arrayResult.end;
          result.c1 = left;
          result.c2 = right;
        }
      }
    }

    return result;
  }

  public static void main(String args[]) {
    runSample(new int[][]{
      {1, 2, -1, -4, -20},
      {-8, -3, 4, 2, 1},
      {3, 8, 10, 1, 3},
      {-4, -1, 1, 7, -6},
    });
  }

  static void runSample(int[][] m) {
    System.out.println("Given:");
    printMatrix(m, 0, 0, m.length - 1, m[0].length - 1);
    System.out.println("ans:");
    MatrixResult result = maximumSumRectangleInMatrix(m);
    printMatrix(m, result.r1, result.c1, result.r2, result.c2);
  }

  static void printMatrix(int[][] m, int r1, int c1, int r2, int c2) {
    for (int r = r1; r <= r2; r++) {
        System.out.println(Arrays.toString(Arrays.copyOfRange(m[r], c1, c2 + 1)));
    }
  }

  static class Result {
    int max = -1;
    int start = 0;
    int end = 0;
  }

  static class MatrixResult {
    int max = -1;
    int r1 = 0;
    int c1 = 0;
    int r2 = 0;
    int c2 = 0;
  }
}

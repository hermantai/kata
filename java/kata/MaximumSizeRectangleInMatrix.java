package kata;

import java.util.*;

/**
 * Maximum size rectangle binary sub-matrix with all 1s
 * <p>Given a binary matrix, find the maximum size rectangle binary-sub-matrix with all 1’s.
 * <p>https://www.geeksforgeeks.org/maximum-size-rectangle-binary-sub-matrix-1s/
 */
public class MaximumSizeRectangleInMatrix {

  static int maximumHistogram(int[] nums) {
    Stack<Pair> pending = new Stack<>();
    int maxArea = 0;

    int prev = 0;
    for (int i = 0; i < nums.length; i++) {
      int n = nums[i];
      if (prev < n) {
        // increasing
        System.out.printf("Push %s at %s\n", n, i);
        pending.push(new Pair(i, n));
      } else if (prev == n) {
        // same, do nothing because the previous one take care of it when 
        // poping.
      } else if (prev > n) {
        // decreasing
        while (!pending.isEmpty() && pending.peek().second > n) {
          Pair p = pending.pop();
          System.out.printf("Pop %s at %s, current index: %s\n", p.second, p.first, i);
          int left = p.first;
          int height = p.second;
          int right = i;
          maxArea = Math.max(maxArea, (right - left) * height);
          System.out.println("Max area: " + maxArea);
        }
        System.out.printf("Push %s at %s\n", n, i);
        pending.push(new Pair(i, n));
      }

      prev = n;
    }

    // take care of the rest of pending
    while (!pending.isEmpty()) {
      Pair p = pending.pop();
      System.out.printf("Pop %s at %s, length: %s\n", p.second, p.first, nums.length);
      int left = p.first;
      int height = p.second;
      maxArea = Math.max(maxArea, (nums.length - left) * height);
      System.out.println("Max area: " + maxArea);
    }

    return maxArea;
  }

  static int maximumSizeRectangleInMatrix(int[][] m) {
    // first, update the rows so at each row[r], we have a histogram that
    // has heights based on row[0..r-1]
    int cols = m[0].length;
    for (int r = 1; r < m.length; r++) {
      for (int c = 0; c < cols; c++) {
        if (m[r][c] == 1) {
          m[r][c] += m[r - 1][c];
        }
      }
    }

    int maxSize = 0;
    // Run maximumHistogram on each row
    for (int r = 0; r < m.length; r++) {
       maxSize = Math.max(maxSize, maximumHistogram(m[r]));
       System.out.println("-> Max size so far: " + maxSize);
    }
    return maxSize;
  }

  public static void main(String args[]) {
    runSample(new int[][]{
      {0, 1, 1, 0},
      {1, 1, 1, 1},
      {1, 1, 1, 1},
      {1, 1, 0, 0},
    });
  }

  static void runSample(int[][] m) {
    printMatrix(m, 0, 0, m.length - 1, m[0].length - 1);
    System.out.printf("%s\n", maximumSizeRectangleInMatrix(m));
  }

  static class Pair {
    int first;
    int second;
    Pair(int first, int second) {
      this.first= first;
      this.second = second;
    }
  }

  static void printMatrix(int[][] m, int r1, int c1, int r2, int c2) {
    for (int r = r1; r <= r2; r++) {
        System.out.println(Arrays.toString(Arrays.copyOfRange(m[r], c1, c2 + 1)));
    }
  }

}

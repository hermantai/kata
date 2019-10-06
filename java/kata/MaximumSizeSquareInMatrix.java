package kata;

import java.util.*;

/**
 * Maximum size square sub-matrix with all 1s
 * Given a binary matrix, find out the maximum size square sub-matrix with all 1s.
 * https://www.geeksforgeeks.org/maximum-size-sub-matrix-with-all-1s-in-a-binary-matrix/
 */
public class MaximumSizeSquareInMatrix {
  static int maximumSizeSquareInMatrix(int[][] m) {
    int rows = m.length;
    int cols = m[0].length;

    int[][] sizes = new int[rows][cols];
    System.arraycopy(m[0], 0, sizes[0], 0, cols);

    for (int r = 1; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        if (c == 0) {
          sizes[r][c] = m[r][c];
          continue;
        }

        if (m[r][c] == 0) {
          continue;
        }

        sizes[r][c] = Math.min(sizes[r-1][c-1], Math.min(sizes[r-1][c], sizes[r][c-1])) + 1;
      }
    }

    System.out.println("sizes matrix");
    printMatrix(sizes, 0, 0, rows-1, cols-1);

    int maxSoFar = 0;

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        maxSoFar = Math.max(maxSoFar, sizes[r][c]);
      }
    }

    return maxSoFar * maxSoFar;
  }

  public static void main(String args[]) {
    runSample(new int[][]{
      {0, 1, 1, 0, 1},
      {1, 1, 0, 1, 0},
      {0, 1, 1, 1, 0},
      {1, 1, 1, 1, 0},
      {1, 1, 1, 1, 1},
      {0, 0, 0, 0, 0},
    });
  }

  static void runSample(int[][] m) {
    printMatrix(m, 0, 0, m.length - 1, m[0].length - 1);
    System.out.printf("%s\n", maximumSizeSquareInMatrix(m));
  }


  static void printMatrix(int[][] m, int r1, int c1, int r2, int c2) {
    for (int r = r1; r <= r2; r++) {
        System.out.println(Arrays.toString(Arrays.copyOfRange(m[r], c1, c2 + 1)));
    }
  }
}

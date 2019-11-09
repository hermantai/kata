package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * https://leetcode.com/problems/falling-squares/
 *
 * <p>On an infinite number line (x-axis), we drop given squares in the order they are given.
 *
 * The i-th square dropped (positions[i] = (left, side_length)) is a square with
 * the left-most point being positions[i][0] and sidelength positions[i][1].
 * 
 * The square is dropped with the bottom edge parallel to the number line, and
 * from a higher height than all currently landed squares. We wait for each square
 * to stick before dropping the next.
 * 
 * The squares are infinitely sticky on their bottom edge, and will remain fixed
 * to any positive length surface they touch (either the number line or another
 * square). Squares dropped adjacent to each other will not stick together
 * prematurely.
 *
 * <p>For the crazy segment tree solution, read
 * https://leetcode.com/problems/falling-squares/solution/
 * https://leetcode.com/articles/a-recursive-approach-to-segment-trees-range-sum-queries-lazy-propagation/
 * https://www.geeksforgeeks.org/segment-tree-set-1-sum-of-given-range/
 */
public class FallingSquare {
  static int[] fallingSquare(int[][] squares) {
    int n = squares.length;
    // Bases and maxes of squares at each position.
    int[] maxes = new int[n];

    for (int i = 0; i < n; i++) {
      // maxes[i] has the base of squares[i], now process squares[i]
      int[] sq = squares[i];
      maxes[i] += sq[1];

      // All subsequent squares are affected by sq if they are overlapped
      // with sq. Their "base" will become the max of maxes[i] or their
      // current "bases".
      int left = sq[0];
      int right = sq[0] + sq[1];
      for (int j = i + 1; j < n; j++) {
        int[] subsq = squares[j];
        int left2 = subsq[0];
        int right2 = subsq[0] + subsq[1];
        if (right <= left2 || left >= right2) {
          continue;
        }
        maxes[j] = Math.max(maxes[i], maxes[j]);
      }
    }
    int[] ans = new int[n];
    ans[0] = maxes[0];
    for (int i = 1; i < n; i++) {
      ans[i] = Math.max(ans[i - 1], maxes[i]);
    }
    return ans;
  }

  public static void main(String args[]) {
    runSample(new int[][]{{1, 2}, {2, 3}, {6, 1}}); // [2, 5, 5]
    runSample(new int[][]{{100, 100}, {200, 100}}); // [100, 100]
  }

  static void runSample(int[][] squares) {
    System.out.printf("%s = %s\n", multiArrayToString(squares), Arrays.toString(fallingSquare(squares)));
  }


}

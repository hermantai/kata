package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * https://leetcode.com/problems/find-smallest-common-element-in-all-rows/
 *
 * Given a matrix mat where every row is sorted in increasing order, return the
 * smallest common element in all rows.
 *
 * If there is no common element, return -1.
 */
public class SmallestCommonElement {
  static int smallestCommonElement(int[][] mat) {
    int m = mat.length;
    int n = mat[0].length;

    int[] pos = new int[m];
    PriorityQueue<Integer> minIndexes = new PriorityQueue<>((x, y) -> {
      return mat[x][pos[x]] - mat[y][pos[y]];
    });

    int curMax = Integer.MIN_VALUE, count = 0;
    for (int i = 0; i < m; i++) {
      int v = mat[i][0];
      if (v > curMax) {
        curMax = v; count = 1;
      } else if (v == curMax) {
        count++;
      }
      minIndexes.offer(i);
    }

    if (count == m) {
      return 0;
    }

    while (true) {
      int p = minIndexes.poll();
      pos[p]++;
      if (pos[p] == n) {
        return -1;
      }
      int newVal = mat[p][pos[p]];
      if (newVal == curMax) {
        count++;
        if (count == m) {
          return newVal;
        }
      } else if (newVal > curMax) {
        count = 1;
        curMax = newVal;
      }
      minIndexes.offer(p);
    }
  }

  public static void main(String args[]) {
    runSample(new int[][]{new int[]{1,2,3,4,5},new int[]{2,4,5,8,10},new int[]{3,5,7,9,11},new int[]{1,3,5,7,9}}, 5);
    runSample(new int[][]{new int[]{1,2,3,4},new int[]{2,4,5,8,10},new int[]{3,5,7,9,11},new int[]{1,3,5,7,9}}, -1);
  }

  static void runSample(int[][] mat, int ans) {
    System.out.printf(
      "%s = %s(%s)\n",
      multiArrayToString(mat),
      smallestCommonElement(mat),
      ans);
  }
}

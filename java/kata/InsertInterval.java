package kata;

import java.util.*;

/**
 * https://leetcode.com/problems/insert-interval/
 */
public class InsertInterval {
  static int[][] insertInterval(int[][] intervals, int[] newInterval) {
    if (intervals.length == 0) {
      return new int[][]{newInterval};
    }
    LinkedList<int[]> newIntervals = new LinkedList<>();

    int n = intervals.length;
    int i = 0;

    int start = newInterval[0];
    int end = newInterval[1];
    while (i < n && intervals[i][0] < start) {
      newIntervals.add(intervals[i]);
      i++;
    }

    if (newIntervals.isEmpty() || newIntervals.getLast()[1] < start) {
      // start has no overlap
      newIntervals.add(new int[]{start, end});
    } else {
      int[] lastInterval = newIntervals.removeLast();
      newIntervals.add(new int[]{lastInterval[0], Math.max(end, lastInterval[1])});
    }

    while (i < n) {
      int[] interval = intervals[i];
      start = interval[0];
      end = interval[1];

      if (newIntervals.getLast()[1] < start) {
        // no overlap
        newIntervals.add(interval);
      } else {
        int[] lastInterval = newIntervals.removeLast();
        newIntervals.add(new int[]{lastInterval[0], Math.max(end, lastInterval[1])});
      }
      i++;
    }

    return newIntervals.toArray(new int[newIntervals.size()][]);
  }

  public static void main(String args[]) {
    runSample(new int[][]{{1,3}, {6, 9}}, new int[]{2, 5}); // [[1,5],[6,9]]
    runSample(new int[][]{{1,2},{3,5},{6,7},{8,10},{12,16}}, new int[]{4, 8});
    // [[1,2],[3,10],[12,16]]
    // Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
    runSample(new int[][]{{1,3}, {6, 9}}, new int[]{3, 6}); // [[1,9]]
    runSample(new int[][]{{1,3}, {6, 9}}, new int[]{3, 5}); // [[1,5], [6, 9]]
    runSample(new int[][]{{1,3}, {6, 9}}, new int[]{4, 6}); // [[1,3], [4, 9]]
  }

  static void runSample(int[][] intervals, int[] newInterval) {
    System.out.printf("%s, %s = %s\n", multiArrayToString(intervals), Arrays.toString(newInterval), multiArrayToString(insertInterval(intervals, newInterval)));
  }

  public static String multiArrayToString(int[][] m) {
    StringBuilder sb = new StringBuilder("[");
    boolean isFirst = true;
    for (int[] row : m) {
      if (isFirst) {
        isFirst = false;
      } else {
        sb.append(", ");
      }
      sb.append(Arrays.toString(row));
    }
    sb.append(']');
    return sb.toString();
  }

}

package kata;

import java.util.*;

/**
 * https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/
 */
public class ShortestSubarraySumToAtLeastK {
  static int[] shortestSubarraySumToAtLeastK(int[] ar, int k) {
    if (ar.length == 0) {
      if (k > 0) {
        return null;
      }
      return new int[]{};
    }

    long[] psums = new long[ar.length + 1];
    for (int i = 0; i < ar.length; i++) {
      psums[i + 1] = psums[i] + ar[i];
    }

    // Need to find the shortest i - j such that psums[j] - psums[i]
    // >= k. The solution is to run a sliding windown on psums. When
    // i2 > i1 and psums[i2] < psums[i1], we know i2 cannot be an answer
    // because psums[j] - psums[i2] > psums[j] - psums[i1] and j - i2 is
    // smaller.
    //
    // That means we can keep a list of increasing psums[i1], psums[i2]...
    // psums[i]. If psums[i] >= the previous one, take the previous one out
    // from the list. Keep tracking if the current psums[y] - psums[i1] is >= K.
    int start = 0;
    int end = -1; // exclusive
    int shortest = Integer.MAX_VALUE;

    Deque<Integer> starts = new ArrayDeque<>();
    for (int j = 0; j < psums.length; j++) {
      long cur = psums[j];

      // shrinking starts by taking out impossible candidates
      while (!starts.isEmpty() && cur <= psums[starts.getLast()]) {
        starts.removeLast();
      }

      // Check each of starts to see if psums[j] - psums[i] >= K. Because j
      // is better than j + 1, so if the condition is true, we can take out
      // i from the starts.
      //
      // Because starts is increasing, we can stop immediately if psums[j] -
      // psums[i] < K.
      while (!starts.isEmpty() && cur - psums[starts.getFirst()] >= k) {
        int val = j - starts.getFirst();
        if (val < shortest) {
          shortest = val;
          start = starts.getFirst();
          end = j;
        }
        starts.removeFirst();
      }

      starts.addLast(j);
    }
    
    if (end == -1) {
      return null;
    }
    return Arrays.copyOfRange(ar, start, end);
  }

  public static void main(String args[]) {
    runSample(new int[]{1}, 1);
    runSample(new int[]{1, 2}, 4);
    runSample(new int[]{2, -1, 2}, 3);
    runSample(new int[]{2, 1, -1, 3, 2, -3, 4, 0, 1}, 5);
    runSample(new int[]{2, 1, -1, 3, 2, -3, 4, 0, 1, 5}, 5);
    runSample(new int[]{}, 1);
    runSample(new int[]{}, -1);
  }

  static void runSample(int[] ar, int k) {
    System.out.printf("%s, %s = %s\n", Arrays.toString(ar), k, Arrays.toString(shortestSubarraySumToAtLeastK(ar, k)));
  }
}

package kata;

import java.util.*;

/**
 * https://leetcode.com/problems/sliding-window-maximum/
 * Given an array nums, there is a sliding window of size k which is moving
 * from the very left of the array to the very right. You can only see the k
 * numbers in the window. Each time the sliding window moves right by one
 * position. Return the max sliding window.  * 
 * Example:
 * 
 * Input: nums = [1,3,-1,-3,5,3,6,7], and k = 3
 * Output: [3,3,5,5,6,7] 
 * Explanation: 
 * 
 * Window position                Max
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 *  1 [3  -1  -3] 5  3  6  7       3
 *  1  3 [-1  -3  5] 3  6  7       5
 *  1  3  -1 [-3  5  3] 6  7       5
 *  1  3  -1  -3 [5  3  6] 7       6
 *  1  3  -1  -3  5 [3  6  7]      7
 */
public class MaximumSlidingWindow {
  static int[] maximumSlidingWindow(int[] ar, int k) {
    int n = ar.length;
    if (n == 0) {
      return new int[]{};
    }
    if (k == 1) {
      return ar;
    }
    int[] output = new int[Math.max(1, n - k + 1)];

    // Store a queue of indices of ar.  ar[queue[i]] should be decreasing
    // such that if i < j, ar[queue[i]] > ar[queue[j]].
    // queue.getFirst() always points to the last integer in the window.
    LinkedList<Integer> queue = new LinkedList<>();
    // Put k integers from ar into queue
    for (int i = 0; i < Math.min(n, k); i++) {
      putIn(queue, ar, i, k);
    }
    output[0] = ar[queue.getFirst()];

    for (int i = k; i < n; i++) {
      putIn(queue, ar, i, k);
      output[i - k + 1] = ar[queue.getFirst()];
    }

    return output;
  }

  static void putIn(LinkedList<Integer> queue, int[] ar, int i, int k) {
    // Take out numbers from queue which should not exist in queue for the
    // window ending at ar[i].
    while (!queue.isEmpty() && queue.getFirst() <= (i - k)) {
      queue.removeFirst();
    }

    // Remove numbers from queue which are smaller than ar[i]
    while (!queue.isEmpty() && ar[queue.getLast()] < ar[i]) {
      queue.removeLast();
    }
    queue.addLast(i);
  }

  public static void main(String args[]) {
    runSample(new int[]{1, 3, -1, -3, 5, 3, 6, 7}); // output: 3, 3, 5, 5, 6, 7
  }

  static void runSample(int[] ar) {
    System.out.printf("%s = %s\n", Arrays.toString(ar), Arrays.toString(maximumSlidingWindow(ar, 3)));
  }
}

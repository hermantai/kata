package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/
 *
 * <p>You have k lists of sorted integers in ascending order. Find the smallest
 * range that includes at least one number from each of the k lists.
 *
 * We define the range [a,b] is smaller than range [c,d] if b-a < d-c or a < c
 * if b-a == d-c.
 */
public class SmallestRangeCovering {
  static int[] smallestRangeCovering(int[][] nums) {
    int k = nums.length;
    int[] ptrs = new int[k];

    int[] ret = new int[2];
    int max = Integer.MIN_VALUE;
    // A min heap that the min means the i'th list has the minimum value among
    // k lists.
    PriorityQueue<Integer> listIndices = new PriorityQueue<>((x, y) -> {
      return nums[x][ptrs[x]] - nums[y][ptrs[y]];
    });

    for (int i = 0; i < k; i++) {
      if (nums[i].length == 0) {
        return null;
      }

      int v = nums[i][0];
      max = Math.max(v, max);
      listIndices.offer(i);
    }

    int min_i = listIndices.peek();
    ret[0] = 0;
    ret[1] = Integer.MAX_VALUE;

    while (true) {
      // process current min and max
      int minVal = nums[min_i][ptrs[min_i]];
      if (max - minVal < ret[1] - ret[0]) {
        ret[0] = minVal;
        ret[1] = max;
      }

      listIndices.poll();
      // advance ptrs[min_i]
      ptrs[min_i]++;
      if (ptrs[min_i] == nums[min_i].length) {
        break;
      }
      max = Math.max(max, nums[min_i][ptrs[min_i]]);

      listIndices.offer(min_i);
      min_i = listIndices.peek();
    }

    return ret;
  }

  public static void main(String args[]) {
    runSample(new int[][]{{4,10,15,24,26}, {0,9,12,20}, {5,18,22,30}}); // [20, 24]
  }

  static void runSample(int[][] nums) {
    System.out.printf("%s = %s\n", multiArrayToString(nums), Arrays.toString(smallestRangeCovering(nums)));
  }
}

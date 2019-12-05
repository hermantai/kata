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
 *
 * <p>A solution without using a heap:
 * This approach makes use of an array of pointers, next, whose length is
 * equal to the number of given lists. In this array, next[i]next[i] refers to
 * the element which needs to be considered next in the (i-1)^{th}(i−1)th
 * list. The meaning of this will become more clearer when we look at the
 * process.

 * We start by initializing all the elements of next to 0. Thus, currently,
 * we are considering the first(minimum) element among all the lists. Now, we
 * find out the index of the list containing the maximum(max_i) and minimum(min_i)
 * elements from amongst the elements currently pointed by next. The range
 * formed by these maximum and minimum elements surely
 * contains atleast one element from each list.
 * But, now our objective is to minimize this range. To do so, there are two
 * options: Either decrease the maximum value or increase the minimum value.
 
 * Now, the maximum value can't be reduced any further, since it already
 * corresponds to the minimum value in one of the lists. Reducing it any
 * further will lead to the exclusion of all the elements of this
 * list(containing the last maximum value) from the new range.

 * Thus, the only option left in our hand is to try to increase the minimum
 * value. To do so, we now need to consider the next element in the list
 * containing the last minimum value. Thus, we increment the entry at the
 * corresponding index in next, to indicate that the next element in this
 * list now needs to be considered.
 *
 * Thus, at every step, we find the maximum and minimum values being pointed
 * currently, update the next values appropriately, and also find out the
 * range formed by these maximum and minimum values to find out the smallest
 * range satisfying the given criteria.
 *
 * While doing this process, if any of the lists gets completely exhausted, it
 * means that the minimum value being increased for minimizing the range being
 * considered can't be increased any further, without causing the exclusion of
 * all the elements in atleast one of the lists. Thus, we can stop the search
 * process whenever any list gets completely exhausted.
 *
 * We can also stop the process, when all the elements of the given lists have been exhausted.
 *
 * Summarizing the statements above, the process becomes:

 * 1) Initialize next array(pointers) with all 0's.

 * 2) Find the indices of the lists containing the minimum(min_i)
 * and the maximum(max_i) elements amongst the elements pointed by the next
 * array.

 * 3) If the range formed by the maximum and minimum elements found above is
 * larger than the previous maximum range, update the boundary values used for
 * the maximum range.
 *
 * 4) Increment the pointer nums[min_i]

 * 5) Repeat steps 2 to 4 till any of the lists gets exhausted.
 *
 * The solution that uses a heap:
 *
 * In the last approach, at each step, we update the pointer corresponding to
 * the current minimum element and traverse over the whole next array to
 * determine the new maximum and minimum values. We can do some optimization
 * here, by making use of a simple observation.

 * Whenever we update a single entry of next to consider the new maximum and
 * minimum values(if we already know the last maximum and minimum values), all
 * the elements to be considered for finding the maximum and minimum values
 * remain the same except the new element being pointed by a single updated
 * entry in next. This new entry is certainly larger than the last minimum
 * value(since that was the reasoning behind the updation).

 * Thus, we can't be sure whether this is the new minimum element or not. But,
 * since it is larger than the last value being considered, it could be a
 * potential competitor for the new maximum value. Thus, we can directly
 * compare it with the last maximum value to determine the current maximum
 * value.

 * Now, we're left with finding the minimum value iteratively at every step. To
 * avoid this iterative process, a better idea is to make use of a Min-Heap,
 * which stores the values being pointed currently by the next array. Thus, the
 * minimum value always lies at the top of this heap, and we need not do the
 * iterative search process.

 * At every step, we remove the minimum element from this heap and find out the
 * range formed by the current maximum and minimum values, and compare it with
 * the minimum range found so far to determine the required minimum range. We
 * also update the increment the index in next corresponding to the list
 * containing this minimum entry and add this element to the heap as well.

 * The rest of the process remains the same as the last approach.
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

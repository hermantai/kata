package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array
 */
public class FirstAndLastPositionOfElementInSortedArray {
  static int[] firstAndLastPositionOfElementInSortedArray(int[] nums, int target) {
	  int[] targetRange = {-1, -1};

    int leftIdx = extremeInsertionIndex(nums, target, true);

		// assert that `leftIdx` is within the array bounds and that `target`
		// is actually in `nums`.
		if (leftIdx == nums.length || nums[leftIdx] != target) {
			return targetRange;
		}

		targetRange[0] = leftIdx;
		targetRange[1] = extremeInsertionIndex(nums, target, false)-1;

		return targetRange;
  }

  // returns leftmost (or rightmost) index at which `target` should be
  // inserted in sorted array `nums` via binary search.
  private static int extremeInsertionIndex(int[] nums, int target, boolean left) {
      int lo = 0;
      int hi = nums.length;

      while (lo < hi) {
        int mid = (lo + hi) / 2;
        if (nums[mid] > target || (left && target == nums[mid])) {
          hi = mid;
        }
        else {
          lo = mid+1;
        }
      }

      return lo;
  }

  public static void main(String args[]) {
    runSample(new int[]{5,7,7,8,8,10}, 8, new int[]{3, 4});
    runSample(new int[]{5,7,7,8,8,10}, 6, new int[]{-1, -1});
  }

  static void runSample(int[] nums, int target, int[] ans) {
    System.out.printf(
      "%s,%s = %s(%s)\n",
      Arrays.toString(nums),
      target,
      Arrays.toString(firstAndLastPositionOfElementInSortedArray(nums, target)),
      Arrays.toString(ans));
  }
}

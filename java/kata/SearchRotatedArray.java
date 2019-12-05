package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 * (i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).
 * You are given a target value to search. If found in the array return its index, otherwise return -1.
 */
public class SearchRotatedArray {
	static int searchRotatedArray(int[] nums, int target) {
    int start = 0, end = nums.length - 1;
    while (start <= end) {
      int mid = start + (end - start) / 2;
      if (nums[mid] == target) return mid;
      else if (nums[mid] >= nums[start]) {
        if (target >= nums[start] && target < nums[mid]) end = mid - 1;
        else start = mid + 1;
      }
      else {
        if (target <= nums[end] && target > nums[mid]) start = mid + 1;
        else end = mid - 1;
      }
    }
    return -1;
  }

  public static void main(String args[]) {
    runSample(new int[]{4,5,6,7,0,1,2}, 0, 4);
    runSample(new int[]{4,5,6,7,0,1,2}, 3, -1);
  }

  static void runSample(int[] nums, int target, int ans) {
    System.out.printf(
      "%s,%s = %s(%s)\n",
			Arrays.toString(nums),
      target,
      searchRotatedArray(nums, target),
			ans);
  }
}

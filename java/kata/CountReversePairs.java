package kata;

import java.util.*;

/**
 * https://leetcode.com/problems/reverse-pairs/
 *
 * <p>Given an array nums, we call (i, j) an important reverse pair if i < j
 * and nums[i] > 2*nums[j].
 *
 * <p>You need to return the number of important reverse pairs in the given
 * array.
 *
 * <p>The solution uses divide and conquer, by spliting the array into
 * ar[0...mid] and ar[mid + 1...], the count both sides, sort both sides, then
 * merge and count pairs can be generated from ar[a...mid] with ar[mid +1...].
 */
public class CountReversePairs {
  static int countReversePairs(int[] ar) {
    if (ar.length <= 1) {
      return 0;
    }

    return mergeSortAndCount(ar, 0, ar.length - 1, Arrays.copyOf(ar, ar.length));
  }

  static int mergeSortAndCount(int[] ar, int start, int end, int[] copy) {
    if (start >= end) {
      return 0;
    }

    int mid = (start + end) / 2;
    int count = mergeSortAndCount(copy, start, mid, ar);
    count += mergeSortAndCount(copy, mid + 1, end, ar);

    // copy[start...mid] and copy[mid...end] are now sorted. Count the pairs first
    // System.out.printf("count = %s, left = %s, right = %s\n", count, Arrays.toString(Arrays.copyOfRange(copy, start, mid +1)), Arrays.toString(Arrays.copyOfRange(copy, mid + 1, end + 1)));
    int j = mid + 1;
    for (int i = start; i <= mid; i++) {
      // if copy[i] > 2 * copy[j], copy[i + 1] must be > copy[j] as well, so
      // we never have to visit copy[j] again in that case..
      while (j <= end && copy[i] > 2 * copy[j]) {
        j++;
      }
      count += j - (mid + 1);
    }
    
    // then merge copy to ar
    int i = start;
    j = mid + 1;
    int d = start;
    while (i <= mid && j <= end) {
      if (copy[i] <= copy[j]) {
        ar[d] = copy[i];
        i++;
      } else {
        ar[d] = copy[j];
        j++;
      }
      d++;
    }

    while (i <= mid) {
      ar[d] = copy[i];
      i++;
      d++;
    }
    while (j <= end) {
      ar[d] = copy[j];
      j++;
      d++;
    }

    // ar[start...end] is now sorted
    // System.out.printf("ar should be sorted: %s\n", Arrays.toString(Arrays.copyOfRange(ar, start, end+1)));
    return count;
  }

  public static void main(String args[]) {
    runSample(new int[]{1, 3, 2, 3, 1}); // 2
    runSample(new int[]{2, 4, 3, 5, 1}); // 3
  }

  static void runSample(int[] ar) {
    System.out.printf("%s = %s\n", Arrays.toString(ar), countReversePairs(ar));
  }
}

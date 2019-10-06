package kata;

import java.util.*;

/**
 * Maximum subarray size, such that all subarrays of that size have sum less than k.
 *
 * <p> Given an array of n positive integers and a positive integer k, the task
 * is to find the maximum subarray size such that all subarrays of that size
 * have sum of elements less than k.
 *
 * <p> https://www.geeksforgeeks.org/maximum-subarray-size-subarrays-size-sum-less-k/
 */
public class MaxSubarraySizeAllLessThanK {
  static int binsearch(int[] sumarr, int k) {
    int left = 1;
    int right = sumarr.length;
    int ans = -1;
    while (left < right) {
      int mid = left + right / 2;

      int i = mid;
      for (;i < sumarr.length; i++) {
        if (sumarr[i] - sumarr[i - mid] > k) {
          // some subarrays of size mid is greater than k
          break;
        }
      }
      if (i == sumarr.length) {
          // all subarrays of size mid is less than k
          left = mid + 1;
          ans = mid; // this can potentiall be an answer;
      } else {
        // size mid is too big, try lower
        right = mid - 1;
      }
    }

    return ans;
  }

  static int maxSubarraySizeAllLessThanK(int[] arr, int k) {
    int[] sumarr = new int[arr.length + 1];

    sumarr[0] = 0;
    for (int i = 0; i < arr.length; i++) {
      sumarr[i+1] = sumarr[i] + arr[i];
    }

    return binsearch(sumarr, k);
  }

  public static void main(String args[]) {
    runSample(new int[]{1,2,3,4}, 8);
    runSample(new int[]{1,2,10,4}, 8);
    runSample(new int[]{1,2,10,4}, 14);
  }

  static void runSample(int[] arr, int k) {
    System.out.printf("%s, %s = %s\n", Arrays.toString(arr), k, maxSubarraySizeAllLessThanK(arr, k));
  }
}

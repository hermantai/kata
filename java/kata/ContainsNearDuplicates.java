package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * Given an array of integers, find out whether there are two distinct indices
 * i and j in the array such that the absolute difference between nums[i] and
 * nums[j] is at most t and the absolute difference between i and j is at most
 * k.
 *
 * <p>https://leetcode.com/problems/contains-duplicate-iii/
 *
 * <p>Solution:
 *
 * <p>bucket sorting, then maintain the hash table to only include the window
 * of numbers.
 */
public class ContainsNearDuplicates {
  static boolean containsNearDuplicates(int[] ar, int k, int t) {
    Map<Integer, Integer> buckets = new HashMap<Integer, Integer>();

    int w = t + 1;
    for (int i = 0; i < ar.length; i++) {
      int n = ar[i];
      int b = getBucket(n, w);
      if (buckets.containsKey(b)) {
        return true;
      }
      if (buckets.containsKey(b - 1) && (n - buckets.get(b - 1) <= t)) {
        return true;
      }
      if (buckets.containsKey(b + 1) && (buckets.get(b + 1) - n <= t)) {
        return true;
      }
      buckets.put(b, n);
      if (i >= k) {
        // We have processed ar[i], so get rid of the earliest number
        // within buckets to prepare for processing the next number.
        buckets.remove(getBucket(ar[i - k], w));
      }
    }
    return false;
  }

  // To ensure -6,-5...-1 go to bucket -1 if w is 6, we need to 
  // do (n + 1) / w - 1, so that (-6 + 1) / 6 - 1 = -1.
  static int getBucket(int n, int w) {
    return n < 0 ? (n+1) / w - 1 : n / w;
  }

  public static void main(String args[]) {
    runSample(new int[]{1, 2, 3, 1}, 3, 0, "true");
    runSample(new int[]{1, 0, 1, 1}, 1, 2, "true");
    runSample(new int[]{1, 5, 9, 1, 5, 9}, 2, 3, "false");
  }

  static void runSample(int[] ar, int k, int t, String ans) {
    System.out.printf("%s,%s,%s = %s (%s)\n", Arrays.toString(ar), k, t, containsNearDuplicates(ar, k, t), ans);
  }
}

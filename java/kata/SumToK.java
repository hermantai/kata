package kata;

import java.util.*;

/**
 * Cracking the coding interview 6th ed. p.67
 * Find all pairs of integers summed to k.
 */
public class SumToK {
  static List<List<Integer>> sumToK(List<Integer> nums, int sum) {
    Set<Integer> setOfNums = new HashSet<>(nums);
    List<List<Integer>> ret = new ArrayList<>();

    for (int a : new HashSet<>(nums)) {
      int diff = sum - a;
      setOfNums.remove(a);
      if (setOfNums.contains(diff)) {
        ret.add(Arrays.asList(a, diff));
      }
    }

    return ret;
  }

  public static void main(String args[]) {
    runSample(Arrays.asList(1, 9, 4, 6, 2, 3,5, 8), 10);
  }

  public static void runSample(List<Integer> nums, int k) {
    System.out.printf("%s,%s = %s\n", nums, k, sumToK(nums, k));
  }
}

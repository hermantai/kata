package kata;

import java.util.*;

/**
 * https://leetcode.com/problems/arithmetic-slices-ii-subsequence
 *
 * <p>An arithemtic sequence is sequence with at least three numbers
 * and all the differences between two consecutive numbers are the same.
 * E.g. [1, 2, 3], [1, 2, 3, 4]. Not [1, 1, 2].
 */
public class ArithmeticSlicesCount {
  static int arithmeticSlicesCount(int[] ar) {
    int n = ar.length;
    List<Map<Integer, Integer>> diffsToCountsList = new ArrayList<>();

    int sum = 0;
    for (int i = 0; i < n; i++) {
      Map<Integer, Integer> countsSoFar = new HashMap<>();
      diffsToCountsList.add(countsSoFar);
      for (int j = 0; j < i; j++) {
        // Look at the diff for each ar[i] and ar[j], then update the count
        // diffsToCountsList.get(i).get(diff)
        int diff = ar[j] - ar[i];
        
        int prevCount = diffsToCountsList.get(j).getOrDefault(diff, 0);
        // There were prevCount for ar[j] with diff. Each of this sequence
        // can be appended by ar[i] to satisfy the criteria and be part of
        // the answer, so prevCount should be added to sum.
        // Then, there will be one more sequence formed by ar[j] and ar[i]
        // only, so the count should be increased by 1, which is not part
        // of the answer, yet, because there are only two elements in
        // the sequence.
        int countSoFar = countsSoFar.getOrDefault(diff, 0);
        countsSoFar.put(diff, prevCount + countSoFar + 1);

        sum += prevCount;
      }
    }

    return sum;
  }

  public static void main(String args[]) {
    runSample(new int[]{1, 1, 2, 3, 4, 5});
  }

  static void runSample(int[] ar) {
    System.out.printf("%s = %s\n", Arrays.toString(ar), arithmeticSlicesCount(ar));
  }
}

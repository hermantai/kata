package kata;

import java.util.*;

/**
 * https://www.geeksforgeeks.org/minimum-number-of-jumps-to-reach-end-of-a-given-array/
 *
 * <p>Given an array of integers where each element represents the max number
 * of steps that can be made forward from that element. Write a function to
 * return the minimum number of jumps to reach the end of the array (starting
 * from the first element). If an element is 0, then cannot move through that
 * element.
 */
public class MinJump {
  static int[] minJump(int[] steps) {
    if (steps.length == 0 || steps[0] == 0) {
      return null;
    }

    int[] backtrack = new int[steps.length];
    backtrack[0] = -1;
    // mins[i]: minmum number of jumps to reach step i. The last jump
    // is from backtrack[i].
    int[] mins = new int[steps.length];
    mins[0] = 0;

    for (int i = 1; i < steps.length; i++) {
      // assume Integer.MAX_VALUE to indicate a step cannot be reached
      mins[i] = Integer.MAX_VALUE;
      for (int j = 0; j < i; j++) {
        if (steps[j] + j >= i && mins[j] != Integer.MAX_VALUE && mins[j] + 1 < mins[i]) {
          mins[i] = mins[j] + 1;
          backtrack[i] = j;
        }
      }
    }

    if (mins[steps.length - 1] == Integer.MAX_VALUE) { 
      return null;
    }

    List<Integer> reversedPosits = new ArrayList<>();
    int p = steps.length - 1;
    while (backtrack[p] != -1) {
      reversedPosits.add(p);
      p = backtrack[p];
    }
    reversedPosits.add(p);

    Collections.reverse(reversedPosits);
    return reversedPosits.stream().mapToInt(i -> i).toArray();
  }

  public static void main(String args[]) {
    runSample(new int[] {1, 3, 6, 1, 0, 9}); // [0, 1, 2, 5]
    runSample(new int[] {1, 3, 5, 8, 9, 2, 6, 7, 6, 8, 9}); // [0, 1, 3, 10]
    runSample(new int[] {1, 3, 6, 3, 2, 3, 6, 8, 9, 5}); // [0, 1, 2, 6, 9]
    runSample(new int[] {}); // null
    runSample(new int[] {0}); // null
    runSample(new int[] {1, 0, 0}); // null
  }

  static void runSample(int[] steps) {
    System.out.printf("%s = %s\n", Arrays.toString(steps), Arrays.toString(minJump(steps)));
  }
}

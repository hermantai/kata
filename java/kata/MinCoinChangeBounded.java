package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * Gets the number of coins needed to get the amount, k. The number of coins
 * are given in limited quantity.
 */
public class MinCoinChangeBounded {
  static int minCoinChangeBounded(int k, int[] coins, int[] quantities) {
    if (k <= 0) {
      return 0;
    }
    if (coins.length == 0) {
      return -1;
    }
    // curr[i] the minimum number of coins needed to get i. -1 means
    // no combination of coins can get i.
    int[] curr = new int[k + 1];
    for (int i = 1; i <= k; i++) {
      curr[i] = -1;
    }
    int[] next = Arrays.copyOf(curr, curr.length);

    int c = 0;
    int maxSoFar = 0;
    while (c < quantities.length) {
      if (quantities[c] == 0) {
        c++;
        continue;
      }
      // Use curr to process next
      int val = coins[c];
      quantities[c]--;

      maxSoFar += val;
      int end = Math.min(maxSoFar, k);
      for (int i = 1; i <= end; i++) {
        if (i >= val) {
          if (curr[i - val] != -1) {
            if (curr[i] == -1) {
              // use val to finish the amount
              next[i] = 1 + curr[i - val];
            } else {
              next[i] = Math.min(curr[i], 1 + curr[i - val]);
            }
          }
        }
      }
      
      // next is now the answer, so curr becomes next.
      int[] tmp = curr;
      curr = next;
      next = tmp;
    }
    
    return curr[k];
  }

  public static void main(String args[]) {
    runSample(100, new int[]{25, 50, 5}, new int[]{2, 1, 20}, 3);
    runSample(100, new int[]{25, 50, 5}, new int[]{2, 2, 20}, 2);
    runSample(100, new int[]{25, 50, 5}, new int[]{1, 1, 4}, -1);
    runSample(100, new int[]{25, 50, 5}, new int[]{1, 1, 5}, 7);
  }

  static void runSample(int k, int[] coins, int[] quantities, int ans) {
    System.out.printf(
        "%s,%s,%s = %s (%s)\n",
        k,
        Arrays.toString(coins),
        Arrays.toString(quantities),
        minCoinChangeBounded(k, coins, quantities),
        ans);
  }
}

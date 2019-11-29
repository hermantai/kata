package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * https://leetcode.com/problems/coin-change
 *
 * <p>You are given coins of different denominations and a total amount of
 * money amount. Write a function to compute the fewest number of coins that
 * you need to make up that amount. If that amount of money cannot be made up
 * by any combination of the coins, return -1.
 */
public class MinCoinChangeUnbounded {
  static int minCoinChangeUnbounded(int[] coins, int amount) {
    if (amount <= 0) {
      return 0;
    }
    if (coins.length == 0) {
      return -1;
    }

    int[] table = new int[amount+1];
    for (int i = 1; i < table.length; i++) {
      table[i] = amount + 1;
      for (int coin : coins) {
        if (i >= coin) {
          table[i] = Math.min(table[i], table[i - coin] + 1);
        }
      }
    }
    return table[amount] > amount ? - 1 : table[amount];
  }

  public static void main(String args[]) {
    runSample(new int[]{1, 2, 5}, 11, 3);
    runSample(new int[]{2}, 3, -1);
    runSample(new int[]{3}, 2, -1);
  }

  static void runSample(int[] coins, int amount, int ans) {
    System.out.printf("%s,%s = %s(%s)\n", Arrays.toString(coins), amount, minCoinChangeUnbounded(coins, amount), ans);
  }
}

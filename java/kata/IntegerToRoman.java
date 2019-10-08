package kata;

import java.util.*;

/**
 * https://leetcode.com/problems/integer-to-roman/
 */
public class IntegerToRoman {

  private static int[] decs = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
  private static String[] romans = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
  static String integerToRoman(int n) {
    if (n < 0 || n >= 4000) {
      return null;
    }

    StringBuilder sb = new StringBuilder();
    int i = 0;
    while (n > 0) {
      if (decs[i] <= n) {
        sb.append(romans[i]);
        n -= decs[i];
        continue;
      }
      i++;
    }

    return sb.toString();
  }

  public static void main(String args[]) {
    runSample(3);
    runSample(4);
    runSample(9);
    runSample(58);
    runSample(1994);
    runSample(4000);
    runSample(-1);
  }

  static void runSample(int n) {
    System.out.printf("%s = %s\n", n, integerToRoman(n));
  }
}

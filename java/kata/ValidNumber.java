package kata;

import java.util.*;

/**
 * https://leetcode.com/problems/valid-number/
 *
 * <p>Validate if a given string can be interpreted as a decimal number.
 * 
 * Some examples:
 * "0" => true
 * " 0.1 " => true
 * "abc" => false
 * "1 a" => false
 * "2e10" => true
 * " -90e3   " => true
 * " 1e" => false
 * "e3" => false
 * " 6e-1" => true
 * " 99e2.5 " => false
 * "53.5e93" => true
 * " --6 " => false
 * "-+3" => false
 * "95a54e53" => false
 * 
 * Note: It is intended for the problem statement to be ambiguous. You should gather all requirements up front before implementing one. However, here is a list of characters that can be in a valid decimal number:
 * 
 *   - Numbers 0-9
 *   - Exponent - "e"
 *   - Positive/negative sign - "+"/"-"
 *   - Decimal point - "."
 */
public class ValidNumber {
  static boolean validNumber(String str) {
    // TODO
    return false;
  }

  public static void main(String args[]) {
    runSample("0");
    runSample(" 0.1");
    runSample("abc");
    runSample("1 a");
    runSample("2e10");
    runSample(" -90e3  ");
    runSample(" 1e");
    runSample("e3");
    runSample(" 6e-1");
    runSample(" 99e2.5 ");
    runSample(" 53.5e93");
    runSample(" --6 ");
    runSample("-+3");
    runSample("95a54e53");
  }

  static void runSample(String s) {
    System.out.printf("%s = %s\n", s, validNumber(s));
  }
}

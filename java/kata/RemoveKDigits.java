package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * Given a non-negative integer num represented as a string, remove k digits
 * from the number so that the new number is the smallest possible.
 *
 * <p>https://leetcode.com/problems/remove-k-digits/
 */
public class RemoveKDigits {
  static String removeKDigits(String str, int k) {
    Stack<Character> stack = new Stack<>();
    char[] chars = str.toCharArray();
    int n = chars.length;
    // whenever there is a digit smaller the the previous digit, remove the
    // previous digit.
    for (int i = 0; i < n; i++) {
      char c = chars[i];
      while (!stack.isEmpty() && c < stack.peek() && k > 0) {
        stack.pop();
        k--;
      }
      stack.push(c);
    }
    // The digits are now non-decreasing from left to right, so remove
    // the largest digits from the right if k > 0
    while (k > 0 && !stack.isEmpty()) {
      stack.pop();
      k--;
    }

    if (stack.isEmpty()) {
      return "0";
    }

    StringBuilder sb = new StringBuilder();
    // Store the number in sb in reversed order.
    // stack.stream().forEach(v -> sb.append(v));
    while (!stack.isEmpty()) {
      sb.append(stack.pop());
    }
    

    // Remove "leading" zeros of the numbers.
    while (sb.length() > 0 && sb.charAt(sb.length() - 1) == '0') {
      sb.deleteCharAt(sb.length() - 1);
    }

    if (sb.length() == 0) {
      return "0";
    }
    sb.reverse();

    return sb.toString();
  }

  public static void main(String args[]) {
    runSample("1432219", 3, "1219");
    runSample("10200", 1, "200");
    runSample("123", 2, "1");
    runSample("10", 2, "0");
    runSample("10", 1, "0");
  }

  static void runSample(String s, int k, String ans) {
    System.out.printf("%s,%s = %s (%s)\n", s, k, removeKDigits(s, k), ans);
  }
}

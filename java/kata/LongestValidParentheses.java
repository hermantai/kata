package kata;

import java.util.*;

/**
 * https://leetcode.com/problems/longest-valid-parentheses
 */
public class LongestValidParentheses {
  static int longestValidParentheses(String str) {
    Stack<Integer> invalids = new Stack<>();
    invalids.push(-1);

    int max = 0;
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == '(') {
        invalids.push(i);
      } else {
        invalids.pop();
        if (invalids.isEmpty()) {
          // The string is invalid up to i
          invalids.push(i);
        } else {
          max = Math.max(max, i - invalids.peek());
        }
      }
    }
    return max;
  }

  public static void main(String args[]) {
    runSample("(()"); // 2
    runSample(")()())"); // 4
    runSample("(()())"); // 6
    runSample("())"); // 2
  }

  static void runSample(String s) {
    System.out.printf("%s = %s\n", s, longestValidParentheses(s));
  }
}

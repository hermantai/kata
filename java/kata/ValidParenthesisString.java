package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * https://leetcode.com/problems/valid-parenthesis-string
 *
 * <p>Given a string containing only three types of characters: '(', ')' and
 * '*', write a function to check whether this string is valid. We define the
 * validity of a string by these rules:
 *
 * Any left parenthesis '(' must have a corresponding right parenthesis ')'.
 * Any right parenthesis ')' must have a corresponding left parenthesis '('.
 * Left parenthesis '(' must go before the corresponding right parenthesis ')'.
 * '*' could be treated as a single right parenthesis ')' or a single left parenthesis '(' or an empty string.
 * An empty string is also valid.
 */
public class ValidParenthesisString {
  static boolean validParenthesisString(String str) {
    // Keep track of the number of left parentheses, the string is valid
    // if at the end the number is 0. If it goes negative, we can return
    // false immediately, e.g. ))( (position 0 can return false).
    // 
    // Because of '*', we need to track the number of possible left
    // parentheses. We just need lo and hi to track this.
    
    int lo = 0, hi = 0;
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      
      if (c == '(') {
        lo++;
      } else {
        lo--;
      }
      
      if (c == ')') {
        hi--;
      } else {
        hi++;
      }

      if (hi < 0) {
        return false;
      }
      if (lo < 0) {
        lo = 0;
      }
    }
    return lo == 0;
  }

  public static void main(String args[]) {
    runSample("()", true);
    runSample("(*)", true);
    runSample("(*))", true);
    runSample("*))", false);
    runSample("*(()", false);
  }

  static void runSample(String s, boolean ans) {
    System.out.printf("%s = %s(%s)\n", s, validParenthesisString(s), ans);
  }
}

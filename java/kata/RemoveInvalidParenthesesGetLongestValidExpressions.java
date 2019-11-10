package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * https://leetcode.com/problems/remove-invalid-parentheses
 *
 * <p>Remove the minimum number of invalid parentheses in order to make the
 * input string valid. Return all possible results.  Note: The input string may
 * contain letters other than the parentheses ( and ).
 *
 * "()())()" ->  ["()()()", "(())()"]
 * "(a)())()" -> "(a)()()", "(a())()"]
 * ")(" -> [""]
 */
public class RemoveInvalidParenthesesGetLongestValidExpressions {
  static Set<String> removeInvalidParenthesesGetLongestValidExpressions(String str) {
    // Get number of open's and close's which have to be removed.
    int open = 0, close = 0;
    char[] chars = str.toCharArray();
    for (char c: chars) {
      if (c == '(') {
        open++;
      } else if (c == ')') {
        if (open > 0) {
          open--;
        } else {
          close++;
        }
      }
    }

    Set<String> output = new HashSet<>();
    if (open != 0 || close != 0) {
      recurse(0, str.length(), chars, new StringBuilder(), /* openCount= */ 0, /* closeCount= */ 0, /* openRem = */ open, /* closeRem = */ close, output);
    } else {
      output.add(str);
    }
    return output;
  }

  static void recurse(int i, int n, char[] chars, StringBuilder soFar, int openCount, int closeCount, int openRem, int closeRem, Set<String> output) {
    if (i == n) {
      if (openRem == closeRem) {
        output.add(soFar.toString());
      }
      return;
    }

    char c = chars[i];
    if ((c == '(' && openRem > 0) || (c == ')' && closeRem > 0)) {
      recurse(i + 1, n, chars, soFar, openCount, closeCount, c == '(' ? openRem - 1 : openRem, c == ')' ? closeRem - 1 : closeRem, output);
    }

    soFar.append(c);

    if (c == '(') {
      recurse(i + 1, n, chars, soFar, openCount+1, closeCount, openRem, closeRem, output);
    } else if (c == ')' && openCount > closeCount) {
      recurse(i + 1, n, chars, soFar, openCount, closeCount+1, openRem, closeRem, output);
    } else if (c != ')') {
      recurse(i + 1, n, chars, soFar, openCount, closeCount, openRem, closeRem, output);
    }
    soFar.deleteCharAt(soFar.length() - 1);
  }

  public static void main(String args[]) {
    runSample("()())()");
    runSample("(a)())()");
    runSample(")(");
  }

  static void runSample(String s) {
    System.out.printf("%s = %s\n", s, removeInvalidParenthesesGetLongestValidExpressions(s));
  }
}

package kata;

import java.util.*;

/**
 * https://leetcode.com/problems/basic-calculator
 */
public class PlusMinusCalculator {
  static int plusMinusCalculator(String str) {
    // stores the sides and results outside of parens, so the expression
    // in side parens can be evaluated before evaulating results outside
    // the parens.
    Stack<Integer> outsideParens = new Stack<Integer>();

    int result = 0;
    int sign = 1; // 1 for positive, -1 for negative
    int operand = 0;
    for (char c : str.toCharArray()) {
      if (Character.isDigit(c)) {
        operand = operand * 10 + (c - '0');
      } else if (c == '+') {
        // evaulate the result on the left
        result += sign * operand;
        sign = 1;
        operand = 0;
      } else if (c == '-') {
        result += sign * operand;
        sign = -1;
        operand = 0;
      } else if (c == '(') {
        // the result and sign will be saved, we then start result with 0
        outsideParens.push(result);
        outsideParens.push(sign);
        sign = 1;
        result = 0;
      } else if (c == ')') {
        result += sign * operand;
        result *= outsideParens.pop(); // the thing on top is a sign
        result += outsideParens.pop();
        operand = 0;
      }
    }

    result += operand * sign;
    return result;
  }

  public static void main(String args[]) {
    runSample("1+2");
    runSample("1+4-3");
    runSample("10-4-3");
    runSample("10-(4-3)");
    runSample("(10-(4+3))");
    runSample("-(10+(4+3))");
    runSample("(-(10))");
    runSample("20-(15-(5-4))");
    runSample("20+(15-(5-4))");
  }

  static void runSample(String s) {
    System.out.printf("%s = %s\n", s, plusMinusCalculator(s));
  }
}

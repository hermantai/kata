package kata;

import java.util.*;

/**
 * Basic caculator that handles '+', '-', '*'
 */
public class BasicCalculator {
  static int basicCalculator(String exp) {
    Stack<Delayed> delayeds = new Stack<Delayed>();
    return calculate(exp.toCharArray(), 0, exp.length(), delayeds, /* prevVal= */ 0, /* prevOp= */ '0', /* curVal= */ 0);
  }

  static int calculate(char[] chars, int i, int n, Stack<Delayed> delayeds, int prevVal, char prevOp, int curVal) {
    if (i == n) {
      return evaluate(prevVal, prevOp, curVal, delayeds);
    }

    char c = chars[i];
    if (Character.isDigit(c)) {
      return calculate(chars, i + 1, n, delayeds, prevVal, prevOp, curVal * 10 + (c - '0'));
    }
    if (c == '+' || c == '-') {
      if (prevOp == '*') {
        Delayed delayed = delayeds.pop();
        curVal = evalOp(delayed.op, delayed.operand, evalOp(prevOp, prevVal, curVal));
      }
      return evalOpAndContinue(chars, i + 1, n, delayeds, prevVal, /* prevOp= */ prevOp, /* curVal= */ curVal, c);
    } else if (c == '*') {
      delayeds.push(new Delayed(prevVal, prevOp));
      return calculate(chars, i + 1, n, delayeds, /* prevVal= */ curVal, /* prevOp= */ '*', /* curVal= */ 0);
    } else if (c == '(') {
      delayeds.push(new Delayed(prevVal, prevOp));
      return calculate(chars, i + 1, n, delayeds, /* prevVal= */ 0, /* prevOp= */ '0', /* curVal= */ 0);
    } else if (c == ')') {
      Delayed delayed = delayeds.pop();
      int result = evalOp(delayed.op, delayed.operand, evalOp(prevOp, prevVal, curVal));
      return calculate(chars, i + 1, n, delayeds, /* prevVal= */ 0, /* prevOp= */ '0', /* curVal= */ result);
    }
    throw new RuntimeException("impossible");
  }

  static int evalOpAndContinue(char[] chars, int i, int n, Stack<Delayed> delayeds, int prevVal, char prevOp, int curVal, char curOp) {
    int val = evalOp(prevOp, prevVal, curVal);
    return calculate(chars, i, n, delayeds, /* prevVal= */ val, /* prevOp= */ curOp, /* curVal= */ 0);
  }

  static int evalOp(char op, int operand1, int operand2) {
    if (op == '*') {
      return operand1 * operand2;
    }
    if (op == '+') {
      return operand1 + operand2;
    }
    if (op == '-') {
      return operand1 - operand2;
    }
    // special case for "(" happens at the beginning.
    return operand2;
  }

  static int evaluate(int prevVal, char prevOp, int curVal, Stack<Delayed> delayeds) {
    int result = evalOp(prevOp, prevVal, curVal);
    if (delayeds.isEmpty()) {
      return result;
    }
    Delayed delayed = delayeds.pop();
    return evaluate(delayed.operand, delayed.op, result, delayeds);
  }

  static class Delayed {
    int operand;
    char op;
    Delayed(int operand, char op) {
      this.operand = operand;
      this.op = op;
    }
  }

  public static void main(String args[]) {
    runSample("1+2*3");
    runSample("1*2+3");
    runSample("7-2-3");
    runSample("7-(3-1)");
    runSample("(1+2)*3");
    runSample("((1+2)*(3+4))");
  }

  static void runSample(String s) {
    System.out.printf("%s = %s\n", s, basicCalculator(s));
  }
}

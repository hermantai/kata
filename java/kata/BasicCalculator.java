package kata;

import java.util.*;

/**
 * https://www.geeksforgeeks.org/expression-evaluation/
 * Basic caculator that handles '+', '-', '*', '/'
 */
public class BasicCalculator {
  static int basicCalculator(String exp) {
    char[] chars = exp.toCharArray();
    Stack<Integer> values = new Stack<>();
    Stack<Character> ops = new Stack<>();

    for (int i = 0; i < chars.length; i++) {
      char c = chars[i];
      if (Character.isDigit(c)) {
        int val = c - '0';
        int j = i + 1;
        while (j < chars.length && Character.isDigit(chars[j])) {
          val = val * 10 + chars[j] - '0';
          j++;
        }
        values.push(val);
        i = j - 1; // advance i to the last digit, so the next i++ works
      } else if (c == '+' || c == '-' || c == '*' || c == '/') {
        // Only evaluate if there is a previous op
        while (!ops.isEmpty() && hasPrecedence(ops.peek(), c)) {
          values.push(evalOp(ops.pop(), values.pop(), values.pop()));
        }
        ops.push(c);
      } else if (c == '(') {
        ops.push(c);
      } else if (c == ')') {
        while (ops.peek() != '(') {
          values.push(evalOp(ops.pop(), values.pop(), values.pop()));
        }
        ops.pop(); // take out '('
      }
    }

    // the expression is parsed, now just take care of the ops stack
    while (!ops.isEmpty()) {
      values.push(evalOp(ops.pop(), values.pop(), values.pop()));
    }
    return values.pop();
  }

  // If op1 has higher or equal precedence as op2.
  static boolean hasPrecedence(char op1, char op2) {
    if (op1 == '(' || op1 == ')') {
      return false;
    }
    if ((op1 == '+' || op1 == '-') && (op2 == '*' || op2 == '/')) {
      return false;
    }
    return true;
  }

  // The operand parameters are reversed so the caller can pass the parameters
  // in reverse, i.e. stack.pop(), stack.pop().
  static int evalOperator(char op, int operand2, int operand1) {
    if (op == '*') {
      return operand1 * operand2;
    }
    if (op == '+') {
      return operand1 + operand2;
    }
    if (op == '-') {
      return operand1 - operand2;
    }
    if (op == '/') {
      return operand1 - operand2;
    }
    throw new RuntimeException("impossible");
  }

  // My dumb method
  static int basicCalculator2(String exp) {
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
    runSample("((10+20)*(19+24))");
  }

  static void runSample(String s) {
    System.out.printf("%s = %s\n", s, basicCalculator(s));
  }
}

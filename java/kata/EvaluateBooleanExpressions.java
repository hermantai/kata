package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * Given an expression that has "true", "false", "|", "&", and parentheses.
 * There maybe spaces in between.
 *
 * E.g "true" -> true
 * "false & true" -> false
 * "true | true & false" -> true
 * "(true | true) & false" -> false
 */
public class EvaluateBooleanExpressions {
  static boolean evaluateBooleanExpressions(String str) {
    char[] chars = str.toCharArray();

    int n = chars.length;
    Stack<Boolean> vals = new Stack<>();
    Stack<Character> ops = new Stack<>();
    // ! can only happen right before true/false or ( or !
    for (int i = 0; i < n; i++) {
      char c = chars[i];
      if (c == 'f' || c == 't') {
        boolean val;
        if (c == 'f') {
          i += 4;
          val = false;
        } else {
          i += 3;
          val = true;
        }
        // eval !
        
        vals.push(evalNot(val, ops));
      } else if (c == '|' || c == '&') {
        while (!ops.isEmpty() && hasPrecedence(ops.peek(), c)) {
          vals.push(evalOp(ops.pop(), vals.pop(), vals.pop()));
        }
        ops.push(c);
      } else if (c == '(' || c == '!') {
        ops.push(c);
      } else if (c == ')') {
        while (!ops.isEmpty() && ops.peek() != '(') {
          vals.push(evalOp(ops.pop(), vals.pop(), vals.pop()));
        }
        // pop '('
        ops.pop();
        if (!ops.isEmpty() && ops.peek() == '!') {
          vals.push(evalNot(vals.pop(), ops));
        }
      }
    }

    while (!ops.isEmpty()) {
      vals.push(evalOp(ops.pop(), vals.pop(), vals.pop()));
    }
    return vals.pop();
  }

  static boolean hasPrecedence(char op1, char op2) {
    if (op1 == '(') {
      return false;
    }
    if (op1 == '|' && op2 == '&') {
      return false;
    }
    return true;
  }

  static boolean evalOp(char op, boolean val1, boolean val2) {
    if (op == '|') {
      return val1 || val2;
    }
    if (op == '&') {
      return val1 && val2;
    }
    throw new RuntimeException("impossible");
  }

  static boolean evalNot(boolean val, Stack<Character> ops) {
    while (!ops.isEmpty() && ops.peek() == '!') {
      val = !val;
      ops.pop();
    }
    return val;
  }

  public static void main(String args[]) {
    runSample("true");
    runSample("false & true");
    runSample("true | true & false");
    runSample("(true | true) & false");
    runSample("(true | true) & !!!false");
    runSample("!(true | true) & true");
    runSample("!(!(!(true)) | true) & true");
  }

  static void runSample(String s) {
    System.out.printf("%s = %s\n", s, evaluateBooleanExpressions(s));
  }
}

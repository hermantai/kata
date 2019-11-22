package kata;

import java.util.*;

/**
 * https://leetcode.com/problems/expression-add-operators/
 *
 * <p>Given a string that contains only digits 0-9 and a target value, return
 * all possibilities to add binary operators (not unary) +, -, or * between the
 * digits so they evaluate to the target value.
 */
public class ExpressionAddOperators {
  static List<String> expressionAddOperators(String s, int t) {
    int n = s.length();
    if (n == 0) {
      return List.of();
    }

    List<String> output = new ArrayList<>();
    LinkedList<String> soFar = new LinkedList<>();
    recurse(0, 0, 0, 0, s, t, s.length(), soFar, output);
    return output;
  }

  static void recurse(int index, int prevOp, int curOp, int val, String s, int t, int n, LinkedList<String> soFar, List<String> output) {
    if (index == n) {
      if (curOp == 0 && val == t) {
        StringBuilder sb = new StringBuilder();
        soFar.stream().forEach(op -> sb.append(op));
        output.add(sb.toString());
      }
      return;
    }

    char c = s.charAt(index);
    curOp = curOp * 10 + Character.getNumericValue(c);

    // Treat s[index] as an extra digit of the current value
    if (curOp > 0) {
      recurse(index + 1, prevOp, curOp, val, s, t, n, soFar, output);
    }

    String opStr = Integer.toString(curOp);
    if (soFar.isEmpty()) {
      soFar.addLast(opStr);
      recurse(index + 1, curOp, 0, val + curOp, s, t, n, soFar, output);
      soFar.removeLast();
      return;
    }

    if (!soFar.isEmpty()) {
      // Try "+"
      soFar.addLast("+");
      soFar.addLast(opStr);
      recurse(index + 1, curOp, 0, val + curOp, s, t, n, soFar, output);
      soFar.removeLast();
      soFar.removeLast();

      // Try '-'
      soFar.addLast("-");
      soFar.addLast(opStr);
      recurse(index + 1, -curOp, 0, val - curOp, s, t, n, soFar, output);
      soFar.removeLast();
      soFar.removeLast();

      // Try '*'
      soFar.addLast("*");
      soFar.addLast(opStr);
      recurse(
          index + 1,
          curOp * prevOp,
          0,
          val - prevOp + (curOp * prevOp),
          s,
          t,
          n,
          soFar,
          output);
      soFar.removeLast();
      soFar.removeLast();
    }
  }

  public static void main(String args[]) {
    runSample("123", 6);
    // Output: ["1+2+3", "1*2*3"]

    runSample("232", 8);
    // Output: ["2*3+2", "2+3*2"]

    runSample("105", 5);
		// Output: ["1*0+5","10-5"]

    runSample("00", 0);
		// Output: ["0+0", "0-0", "0*0"]

    runSample("123", 123);
    // Output: ["123"]
    //
    runSample("1234", 46);
    // Output: ["12+34"]

    runSample("3456237490", 9191);
		// Output: []

    runSample("1234", 1234);
    // Output: ["1234"]
	}

  static void runSample(String s, int target) {
    System.out.printf("%s, %s = %s\n", s, target, expressionAddOperators(s, target));
  }
}

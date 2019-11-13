package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * https://leetcode.com/problems/number-of-atoms/
 *
 * <p>Given a chemical formula (given as a string), return the count of each atom.
 *
 * An atomic element always starts with an uppercase character, then zero or
 * more lowercase letters, representing the name.
 *
 * 1 or more digits representing the count of that element may follow if the
 * count is greater than 1. If the count is 1, no digits will follow. For
 * example, H2O and H2O2 are possible, but H1O2 is impossible.
 *
 * Two formulas concatenated together produce another formula. For example,
 * H2O2He3Mg4 is also a formula.
 *
 * A formula placed in parentheses, and a count (optionally added) is also a
 * formula. For example, (H2O2) and (H2O2)3 are formulas.
 *
 * Given a formula, output the count of all elements as a string in the
 * following form: the first name (in sorted order), followed by its count (if
 * that count is more than 1), followed by the second name (in sorted order),
 * followed by its count (if that count is more than 1), and so on.
 */
public class NumberOfAtoms {
  static String numberOfAtoms(String str) {
    Stack<Map<String, Integer>> st = new Stack<>();
    Map<String, Integer> curCounts = new HashMap<>();
    st.push(curCounts);

    int n = str.length();
    char[] chars = str.toCharArray();
    for (int i = 0; i < n;) {
      char c = chars[i];
      if (Character.isUpperCase(c)) {
        int start = i;
        i++;
        while (i < n && Character.isLowerCase(chars[i])) {
          i++;
        }
        String name = new String(chars, start, i - start);

        int quantity = 1;
        start = i;
        while (i < n && Character.isDigit(chars[i])) {
          i++;
        }
        if (start != i) {
          quantity = Integer.parseInt(new String(chars, start, i - start));
        }
        curCounts.put(name, curCounts.getOrDefault(name, 0) + quantity);
      } else if (c == '(') {
        curCounts = new HashMap<>();
        st.push(curCounts);
        i++;
      } else if (c == ')') {
        int quantity = 1;
        i++;
        int start = i;
        while (i < n && Character.isDigit(chars[i])) {
          i++;
        }
        if (start != i) {
          quantity = Integer.parseInt(new String(chars, start, i - start));
        }
        Map<String, Integer> tmp = st.pop();
        curCounts = st.peek();
        for (Map.Entry<String, Integer> entry : tmp.entrySet()) {
          String name = entry.getKey();
          curCounts.put(name, curCounts.getOrDefault(name, 0) + entry.getValue() * quantity);
        }
      }
    }

    StringBuilder sb = new StringBuilder();
    List<String> names = new ArrayList<>(curCounts.keySet());
    Collections.sort(names);
    for (String name : names) {
      int q = curCounts.get(name);
      sb.append(name);
      if (q != 1) {
        sb.append(q);
      }
    }
    return sb.toString();
  }

  public static void main(String args[]) {
    runSample("H2O");
    runSample("Mg(OH)2");
    runSample("K4(ON(S03)2)2");
  }

  static void runSample(String s) {
    System.out.printf("%s = %s\n", s, numberOfAtoms(s));
  }
}

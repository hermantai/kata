package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * https://leetcode.com/problems/restore-ip-addresses/
 *
 * Given a string containing only digits, restore it by returning all possible
 * valid IP address combinations.
 */
public class RestoreIpAddresses {
  static List<String> restoreIpAddresses(String str) {
    LinkedList<String> segments = new LinkedList<>();
    List<String> output = new ArrayList<>();
    int dots = 3;
    int pos = 0;

    restoreIpAddressesHelper(pos, dots, str.toCharArray(), segments, output);
    return output;
  }

  static void restoreIpAddressesHelper(int pos, int dots, char[] chars,  LinkedList<String> segments, List<String> output) {
    if (pos == chars.length) {
      return;
    }
    if (dots == 0) {
      String s = new String(chars, pos, chars.length - pos);
      if (isValid(s)) {
        StringBuilder sb = new StringBuilder();
        for (String segment : segments) {
          sb.append(segment);
          sb.append('.');
        }
        sb.append(s);
        output.add(sb.toString());
      }
      return;
    }

    int maxDotPos = Math.min(pos + 3, chars.length - 1);
    for (int dotPos = pos + 1; dotPos <= maxDotPos; dotPos++) {
      String seg = new String(chars, pos, dotPos - pos);
      if (isValid(seg)) {
        segments.addLast(seg);
        restoreIpAddressesHelper(dotPos, dots - 1, chars, segments, output);
        segments.removeLast();
      }
    }
  }

  static boolean isValid(String s) {
    if (s.charAt(0) == '0') {
      return s.length() == 1;
    }
    return Integer.valueOf(s) <= 255;
  }

  public static void main(String args[]) {
    runSample("25525511135", new String[]{"255.255.11.135", "255.255.111.35"});
    runSample("025511135", new String[]{"0.255.11.135", "0.255.111.35"});
    runSample("2552551113", new String[]{"255.25.51.113", "255.255.1.113", "255.255.11.13", "255.255.111.3"});
    runSample("255011135", new String[]{"25.50.11.135", "25.50.111.35", "255.0.11.135", "255.0.111.35"});
  }

  static void runSample(String s, String[] ans) {
    System.out.printf("%s = %s (%s)\n", s, restoreIpAddresses(s), Arrays.toString(ans));
  }
}

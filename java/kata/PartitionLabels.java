package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * A string S of lowercase letters is given. We want to partition this string
 * into as many parts as possible so that each letter appears in at most one
 * part, and return a list of integers representing the size of these parts.
 *
 * https://leetcode.com/problems/partition-labels/
 */
public class PartitionLabels {
  static List<Integer> partitionLabels(String str) {
    List<Integer> output = new ArrayList<>();
    char[] chars = str.toCharArray();

    Map<Character, Integer> lasts = new HashMap<>();
    for (int i = 0; i < chars.length; i++) {
      lasts.put(chars[i], i);
    }

    int start = 0, curLast = 0;
    for (int i = 0; i < chars.length; i++) {
      // find partition chars[start...curLast] inclusive
      char c = chars[i];
      curLast = Math.max(lasts.get(c), curLast);
      if (i == curLast) {
        // "aaabb", the first partition:
        // curLast is 2, start is 0, so the answer is 3
        output.add(curLast - start + 1);
        start = i + 1;
      }
    }

    return output;
  }

  public static void main(String args[]) {
    runSample("ababcbacadefegdehijhklij", Arrays.asList(9, 7, 8));
    runSample("abccaddbeffe", Arrays.asList(8, 4));
  }

  static void runSample(String s, List<Integer> ans) {
    System.out.printf(
      "%s = %s(%s)\n",
      s,
      partitionLabels(s),
      ans);
  }
}

package kata;

import java.util.*;
import java.util.stream.Collectors;

/**
 * https://leetcode.com/problems/stickers-to-spell-word/
 */
public class StickersToSpellWord {
  static int stickersToSpellWord(String[] stickers, String target) {
    Map<Character, Integer> targetCounters = getCounters(target);
    Map<Map<Character, Integer>, Integer> memo = new HashMap<>();
    return stickersToSpellWordHelper(
        Arrays.stream(stickers).map(StickersToSpellWord::getCounters).collect(Collectors.toList()), targetCounters, targetCounters.size(), 0, memo);
  }

  static int stickersToSpellWordHelper(List<Map<Character,Integer>> stickersCounters, Map<Character,Integer> targetCounters, int n, int soFar, Map<Map<Character, Integer>, Integer> memo) {
    if (memo.containsKey(targetCounters)) {
      return memo.get(targetCounters);
    }

    if (n == 0) {
      return soFar;
    }

    int min = Integer.MAX_VALUE;
    for (Map<Character, Integer> stickerCounters : stickersCounters) {
      DiffResult result = useUp(stickerCounters, targetCounters);
      if (result.hasDiff) {
        int val = stickersToSpellWordHelper(stickersCounters, targetCounters, n - result.used, soFar + 1, memo);
        // backtrack
        addCounters(targetCounters, result.reverseOp);

        if (val != -1 && val < min) {
          min = val;
        }
      }
    }

    if (min == Integer.MAX_VALUE) {
      min = -1;
    }
    memo.put(new HashMap<>(targetCounters), min);
    return min;
  }

  static Map<Character, Integer> getCounters(String s) {
    Map<Character, Integer> counters = new HashMap<>();
    for (char c : s.toCharArray()) {
      int count = counters.getOrDefault(c, 0);
      counters.put(c, count + 1);
    }
    return counters;
  }

  static DiffResult useUp(Map<Character, Integer> stickerCounters, Map<Character, Integer> targetCounters) {
    DiffResult result = new DiffResult();
    Map<Character, Integer> reverseOp = new HashMap<>();
    result.reverseOp = reverseOp;
    for (Map.Entry<Character, Integer> entry : stickerCounters.entrySet()) {
      char c = entry.getKey();
      int needed = targetCounters.getOrDefault(c, 0);
      if (needed > 0) {
        int count = entry.getValue();
        int usedForC = Math.min(needed, count);
        targetCounters.put(c, needed - usedForC);
        if (usedForC == needed) {
          result.used++;
        }
        reverseOp.put(c, usedForC);
        result.hasDiff = true;
      }
    }


    return result;
  }

  static void addCounters(Map<Character, Integer> targetCounters, Map<Character, Integer> counters) {
    for (Map.Entry<Character, Integer> entry: counters.entrySet()) {
      char c = entry.getKey();
      targetCounters.put(c, targetCounters.get(c) + entry.getValue());
    }
  }

  static class DiffResult {
    int used = 0;
    Map<Character, Integer> reverseOp;
    boolean hasDiff;
  }

  public static void main(String args[]) {
    runSample(new String[]{"with", "example", "science"}, "thehat"); // 2
    runSample(new String[]{"notice", "possible"}, "basicbasic"); // -1
  }

  static void runSample(String[] stickers, String target) {
    System.out.printf("%s,%s = %s\n", Arrays.toString(stickers), target, stickersToSpellWord(stickers, target));
  }
}

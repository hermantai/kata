package kata;

import java.util.*;

/**
 * Cracking the coding interview 6th ed. p.XX(TODO)
 */
public class LongestIncreasingSubsequence {
  static int[] longestIncreasingSubsequence(int[] seq) {
    if (seq.length <= 1) {
      return seq;
    }
    List<List<Integer>> activeLists = new ArrayList<>();
    activeLists.add(new ArrayList<>(List.of(seq[0])));

    for (int i = 1; i < seq.length; i++) {
      int n = seq[i];
      int index = findInsertionPoint(activeLists, n);
      if (index == 0) {
        // n is smaller than all tails of the active list, so put in the
        // front by itself.
        activeLists.set(0, new ArrayList<>(List.of(n)));
      } else if (index == activeLists.size()) {
        // n is larger than all tails, so put at the tail of the longest list
        List<Integer> newList = new ArrayList<>(activeLists.get(activeLists.size() - 1));
        newList.add(n);
        activeLists.add(newList);
      } else {
        // n should be inserted at index, that means it's larger than
        // the tail at activeLists.get(index - 1). Clone it and replace
        // activeLists.get(index) because n is smaller than the tail in that
        // list. The length of each active list is increased one by one,
        // so the new active list must have the same length as
        // the old activeLists.get(index) with a smaller tail.
        List<Integer> newList = new ArrayList<>(activeLists.get(index - 1));
        newList.add(n);
        activeLists.set(index, newList);
      }
    }
    return activeLists.get(activeLists.size() - 1).stream().mapToInt(i -> i).toArray();
  }

  static int findInsertionPoint(List<List<Integer>> ls, int n) {
    int left = 0;
    int right = ls.size() - 1;
    while (left <= right) {
      int mid = left + (right - left) / 2;
      List<Integer> curLs = ls.get(mid);
      int val = curLs.get(curLs.size() - 1);
      if (n < val) {
        right = mid - 1;
        continue;
      } else if (n > val) {
        left = mid + 1;
        continue;
      } else {
        // found it!
        return mid;
      }
    }
    return left;
  }

  public static void main(String args[]) {
    runSample(new int[]{5, 1, 3, 7, 4, 5, 6, 6, 6, 8, 3});
    runSample(new int[]{5, 1});
    runSample(new int[]{1, 2, 3});
    runSample(new int[]{0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15});
  }

  static void runSample(int[] seq) {
    System.out.printf("%s = %s\n", Arrays.toString(seq), Arrays.toString(longestIncreasingSubsequence(seq)));
  }

}

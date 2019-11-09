package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * Similar to https://leetcode.com/problems/russian-doll-envelopes/, but
 * with 90 degree rotation allowed.
 *
 * <p>Given a a list of boxes, find the maximum number of boxes you can 
 * stack inside each other. You are allowed to rotate them by 90 degrees.
 */
public class Stacked2DBoxes {
  static class CompareBox implements Comparator<int[]> {
    public int compare(int[] b1, int[] b2) {
      return b1[0] * b1[1] - b2[0] * b2[1];
    }

    public boolean equals(int[] b1, int[] b2) {
      return b1[0] == b2[0] && b1[1] == b2[1];
    }
  }

  static int stacked2DBoxes(int[][] boxes) {
    Arrays.sort(boxes, new CompareBox());
    return solveLongestIncreasingSubsequence(boxes);
  }

  static int solveLongestIncreasingSubsequence(int[][] sortedBoxes) {
    int n = sortedBoxes.length;
    if (n == 0) {
      return 0;
    }

    List<List<int[]>> candidates = new ArrayList<>();
    List<int[]> first = new ArrayList<>();
    first.add(sortedBoxes[0]);
    candidates.add(first);

    for (int i = 1; i < n; i++) {
      int[] box = sortedBoxes[i];
      int insertion = getInsertion(candidates, box);

      if (insertion == candidates.size()) {
        // Be careful! We can only add the box to the list if the box can
        // really contain the box at the end of newList.
        List<int[]> chosenList = candidates.get(insertion - 1);
        if (canContains(box, chosenList.get(chosenList.size() - 1))) {
          List<int[]> newList = new ArrayList<>(chosenList);
          newList.add(box);
          candidates.add(newList);
        }
      } else if (insertion == 0) {
        candidates.get(0).set(0, box);
      } else {
        List<int[]> chosenList = candidates.get(insertion - 1);
        // Be careful! We can only add the box to the list if the box can
        // really contain the box at the end of newList.
        if (canContains(box, chosenList.get(chosenList.size() - 1))) {
          List<int[]> newList = new ArrayList<>(chosenList);
          newList.add(box);
          candidates.set(insertion, newList);
        }
      }
    }
    return candidates.get(candidates.size() - 1).size();
  }

  static int getInsertion(List<List<int[]>> boxes, int[] box) {
    int left = 0, right = boxes.size() - 1;

    while (left <= right) {
      int mid = left + (right - left) / 2;
      List<int[]> midBoxes = boxes.get(mid);
      int[] midBox = midBoxes.get(midBoxes.size() - 1);
      if (canContains(midBox, box)) {
        right = mid - 1;
      } else {
        left = mid + 1;
      }
    }
    return left;
  }

  static boolean canContains(int[] container, int[] box) {
    return (container[0] > box[0] && container[1] > box[1]) ||
      (container[0] > box[1] && container[1] > box[0]);
  }

  public static void main(String args[]) {
    // A box is {w, h}
    runSample(new int[][]{{5,4},{6,4},{6,7},{2,3}}); // ans: 3, [2,3] => [5,4] => [6,7]
    runSample(new int[][]{{3,4},{2,7},{3,2},{1,8}}); // ans: 2, [3, 4] => [3, 2]
    runSample(new int[][]{{2,3},{3,4},{1,2},{7,8}}); // ans: 4
  }

  static void runSample(int[][] boxes) {
    System.out.printf("%s = %s\n", multiArrayToString(boxes), stacked2DBoxes(boxes));
  }
}

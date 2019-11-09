package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * https://leetcode.com/problems/the-skyline-problem/
 *
 * <p>The geometric information of each building is represented by a triplet of
 * integers [Li, Ri, Hi], where Li and Ri are the x coordinates of the left and
 * right edge of the ith building, respectively, and Hi is its height. It is
 * guaranteed that 0 ≤ Li, Ri ≤ INT_MAX, 0 < Hi ≤ INT_MAX, and Ri - Li > 0. You
 * may assume all buildings are perfect rectangles grounded on an absolutely
 * flat surface at height 0.
 *
 * For instance, the dimensions of all buildings in Figure A are recorded as: [
 * [2 9 10], [3 7 15], [5 12 12], [15 20 10], [19 24 8] ] .
 *
 * The output is a list of "key points" (red dots in Figure B) in the format of
 * [ [x1,y1], [x2, y2], [x3, y3], ... ] that uniquely defines a skyline. A key
 * point is the left endpoint of a horizontal line segment. Note that the last
 * key point, where the rightmost building ends, is merely used to mark the
 * termination of the skyline, and always has zero height. Also, the ground in
 * between any two adjacent buildings should be considered part of the skyline
 * contour.
 *
 * For instance, the skyline in Figure B should be represented as:[ [2 10], [3
 * 15], [7 12], [12 0], [15 10], [20 8], [24, 0] ].
 *
 * <p>Solution: divide and conqueur. Divide buildings, merge skylines.
 */
public class Skyline {
  // index of a point for x-coordinate
  private static final int X = 0;
  // index of a point for y-coordinate
  private static final int Y = 1;

  // index of a building for the left x-coordinate
  private static final int LX = 0;
  // index of a building for the right x-coordinate
  private static final int RX = 1;
  // index of a building for the left y-coordinate
  private static final int H = 2;

  static List<int[]> skyline(int[][] buildings) {
    return skylineHelper(buildings, 0, buildings.length - 1);
  }

  static List<int[]> skylineHelper(int[][] buildings, int start, int end) {
    if (start > end) {
      return new ArrayList<>();
    }
    if (start == end) {
      int[] b = buildings[start];
      List<int[]> points = new ArrayList<>();
      points.add(new int[]{b[LX], b[H]});
      points.add(new int[]{b[RX], 0});
      return points;
    }

    int mid = start + (end - start) / 2;
    List<int[]> left = skylineHelper(buildings, 0, mid);
    List<int[]> right = skylineHelper(buildings, mid + 1, end);
    return mergeSkylines(left, right);
  }

  static List<int[]> mergeSkylines(List<int[]> left, List<int[]> right) {
    int leftP = 0, rightP = 0;
    int leftN = left.size();
    int rightN = right.size();

    List<int[]> output = new ArrayList<>();
    int leftY = 0, rightY = 0, curY = 0;
    while (leftP < leftN && rightP < rightN) {
      int x;
      int[] leftPt = left.get(leftP);
      int[] rightPt = right.get(rightP);
      if (leftPt[X] < rightPt[X]) {
        x = leftPt[X];
        leftY = leftPt[Y];
        leftP++;
      } else {
        x = rightPt[X];
        rightY = rightPt[Y];
        rightP++;
      }

      int maxY = Math.max(leftY, rightY);

      if (curY != maxY) {
        // no need to check curX because we still need to
        // update Y if Y changes even x stays the same.
        addOutput(output, x, maxY);
        curY = maxY;
      }
    }

    while (leftP < leftN) {
      int[] pt = left.get(leftP);
      addOutput(output, pt[X], pt[Y]);
      leftP++;
    }
    while (rightP < rightN) {
      int[] pt = right.get(rightP);
      addOutput(output, pt[X], pt[Y]);
      rightP++;
    }

    return output;
  }

  static void addOutput(List<int[]> output, int x, int y) {
    int n = output.size();
    if (output.isEmpty() || output.get(n - 1)[X] != x) {
      output.add(new int[]{x, y});
    } else {
      output.get(n - 1)[Y] = y;
    }
  }

  public static void main(String args[]) {
    int[][] buildings = { {2, 9, 10}, {3, 7, 15}, {5, 12, 12}, {15, 20, 10}, {19, 24, 8} };
    runSample(buildings);
    // [2, 10], [3, 15], [7, 12], [12, 0], [15, 10], [20, 8], [24, 0]
  }

  static void runSample(int[][] buildings) {
    System.out.printf("%s = %s\n", multiArrayToString(buildings), listOfArraysToString(skyline(buildings)));
  }
}

package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * https://leetcode.com/problems/trapping-rain-water/
 *
 * <p>Given n non-negative integers representing an elevation map where the
 * width of each bar is 1, compute how much water it is able to trap after
 * raining.
 */
public class TrappingWater {
  static int trappingWater(int[] heights) {
    int result = 0;

    // indices of decreasing height
    Stack<Integer> indices = new Stack<>();
    for (int i = 0; i < heights.length; i++) {
      int h = heights[i];
      while (!indices.isEmpty() && heights[indices.peek()] < h) {
        int bottomIndex = indices.pop();
        if (indices.isEmpty()) {
          // It's the case that the left side is bounded by the left most
          // space, which is empty.
          break;
        }
        int prevH = heights[indices.peek()];
        int waterHeight = Math.min(h, prevH) - heights[bottomIndex];
        result += waterHeight  * (i - indices.peek() - 1);
      }
      indices.push(i);
    }
    return result;
  }

  public static void main(String args[]) {
    runSample(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}); // 6
  }

  static void runSample(int[] heights) {
    System.out.printf("%s = %s\n", Arrays.toString(heights), trappingWater(heights));
  }
}

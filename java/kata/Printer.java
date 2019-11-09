/**
 * Provides utilities for printing.
 */
package kata;

import java.util.*;

public class Printer {

  public static String multiArrayToString(int[][] m) {
    StringBuilder sb = new StringBuilder("[");
    boolean isFirst = true;
    for (int[] row : m) {
      if (isFirst) {
        isFirst = false;
      } else {
        sb.append(", ");
      }
      sb.append(Arrays.toString(row));
    }
    sb.append(']');
    return sb.toString();
  }

  public static String listOfArraysToString(List<int[]> m) {
    StringBuilder sb = new StringBuilder("(");
    boolean isFirst = true;
    for (int[] row : m) {
      if (isFirst) {
        isFirst = false;
      } else {
        sb.append(", ");
      }
      sb.append(Arrays.toString(row));
    }
    sb.append(")");
    return sb.toString();
  }
}

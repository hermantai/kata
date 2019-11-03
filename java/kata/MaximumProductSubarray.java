package kata;

import java.util.*;

/**
 * Enhancement of https://www.geeksforgeeks.org/maximum-product-subarray/
 *
 * <p>Given an array of floating point numbers, find the maximum product of
 * a continuous subarray. Forget about infinite or NaN. Assume the array has
 * at least one element.
 */
public class MaximumProductSubarray {
  static double maximumProductSubarray(double[] ar) {
    double maxSoFar = -Double.MAX_VALUE;
    Double posMaxSoFar = null;
    Double negMaxSoFar = null;

    for (double d : ar) {
      if (d > 0) {
        posMaxSoFar = multiWithNullable(posMaxSoFar, d);
        if (negMaxSoFar != null) {
          negMaxSoFar *= d;
        }
      } else if (d == 0) {
        posMaxSoFar = null;
        negMaxSoFar = null;
        maxSoFar = Math.max(maxSoFar, 0);
        continue;
      } else {
        if (negMaxSoFar != null) {
          posMaxSoFar = negMaxSoFar * d;
          negMaxSoFar = d;
        } else {
          if (posMaxSoFar == null) {
            negMaxSoFar = d;
          } else {
            negMaxSoFar = posMaxSoFar * d;
          }
          posMaxSoFar = null;
        }
      }

      if (posMaxSoFar != null) {
        maxSoFar = Math.max(maxSoFar, posMaxSoFar);
      }
      if (posMaxSoFar != null && posMaxSoFar < 1) {
        posMaxSoFar = null;
      }
      if (negMaxSoFar != null && negMaxSoFar > -1) {
        negMaxSoFar = null;
      }
    }

    // edge case, only one negative number
    if (maxSoFar == -Double.MAX_VALUE) {
      return ar[0];
    }

    return maxSoFar;
  }

  static double multiWithNullable(Double d1, double d2) {
    if (d1 == null) {
      return d2;
    }
    return d1 * d2;
  }

  public static void main(String args[]) {
    runSample(new double[]{2, 2.3, 4, 0.1, 3, 4});  // 22.08
    runSample(new double[]{2.3, 4, 0.1, 3, 4});  // 12
    runSample(new double[]{-2, 2.3, 4, 0.1, 3, 4}); // 12
    runSample(new double[]{3, 4, -3, 5, 0.1});  // 12
    runSample(new double[]{3, 4, -3, 5, 0.1, -0.9}); // 16.2
    runSample(new double[]{0, -1, 0, 0.5, 0.3}); // 0.5
    runSample(new double[]{-3, 0, -4}); // 0
    runSample(new double[]{-3, -2, -4}); // 8
    runSample(new double[]{-3}); // -3
    runSample(new double[]{3, 0.1, 0.2, 4}); // 4
  }

  static void runSample(double[] ar) {
    System.out.printf("%s = %s\n", Arrays.toString(ar), maximumProductSubarray(ar));
  }
}

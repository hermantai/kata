package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * Finds all pairs of subarrays in the given array such that all elements
 * in the subarrays have elements greater than or equal to X. Each subarray
 * has at least one element.
 */
public class PairsOfSubarraysGreaterThanX {
  static List<int[][]> pairsOfSubarraysGreaterThanX(int[] ar, int x) {
    List<int[][]> output = new ArrayList<>();
    int n = ar.length;

    // subarrays[i] = j. The maximum subarray starting at i and end at j.
    // The first element 0'th is not needed because that cannot create
    // a pair of subarrays.
    int[] subarrays = new int[n];
    int i = n - 1;
    int tail = n - 1;
    while (i > 0) {
      // ar[i+1...tail] has all elements >= x, process i
      if (ar[i] >= x) {
        subarrays[i] = tail;
      } else {
        tail = i - 1;
      }
      i--;
    }

    // Get the first subarray starting from 0'th
    int head = 0;
    i = 0;

    // ar[head...i- 1] is processed, process ar[i]
    while (i < n - 1) {
      if (ar[i] >= x) {
        for (int j = head; j <= i; j++) {
          for (int ar2Head = i + 1; ar2Head < n; ar2Head++) {
            int ar2Tail = subarrays[ar2Head];
            for (int ar2End = ar2Head; ar2End <= ar2Tail; ar2End++) {
              int[][] pair = new int[2][];
              pair[0] = Arrays.copyOfRange(ar, j, i + 1);
              pair[1] = Arrays.copyOfRange(ar, ar2Head, ar2End + 1);
              output.add(pair);
            }
          }
        }
      } else {
        head = i + 1;
      }
      i++;
    }
    
    return output;
  }

  static int pairsOfSubarraysGreaterThanXCount(int[] ar, int x) {
    int n = ar.length;
    int result = 0;

    // secPairCounts[i] = the number of subarrays within ar[i...n]
    // The first element 0'th is not needed because that cannot create
    // a pair of subarrays.
    int[] subarrays = new int[n];
    int i = n - 1;
    int tail = n - 1;
    while (i > 0) {
      // ar[i+1...tail] has all elements >= x, process i
      if (ar[i] >= x) {
        subarrays[i] = tail - i + 1;
      } else {
        tail = i - 1;
      }
      if (i < n - 1) {
        subarrays[i] += subarrays[i+1];
      }
      i--;
    }

    // Get the first subarray starting from 0'th
    int head = 0;
    i = 0;

    // ar[head...i- 1] is processed, process ar[i]
    while (i < n - 1) {
      if (ar[i] >= x) {
        result += (i - head + 1) * subarrays[i + 1];
      } else {
        head = i + 1;
      }
      i++;
    }
    
    return result;
  }

  public static void main(String args[]) {
    runSample(new int[]{1, 3, 2, 5, 6, 7, 8, 1, 3, 1, 5, 7, 8}, 2);
    runSample(new int[]{1, 2, 3, 4}, 2);
  }

  static void runSample(int[] ar, int x) {
    System.out.printf("%s,%s = %s:\n", Arrays.toString(ar), x, pairsOfSubarraysGreaterThanXCount(ar, x));
    List<int[][]> pairs = pairsOfSubarraysGreaterThanX(ar, x);
    int i = 1;
    for (int[][] pair : pairs) {
      System.out.printf("%02d: %s, %s\n", i, Arrays.toString(pair[0]), Arrays.toString(pair[1]));
      i++;
    }
  }
}

package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * Creates a permutation iterator that given an integer N, returns all
 * permutations of all integers from 1 to N.
 *
 * <p>The key is to find an ordering of permutation. The ordering can
 * be defined as iterating from the smallest number to the largest number.
 * Given N=5, the smallest permutation is 1,2,3,4,5 because the smallest
 * numbers are at the most significant digits and the largest numbers
 * are at the most significant digits. With similar reasoning, we can find
 * that the last permutation would be 5,4,3,2,1.
 *
 * <p>The iteration is then done by finding the smallest number that is
 * larger than the previous number (a number is defined by the permutation
 * of the digits). Let's say the previous permutation is 2,3,5,4,1. The
 * next permutation would be 2,4,1,3,5. How do we find that? First of all,
 * from right to left, we know that if the sequence is non-decreasing,
 * the sequence does not have the "next" permutation because that would be
 * the maximum "number" for that "suffix" (a number with the least significant
 * digits). When we get to the first decreasing number, in our example, "3",
 * we should find a number from that suffix that is barely greater the
 * first decreasing number, then we can swap them (notice we cannot touch
 * numbers on the left of "3" because they are more significance and cannot
 * produce a permutation that is barely larger than the current permutation.).
 *
 * <p>After the swapping, the example becomes 2,4,5,3,1. We can then reverse
 * the "suffix" so that it's the smallest for that number of digits. That
 * will be the permutation that is barely larger than the current permutation,
 * 2,4,1,3,5.
 */
public class Permutator implements Iterable<int[]>,Iterator<int[]> {

  private int[] next;
  private boolean done = false;

  Permutator(int n) {
    next = new int[n];
    for (int i = 0; i < n; i++) {
      next[i] = i + 1;
    }
  }

  public Iterator<int[]> iterator() {
    return new Permutator(next.length);
  }

  public void remove() {
    throw new UnsupportedOperationException ();
  }

  public boolean hasNext() {
    return !done;
  }

  public int[] next() {
    if (done) {
      throw new NoSuchElementException();
    }
    int last = next.length - 1;
    int[] ret = next;
    // prepare the next permutation
    next = Arrays.copyOf(ret, ret.length);

    // find the first decreasing number from right to left
    int i = last - 1;
    while (i >= 0 && next[i] >= next[i + 1]) i--;

    if (i < 0) {
      done = true;
      return ret;
    }

    // next[i] is the first decreasing number from right to left.
    // find the number from the non-decreasing that is barely greater than
    // next[i].
    int j = last;
    while (next[j] <= next[i]) j--;

    swap(next, i, j);

    // reverse the "suffix"
    int left = i + 1;
    int right = last;
    while (left < right) {
      swap(next, left, right);
      left++;
      right--;
    }

    return ret;
  }

  private void swap(int[] ar, int i, int j) {
    int tmp = ar[i];
    ar[i] = ar[j];
    ar[j] = tmp;
  }

  public static void main(String args[]) {
    runSample(5);
  }

  static void runSample(int n) {
    System.out.println("Sample for n = " + n);
    Permutator p = new Permutator(n);
    int i = 1;
    for (int[] ar : p) {
      System.out.printf("%02d: %s\n", i, Arrays.toString(ar));
      i++;
    }
  }
}

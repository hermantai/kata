package kata;

import java.util.*;

/**
 * https://leetcode.com/problems/frog-jump/
 *
 * <p>A frog is crossing a river. The river is divided into x units and at each
 * unit there may or may not exist a stone. The frog can jump on a stone, but
 * it must not jump into the water.
 *
 * <p>Given a list of stones' positions (in units) in sorted ascending order,
 * determine if the frog is able to cross the river by landing on the last stone.
 * Initially, the frog is on the first stone and assume the first jump must be 1
 * unit.

 * <p>If the frog's last jump was k units, then its next jump must be either k -
 * 1, k, or k + 1 units. Note that the frog can only jump in the forward
 * direction.
 */
public class FrogJump {

  static boolean frogJump(int[] stones) {
    if (stones.length <= 1) {
      return true;
    }
    if (stones[0] != 0) {
      return false;
    }

    // key: position
    // value: sets of jump to arrive this position
    Map<Integer, Set<Integer>> jumps = new HashMap<>();
    for (int i = 0; i < stones.length; i++) {
      jumps.put(stones[i], new HashSet<>());
    }

    // the 0'th position is achieved by taking 0 steps
    jumps.get(0).add(0);
    for (int i = 0; i < stones.length; i++) {
      int stone = stones[i];
      for (int lastJump : jumps.get(stone)) {
        for (int jump = lastJump - 1; jump <= lastJump + 1; jump++) {
          int next = jump + stone;
          if (jump > 0 && jumps.containsKey(next)) {
            jumps.get(next).add(jump);
          }
        }
      }
    }

    return !jumps.get(stones[stones.length - 1]).isEmpty();
  }

  public static void main(String args[]) {
    runSample(new int[]{0,1,3,5,6,8,12,17}); // true
    runSample(new int[]{0,1,2,3,4,8,9,11}); // false
  }

  static void runSample(int[] nums) {
    System.out.printf("%s = %s\n", Arrays.toString(nums), frogJump(nums));
  }
}

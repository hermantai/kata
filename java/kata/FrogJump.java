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
  /*
    // Leetcode solution
		public class Solution {
				public boolean canCross(int[] stones) {
						HashMap<Integer, Set<Integer>> map = new HashMap<>();
						for (int i = 0; i < stones.length; i++) {
								map.put(stones[i], new HashSet<Integer>());
						}
						map.get(0).add(0);
						for (int i = 0; i < stones.length; i++) {
								for (int k : map.get(stones[i])) {
										for (int step = k - 1; step <= k + 1; step++) {
												if (step > 0 && map.containsKey(stones[i] + step)) {
														map.get(stones[i] + step).add(step);
												}
										}
								}
						}
						return map.get(stones[stones.length - 1]).size() > 0;
				}
		}
	*/
  static boolean frogJump(int[] stones) {
    if (stones.length <= 1) {
      return true;
    }
    if (stones[0] != 0) {
      return false;
    }
    List<Set<Integer>> jumps = new ArrayList<>();
    for (int i = 0; i < stones.length; i++) {
      jumps.add(new HashSet<>());
    }
    jumps.get(0).add(0);
    jumps.get(1).add(1);
    for (int i = 1; i < stones.length - 1; i++) {
      int curStone = stones[i];

      for (int j : jumps.get(i)) {
        List<Integer> curJumps = new ArrayList<>();
        curJumps.add(j - 1);
        curJumps.add(j);
        curJumps.add(j + 1);
        
        for (int curJump : curJumps) {
          if (curJump <= 0) {
            continue;
          }
          int nextLandedStone = curStone + curJump;
          for (int k = i+1; k < stones.length; k++) {
            int nextStone = stones[k];
            if (nextStone < nextLandedStone) {
              continue;
            } else if (nextStone > nextLandedStone) {
              // stones are in increasing positiions, so we found no landed stone
              break;
            } else {
              // nextStone == nextLandedStone
              Set<Integer> nextJumps = jumps.get(k);
              nextJumps.add(curJump);
              break;
            }
          }
        }
      }
    }

    return !jumps.get(jumps.size() - 1).isEmpty();
  }

  public static void main(String args[]) {
    runSample(new int[]{0,1,3,5,6,8,12,17}); // true
    runSample(new int[]{0,1,2,3,4,8,9,11}); // false
  }

  static void runSample(int[] nums) {
    System.out.printf("%s = %s\n", Arrays.toString(nums), frogJump(nums));
  }
}

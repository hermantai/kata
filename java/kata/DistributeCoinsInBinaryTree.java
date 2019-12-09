package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * https://leetcode.com/problems/distribute-coins-in-binary-tree/
 *
 * Given the root of a binary tree with N nodes, each node in the tree has node.val coins, and there are N coins total.

 * In one move, we may choose two adjacent nodes and move one coin from one
 * node to another.  (The move may be from parent to child, or from child to
 * parent.)

 * Return the number of moves required to make every node have exactly one
 * coin.

 * Intuition

 * If the leaf of a tree has 0 coins (an excess of -1 from what it needs), then
 * we should push a coin from its parent onto the leaf. If it has say, 4 coins (an
 * excess of 3), then we should push 3 coins off the leaf. In total, the number of
 * moves from that leaf to or from its parent is excess = Math.abs(num_coins - 1).
 * Afterwards, we never have to consider this leaf again in the rest of our
 * calculation.

 * Algorithm

 * We can use the above fact to build our answer. Let dfs(node) be the excess
 * number of coins in the subtree at or below this node: namely, the number of
 * coins in the subtree, minus the number of nodes in the subtree. Then, the
 * number of moves we make from this node to and from its children is
 * abs(dfs(node.left)) + abs(dfs(node.right)). After, we have an excess of
 * node.val + dfs(node.left) + dfs(node.right) - 1 coins at this node.
 */
public class DistributeCoinsInBinaryTree {
  static class Node {
    int val;
    Node left;
    Node right;

    Node(int val) {
      this.val = val;
    }
  }

  int ans;

  int distributeCoinsInBinaryTree(Node node) {
    ans = 0;
    dfs(node);
    return ans;
  }

  // return excess number of coins, can be negative
  int dfs(Node node) {
    if (node == null) {
      return 0;
    }
    int L = dfs(node.left);
    int R = dfs(node.right);
    ans += Math.abs(L) + Math.abs(R);
    return L + R + node.val - 1;
  }

  public static void main(String args[]) {
    Node node = new Node(3);
    node.left = new Node(0);
    node.right = new Node(0);
    runSample(node, 2);

    node = new Node(0);
    node.left = new Node(3);
    node.right = new Node(0);
    runSample(node, 3);
  }

  static void runSample(Node node, int ans) {
    System.out.printf(
      "%s(%s)\n",
      new DistributeCoinsInBinaryTree().distributeCoinsInBinaryTree(node),
      ans);
  }
}

package kata;

import java.util.*;

/**
 * BST implementation.
 */
public class BSTImpl {

  static class Node {
    int data;
    Node left;
    Node right;

    Node(int data) {
      this.data = data;
    }
  }

  /**
   * https://leetcode.com/problems/validate-binary-search-tree
   */
  static boolean isValidBst(Node root) {
    return isValidBstHelper(root, null, null);
  }

  private static boolean isValidBstHelper(Node node, Integer lower, Integer upper) {
    int val = node.data;
    if (lower != null && lower >= val) return false;
    if (upper != null && upper <= val) return false;
    if (node.left != null && !isValidBstHelper(node.left, lower, val)) return false;
    if (node.right != null && !isValidBstHelper(node.right, val, upper)) return false;
    return true;
  }

  static boolean isValidBstIter(Node root) {
    Stack<Node> stack = new Stack<>();
    Stack<Integer> lowers = new Stack<>(),
      uppers = new Stack<>();

    Integer lower = null, upper = null;
    lowers.push(lower);
    uppers.push(upper);
    stack.push(root);
    while (!stack.isEmpty()) {
      Node n = stack.pop();
      lower = lowers.pop();
      upper = uppers.pop();
      if (n == null) {
        continue;
      }
      int val = n.data;
      if (lower != null && val <= lower) return false;
      if (upper != null && val >= upper) return false;
      lowers.push(val);
      uppers.push(upper);
      stack.push(n.right);

      lowers.push(lower);
      uppers.push(val);
      stack.push(n.left);
    }
    return true;
  }

  static Node insert(Node node, int data) {
    if (node == null) {
      return new Node(data);
    }

    if (data < node.data) {
      node.left = insert(node.left, data);
    } else {
      node.right = insert(node.right, data);
    }
    return node;
  }

  static boolean search(Node node, int data) {
    if (node == null) {
      return false;
    }
    if (data < node.data) {
      return search(node.left, data);
    } else if (data > node.data) {
      return search(node.right, data);
    }
    return true;
  }

  static Node remove(Node node, int data) {
    if (node == null) {
      return null;
    }
    if (data < node.data) {
      node.left = remove(node.left, data);
    } else if (data > node.data) {
      node.right = remove(node.right, data);

    } else {
      // we found the value in this node, so remove this node
      if (node.left == null && node.right == null) {
        return null;
      }
      if (node.left == null) {
        return node.right;
      }
      if (node.right == null) {
        return node.left;
      }

      // replace this node's value with the smallest of the right, remove
      // that node, then return the current node
      node.right = replaceSmallestAndRemove(node.right, node);
    }
    return node;
  }

  static Node replaceSmallestAndRemove(Node node, Node originalNode) {
    if (node.left != null) {
      node.left = replaceSmallestAndRemove(node.left, originalNode);
      return node;
    } else {
      originalNode.data = node.data;
      return node.right;
    }
  }

  static int bSTImpl(String str) {
    return 0;
  }

  public static void main(String args[]) {
    Node tree = runSample(new int[]{9, 6, 3, 8, 18, 15, 16});
    runSearch(tree, 9);
    runSearch(tree, 6);
    runSearch(tree, 3);
    runSearch(tree, 8);
    runSearch(tree, 18);
    runSearch(tree, 15);
    runSearch(tree, 16);
    runSearch(tree, 2);
    runSearch(tree, 19);
    runSearch(tree, 14);

    tree = runRemove(tree, 15);
    tree = runRemove(tree, 6);

    Node tree2 = runSample(new int[]{9, 6, 3, 8, 18, 15, 16});
    System.out.println("is valid BST? " + isValidBst(tree2));
    System.out.println("is valid BST iterative? " + isValidBstIter(tree2));
  }

  static Node runSample(int[] nums) {
    Node node = null;
    for (int n : nums) {
      node = insert(node, n);
    }
    printTree(node);
    printTreeInOrder(node);
    return node;
  }

  static void runSearch(Node tree, int data) {
    System.out.printf("%s? %s\n", data, search(tree, data));
  }

  static Node runRemove(Node tree, int data) {
    tree = remove(tree, data);
    System.out.println("remove " + data);
    printTree(tree);
    printTreeInOrder(tree);
    return tree;
  }

  static void printTree(Node head) {
    List<Node> ls = new ArrayList<>();
    List<Node> ls2 = new ArrayList<>();

    if (head == null) {
      return;
    }
    ls.add(head);

    while (!ls.isEmpty()) {
      for (Node n : ls) {
        System.out.print(n.data + " ");
        if (n.left != null) {
          ls2.add(n.left);
        }
        if (n.right != null) {
          ls2.add(n.right);
        }
      }
      System.out.println();

      ls.clear();
      List<Node> tmp = ls;
      ls = ls2;
      ls2 = tmp;
    }
  }

  static void printTreeInOrder(Node node) {
    printTreeInOrderHelper(node);
    System.out.println();
  }

  static void printTreeInOrderHelper(Node node) {
    if (node == null) {
      return;
    }

    printTreeInOrderHelper(node.left);
    System.out.print(node.data + " ");
    printTreeInOrderHelper(node.right);
  }
}

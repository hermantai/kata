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

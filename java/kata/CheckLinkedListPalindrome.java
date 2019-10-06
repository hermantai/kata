package kata;

import java.util.*;

/**
 * Cracking the coding interview 6th ed. p.106
 */
public class CheckLinkedListPalindrome {
  static boolean checkLinkedListPalindrome(LinkedList ls) {
    Stack<Integer> saved = new Stack<>();

    Node slow = ls.head;
    Node fast = slow;

    if (slow == null) {
      return true;
    }

    while (fast != null && fast.next != null) {
      saved.push(slow.data);

      slow = slow.next;
      fast = fast.next.next;
    }

    if (fast != null) {
      slow = slow.next;
    }

    while (slow != null) {
      if (slow.data != saved.pop()) {
        return false;
      }
      slow = slow.next;
    }

    return true;
  }

  public static void main(String args[]) {
    runSample(new int[]{7, 1, 6, 5, 6, 1, 7});
    runSample(new int[]{3, 5, 6, 6, 5, 3});
    runSample(new int[]{3, 5, 6, 7, 5, 3});
    runSample(new int[]{2, 5, 6, 8, 6, 5, 3});
    runSample(new int[]{2});
    runSample(new int[]{2, 2});
    runSample(new int[]{2, 5});
  }

  static void runSample(int[] a) {
    System.out.printf("%s = %s\n", Arrays.toString(a), checkLinkedListPalindrome(createList(a)));
  }

  static class LinkedList {
    Node head;
  }

  static class Node {
    int data;
    Node next;
  }

  static void printList(LinkedList ls) {
    Node n = ls.head;
    while (n != null) {
      System.out.print(n.data + " ");
      n = n.next;
    }
    System.out.println();
  }

  static LinkedList createList(int[] nums) {
    Node head = new Node();
    Node cur = head;
    for (int num : nums) {
      Node n = createNode(num);
      cur.next = n;
      cur = n;
    }

    LinkedList ls = new LinkedList();
    ls.head = head.next;
    return ls;
  }

  static Node createNode(int n) {
    Node node = new Node();
    node.data = n;
    return node;
  }
}

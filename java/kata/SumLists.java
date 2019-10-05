package kata;

import java.util.*;

/**
 * Cracking the coding interview 6th ed. p.XX(TODO)
 */
public class SumLists {
  static LinkedList sumLists(LinkedList a, LinkedList b) {

    Node n1 = a.head;
    Node n2 = b.head;

    int carried = 0;
    Node ans = new Node();
    Node current = ans;
    while (n1 != null || n2 != null) {
      if (n1 == null) {
        current.next =  createNode(n2.data);
        current = current.next;
        n2 = n2.next;
        continue;
      }
      if (n2 == null) {
        current.next =  createNode(n1.data);
        current = current.next;
        n1 = n1.next;
        continue;
      }

      int sum = n1.data + n2.data + carried;
      carried = sum / 10;
      current.next = createNode(sum % 10);
      current = current.next;
      n2 = n2.next;
      n1 = n1.next;
    }

    if (carried > 0) {
      current.next = createNode(carried);
      current = current.next;
    }

    LinkedList ls = new LinkedList();
    ls.head = ans.next;

    return ls;
  }

  public static void main(String args[]) {
    runSample(new int[]{7, 1, 6}, new int[]{5, 9 , 2});
    runSample(new int[]{3, 5, 6}, new int[]{8, 7 , 5});
  }

  static void runSample(int[] a, int[] b) {
    LinkedList ls = sumLists(createList(a), createList(b));
    System.out.printf("%s + %s = ", Arrays.toString(a), Arrays.toString(b));
    printList(ls);
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

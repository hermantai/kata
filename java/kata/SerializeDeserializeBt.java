/**
 * Serialize and deserialize binary trees.
 */
package kata;

import java.util.*;

public class SerializeDeserializeBt {

	public static void main(String[] args) {

		Node n20 = new Node(20, null, null);
		Node n40 = new Node(40, null, null);
		Node n60 = new Node(60, null, null);
		Node n50 = new Node(50, n40, n60);
		Node root = new Node(30, n20, n50);
		
		System.out.format("Original tree:%n");
		printTreeInLevelOrder(root);
		
		ArrayList<String> buf = serialize(root);
		System.out.format("Serialized buf = %s%n", buf);
		
		Node r = deserialize(buf);
		System.out.format("Deserialized tree:%n");
		printTreeInLevelOrder(r);
	}

	static ArrayList<String> serialize(Node n) {
		ArrayList<String> res = new ArrayList<String>();
		serializeUtil(n, res);
		return res;
	}
	
	static void serializeUtil(Node n, ArrayList<String> buf) {
		if (n == null) {
			buf.add("N");
			return;
		}
		buf.add(String.valueOf(n.val));
		serializeUtil(n.left, buf);
		serializeUtil(n.right, buf);
	}

	static Node deserialize(ArrayList<String> buf) {
		Iterator<String> it = buf.iterator();
		return deserializeUtil(it);
	}
	
	static Node deserializeUtil(Iterator<String> it) {
		String s = it.next();
		if (s.equals("N"))
			return null;
		int val = Integer.parseInt(s);
		Node l = deserializeUtil(it);
		Node r = deserializeUtil(it);
		return new Node(val, l, r);

	}
	static void printTreeInLevelOrder(Node root) {
		LinkedList<Node> current = new LinkedList<Node>();
		LinkedList<Node> next = new LinkedList<Node>();
		
		current.add(root);
		while (!current.isEmpty()) {
			System.out.println(current);
			
			while (!current.isEmpty()) {
				Node n = current.remove();
				if (n.left != null)
					next.add(n.left);
				if (n.right != null)
					next.add(n.right);
			}
			
			LinkedList<Node> temp = current;
			current = next;
			next = temp;
		}
		
		
	}

  static class Node {

    int val;
    Node left;
    Node right;
    
    Node(int v, Node l, Node r) {
      val = v;
      left = l;
      right = r;
    }
    
    public String toString() {
      return String.valueOf(val);
    }
  }
}

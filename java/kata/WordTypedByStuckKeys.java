package kata;

import java.util.*;

/**
 * Given a dictionary and a word, check if the word is typed by a stucked
 * keyboard.
 *
 * For example, a word "hello" in the dictionary can be typed with "helllllo" or
 * "heeeello" but not "heeeeelo".
 */
public class WordTypedByStuckKeys {
  public static List<String> DICT = List.of(
        "hello", "world", "cat", "dog", "bird", "green", "help", "great", "greet", "pool");

  static boolean wordTypedByStuckKeys(String str, List<String> dict) {
    TrieNode trie = TrieNode.build(dict);
    return trie.isInTrieForStucked(str);
  }

  public static void main(String args[]) {
    runSample("hellp", true);
    runSample("heeelo", false);
    runSample("bbbiirrdd", true);
    runSample("greeeeat", true);
    runSample("cad", false);
    runSample("green", true);
    runSample("aaaata", false);
    runSample("poolllll", true);
  }

  static void runSample(String s, boolean expected) {
    System.out.printf("%s = %s (expected: %s)\n", s, wordTypedByStuckKeys(s, DICT), expected);
  }

  static class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isWord = false;

    static TrieNode build(List<String> strs) {
      TrieNode root = new TrieNode();

      for (String s: strs) {
        TrieNode node = root;

        for (char c: s.toCharArray()) {
          if (node.children.containsKey(c)) {
            node = node.children.get(c);
          } else {
            TrieNode newNode = new TrieNode();
            node.children.put(c, newNode);
            node = newNode;
          }
        }
        node.isWord = true;
      }
      return root;
    }

    boolean isInTrie(String s) {
      TrieNode n = this;
      for (char c: s.toCharArray()) {
        if (!n.children.containsKey(c)) {
          return false;
        }
        n = n.children.get(c);
      }
      return n.isWord;
    }

    boolean isInTrieForStucked(String s) {
      return isInTrieForStucked(s.toCharArray(), 0);
    }

    boolean isInTrieForStucked(char[] chars, int i) {
      if (i == chars.length) {
        return isWord;
      }
      
      char c = chars[i];
      if (!children.containsKey(c)) {
        return false;
      }

      return isInTrieForStucked(chars, i + 1) || children.get(c).isInTrieForStucked(chars, i + 1);
    }
  }
}

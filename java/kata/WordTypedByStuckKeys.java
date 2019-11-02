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
        "hello", "world", "cat", "dog", "bird", "green", "help", "great", "greet", "pool", "leet", "let");

  static boolean wordTypedByStuckKeys(String str, List<String> dict) {
    TrieNode trie = TrieNode.build(dict);
    return trie.isInTrieForStucked(str);
  }

  /**
   * Stores a map of Map<Character, List<String>words> instead of a Trie
   */
  static boolean wordTypedByStuckKeysWithMap(String str, List<String> dict) {
    Map<String, List<String>> simplified = buildMap(dict);

    String simplifiedString = simplifyString(str);
    List<String> matcheds = simplified.get(simplifiedString);
    if (matcheds == null) {
      return false;
    }
    char[] chars = str.toCharArray();
    for (String matched: matcheds) { 
      if (matchedSimplified(chars, 0, matched.toCharArray(), 0)) {
        return true;
      }
    }
    return false;
  }

  static Map<String, List<String>>  buildMap(List<String> dict) {
    Map<String, List<String>> simplified = new HashMap<>();
    for (String s: dict) {
      String newS = simplifyString(s);
      List<String> values = simplified.get(newS);
      if (values == null) {
        values = new ArrayList<>();
        simplified.put(newS, values);
      }
      values.add(s);
    }
    return simplified;
  }

  static String simplifyString(String s) {
      int w = 1;
      char[] chars = s.toCharArray();
      for (int r = 1; r < chars.length; r++) {
        if (chars[r] != chars[r - 1]) {
          chars[w] = chars[r];
          w++;
        }
      }
      return new String(chars, 0, w);
  }

  static boolean matchedSimplified(char[] chars, int i, char[] tchars, int j) {
    if (i == chars.length && j == tchars.length) {
      return true;
    }
    if (i == chars.length || j == tchars.length) {
      return false;
    }

    if (chars[i] != tchars[j]) {
      return false;
    }
    return matchedSimplified(chars, i + 1, tchars, j) || matchedSimplified(
        chars, i + 1, tchars, j + 1);
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
    runSample("leeet", true);
    runSample("let", true);
  }

  static void runSample(String s, boolean expected) {
    System.out.printf("%s = %s and %s (expected: %s)\n", s, wordTypedByStuckKeys(s, DICT), wordTypedByStuckKeysWithMap(s, DICT), expected);
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

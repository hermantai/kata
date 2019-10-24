package kata;

import java.util.*;

/**
 * https://leetcode.com/problems/word-search-ii/
 * 
 * <p>Word search
 */
public class WordPuzzle {
  static class TrieNode {
    HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
    String word = null;
    public TrieNode() {}
  }

  static List<String> wordPuzzle(char[][] board, String[] words) {

    // Step 1). Construct the Trie
    TrieNode root = new TrieNode();
    for (String word : words) {
      TrieNode node = root;

      for (Character letter : word.toCharArray()) {
        if (node.children.containsKey(letter)) {
          node = node.children.get(letter);
        } else {
          TrieNode newNode = new TrieNode();
          node.children.put(letter, newNode);
          node = newNode;
        }
      }
      node.word = word;  // store words in Trie
    }

    List<String> result = new ArrayList<>();
    // Step 2). Backtracking starting for each cell in the board
    for (int row = 0; row < board.length; ++row) {
      for (int col = 0; col < board[row].length; ++col) {
        if (root.children.containsKey(board[row][col])) {
          backtracking(board, row, col, root, result);
        }
      }
    }

    return result;
  }

  static void backtracking(char[][] board, int row, int col, TrieNode parent, List<String> collect) {
    Character letter = board[row][col];
    TrieNode currNode = parent.children.get(letter);

    // check if there is any match
    if (currNode.word != null) {
      collect.add(currNode.word);
      currNode.word = null;
    }

    // mark the current letter before the EXPLORATION
    board[row][col] = '#';

    // explore neighbor cells in around-clock directions: up, right, down, left
    int[] rowOffset = {-1, 0, 1, 0};
    int[] colOffset = {0, 1, 0, -1};
    for (int i = 0; i < 4; ++i) {
      int newRow = row + rowOffset[i];
      int newCol = col + colOffset[i];
      if (newRow < 0 || newRow >= board.length || newCol < 0
          || newCol >= board[0].length) {
        continue;
      }
      if (currNode.children.containsKey(board[newRow][newCol])) {
        backtracking(board, newRow, newCol, currNode, collect);
      }
    }

    // End of EXPLORATION, restore the original letter in the board.
    board[row][col] = letter;

    // Optimization: incrementally remove the leaf nodes
    if (currNode.children.isEmpty()) {
      parent.children.remove(letter);
    }
  }

  public static void main(String args[]) {
		char[][] board = {
			{'o','a','a','n'},
			{'e','t','a','e'},
			{'i','h','k','r'},
			{'i','f','l','v'},
		};
		String[] words = {"oath","pea","eat","rain"};

    runSample(board, words);

		board = new char[][]{
			{'o','a','a','n'},
			{'e','t','a','e'},
			{'t','h','d','o'},
			{'i','f','l','g'},
		};
		words = new String[]{"oath","dig","dog","dogs"};
    runSample(board, words);
  }

  static void runSample(char[][] board, String[] words) {
		System.out.println("Input: a board and " + Arrays.toString(words));
    System.out.println("Output: " + wordPuzzle(board, words));
  }
}

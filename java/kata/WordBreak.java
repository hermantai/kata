package kata;

import java.util.*;

/**
 * https://leetcode.com/problems/word-break-ii/
 */
public class WordBreak {
  static Set<String> DICT = new HashSet<>();

  static {
    DICT.add("cat");
    DICT.add("cats");
    DICT.add("and");
    DICT.add("sand");
    DICT.add("dog");
    DICT.add("apple");
    DICT.add("pen");
    DICT.add("applepen");
    DICT.add("pine");
    DICT.add("pineapple");
  }
  static List<String> wordBreak(String str, Set<String> wordDict) {
    // allLinesOfLines[i] = all sentences can be made for first i chars of str
    List<List<List<String>>> allLinesOfLines = new ArrayList<>();

    for (int len = 0; len <= str.length(); len++) {
      List<List<String>> linesOfLines = new ArrayList<>();

      for (int j = 0; j < len; j++) {
        String w = str.substring(j, len);
        if (wordDict.contains(w)) {
          List<List<String>> prevLinesOfLines = allLinesOfLines.get(j);
          if (prevLinesOfLines.isEmpty()) {
            linesOfLines.add(Arrays.asList(w));
          } else {
            for (List<String> lines : prevLinesOfLines) {
              List<String> newLine = new ArrayList<>(lines);
              newLine.add(w);
              linesOfLines.add(newLine);
            }
          }
        }
      }
      allLinesOfLines.add(linesOfLines);
    }

    List<String> ret = new ArrayList<>();
    for (List<String> line : allLinesOfLines.get(str.length())) {
      ret.add(join(line));
    }
    return ret;
  }

  static String join(List<String> words) {
    StringBuilder sb = new StringBuilder();

    boolean isFirst = true;
    for (String word : words) {
      if (isFirst) {
        isFirst = false;
      } else {
        sb.append(" ");
      }
      sb.append(word);
    }

    return sb.toString();
  }

  public static void main(String args[]) {
    // catsanddog = [cat sand dog, cats and dog]
    runSample("catsanddog", DICT);
    // pineapplepenapple = [pine applepen apple, pineapple pen apple, pine apple pen apple]
    runSample("pineapplepenapple", DICT);
  }

  static void runSample(String s, Set<String> dict) {
    System.out.printf("%s = %s\n", s, wordBreak(s, dict));
  }
}

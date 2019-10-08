package kata;

import java.util.*;

/**
 * https://leetcode.com/problems/letter-combinations-of-a-phone-number/solution/
 */
public class LetterCombinationsOfPhoneNumber {

  static Map<String, String> phoneChars = new HashMap<>();
  
  static {
    phoneChars.put("2", "abc");
    phoneChars.put("3", "def");
    phoneChars.put("4", "ghi");
    phoneChars.put("5", "jkl");
    phoneChars.put("6", "mno");
    phoneChars.put("7", "pqrs");
    phoneChars.put("8", "tuv");
    phoneChars.put("9", "wxyz");
  };

  static List<String> letterCombinationsOfPhoneNumber(String str) {
		List<String> ret = new ArrayList<>();
    
    helper(str, 0, new StringBuilder(), ret);

    return ret;
  }

  static void helper(String str, int i, StringBuilder sb, List<String> collector) {
    if (str.length() == i) {
      collector.add(sb.toString());
      return;
    }

    for (char c : phoneChars.get(str.substring(i, i+1)).toCharArray()) {
      sb.append(c);
      helper(str, i + 1, sb, collector);
      sb.deleteCharAt(i);
    }
  }

  public static void main(String args[]) {
    runSample("23");
    runSample("659");
  }

  static void runSample(String s) {
    System.out.printf("%s = %s\n", s, letterCombinationsOfPhoneNumber(s));
  }
}

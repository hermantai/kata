package kata;

import java.util.*;

/**
 * https://leetcode.com/problems/letter-combinations-of-a-phone-number/solution/
 */
public class LetterCombinationsOfPhoneNumber {

  static Map<String, String> phone = new HashMap<String, String>() {{
    put("2", "abc");
    put("3", "def");
    put("4", "ghi");
    put("5", "jkl");
    put("6", "mno");
    put("7", "pqrs");
    put("8", "tuv");
    put("9", "wxyz");
  }};

  static List<String> letterCombinationsOfPhoneNumber(String str) {
		List<String> ret = new ArrayList<>();
    // TODO
    return ret;
  }

  public static void main(String args[]) {
    runSample("23");
    runSample("659");
  }

  static void runSample(String s) {
    System.out.printf("%s = %s\n", s, letterCombinationsOfPhoneNumber(s));
  }
}

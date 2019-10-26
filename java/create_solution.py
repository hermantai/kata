import os
import sys

templ = """package kata;

import java.util.*;

/**
 * Cracking the coding interview 6th ed. p.XX(TODO)
 */
public class %(classname)s {
  static int %(methodname)s(String str) {
    return 0;
  }

  public static void main(String args[]) {
    runSample("abcabcdd");
  }

  static void runSample(String s) {
    System.out.printf("%%s = %%s\\n", s, %(methodname)s(s));
  }
}
"""


def main():
    if len(sys.argv) != 2:
        print("Usage: create_solution.py ClassName")
        return

    classname = sys.argv[1]
    methodname = classname[0].lower() + classname[1:]
    filepath = os.path.join(os.path.join(os.path.dirname(__file__), "kata"),
                            classname + ".java")
    if os.path.exists(filepath):
        print("%s already exists" % filepath)
        return

    with open(filepath, "w") as f:
        f.write(templ % locals())
    print("Written to %s" % filepath)


if __name__ == '__main__':
    main()

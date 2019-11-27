package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * Cracking the coding interview 6th ed. p.XX(TODO)
 */
public class FractionToDecimal {
	static String fractionToDecimal(int numerator, int denominator) {
		if (numerator == 0) {
				return "0";
		}
		StringBuilder fraction = new StringBuilder();
		// If either one is negative (not both)
		if (numerator < 0 ^ denominator < 0) {
				fraction.append("-");
		}
		// Convert to Long or else abs(-2147483648) overflows
		long dividend = Math.abs(Long.valueOf(numerator));
		long divisor = Math.abs(Long.valueOf(denominator));
		fraction.append(String.valueOf(dividend / divisor));
		long remainder = dividend % divisor;
		if (remainder == 0) {
				return fraction.toString();
		}
		fraction.append(".");
		Map<Long, Integer> map = new HashMap<>();
		while (remainder != 0) {
				if (map.containsKey(remainder)) {
						fraction.insert(map.get(remainder), "(");
						fraction.append(")");
						break;
				}
				map.put(remainder, fraction.length());
				remainder *= 10;
				fraction.append(String.valueOf(remainder / divisor));
				remainder %= divisor;
		}
		return fraction.toString();
	}

  public static void main(String args[]) {
    runSample(0, 1);
    runSample(20, 4);
    runSample(1, 2);
    runSample(-1, 4);
    runSample(-1, -4);
    runSample(2, 3);
    runSample(111, 99);
  }

  static void runSample(int n, int d) {
    System.out.printf("%s/%s = %s\n", n, d, fractionToDecimal(n, d));
  }
}

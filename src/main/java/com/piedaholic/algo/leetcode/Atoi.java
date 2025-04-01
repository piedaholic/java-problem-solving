package com.piedaholic.algo.leetcode;

public class Atoi {

  /**
   * Implement the myAtoi(string s) function, which converts a string to a 32-bit signed integer.
   *
   * <p>The algorithm for myAtoi(string s) is as follows:
   * <li>Whitespace: Ignore any leading whitespace (" ").
   * <li>Signedness: Determine the sign by checking if the next character is '-' or '+', assuming
   *     positivity if neither present.
   * <li>Conversion: Read the integer by skipping leading zeros until a non-digit character is
   *     encountered or the end of the string is reached. If no digits were read, then the result is
   *     0.
   * <li>Rounding: If the integer is out of the 32-bit signed integer range [-2^31, 2^31 - 1], then
   *     round the integer to remain in the range. Specifically, integers less than -2^31 should be
   *     rounded to -2^31, and integers greater than 2^31 - 1 should be rounded to 2^31 - 1.
   *
   *     <p>Return the integer as the final result.
   */
  public int myAtoi(String s) {
    boolean found = false;
    boolean trailing = false;

    int multiplier = 1, base = 0;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (found && !isDigit(c)) {
        break;
      } else if (c == ' ') {
      } else if (c == '-' || c == '+' || isDigit(c)) {
        if (!found) found = true;

        if (c == '-') {
          multiplier = -1;
        }

        if ((!trailing && c == '0') || c == '-' || c == '+') {
          continue;
        }

        if (!trailing) trailing = true;

        if (base > Integer.MAX_VALUE / 10 || (base == Integer.MAX_VALUE / 10 && c - '0' > 7)) {
          if (multiplier == 1) return Integer.MAX_VALUE;
          else return Integer.MIN_VALUE;
        }
        base = 10 * base + (c - '0');
      } else {
        break;
      }
    }
    return multiplier * base;
  }

  private boolean isDigit(char c) {
    return c > 47 && c < 58;
  }
}

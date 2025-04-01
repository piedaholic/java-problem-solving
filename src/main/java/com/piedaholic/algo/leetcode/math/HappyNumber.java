package com.piedaholic.algo.leetcode.math;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Write an algorithm to determine if a number n is happy.
 * <p>A happy number is a number defined by the following process:
 * <ul>Starting with any positive integer, replace the number by the sum of the squares of its digits.</ul>
 * <ul>Repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1.</ul>
 * <ul>Those numbers for which this process ends in 1 are happy.</ul></p>
 * Return true if n is a happy number, and false if not.
 */
public class HappyNumber {
    private final int iterations;
    private final int computeUntil;

    private final Set<Integer> temp = new HashSet<>();
    private final HashMap<Integer, Boolean> memoization = new HashMap<>();

    public HappyNumber(int iterations, int computeUntil) {
        this.iterations = iterations;
        this.computeUntil = computeUntil;
        init();
    }

    private void init() {
        memoization.put(1, true);
        for (int i = 2; i <= computeUntil; i++) {
            boolean isHappy = isHappy(i);
            temp.forEach(each -> memoization.put(each, isHappy));
            temp.clear();
        }
    }

    public boolean isHappy(int n) {
        if (memoization.containsKey(n)) {
            return memoization.get(n);
        }
        int ctr = iterations;
        while (ctr > 0) {
            int sum = 0;
            while (n > 0) {
                int digit = n % 10;
                sum += digit * digit;
                n = n / 10;
            }
            if (sum == 1) {
                return true;
            } else if (memoization.containsKey(sum)) {
                return memoization.get(sum);
            } else if (temp.contains(sum)) {
                return false;
            }
            else {
                temp.add(sum);
                n = sum;
            }
            ctr--;
        }
        return false;
    }
}

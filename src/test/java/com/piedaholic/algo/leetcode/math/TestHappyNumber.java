package com.piedaholic.algo.leetcode.math;

import org.junit.Before;
import org.junit.Test;

public class TestHappyNumber {
    HappyNumber happyNumber;

    @Before
    public void init() {
        happyNumber = new HappyNumber(20, 5);
    }
    @Test
    public void test() {
        assert happyNumber.isHappy(19);
        assert happyNumber.isHappy(7);
        assert !happyNumber.isHappy(2);
        assert !happyNumber.isHappy(4);
    }
}

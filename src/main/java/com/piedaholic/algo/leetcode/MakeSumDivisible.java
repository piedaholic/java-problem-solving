package com.piedaholic.algo.leetcode;

import java.util.HashMap;

/**
 * We want to reduce the number of subarray checks while still solving the problem correctly.
 * Usually, when we have a problem that involves the summation of a subarray, we resort to prefix sums.
 * This lowers the time complexity of computing subarray summations to O(1), as sum(i, j) = sum(0, j) - sum(0, i-1).
 * We need to remove a subarray such that the sum of the remaining elements is divisible by p.
 * This indicates that the remainder of the sum of the elements, after removing the subarray, must be zero when divided by p.
 * We aim to use this information to find subarrays quickly.
 * Instead of trying all subarrays, we keep track of the prefix sum as we iterate through the array.
 * For each index, we compute the current prefix sum modulo p.
 * The remainder of the total sum modulo p gives us a "target" remainder we want to eliminate.
 * This is where modular arithmetic becomes useful: if, at some point in our prefix sum, we find that removing a certain portion of the array will leave a sum divisible by p, we have our solution.
 * To speed this up, we use a hash map to store the earliest occurrence of each remainder (prefix sum modulo p).
 * By doing so, when we encounter the same remainder later on, we know that the subarray between these two occurrences can be removed to make the sum divisible by p.
 * This allows us to find the smallest subarray length in linear time, drastically improving the efficiency of the algorithm.
 */
public class MakeSumDivisible {

    public static void main(String[] args) {
        int[] nums = {3, 1, 4, 2};
        int p = 6;
        int count = minSubArray(nums, p);
    }

    public static int minSubArray(int[] nums, int p) {
        int n = nums.length;
        int totalSum = 0;

        // Step 1: Calculate total sum and target remainder
        for (int num : nums) {
            totalSum = (totalSum + num) % p;
        }

        int target = totalSum % p;
        if (target == 0) {
            return 0; // The array is already divisible by p
        }

        // Step 2: Use a hash map to track prefix sum mod p
        HashMap<Integer, Integer> modMap = new HashMap<>();
        modMap.put(0, -1); // To handle the case where the whole prefix is the answer
        int currentSum = 0;
        int minLen = n;

        // Step 3: Iterate over the array
        for (int i = 0; i < n; ++i) {
            currentSum = (currentSum + nums[i]) % p;

            // Calculate what we need to remove
            int needed = (currentSum - target + p) % p;

            // If we have seen the needed remainder, we can consider this subarray
            if (modMap.containsKey(needed)) {
                minLen = Math.min(minLen, i - modMap.get(needed));
            }

            // Store the current remainder and index
            modMap.put(currentSum, i);
        }

        // Step 4: Return result
        return minLen == n ? -1 : minLen;
    }
}

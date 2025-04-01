package com.piedaholic.algo.window;

public class DynamicSlidingWindow implements SlidingWindow {

    public static void main(String[] args) {
        int[] numbers = {4, 4, 2};
        int p = 7;
        int count = minSubArray(numbers, p);
    }

    public static  int minSubArray(int[] numbers, int p) {
        int sum = 0;
        for (int num : numbers) {
            sum = sum + num;
        }
        int rem = sum % p;
        int ctr = rem;
        int lastCount = -1;
        while (ctr < sum) {
            int count = findMin(numbers, rem);
            if ((lastCount < 0 && count > 0) || (count > 0 && count < lastCount)) {
                lastCount = count;
            }
            ctr += p;
        }
        return lastCount;
    }

    private static int findMin(int[] numbers, int num) {
        int left = 0;
        int right = 0;
        int sum = 0;
        int count = -1;
        while (left < numbers.length) {
            if (right < numbers.length && sum < num) {
                sum += numbers[right];
                right++;
            } else if (sum >= num) {
                int temp = right - left;
                if ((count < 0 && temp > 0) || (count > 0 && count > temp)) {
                    count = temp;
                }
                sum -= numbers[left];
                left++;
            } else {
                break;
            }
        }
        return count;
    }
}
